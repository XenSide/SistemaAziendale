package com.lsdd.system.utils;


import com.lsdd.system.autenticazione.LoginBoundary;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class ConfermaOperazioneController implements Initializable {
    private final Stage stage;
    private final boolean aBoolean;
    private final Stage oldStage;
    private final String stringa;

    @Setter
    @FXML
    private Label messaggioErrore;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton confirmButton;
    @FXML
    private MFXButton cancelButton;

    public void onClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == confirmButton) {
            Utils.aBoolean=true;
            stage.close();
        } else if (actionEvent.getSource() == cancelButton) {
            Utils.aBoolean=false;
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messaggioErrore.setText(stringa);
        //FXML edit code here
    }
}
