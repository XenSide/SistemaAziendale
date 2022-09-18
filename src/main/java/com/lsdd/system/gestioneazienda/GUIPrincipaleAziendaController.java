package com.lsdd.system.gestioneazienda;


import com.lsdd.system.autenticazione.AuthManager;
import com.lsdd.system.utils.LogoutConfirmationBoundary;
import com.lsdd.system.utils.LogoutConfirmationController;
import com.lsdd.system.utils.Utente;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GUIPrincipaleAziendaController implements Initializable {
    private final AuthManager authManager;
    private final Utente utente;
    private final Stage stage;

    private final FXMLLoader fxmlLoader;
    private ControlProdottiA controlProdottiA;
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
    @FXML
    private Button logoutButton;


    public void onClick(ActionEvent event) {
        if (event.getSource() == registraProdottiButton) {
            controlProdottiA.onClickRegistraProdotti();
        } else if (event.getSource() == ricercaButton) {
            controlProdottiA.onClickRicerca();
        } else if (event.getSource() == venditaButton) {
            controlOrdiniA.onClickVendita();
        } else if (event.getSource() == listaOrdineButton) {
            controlOrdiniA.onClickListaOrdiniRicevuti();
        } else if (event.getSource() == modificaButton) {
            controlOrdiniA.onclickModificaProduzione();
        } else if (event.getSource() == logoutButton) {
            FXMLLoader fxmlLoader = new FXMLLoader(LogoutConfirmationBoundary.class.getResource("LogoutConfirmation.fxml"));
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.TRANSPARENT);
            LogoutConfirmationController logoutConfirmationController = new LogoutConfirmationController(newStage, stage, "Sei sicuro di voler eseguire il logout?");
            fxmlLoader.setController(logoutConfirmationController);
            new LogoutConfirmationBoundary(newStage, fxmlLoader); //new Stage() per creare una nuova finestra
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        username.setText(utente.getNome());
        this.controlProdottiA = new ControlProdottiA(stage, utente);
        this.controlOrdiniA = new ControlOrdiniA(stage, utente);
    }
}
