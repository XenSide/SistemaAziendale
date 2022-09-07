package com.lsdd.system.utils;


import com.lsdd.system.autenticazione.AuthManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class AlertController implements Initializable {
    private final Stage stage;
    private final AuthManager authManager;
    private final String stringaErrore;

    @Setter
    @FXML
    private Label messaggioErrore;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    public void onConfirmButtonClick(ActionEvent actionEvent) {
        authManager.deleteStage(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messaggioErrore.setText(stringaErrore);
        //FXML edit code here
    }
}
