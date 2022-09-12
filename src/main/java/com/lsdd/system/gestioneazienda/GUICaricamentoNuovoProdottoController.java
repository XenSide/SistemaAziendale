package com.lsdd.system.gestioneazienda;


import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GUICaricamentoNuovoProdottoController implements Initializable {
    private final Stage stage;
    private final ProdottoManager prodottoManager;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXTextField uidField;

    @FXML
    private MFXTextField nomeField;

    @FXML
    private MFXTextField lottoField;

    @FXML
    private MFXComboBox daBancoField;

    @FXML
    private MFXTextField quantitaField;

    @FXML
    private MFXTextField costoField;

    @FXML
    private MFXTextField pAttivoField;

    @FXML
    private MFXDatePicker dataProduzioneField;


    @FXML
    private MFXDatePicker dataScadenzaField;

    @FXML
    private MFXButton cancelButton;
    @FXML
    private MFXButton confirmButton;

    @FXML
    private Label username;

    public void onConfirmButtonClick(ActionEvent actionEvent) throws DateTimeParseException, ParseException {


    }


    public void onClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelButton) {
            stage.close();
        } else if (actionEvent.getSource() == confirmButton) {
            try {
                prodottoManager.controlloCaricoDelFarmaco(uidField.getText(), nomeField.getText(), lottoField.getText(),
                        daBancoField.getText().equals("Si"), quantitaField.getText(), costoField.getText(), pAttivoField.getText(),
                        dataProduzioneField.getText(), dataScadenzaField.getText(), stage);
            }catch (Exception exception){
                System.err.println(exception);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        daBancoField.setItems(FXCollections.observableArrayList("Si", "No"));
        dataProduzioneField.setConverterSupplier(() -> new DateStringConverter("dd/MM/yyyy", dataProduzioneField.getLocale()));
        dataScadenzaField.setConverterSupplier(() -> new DateStringConverter("dd/MM/yyyy", dataScadenzaField.getLocale()));
        username.setText(prodottoManager.getUsername());

    }
}
