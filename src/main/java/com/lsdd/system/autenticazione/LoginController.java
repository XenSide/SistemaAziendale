package com.lsdd.system.autenticazione;


import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    @Setter
    private Label confirmationText;
    @FXML
    private MFXTextField email;
    @FXML
    private MFXPasswordField password;

    @FXML
    private ImageView logo;

    @FXML
    private Button close;

    public LoginController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
    }

    @FXML
    public void onEmailKeyPressed(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            password.requestFocus();
        }
    }

    @FXML
    public void onPasswordKeyPressed(KeyEvent event) {
        //Stage stage = (Stage) close.getScene().getWindow(); // get stage from button instead of Event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AuthManager authManager = new AuthManager(stage);
        if (!password.getText().isEmpty()) {
            if (event.getCode().toString().equals("CONTROL")) { //DEBUG HASH
                authManager.createHash(password.getText());
            } else if (event.getCode().toString().equals("ENTER")) {
                email.requestFocus();
                authManager.confirmLogin(email.getText(), password.getText());
                //confirmationText.setText("Password usata ".concat(password.getText()));
            }
        }
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        email.requestFocus();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AuthManager authManager = new AuthManager(stage);
        authManager.confirmLogin(email.getText(), password.getText());
        //confirmationText.setText("Password usata ".concat(password.getText()));
    }

    @FXML
    public void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AuthManager authManager = new AuthManager(stage);
        authManager.deleteStage(stage);
    }
}