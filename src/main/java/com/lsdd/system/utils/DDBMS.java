package com.lsdd.system.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class DDBMS {
    @Getter(lazy = true)
    private static final DDBMS azienda = new DDBMS("azienda", 30002, "giorgio", "giorgiosimone");

    @Getter(lazy = true)
    private static final DDBMS farmacia = new DDBMS("farmacia", 30003, "giorgio", "giorgiosimone");

    private final String host = "129.152.17.152";
    private final String database;
    private final String username;
    private final String password;
    private final int port;
    private final boolean ssl = false;
    private final HikariConfig hikariConfig = new HikariConfig();
    private final ExecutorService executor;
    private HikariDataSource hikariDataSource;
    private Connection connection;

    private DDBMS(String database, int port, String username, String password) {
        this.database = database;
        this.port = port;
        this.username = username;
        this.password = password;
        hikariConfig.setConnectionTimeout(5000);
        this.executor = Executors.newFixedThreadPool(2);
        this.setup();
    }

    private Connection getJdbcUrl() throws SQLException {
        hikariConfig.setJdbcUrl("jdbc:mysql://" +
                this.host +
                ":" +
                this.port +
                "/" +
                this.database +
                "?allowPublicKeyRetrieval=true&useSSL=" +
                this.ssl);
        hikariConfig.setUsername(this.username);
        hikariConfig.setPassword(this.password);

        hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource.getConnection();
    }

    public void closePool() {
        if (hikariDataSource != null && !hikariDataSource.isClosed()) {
            hikariDataSource.close();
        }
        executor.shutdown();
    }

    public synchronized Connection getConnection() throws SQLException {
        if (hikariDataSource == null) {
            throw new SQLException("Hikari is null");
        }
        Connection connection;
        try {
            connection = this.hikariDataSource.getConnection();
            return connection;
        } catch (SQLTransientConnectionException e) {
            AtomicReference<Stage> stage = new AtomicReference<>();
            Platform.runLater(() -> {
                stage.set(new Stage());
                new RestoreConnectionC(stage.get());
            });
            while (true) {
                try {
                    Connection connection1 = this.hikariDataSource.getConnection();
                    if (connection1 != null)
                        Platform.runLater(() -> stage.get().close());
                    return connection1;
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void setup() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return;
            }
            connection = this.getJdbcUrl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Utente> controlloCredenziali(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE EMAIL=? AND PASSWORD=?")) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (resultSet.getInt("tipo_account") == 2) {
                        String IDFarmacia = this.getFarmaciaFromUserId(resultSet.getInt("IDAccount")).join().get(0);
                        return User.createInstance(resultSet.getInt("IDAccount"), resultSet.getInt("tipo_account"), resultSet.getString("email"), resultSet.getString("nome"), resultSet.getString("cognome"), IDFarmacia);
                    }
                    return User.createInstance(resultSet.getInt("IDAccount"), resultSet.getInt("tipo_account"), resultSet.getString("email"), resultSet.getString("nome"), resultSet.getString("cognome"));
                } else return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    /**
     * Metodo per ottenere le informazioni di una farmacia
     *
     * @param userId
     * @return farmaciainfo: list(0)=ID, list(1)=nome, list(2)=cap, list(3)=indirizzo
     */

    public CompletableFuture<List<String>> getFarmaciaFromUserId(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT f.IDFarmacia, f.nome, f.cap, f.indirizzo FROM farmacia f,account a, farmacia_account fa WHERE a.IDAccount=fa.IDAccount AND fa.IDFarmacia=f.IDFarmacia AND a.IDAccount=?")) {
                preparedStatement.setInt(1, userId);
                List<String> farmaciaInfo = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String iDFarmacia = resultSet.getString("IDFarmacia");
                    String nome = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");

                    farmaciaInfo.add(iDFarmacia);
                    farmaciaInfo.add(nome);
                    farmaciaInfo.add(cap);
                    farmaciaInfo.add(indirizzo);
                }
                return farmaciaInfo;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void salvaStato(int userId) {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement updateStatement = connection.prepareStatement("UDPATE account SET ultimo_accesso = CURRENT_TIMESTAMP WHERE IDAccount = ?")) {
                updateStatement.setInt(1, userId);
                updateStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    //id int, nome str, lotto str, dabanco bool, quantità int, costo float, principio string, data produzione e scadenza date
    public void caricoProdotto(Prodotto p) {
        boolean azienda = this == DDBMS.getAzienda(); //controlla da chi viene chiamato il metodo
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO prodotto (IDProdotto, nome, principio_attivo, daBanco, costo) VALUES (?,?,?,?,?)");
                 PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO produzione (IDProdotto, quantità, periodicità_produzione_giorni) VALUES (?,?,30)");
                 PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO lotto (IDLotto, IDProdotto, quantità, data_produzione, data_scadenza) VALUES (?,?,?,?,?)")) {
                preparedStatement.setInt(1, p.getID());
                preparedStatement.setString(2, p.getNome());
                preparedStatement.setString(3, p.getPrincipioAttivo());
                preparedStatement.setBoolean(4, p.getDaBanco());
                preparedStatement.setDouble(5, p.getCosto());
                preparedStatement1.setInt(1, p.getID());
                preparedStatement1.setInt(2, p.getQta() * 100);
                preparedStatement2.setString(1, p.getLotto());
                preparedStatement2.setString(2, p.getID());
                preparedStatement2.setInt(3, p.getQta());
                preparedStatement2.setString(4, p.getDataProduzione());
                preparedStatement2.setString(5, p.getDataScadenza());
                preparedStatement.executeUpdate();
                if (azienda) preparedStatement1.executeUpdate(); //esegue solo se il metodo viene chiamato da azienda
                preparedStatement2.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void modificaProduzioneProdotto(int iD, int qta, int tempo) {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE produzione SET quantità=?, periodicità_produzione_giorni=? WHERE IDProdotto=?")) {
                preparedStatement.setInt(1, qta);
                preparedStatement.setInt(2, tempo);
                preparedStatement.setInt(3, iD);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Prodotto>> getProduzione() { //claudio help
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT pz.IDProdotto, p.nome, p.principio_attivo, p.daBanco, pz.quantità, pz.periodicità_produzione_giorni FROM prodotto p, produzione pz WHERE pz.IDProdotto=p.IDProdotto")) {

                List<Prodotto> produzioneList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                Prodotto p = new Prodotto();
                while (resultSet.next()) {
                    p.setID(resultSet.getInt("IDProdotto"));
                    p.setNome(resultSet.getString("nome"));
                    p.setPrincipioAttivo(resultSet.getString("principio_attivo"));
                    p.setDaBanco(resultSet.getBoolean("daBanco"));
                    p.setquantita(resultSet.getInt("quantità"));
                    p.setPeriodicita(resultSet.getInt("periodicità_produzione_giorni"));
                    prodottoList.add(p);
                }
                return listaProdotti;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Prodotto>> getProdotti() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT l.IDProdotto, l.IDLotto, p.nome, p.principio_attivo, p.daBanco, l.quantità, p.costo, l.data_produzione, l.data_scadenza FROM prodotto p, lotto l WHERE p.IDProdotto=l.IDProdotto")) {

                List<Prodotto> prodottoList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int iD = resultSet.getInt("IDProdotto");
                    String lotto = resultSet.getString("lotto");
                    String nome = resultSet.getString("nome");
                    String principioAttivo = resultSet.getString("principio_attivo");
                    boolean daBanco = resultSet.getBoolean("daBanco");
                    int unita = resultSet.getInt("quantità");
                    double costo = resultSet.getDouble("costo");
                    String produzione = resultSet.getString("data_produzione");
                    String scadenza = resultSet.getString("data_scadenza");
                    prodottoList.add(new Prodotto(iD, lotto, nome, principioAttivo, daBanco, unita, costo, produzione, scadenza));
                }
                return listaProdotti;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void rimuoviProdottiScaduti() {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM lotto WHERE data_scadenza<?")) {
                preparedStatement.setDate(1, new Date(System.currentTimeMillis() - 1209600000)); //rimuove i farmaci con meno di 2 settimane di scadenza
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<Notifica> getNotifiche() {
        CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM notifica WHERE data_creazione_notifica=?")) {
                preparedStatement.setDate(1, new Date(System.currentTimeMillis())); //prende le notifiche del giorno
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Notifica> notificheList = new ArrayList<>();
                while (resultSet.next()) {
                    int iDNotifica = resultSet.getInt("IDNotifica");
                    int iDOrdine = resultSet.getInt("IDOrdine");
                    String data_creazione_notifica = resultSet.getString("data_creazione_notifica")
                    notificheList.add(new Notifica(iDNotifica, iDOrdine, data_creazione_notifica));
                }
                return notificheList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Ordine>> getListaOrdiniRicevuti() { //TODO: ordini periodiciv
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.*, f.nome, f.cap, f.indirizzo, c.data_consegna, op.IDProdotto, p.nome, op.quantità, op.lotto FROM ordine o, farmacia f, consegna c, prodotto p, ordine_prodotto op " +
                         "WHERE o.IDFarmacia=f.IDFarmacia AND o.IDOrdine=c.IDOrdine AND o.IDOrdine=op.IDOrdine AND op.IDProdotto=p.IDProdotto ORDER BY IDOrdine")) {
                List<Ordine> ordiniList = new ArrayList<>();
                List<Prodotto> prodottiOrdineList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                int idLastOrder = -1;
                while (resultSet.next()) {
                    int iDOrdine = resultSet.getInt("IDOrdine");

                    if (iDOrdine != idLastOrder && idLastOrder != -1) {
                        ordiniList.add(new Ordine(idLastOrder, data_creazione, stato, iDFarmacia, nomeFarmacia, cap, indirizzo, dataConsegna, prodottiOrdineList, 0));
                        prodottiOrdineList.clear();
                    }
                    idLastOrder = iDOrdine;
                    String data_creazione = resultSet.getString("data_creazione");
                    int stato = resultSet.getInt("stato_ordine");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");
                    Date dataConsegna = resultSet.getDate("data_consegna");

                    int iDProdotto = resultSet.getInt("IDProdotto");
                    String nomeFarmacia = resultSet.getString("nome");
                    int quantità = resultSet.getInt("quantità");
                    String lotto = resultSet.getString("lotto");

                    prodottiOrdineList.add(new Prodotto(iDProdotto, lotto, nome, null, null, quantità, null, null, null));
                    //prodottoList.add(new Prodotto(iD, lotto, nome, principioAttivo, daBanco, unita, costo, produzione, scadenza));
                }
                return ordiniList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Ordine>> getListaOrdiniPeriodiciRicevuti() { //TODO: ordini periodiciv
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.*, f.nome, f.cap, f.indirizzo, c.data_consegna, op.IDProdotto, p.nome, op.quantità, op.lotto FROM ordine_periodico o, farmacia f, consegna c, prodotto p, ordine_prodotto op " +
                         "WHERE o.IDFarmacia=f.IDFarmacia AND o.IDOrdine=c.IDOrdine AND o.IDOrdine=op.IDOrdine AND op.IDProdotto=p.IDProdotto ORDER BY IDOrdine")) {
                List<Ordine> ordiniList = new ArrayList<>();
                List<Prodotto> prodottiOrdineList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                int idLastOrder = -1;
                while (resultSet.next()) {
                    int iDOrdine = resultSet.getInt("IDOrdine");

                    if (iDOrdine != idLastOrder && idLastOrder != -1) {
                        ordiniList.add(new Ordine(idLastOrder, data_creazione, stato, iDFarmacia, nomeFarmacia, cap, indirizzo, dataConsegna, prodottiOrdineList, periodicita));
                        prodottiOrdineList.clear();
                    }
                    idLastOrder = iDOrdine;
                    String data_creazione = resultSet.getString("data_creazione");
                    int periodicita = resultSet.getInt("periodicità_ordine_giorni");
                    int stato = resultSet.getInt("stato_ordine");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");
                    Date dataConsegna = resultSet.getDate("data_consegna");

                    int iDProdotto = resultSet.getInt("IDProdotto");
                    String nomeFarmacia = resultSet.getString("nome");
                    int quantità = resultSet.getInt("quantità");
                    String lotto = resultSet.getString("lotto");

                    prodottiOrdineList.add(new Prodotto(iDProdotto, lotto, nome, null, null, quantità, null, null, null));
                    //prodottoList.add(new Prodotto(iD, lotto, nome, principioAttivo, daBanco, unita, costo, produzione, scadenza));
                }
                return ordiniList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Ordine>> getListaOrdiniEffettuati(int iDFarmacia) { //TODO: ordini periodici
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.*, f.nome, f.cap, f.indirizzo, c.data_consegna, op.IDProdotto, p.nome, op.quantità, op.lotto FROM ordine o, farmacia f, consegna c, prodotto p, ordine_prodotto op " +
                         "WHERE o.IDFarmacia=? AND o.IDOrdine=c.IDOrdine AND o.IDOrdine=op.IDOrdine AND op.IDProdotto=p.IDProdotto ORDER BY IDOrdine")) {
                preparedStatement.setInt(1, iDFarmacia); //prende gli ordini effettuati dalla farmacia che richiama il metodo
                List<Ordine> ordiniList = new ArrayList<>();
                List<Prodotto> prodottiOrdineList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                int idLastOrder = -1;
                while (resultSet.next()) {
                    int iDOrdine = resultSet.getInt("IDOrdine");

                    if (iDOrdine != idLastOrder && idLastOrder != -1) {
                        ordiniList.add(new Ordine(idLastOrder, data_creazione, stato, iDFarmacia, nomeFarmacia, cap, indirizzo, dataConsegna, prodottiOrdineList, 0));
                        prodottiOrdineList.clear();
                    }
                    idLastOrder = iDOrdine;
                    String data_creazione = resultSet.getString("data_creazione");
                    int stato = resultSet.getInt("stato_ordine");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");
                    Date dataConsegna = resultSet.getDate("data_consegna");

                    int iDProdotto = resultSet.getInt("IDProdotto");
                    String nomeFarmacia = resultSet.getString("nome");
                    int quantità = resultSet.getInt("quantità");
                    String lotto = resultSet.getString("lotto");

                    prodottiOrdineList.add(new Prodotto(iDProdotto, lotto, nome, null, null, quantità, null, null, null));
                }
                return ordiniList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Ordine>> getListaOrdiniPeriodiciEffettuati(int iDFarmacia) { //TODO: ordini periodici
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.*, f.nome, f.cap, f.indirizzo, c.data_consegna, op.IDProdotto, p.nome, op.quantità, op.lotto FROM ordine_periodico o, farmacia f, consegna c, prodotto p, ordine_prodotto op " +
                         "WHERE o.IDFarmacia=? AND o.IDOrdine=c.IDOrdine AND o.IDOrdine=op.IDOrdine AND op.IDProdotto=p.IDProdotto ORDER BY IDOrdine")) {
                preparedStatement.setInt(1, iDFarmacia); //prende gli ordini effettuati dalla farmacia che richiama il metodo
                List<Ordine> ordiniList = new ArrayList<>();
                List<Prodotto> prodottiOrdineList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                int idLastOrder = -1;
                while (resultSet.next()) {
                    int iDOrdine = resultSet.getInt("IDOrdine");

                    if (iDOrdine != idLastOrder && idLastOrder != -1) {
                        ordiniList.add(new Ordine(idLastOrder, data_creazione, stato, iDFarmacia, nomeFarmacia, cap, indirizzo, dataConsegna, prodottiOrdineList, periodicita));
                        prodottiOrdineList.clear();
                    }
                    idLastOrder = iDOrdine;
                    String data_creazione = resultSet.getString("data_creazione");
                    int periodicita = resultSet.getInt("periodicità_ordine_giorni");
                    int stato = resultSet.getInt("stato_ordine");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");
                    Date dataConsegna = resultSet.getDate("data_consegna");

                    int iDProdotto = resultSet.getInt("IDProdotto");
                    String nomeFarmacia = resultSet.getString("nome");
                    int quantità = resultSet.getInt("quantità");
                    String lotto = resultSet.getString("lotto");

                    prodottiOrdineList.add(new Prodotto(iDProdotto, lotto, nome, null, null, quantità, null, null, null));
                }
                return ordiniList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Ordine>> getListaOrdiniInAttesa() { //TODO: ordini periodici
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.*, f.nome, f.cap, f.indirizzo, c.data_consegna, op.IDProdotto, p.nome, op.quantità, op.lotto FROM ordine o, farmacia f, consegna c, prodotto p, ordine_prodotto op " +
                         "WHERE o.IDFarmacia=f.IDFarmacia AND o.IDOrdine=c.IDOrdine AND o.IDOrdine=op.IDOrdine AND op.IDProdotto=p.IDProdotto AND o.stato_ordine=1 ORDER BY IDOrdine")) {
                List<Ordine> ordiniList = new ArrayList<>();
                List<Prodotto> prodottiOrdineList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                int idLastOrder = -1;
                while (resultSet.next()) {
                    int iDOrdine = resultSet.getInt("IDOrdine");

                    if (iDOrdine != idLastOrder && idLastOrder != -1) {
                        ordiniList.add(new Ordine(idLastOrder, data_creazione, stato, iDFarmacia, nomeFarmacia, cap, indirizzo, dataConsegna, prodottiOrdineList, 0));
                        prodottiOrdineList.clear();
                    }
                    idLastOrder = iDOrdine;
                    String data_creazione = resultSet.getString("data_creazione");
                    int stato = resultSet.getInt("stato_ordine");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");
                    Date dataConsegna = resultSet.getDate("data_consegna");

                    int iDProdotto = resultSet.getInt("IDProdotto");
                    String nomeFarmacia = resultSet.getString("nome");
                    int quantità = resultSet.getInt("quantità");
                    String lotto = resultSet.getString("lotto");

                    prodottiOrdineList.add(new Prodotto(iDProdotto, lotto, nome, null, null, quantità, null, null, null));
                }
                return ordiniList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void confermaOrdine(int iDOrdine, boolean periodico) {
        String tabella = (periodico) ? "ordine_periodico" : "ordine";
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("UDPATE ? SET stato_ordine = 1 WHERE IDOrdine = ?");
                 PreparedStatement preparedStatement1 = connection.prepareStatement("UDPATE consegna SET stato_consegna = 2 WHERE stato_consegna = -1 AND IDOrdine = ?")) {
                preparedStatement.setString(1, tabella);
                preparedStatement.setInt(2, iDOrdine);
                preparedStatement1.setInt(1, iDOrdine);
                preparedStatement.executeUpdate();
                preparedStatement1.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Ordine>> getListaOrdiniPeriodiciInAttesa() { //TODO: lista consegne, where id corriere = id corriere and stato_consegna=2 OR stato consegna= 1, when idcorriere wiew the list stato_consegna goes from 2 to 1
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.*, f.nome, f.cap, f.indirizzo, c.data_consegna, op.IDProdotto, p.nome, op.quantità, op.lotto FROM ordine_periodico o, farmacia f, consegna c, prodotto p, ordine_prodotto op " +
                         "WHERE o.IDFarmacia=f.IDFarmacia AND o.IDOrdine=c.IDOrdine AND o.IDOrdine=op.IDOrdine AND op.IDProdotto=p.IDProdotto AND o.stato_ordine=1 ORDER BY IDOrdine")) {
                List<Ordine> ordiniList = new ArrayList<>();
                List<Prodotto> prodottiOrdineList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                int idLastOrder = -1;
                while (resultSet.next()) {
                    int iDOrdine = resultSet.getInt("IDOrdine");

                    if (iDOrdine != idLastOrder && idLastOrder != -1) {
                        ordiniList.add(new Ordine(idLastOrder, data_creazione, stato, iDFarmacia, nomeFarmacia, cap, indirizzo, dataConsegna, prodottiOrdineList, periodicita);
                        prodottiOrdineList.clear();
                    }
                    idLastOrder = iDOrdine;
                    String data_creazione = resultSet.getString("data_creazione");
                    int periodicita = resultSet.getInt("periodicità_ordine_giorni");
                    int stato = resultSet.getInt("stato_ordine");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");
                    Date dataConsegna = resultSet.getDate("data_consegna");

                    int iDProdotto = resultSet.getInt("IDProdotto");
                    String nomeFarmacia = resultSet.getString("nome");
                    int quantità = resultSet.getInt("quantità");
                    String lotto = resultSet.getString("lotto");

                    prodottiOrdineList.add(new Prodotto(iDProdotto, lotto, nome, null, null, quantità, null, null, null));
                }
                return ordiniList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Ordine>> nuovaRichiestaProdotti(Richiesta richiesta) { //TODO: bozzacrearichiesta
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT o.*, f.nome, f.cap, f.indirizzo, c.data_consegna, op.IDProdotto, p.nome, op.quantità, op.lotto FROM ordine o, farmacia f, consegna c, prodotto p, ordine_prodotto op " +
                         "WHERE o.IDFarmacia=f.IDFarmacia AND o.IDOrdine=c.IDOrdine AND o.IDOrdine=op.IDOrdine AND op.IDProdotto=p.IDProdotto AND o.stato_ordine=1 ORDER BY IDOrdine")) {
                List<Ordine> ordiniList = new LinkedList<>();
                List<Prodotto> prodottiOrdineList = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                int idLastOrder = -1;
                while (resultSet.next()) {
                    int iDOrdine = resultSet.getInt("IDOrdine");

                    if (iDOrdine != idLastOrder && idLastOrder != -1) {
                        ordiniList.add(new Ordine(idLastOrder, data_creazione, stato, iDFarmacia, nomeFarmacia, cap, indirizzo, dataConsegna, prodottiOrdineList, 0));
                        prodottiOrdineList.clear();
                    }
                    idLastOrder = iDOrdine;
                    String data_creazione = resultSet.getString("data_creazione");
                    int stato = resultSet.getInt("stato_ordine");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");
                    Date dataConsegna = resultSet.getDate("data_consegna");

                    int iDProdotto = resultSet.getInt("IDProdotto");
                    String nomeFarmacia = resultSet.getString("nome");
                    int quantità = resultSet.getInt("quantità");
                    String lotto = resultSet.getString("lotto");

                    prodottiOrdineList.add(new Prodotto(iDProdotto, lotto, nome, null, null, quantità, null, null, null));
                }
                return ordiniList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public CompletableFuture<List<Consegna>> getListaConsegne(int idcorriere) { //TODO: lista consegne, where id corriere = id corriere and stato_consegna=2 OR stato consegna= 1, when idcorriere wiew the list stato_consegna goes from 2 to 1
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.IDConsegna, c.data_consegna, f.IDFarmacia, f.nome, f.cap, f.indirizzo FROM ordine o, ordine_periodico op, farmacia f, consegna c " +
                         "WHERE (o.IDFarmacia=f.IDFarmacia OR op.IDFarmacia=f.IDFarmacia) AND (o.IDOrdine=c.IDOrdine OR op.IDOrdine=c.IDOrdine) AND (o.stato_consegna=1 OR o.stato_consegna=2) AND data_consegna>CURRENT_TIMESTAMP AND IDCorriere=? ORDER BY data_consegna")) {
                List<Consegna> consegneList = new ArrayList<>();
                preparedStatement.setInt(1, idcorriere);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int iDConsegna = resultSet.getInt("IDConsegna");
                    Date dataConsegna = resultSet.getDate("data_consegna");
                    int iDFarmacia = resultSet.getString("IDFarmacia");
                    String nomeFarmacia = resultSet.getString("nome");
                    String cap = resultSet.getString("cap");
                    String indirizzo = resultSet.getString("indirizzo");

                    consegneList.add(new Consega(iDConsegna, dataConsegna, iDFarmacia, nomeFarmacia, cap, indirizzo));
                }
                return consegneList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void creaNotifica(int iDordine) {//creata quando il farmacista conferma la ricezione
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO notifica (IDOrdine, data_creazione_notifica) VALUES (?,CURRENT_TIMESTAMP)")) {
                preparedStatement.setInt(1, iDordine);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    //conferma ricezione= query 1 UPDATE ordine/ordinep SET stator_ordine=3 where id=idpassato; query 2 INSERT INTO lotto(IDLotto, IDProdotto, quantità, data_produzione, data_scadenza) VALUES [DENTRO IL WHILE LE STRINGHE] "(),(),()..."
    //richiesta prodotti crea record in ordine, ordine_prodotti, consegna
    //modifica ordine
    //vendita prodotti

    public void confermaRicezione(Ordine ordine, boolean periodico) { //TODO: creare una consegna diversa per ogni ordine periodico quando viene consegnata la precendete; DELETARE DAL DBMS AZIENDALE LE COSE ARRIVATE
        String tabella = (periodico) ? "ordine_periodico" : "ordine";
        CompletableFuture.runAsync(() -> {
            String query = "";
            for (int i = 0; i < ordine.getProdotti.size(); i++) {
                query += "(" + ordine.getProdotti.toArray()[i].getIDLotto();
                query += "," + ordine.getProdotti.toArray()[i].getIDProdotto();
                query += "," + ordine.getProdotti.toArray()[i].getQta();
                query += "," + ordine.getProdotti.toArray()[i].getData_produzione();
                query += "," + ordine.getProdotti.toArray()[i].getData_scadenza() + "),";
            }
            query = query.substring(0, query.length() - 1) + " ";

            try (Connection connection = getConnection();
                 Connection connection2 = farmacia.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("UDPATE ? SET stato_ordine = 3 WHERE IDOrdine = ?");
                 PreparedStatement preparedStatement1 = connection2.prepareStatement("INSERT INTO lotto(IDLotto, IDProdotto, quantità, data_produzione, data_scadenza) VALUES " + query);
                 PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO consegna (IDCorriere, IDOrdine, stato_consegna) SELECT IDCorriere, ?, 1 FROM consegna GROUP BY IDCorriere ORDER BY count(*) LIMIT 1")) {
                preparedStatement.setString(1, tabella);
                preparedStatement.setInt(2, ordine.getIDOrdine());
                preparedStatement2.setInt(1, ordine.getIDOrdine());
                //preparedStatement1.setInt(1, iDOrdine);

                preparedStatement.executeUpdate();
                preparedStatement1.executeUpdate();
                preparedStatement2.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void modificaOrdine(Ordine ordine) { //TODO: 
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < ordine.getProdotti.size(); i++) {
                try (Connection connection = getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ordine_prodotto SET quantità=? WHERE IDOrdine=? AND IDProdotto=?")) {
                    preparedStatement.setInt(1, ordine.getProdotti.toArray()[i].getQta());
                    preparedStatement.setInt(2, ordine.getIDOrdine());
                    preparedStatement.setInt(3, ordine.getProdotti.toArray()[i].getIDProdotto());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }, executor);
    }

    public void venditaProdotti(Fattura fat, int stato) { //TODO: DELETARE LE COSE VENDUTE
        CompletableFuture.runAsync(() -> {
            int iDfat;
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(IDFattura) AS id FROM fattura")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    iDfat = resultSet.getInt("id") + 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String query = "";
            for (int i = 0; i < fat.getProdotti.size(); i++) {
                query += "(" + id;
                query += "," + fat.getProdotti.toArray()[i].getIDProdotto();
                query += "," + fat.getProdotti.toArray()[i].getQta();
                query += "," + fat.getProdotti.toArray()[i].getIDLotto() + "),";
            }
            query = query.substring(0, query.length() - 1) + " ";

            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO fattura (IDFattura, data_creazione, stato_fattura) VALUES (?,CURRENT_TIMESTAMP,?)");
                 PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO fattura_prodotto (IDFattura, IDProdotto, quantità, lotto) VALUES " + query)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, stato);

                preparedStatement.executeUpdate();
                preparedStatement1.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public void richiestaProdotti(Richiesta ric, boolean periodico) { //TODO: 
        String tabella = (periodico) ? "ordine_periodico" : "ordine";
        int iDOrdine;
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(IDOrdine) AS id FROM ordine_prodotto")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    iDOrdine = resultSet.getInt("id") + 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String query = "";
            for (int i = 0; i < ric.getProdotti.size(); i++) {
                query += "(" + iDOrdine;
                query += "," + ric.getProdotti.toArray()[i].getIDProdotto();
                query += "," + ric.getProdotti.toArray()[i].getQta();
                query += "," + ric.getProdotti.toArray()[i].getIDLotto() + "),";
            }
            query = query.substring(0, query.length() - 1) + " ";
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ? (IDOrdine, data_creazione, stato_ordine, IDFarmacia) VALUES (?, CURRENT_TIMESTAMP, 1, ?)");
                 PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO ordine_prodotto (IDOrdine, IDProdotto, quantità, lotto) VALUES " + query);
                 //SELECT IDCorriere, COUNT(*) AS ripetizionitotali FROM consegna GROUP BY IDCorriere ORDER BY ripetizionitotali LIMIT 1;
                 //SELECT IDCorriere FROM consegna GROUP BY IDCorriere ORDER BY count(*) LIMIT 1;
                 //INSERT INTO consegna (IDCorriere, IDOrdine, stato_consegna) SELECT IDCorriere, ?, -1 FROM consegna GROUP BY IDCorriere ORDER BY count(*) LIMIT 1
                 PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO consegna (IDCorriere, IDOrdine, stato_consegna) SELECT IDCorriere, ?, -1 FROM consegna GROUP BY IDCorriere ORDER BY count(*) LIMIT 1")) {
                preparedStatement.setString(1, tabella);
                preparedStatement.setInt(2, iDOrdine);
                preparedStatement.setInt(3, ric.getIDFarmacia());
                preparedStatement2.setInt(1, iDOrdine);

                preparedStatement.executeUpdate();
                preparedStatement1.executeUpdate();
                preparedStatement2.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

}