package com.lsdd.system.utils;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.lsdd.system.utils.Utils.startStage;


public class LogoutConfirmationBoundary extends Application {
    private final Stage stage;
    private final FXMLLoader fxmlLoader;

    public LogoutConfirmationBoundary(Stage stage, FXMLLoader fxmlLoader) {
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
        scene.setFill(Color.TRANSPARENT);
        startStage(stage, scene);
    }


}
