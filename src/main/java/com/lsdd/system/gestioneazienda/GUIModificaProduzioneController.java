package com.lsdd.system.gestioneazienda;


import com.lsdd.system.utils.Prodotto;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GUIModificaProduzioneController implements Initializable {
    private final String title = "MODIFICA PRODUZIONE";
    private final Stage stage;
    private final ControlOrdiniA controlOrdiniA;
    private final List<Prodotto> listaProdotti;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXTableView table;

    @FXML
    private Label titleLabel;

    @FXML
    private Label username;

    public void onCancelButtonClick(ActionEvent actionEvent) {
        stage.close();
    }


    public void setupTable() {

        MFXTableColumn<Prodotto> nomeFarmacoColumn = new MFXTableColumn<>("Nome Farmaco", true, Comparator.comparing(Prodotto::getNome));
        nomeFarmacoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> pAttivoColumn = new MFXTableColumn<>("Principio Attivo", true, Comparator.comparing(Prodotto::getPrincipioAttivo));
        pAttivoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> quantitaColumn = new MFXTableColumn<>("Quantitá", true, Comparator.comparing(Prodotto::getQuantitá));
        quantitaColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> tempoColumn = new MFXTableColumn<>("Periodicità (gg)", true, Comparator.comparing(Prodotto::getDataProduzione));
        tempoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> infoOrderColumn = new MFXTableColumn<>("", false);
        infoOrderColumn.setRowCellFactory(param -> new MFXTableRowCell<>(prodotto -> prodotto) {
            private final Button infoOrderButton = new MFXButton("");

            @Override
            public void update(Prodotto prodotto) {
                if (prodotto == null) {
                    setGraphic(null);
                    return;
                }

                Image infoButtonImage = new Image((getClass().getResourceAsStream("modifica.png")));
                ImageView imageView = new ImageView(infoButtonImage);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                infoOrderButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0)"); //trasparent
                infoOrderButton.setGraphic(imageView);
                setGraphic(infoOrderButton);
                infoOrderButton.setOnAction(event -> controlOrdiniA.creaModificaProduzione(prodotto));
            }
        });


        nomeFarmacoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getNome));
        pAttivoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getPrincipioAttivo));
        quantitaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getQuantitá));
        tempoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getDataProduzione));
        table.getTableColumns().addAll(nomeFarmacoColumn, pAttivoColumn, quantitaColumn, tempoColumn, infoOrderColumn);
        table.getFilters().addAll(
                new StringFilter<>("Nome Farmaco", Prodotto::getNome),
                new StringFilter<>("Principio Attivo", Prodotto::getPrincipioAttivo),
                new IntegerFilter<>("Quantitá", Prodotto::getQuantitá)
        );
        table.setTableRowFactory(resource -> new MFXTableRow<>(table, resource) {{
            setPrefHeight(40);
            setAlignment(Pos.CENTER_LEFT);
        }});
        table.autosizeColumnsOnInitialization();
        table.setItems(FXCollections.observableArrayList(listaProdotti));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        username.setText(controlOrdiniA.getUsername());
        titleLabel.setText(title);
        setupTable();
    }
}
