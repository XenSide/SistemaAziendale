package com.lsdd.system.gestioneazienda;


import com.lsdd.system.utils.Prodotto;
import com.lsdd.system.utils.Utils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class FormModificaUnitáTempoController implements Initializable {
    private final Stage stage;
    private final ControlOrdiniA controlOrdiniA;
    private final Prodotto prodotto;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXTableView table;

    @FXML
    private Label titleLabel;

    @FXML
    private MFXButton confirmButton;
    @FXML
    private MFXButton cancelButton;

    public void onCancelButtonClick(ActionEvent actionEvent) {
        stage.close();
    }

    public void onClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == confirmButton) {
            if (controlOrdiniA.modificaProduzioneProdotto(prodotto)) {
                Utils.showAlert("Modifiche apportate con successo");
                stage.close();
            } else {
                Utils.showAlert("Si è verificato un errore");
            }


        } else if (actionEvent.getSource() == cancelButton) {
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
    }
}
