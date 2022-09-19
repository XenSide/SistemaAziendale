package com.lsdd.system.gestionefarmacia;


import com.lsdd.system.utils.Ordine;
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
public class GUIConfermaRicezioneController implements Initializable { //TODO: tutta la classe, ho copiato lista ordini effettuati perche è molto simile
    private final boolean vendita; //if true = vendita, false = listaOrdiniRicevuti

    private final Stage stage;
    private final ControlOrdiniF controlOrdiniF;
    private final List<Ordine> listaOrdini;
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
        MFXTableColumn<Ordine> codiceOrdineColumn = new MFXTableColumn<>("Codice Ordine", true, Comparator.comparing(Ordine::getCodiceOrdine));
        codiceOrdineColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> dataOrdineColumn = new MFXTableColumn<>("Data Ordine", true, Comparator.comparing(Ordine::getDataOrdine));
        dataOrdineColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> nomeFarmaciaColumn = new MFXTableColumn<>("Nome Farmacia", true, Comparator.comparing(Ordine::getNomeFarmacia));
        nomeFarmaciaColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> dataConsegnaColumn = new MFXTableColumn<>("Data di Consegna", true, Comparator.comparing(Ordine::getDataConsegna));
        dataConsegnaColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> statoColumn = new MFXTableColumn<>("Stato Ordine", true, Comparator.comparing(Ordine::getStatoOrdine));
        statoColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> infoOrderColumn = new MFXTableColumn<>("", false);
        infoOrderColumn.setRowCellFactory(param -> new MFXTableRowCell<>(order -> order) {
            private final Button infoOrderButton = new MFXButton("");

            @Override
            public void update(Ordine ordine) {
                if (ordine == null) {
                    setGraphic(null);
                    return;
                }
                Image infoButtonImage;
                if (vendita) {
                    infoButtonImage = new Image((getClass().getResourceAsStream("sellList.png")));
                } else {
                    infoButtonImage = new Image((getClass().getResourceAsStream("info.png")));
                }
                ImageView imageView = new ImageView(infoButtonImage);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                infoOrderButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0)"); //trasparent
                infoOrderButton.setGraphic(imageView);
                setGraphic(infoOrderButton);
                infoOrderButton.setOnAction(event -> controlOrdiniF.creaInfoOrdine(ordine, vendita));
            }
        });


        codiceOrdineColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getCodiceOrdine));
        dataOrdineColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getDataOrdine));
        nomeFarmaciaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getNomeFarmacia));
        dataConsegnaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getDataConsegna));
        statoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getStatoOrdine));
        table.getTableColumns().addAll(codiceOrdineColumn, dataOrdineColumn, nomeFarmaciaColumn, dataConsegnaColumn, statoColumn, infoOrderColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Codice Ordine", Ordine::getCodiceOrdine),
                new StringFilter<>("Nome Farmacia", Ordine::getNomeFarmacia),
                new IntegerFilter<>("Stato Ordine", Ordine::getStatoOrdine)
        );
        table.setTableRowFactory(resource -> new MFXTableRow<>(table, resource) {{
            setPrefHeight(40);
            setAlignment(Pos.CENTER_LEFT);
        }});
        table.autosizeColumnsOnInitialization();
        table.setItems(FXCollections.observableArrayList(listaOrdini));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        if (vendita)
            titleLabel.setText("VENDITA");
        else
            titleLabel.setText("LISTA ORDINI EFFETTUATI");
        username.setText(controlOrdiniF.getUsername());
        setupTable();
    }
}
