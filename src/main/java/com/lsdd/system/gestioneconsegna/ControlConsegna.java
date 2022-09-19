package com.lsdd.system.gestioneconsegna;

import com.lsdd.system.utils.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        fxmlLoader.setController(new GUIInfoConsegnaController(consegna, stage, fxmlLoader));
        new GUIInfoConsegnaBoundary(stage, fxmlLoader);
    }

    public void onClickfirma(Consegna consegna){}

}
