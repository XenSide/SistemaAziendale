package com.lsdd.system.gestioneazienda;


import com.lsdd.system.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIPrincipaleAziendaBoundary extends Application {
    private final Stage stage;
    private final FXMLLoader fxmlLoader;

    public GUIPrincipaleAziendaBoundary(Stage stage, FXMLLoader fxmlLoader) {
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
        Utils.startStage(stage, scene);
    }
}
