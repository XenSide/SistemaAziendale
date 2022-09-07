package com.lsdd.system.gestioneazienda;


import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
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


    public void onConfirmButtonClick(ActionEvent actionEvent) throws DateTimeParseException {
        prodottoManager.controlloCaricoDelFarmaco(uidField.getText(), nomeField.getText(), lottoField.getText(),
                daBancoField.getText(), quantitaField.getText(), costoField.getText(), pAttivoField.getText(),
                dataProduzioneField.getText(), dataScadenzaField.getText());
        stage.close();
    }

    public void onCancelButtonClick(ActionEvent actionEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        daBancoField.setItems(FXCollections.observableArrayList("Si", "No"));
        dataProduzioneField.setConverterSupplier(() -> new DateStringConverter("dd/MM/yyyy", dataProduzioneField.getLocale()));
        dataScadenzaField.setConverterSupplier(() -> new DateStringConverter("dd/MM/yyyy", dataScadenzaField.getLocale()));
    }
}
