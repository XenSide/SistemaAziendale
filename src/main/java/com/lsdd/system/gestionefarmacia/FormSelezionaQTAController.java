package com.lsdd.system.gestionefarmacia;

import com.lsdd.system.utils.Prodotto;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class FormSelezionaQTAController implements Initializable {
    private final Prodotto prodotto;
    private final ControlOrdiniF controlOrdiniF;

    private final Stage stage;

    @FXML
    private Label messaggio;

    @FXML
    private MFXTextField quantitáField;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton cancelButton;
    @FXML
    private MFXTextField qtaField;
    @FXML
    private MFXButton confirmButton;

    public void onClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelButton)
            stage.close();
        else if (actionEvent.getSource() == confirmButton) {
            try {
                //controlOrdiniF.richiestaProdotti(prodotto, stage, Integer.parseInt(qtaField.getText()));
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here

    }
}
