package com.lsdd.system.gestioneazienda;


import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GUIPrincipaleController implements Initializable {
    private final Stage stage;

    private final FXMLLoader fxmlLoader;

    private ProdottoManager prodottoManager;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton registraProdottiButton;
    @FXML
    private MFXButton ricercaButton;

    public void onClick(ActionEvent event){
        if(event.getSource() == registraProdottiButton){
            prodottoManager.onClickRegistraProdotti();
        } else if (event.getSource() == ricercaButton) {
            prodottoManager.onClickRicerca();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        this.prodottoManager = new ProdottoManager(stage);
    }
}
