package com.lsdd.system.gestioneconsegna;

import com.lsdd.system.utils.Consegna;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;
@RequiredArgsConstructor
public class GUIFirmaController implements Initializable {
    private final Consegna consegna;
    private final Stage stage;

    private final ControlConsegna controlConsegna;

    private final FXMLLoader fxmlLoader;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private MFXButton confirmButton;
    @FXML
    private MFXButton deleteFirmaButton;
    @FXML
    private Label titleLabel;

    @FXML
    private Label indirizzoLabel;
    @FXML
    private Label capLabel;
    @FXML
    private Label dataLabel;
    @FXML
    private Label destinatarioLabel;
    @FXML
    private Label idcLabel;

    @FXML
    private MFXTextField firmaField;

    public void onClick(ActionEvent event){
        if(event.getSource() == confirmButton) {
            stage.close();
            controlConsegna.firma(firmaField);
        }
        if (event.getSource() == deleteFirmaButton) {
            firmaField.clear();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText("FIRMA");
    }
}
