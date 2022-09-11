package com.lsdd.system.gestioneazienda;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.lsdd.system.utils.Utils.startStage;


public class GUIListaOrdiniRicevutiBoundary extends Application {
    private final Stage stage;
    private final FXMLLoader fxmlLoader;

    public GUIListaOrdiniRicevutiBoundary(Stage stage, FXMLLoader fxmlLoader) {
        this.stage = stage;
        this.fxmlLoader = fxmlLoader;

        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.TRANSPARENT);
        startStage(stage, scene);
    }

}
