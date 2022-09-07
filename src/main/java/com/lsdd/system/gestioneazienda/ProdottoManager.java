package com.lsdd.system.gestioneazienda;

import com.lsdd.system.utils.Ordine;
import com.lsdd.system.utils.Prodotto;
import com.lsdd.system.utils.Utils;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProdottoManager {

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private final Stage stage;
    public ProdottoManager(Stage stage) {
        this.stage = stage;
    }

    public void onClickRegistraProdotti() {
        fxmlLoader = new FXMLLoader(GUICaricamentoNuovoProdottoController.class.getResource("registrazioneProdotti.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new GUICaricamentoNuovoProdottoController(stage, this));
        new GUICaricamentoNuovoProdottoBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void onClickListaOrdine() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIListaOrdiniRicevutiController.class.getResource("listaOrdiniRicevuti.fxml"));
        // TODO: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO 
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3, "Augmento", data, data);
        List<Ordine> ordines = new ArrayList<>();
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        ordines.add(new Ordine(1,"bobbina", "viavai", prodottos, data, 1, 1));
        ordines.add(new Ordine(1,"Antonina", "viavai", prodottos, data, 1, 1));
        fxmlLoader.setController(new GUIListaOrdiniRicevutiController(stage, this, ordines));
        new GUIListaOrdiniRicevutiBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void controlloCaricoDelFarmaco(String uid, String nome, String lotto, Boolean daBanco,
                                          String quantita, String costo, String pAttivo, String dataProduzione,
                                          String dataScadenza, Stage stage) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Prodotto prodotto = null;
        try {
            prodotto = new Prodotto(Integer.parseInt(uid), nome, lotto, daBanco, Integer.parseInt(quantita), Integer.parseInt(costo), pAttivo, formatter.parse(dataProduzione), formatter.parse(dataScadenza));
            Boolean query = true; // FIXME: 07/09/2022 query per salvare
            if (query){
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
        fxmlLoader = new FXMLLoader(InfoPanelController.class.getResource("ProductinfoPanel.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new InfoPanelController(stage, fxmlLoader));
        new GUIInfoProdottoBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void creaInfoOrdine(Ordine ordine) {
        Utils.showAlert("Info Ordine");
    }

    public void cancellaOrdine(Ordine ordine) {
        Utils.showAlert("cancella ordine");
    }
}
