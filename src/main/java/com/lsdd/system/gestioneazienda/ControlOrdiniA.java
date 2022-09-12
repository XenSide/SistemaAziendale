package com.lsdd.system.gestioneazienda;

import com.lsdd.system.utils.Ordine;
import com.lsdd.system.utils.Prodotto;
import com.lsdd.system.utils.Utente;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ControlOrdiniA {

    private final Stage stage;
    private final Utente utente;
    private FXMLLoader fxmlLoader = new FXMLLoader();

    public String getUsername() {
        return utente.getNome();
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

    public boolean annullaOrdine(Ordine ordine) {
        // TODO: 10/09/2022 query annullamento ordine 
        return false;
    }

    public boolean confermaVenditaOrdine(Ordine ordine) {
        // TODO: 10/09/2022 query vendita ordine 
        return false;
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
    }

    public boolean modificaProduzioneProdotto(Prodotto prodotto) {
        // TODO: 11/09/2022 Query per salvare la nuova produzione
        return true;
    }
}
