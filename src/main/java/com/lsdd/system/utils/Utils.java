package com.lsdd.system.utils;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Utils {

    public static void startStage(Stage stage, Scene scene) {
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
    
    public static void showAlert(String errore){
        FXMLLoader fxmlLoader = new FXMLLoader(AlertBoundary.class.getResource("Alert.fxml"));
        Stage stageDatiErrati = new Stage();
        stageDatiErrati.initStyle(StageStyle.TRANSPARENT);
        AlertController alertController = new AlertController(stageDatiErrati,errore);
        fxmlLoader.setController(alertController);
        new AlertBoundary(stageDatiErrati, fxmlLoader); //new Stage() per creare una nuova finestra
    }
    public static void showAlertInSameWindow(String errore, Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(AlertBoundary.class.getResource("Alert.fxml"));
        AlertController alertController = new AlertController(stage,errore);
        fxmlLoader.setController(alertController);
        new AlertBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public static String toDisplayCase(String s) { //"prova prova prova" becomes "Prova Prova Prova"

        final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
        // to be capitalized

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
        }
        return sb.toString();
    }
}

