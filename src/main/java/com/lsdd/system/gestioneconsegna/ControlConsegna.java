package com.lsdd.system.gestioneconsegna;

import com.lsdd.system.utils.Consegna;
import com.lsdd.system.utils.DDBMS;
import com.lsdd.system.utils.Utente;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ControlConsegna {

    private final Stage stage;
    private final Utente utente;
    private FXMLLoader fxmlLoader = new FXMLLoader();

    public String getUsername() {
        return utente.getNome();
    }

    public void onClickListaConsegne() {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(GUIListaConsegneController.class.getResource("tableView.fxml"));
        // DONE: 07/09/2022 QUERY PER PREDERE ORDINE E PRODOTTO
        DDBMS.getAzienda().getListaConsegne(utente.getId()).whenComplete((ordines,throwable)->{
            if (throwable != null)
                throwable.printStackTrace();
        }).thenAccept(ordines  -> {
            Platform.runLater(() -> {

                fxmlLoader.setController(new GUIListaConsegneController(false, stage, this, ordines));
                new GUIListaConsegneBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
            });});

    }

    public void creaInfoConsegna(Consegna consegna, boolean firma) {
        fxmlLoader = new FXMLLoader(GUIInfoConsegnaController.class.getResource("infoConsegnaPanel.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new GUIInfoConsegnaController(consegna, stage, this, fxmlLoader));
        new GUIInfoConsegnaBoundary(stage, fxmlLoader);
    }

    public void onClickfirma(Consegna consegna){
        fxmlLoader = new FXMLLoader(GUIFirmaController.class.getResource("firma.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setController(new GUIFirmaController(consegna, stage, this, fxmlLoader));
        new GUIFirmaBoundary(stage, fxmlLoader);
    }

    public void firma(MFXTextField firmaField) {
        //DDBMS.getAzienda().
    }
}
