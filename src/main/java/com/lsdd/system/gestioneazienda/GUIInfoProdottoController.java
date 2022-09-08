package com.lsdd.system.gestioneazienda;

import io.github.palexdev.materialfx.controls.MFXButton;
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
public class GUIInfoProdottoController implements Initializable {
    private final Stage stage;
    private ProdottoManager prodottoManager;

    private final FXMLLoader fxmlLoader;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton registraProdottiButton;
    @FXML
    private MFXButton confirmButton;

    @FXML
    private Label nomeLabel;
    @FXML
    private Label uidLabel;
    @FXML
    private Label lottoLabel;
    @FXML
    private Label scadenzaLabel;
    @FXML
    private Label pAttivoLabel;
    @FXML
    private Label qtaLabel;
    @FXML
    private Label costoLabel;
    @FXML
    private Label produzioneLabel;

    public void onClick(ActionEvent event){
        if(event.getSource() == confirmButton) {
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        this.prodottoManager = new ProdottoManager(stage);
    }
}
