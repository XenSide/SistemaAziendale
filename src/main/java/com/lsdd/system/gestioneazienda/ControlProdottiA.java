package com.lsdd.system.gestioneazienda;

import com.lsdd.system.utils.Ordine;
import com.lsdd.system.utils.Prodotto;
import com.lsdd.system.utils.Utente;
import com.lsdd.system.utils.Utils;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ControlProdottiA {

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private final Stage stage;
    private final Utente utente;

    public String getUsername() {
        return utente.getNome();
    }

    public void onClickRegistraProdotti() {
        fxmlLoader = new FXMLLoader(GUICaricamentoNuovoProdottoController.class.getResource("registrazioneProdotti.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new GUICaricamentoNuovoProdottoController(stage, this));
        new GUICaricamentoNuovoProdottoBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }


    public void onClickRicerca() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIRicercaController.class.getResource("tableView.fxml"));
        // TODO: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina1 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina2 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina3 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        prodottos.add(augmentina);
        prodottos.add(augmentina1);
        prodottos.add(augmentina2);
        prodottos.add(augmentina3);
        fxmlLoader.setController(new GUIRicercaController(stage, this, prodottos));
        new GUIRicercaBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void controlloCaricoDelFarmaco(String uid, String nome, String lotto, Boolean daBanco,
                                          String quantita, String costo, String pAttivo, String dataProduzione,
                                          String dataScadenza, Stage stage) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Prodotto prodotto = null;
        try {
            prodotto = new Prodotto(Integer.parseInt(uid), nome, lotto, daBanco, Integer.parseInt(quantita), Integer.parseInt(costo), pAttivo, formatter.parse(dataProduzione), formatter.parse(dataScadenza));
            Boolean query = true; // FIXME: 07/09/2022 query per salvare
            if (query) {
            Utils.showAlert("Caricamento effettuato con successo");
            stage.close();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            Utils.showAlert("I dati inseriti sono errati!");
        }

        System.out.println(prodotto);
    }

    public void creaInfoProdotto(Prodotto prodotto) {
        fxmlLoader = new FXMLLoader(GUIInfoProdottoController.class.getResource("infoProdottoPanel.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new GUIInfoProdottoController(prodotto, stage, fxmlLoader));
        new GUIInfoProdottoBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }


    public boolean annullaOrdine(Ordine ordine) {
        // TODO: 10/09/2022 query annullamento ordine
        return false;
    }

    public boolean confermaVenditaOrdine(Ordine ordine) {
        // TODO: 10/09/2022 query vendita ordine
        return false;
    }

/*
    public void onClickListaOrdiniRicevuti() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIListaOrdiniRicevutiController.class.getResource("tableView.fxml"));
        // TODO: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina1 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina2 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina3 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        List<Ordine> ordines = new ArrayList<>();
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        prodottos.add(augmentina);
        prodottos.add(augmentina1);
        prodottos.add(augmentina2);
        prodottos.add(augmentina3);
        ordines.add(new Ordine(1, "bobbina", "viavai", prodottos, data, data, 1, 1));
        ordines.add(new Ordine(1, "Antonina", "viavai", prodottos, data, data, 1, 1));
        fxmlLoader.setController(new GUIListaOrdiniRicevutiController(false, stage, this, ordines));
        new GUIListaOrdiniRicevutiBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }
    public void creaInfoOrdine(Ordine ordine, boolean vendita) {
        fxmlLoader = new FXMLLoader(GUIInfoOrdineController.class.getResource("infoOrdinePanel.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new GUIInfoOrdineController(vendita, stage, this, fxmlLoader, ordine));
        new GUIInfoOrdineBoundary(stage, fxmlLoader);
    }
    public void cancellaOrdine(Ordine ordine) {
        fxmlLoader = new FXMLLoader(GUIInfoOrdineController.class.getResource("ConfermaAnnullamentoOrdine.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new ConfermaAnnullamentoController(stage,
                "Sei sicuro di voler annullare l'ordine?", this, ordine));
        new ConfermaAnnullamentoBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }
    public void onClickVendita() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIListaOrdiniRicevutiController.class.getResource("tableView.fxml"));
        // TODO: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina1 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina2 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina3 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        List<Ordine> ordines = new ArrayList<>();
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        prodottos.add(augmentina);
        prodottos.add(augmentina1);
        prodottos.add(augmentina2);
        prodottos.add(augmentina3);
        ordines.add(new Ordine(1, "bobbina", "viavai", prodottos, data, data, 1, 1));
        ordines.add(new Ordine(1, "Antonina", "viavai", prodottos, data, data, 1, 1));
        fxmlLoader.setController(new GUIListaOrdiniRicevutiController(true, stage, this, ordines));
        new GUIListaOrdiniRicevutiBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }
    public void onclickModificaProduzione() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIModificaProduzioneBoundary.class.getResource("tableView.fxml"));
        // TODO: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina1 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina2 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        Prodotto augmentina3 = new Prodotto(123, "augmentinos", "A123", true, 3, 3, "Augmento", data, data);
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        prodottos.add(augmentina);
        prodottos.add(augmentina1);
        prodottos.add(augmentina2);
        prodottos.add(augmentina3);
        fxmlLoader.setController(new GUIModificaProduzioneController(stage, this, prodottos));
        new GUIModificaProduzioneBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }
   public void creaModificaProduzione(Prodotto prodotto) {
        fxmlLoader = new FXMLLoader(FormModificaUnitáTempoBoundary.class.getResource("modificaProduzionePanel.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new FormModificaUnitáTempoController(stage, this, prodotto));
        new GUIInfoOrdineBoundary(stage, fxmlLoader);
    }*/

    public boolean modificaProduzioneProdotto(Prodotto prodotto) {
        // TODO: 11/09/2022 Query per salvare la nuova produzione
        return true;
    }
}
