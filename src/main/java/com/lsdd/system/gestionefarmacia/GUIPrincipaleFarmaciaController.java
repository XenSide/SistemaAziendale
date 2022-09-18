package com.lsdd.system.gestionefarmacia;


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
public class GUIPrincipaleFarmaciaController implements Initializable {
    private final AuthManager authManager;
    private final Utente utente;
    private final Stage stage;

    private final FXMLLoader fxmlLoader;

    private ControlProdottiF controlProdottiF;

    private ControlOrdiniF controlOrdiniF;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton stoccaggioButton;

    @FXML
    private MFXButton confermaRicezioneButton;
    @FXML
    private MFXButton ricercaButton;

    @FXML
    private MFXButton richiestaProdottiButton;
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
        if (event.getSource() == richiestaProdottiButton) {
            controlProdottiF.onClickRichiestaProdotti();
        } else if (event.getSource() == ricercaButton) {
            controlProdottiF.onClickRicerca();
        } else if (event.getSource() == venditaButton) {
            controlOrdiniF.onClickVendita();
        } else if (event.getSource() == listaOrdineButton) {
            controlOrdiniF.onClickListaOrdiniEffetuati();
        } else if (event.getSource() == modificaButton) {
            controlOrdiniF.onClickModificaOrdine();
        } else if (event.getSource() == logoutButton) {
            FXMLLoader fxmlLoader = new FXMLLoader(LogoutConfirmationBoundary.class.getResource("FormSelezionaUnita.fxml"));
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
        this.controlProdottiF = new ControlProdottiF(stage, utente);
        this.controlOrdiniF = new ControlOrdiniF(stage, utente);
    }
}
