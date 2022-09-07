package com.lsdd.system.gestioneazienda;


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


    public void onRegistraProdottiButtonClick(ActionEvent actionEvent) {
        //Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        prodottoManager.onClickRegistraProdotti();
    }

    public void onRicercaClick(ActionEvent actionEvent) {
        //Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        prodottoManager.onClickRicerca();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        this.prodottoManager = new ProdottoManager(stage);
    }
}
