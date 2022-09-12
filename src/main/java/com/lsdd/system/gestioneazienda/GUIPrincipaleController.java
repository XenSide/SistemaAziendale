package com.lsdd.system.gestioneazienda;


import com.lsdd.system.utils.Utente;
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
public class GUIPrincipaleController implements Initializable {
    private final Utente utente;
    private final Stage stage;

    private final FXMLLoader fxmlLoader;
    private ProdottoManager prodottoManager;
    private ControlOrdiniA controlOrdiniA;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton registraProdottiButton;

    @FXML
    private MFXButton ricercaButton;
    @FXML
    private MFXButton listaOrdineButton;
    @FXML
    private MFXButton venditaButton;
    @FXML
    private MFXButton modificaButton;

    @FXML
    private Label username;

    public void onClick(ActionEvent event) {
        if (event.getSource() == registraProdottiButton) {
            prodottoManager.onClickRegistraProdotti();
        } else if (event.getSource() == ricercaButton) {
            prodottoManager.onClickRicerca();
        } else if (event.getSource() == venditaButton) {
            controlOrdiniA.onClickVendita();
        } else if (event.getSource() == listaOrdineButton) {
            controlOrdiniA.onClickListaOrdiniRicevuti();
        } else if (event.getSource() == modificaButton) {
            controlOrdiniA.onclickModificaProduzione();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        username.setText(utente.getNome());
        this.prodottoManager = new ProdottoManager(stage, utente);
        this.controlOrdiniA = new ControlOrdiniA(stage, utente);
    }
}
