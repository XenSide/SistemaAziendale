package com.lsdd.system.gestionefarmacia;

import com.lsdd.system.gestioneazienda.GUIListaOrdiniRicevutiBoundary;
import com.lsdd.system.gestioneazienda.GUIListaOrdiniRicevutiController;
import com.lsdd.system.gestioneazienda.GUIModificaProduzioneBoundary;
import com.lsdd.system.gestioneazienda.GUIModificaProduzioneController;
import com.lsdd.system.utils.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ControlOrdiniF {

    private Richiesta richiesta;
    private final Stage stage;
    private final Utente utente;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    public String getUsername() {
        return utente.getNome();
    }

    public void onClickModificaOrdine() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIListaOrdiniEffettuatiController.class.getResource("tableView.fxml"));
        // DONE: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO

        DDBMS.getAzienda().getListaOrdiniEffettuati(utente.getUIDFarmacia()).whenComplete((ordines1,throwable)->{
            if (throwable != null)
                throwable.printStackTrace();
        }).thenAccept(ordines1  -> {
            Platform.runLater(() -> {
                DDBMS.getAzienda().getListaOrdiniPeriodiciEffettuati(utente.getUIDFarmacia()).whenComplete((ordines2,throwable)->{
                    if (throwable != null)
                        throwable.printStackTrace();
                }).thenAccept(ordines  -> {
                    Platform.runLater(() -> {
                        ordines.addAll(ordines1);
                        fxmlLoader.setController(new GUIModificaOrdineController(stage, this, ordines));
                        new GUIModificaOrdineBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
                    });});
            });});
        /*
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina1 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina2 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina3 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        List<Ordine> ordines = new ArrayList<>();
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        prodottos.add(augmentina);
        prodottos.add(augmentina1);
        prodottos.add(augmentina2);
        prodottos.add(augmentina3);
        ordines.add(new Ordine(1,1, "bobbina", "90100","viavai", prodottos, data, data, 1, 0));
        ordines.add(new Ordine(1,1, "Antonina","90100", "viavai", prodottos, data, data, 1, 1));
        fxmlLoader.setController(new GUIModificaOrdineController(stage, this, ordines));
        new GUIModificaOrdineBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
        */
    }

    public void onClickModificaProdotti(List<Prodotto> prodotto) {
        //PANNELLO GROSSO
        fxmlLoader = new FXMLLoader(FormSelezionaProdottoBoundary.class.getResource("selezionaProdotto.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new FormSelezionaProdottoController(prodotto, this, stage));
        new FormSelezionaProdottoBoundary(stage, fxmlLoader);
    }


    public void creaModificaQta(Prodotto prodotto) {
        //PANNELLO PICCOLO
        fxmlLoader = new FXMLLoader(FormSelezionaQTAController.class.getResource("FormSelezionaUnita.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new FormSelezionaQTAController(prodotto, this, stage));
        new FormSelezionaQTABoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void creaInfoOrdine(Ordine ordine, boolean vendita) {
        fxmlLoader = new FXMLLoader(GUIInfoOrdineController.class.getResource("infoOrdinePanel.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new GUIInfoOrdineController(vendita, stage, this, fxmlLoader, ordine));
        new GUIInfoOrdineBoundary(stage, fxmlLoader);
    }

    public void onClickListaOrdiniEffetuati() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIListaOrdiniEffettuatiController.class.getResource("tableView.fxml"));
        // DONE: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO
        DDBMS.getAzienda().getListaOrdiniEffettuati(utente.getUIDFarmacia()).whenComplete((ordines1,throwable)->{
            if (throwable != null)
                throwable.printStackTrace();
        }).thenAccept(ordines1  -> {
            Platform.runLater(() -> {
                DDBMS.getAzienda().getListaOrdiniPeriodiciEffettuati(utente.getUIDFarmacia()).whenComplete((ordines2,throwable)->{
                    if (throwable != null)
                        throwable.printStackTrace();
                }).thenAccept(ordines  -> {
                    Platform.runLater(() -> {
                        ordines.addAll(ordines1);
                        fxmlLoader.setController(new GUIListaOrdiniEffettuatiController(false, stage, this, ordines));
                        new GUIListaOrdiniEffettuatiBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
                    });});
            });});
        /*
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina1 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina2 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina3 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        List<Ordine> ordines = new ArrayList<>();
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        prodottos.add(augmentina);
        prodottos.add(augmentina1);
        prodottos.add(augmentina2);
        prodottos.add(augmentina3);
        ordines.add(new Ordine(1,1, "bobbina", "90100","viavai", prodottos, data, data, 1, 0));
        ordines.add(new Ordine(1,1, "Antonina","90100", "viavai", prodottos, data, data, 1, 1));
        fxmlLoader.setController(new GUIListaOrdiniEffettuatiController(false, stage, this, ordines));
        new GUIListaOrdiniEffettuatiBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra

         */
    }

    public boolean confermaVenditaOrdine(Ordine ordine) {
        // DONE: 18/09/2022 query
        try{
            DDBMS.getAzienda().confermaOrdine(ordine.getCodiceOrdine(),(ordine.getTipoOrdine()==0));
        }catch (Exception e){e.printStackTrace();return false;}
        return true;
    }

    public void onClickVendita() {//TODO: da cambiare completamente
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIListaOrdiniEffettuatiBoundary.class.getResource("tableView.fxml"));
        // TODO: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto(123, "augmentin", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina1 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina2 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        Prodotto augmentina3 = new Prodotto(123, "augmentinos", "A123", true, 3, 3.2, "Augmento", data, data);
        List<Ordine> ordines = new ArrayList<>();
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        prodottos.add(augmentina);
        prodottos.add(augmentina1);
        prodottos.add(augmentina2);
        prodottos.add(augmentina3);
        ordines.add(new Ordine(1,1, "bobbina", "90100","viavai", prodottos, data, data, 1, 0));
        ordines.add(new Ordine(1,1, "Antonina","90100", "viavai", prodottos, data, data, 1, 1));
        fxmlLoader.setController(new GUIListaOrdiniEffettuatiController(true, stage, this, ordines));
        new GUIListaOrdiniEffettuatiBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void onClickListaConfermaRicezione() {//TODO:  faretutto

    }

   /*public void richiestaProdotti(Prodotto prodotto, Stage stage, Integer qtaRichiesta) {
       Prodotto prodottoLocal = new Prodotto(prodotto);
       prodottoLocal.setQuantitá(qtaRichiesta);
       richiesta.aggiungiProdotto(prodottoLocal);
       //prodotti.remove(prodotto);
       long day = 24 * 60 * 60 * 1000;
       long twoMonthsFromNow = System.currentTimeMillis() + day * 7;
       if (prodotto.getDataScadenza().getTime() < twoMonthsFromNow) {
           Stage stage1 = new Stage();
           fxmlLoader = new FXMLLoader(ConfermaDataScadenzaController.class.getResource("ConfermaScadenza.fxml"));
           fxmlLoader.setController(new ConfermaDataScadenzaController(prodotto, stage1, richiesta, "La data di scadenza del prodotto richiesto è inferiore a due mesi, vuoi confermare comunque?", this, prodotto.getQuantitá()));
           new ConfermaDataScadenzaBoundary(stage1, fxmlLoader); //new Stage() per creare una nuova finestra
           stage.close();
       } else {
           stage.close();
           checkDateDiverse(richiesta, prodotto.getQuantitá());
           if (prodotto.getQuantitá() - richiesta.getFirst().getQuantitá() < 0)
               prodotto.setQuantitá(0);
           else
               prodotto.setQuantitá(prodotto.getQuantitá() - richiesta.getFirst().getQuantitá());
       }
   }
    public void checkDateDiverse(Richiesta richiesta, Integer qtaMagazzino) {
        System.out.println("qta richiesta date: " + richiesta.getFirst().getQuantitá());
        System.out.println("qta magazzino date: " + qtaMagazzino);
        if (richiesta.getFirst().getQuantitá() > qtaMagazzino) {
            Stage stage = new Stage();
            fxmlLoader = new FXMLLoader(ConfermaDateDiverseController.class.getResource("ConfermaDateDiverse.fxml"));
            fxmlLoader.setController(new ConfermaDateDiverseController(qtaMagazzino, richiesta, stage, "La quantitá richiesta non è al momento disponibile, come preferisci ricevere il tuo ordine?"));
            new ConfermaDateDiverseBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
        }
    }*/
}
