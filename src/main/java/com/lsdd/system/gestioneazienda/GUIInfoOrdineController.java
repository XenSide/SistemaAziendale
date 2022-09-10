package com.lsdd.system.gestioneazienda;

import com.lsdd.system.utils.Ordine;
import com.lsdd.system.utils.Utils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GUIInfoOrdineController implements Initializable {
    private final Stage stage;
    private ProdottoManager prodottoManager;

    private final FXMLLoader fxmlLoader;

    private final Ordine ordine;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton vendiButton;
    @FXML
    private MFXButton indietroButton;

    @FXML
    private Label ordercodeLabel;
    @FXML
    private Label dataOrdineLabel;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label statoLabel;
    @FXML
    private Label tipoLabel;
    @FXML
    private Label dataConsegnaLabel;
    @FXML
    private MFXListView listView;

    public void onClick(ActionEvent event){
        if(event.getSource() == indietroButton) {
            stage.close();
        } else if (event.getSource() == vendiButton) {
            boolean success = prodottoManager.vendiProdotto();
            if (success){
                Utils.showAlertInSameWindow("Vendita confermata", stage);
            }else {
                Utils.showAlertInSameWindow("Vendita fallita", stage);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        ordercodeLabel.setText(Utils.toDisplayCase(ordine.getCodiceOrdine().toString()));
        dataOrdineLabel.setText(Utils.toDisplayCase(ordine.getDataOrdine().toString()));
        indirizzoLabel.setText(Utils.toDisplayCase(ordine.getIndirizzoFarmacia()));
        nomeLabel.setText(Utils.toDisplayCase(ordine.getNomeFarmacia()));
        statoLabel.setText(Utils.toDisplayCase(ordine.getStatoOrdine().toString()));
        dataConsegnaLabel.setText(Utils.toDisplayCase(ordine.getDataConsegna().toString()));
        tipoLabel.setText(Utils.toDisplayCase(ordine.getTipoOrdine().toString()));

        List<String> strings = new ArrayList<>();
        //strings.add(ordine.getProdotto());
        listView.setItems(FXCollections.observableArrayList(ordine.getProdotto()));


        this.prodottoManager = new ProdottoManager(stage);
    }
}
