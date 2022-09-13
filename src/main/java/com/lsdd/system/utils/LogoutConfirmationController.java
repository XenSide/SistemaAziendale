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
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class LogoutConfirmationController implements Initializable {
    private final Stage stage;
    private final Stage oldStage;
    private final String stringaErrore;

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
            try {
                LoginBoundary.startStatic();
            } catch (Exception e) {
            }
            stage.close();
            oldStage.close();
        } else if (actionEvent.getSource() == cancelButton) {
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messaggioErrore.setText(stringaErrore);
        //FXML edit code here
    }
}
