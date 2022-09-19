package com.lsdd.system.gestioneconsegna;

import com.lsdd.system.utils.Consegna;
import com.lsdd.system.utils.Utils;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private Label nomeLabel;

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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        nomeLabel.setText(Utils.toDisplayCase(consegna.getNomeFarmacia()));
        indirizzoLabel.setText(Utils.toDisplayCase(consegna.getIndirizzo()));
        capLabel.setText(Utils.toDisplayCase(consegna.getCap()));
        dataLabel.setText(df.format(consegna.getDataConsegna()));
        destinatarioLabel.setText(Utils.toDisplayCase(consegna.getDestinatario()));
        idcLabel.setText(consegna.getIDConsegna().toString());
    }
}
