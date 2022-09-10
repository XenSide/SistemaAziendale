package com.lsdd.system.gestioneazienda;


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
import javafx.scene.Node;
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
public class GUIVenditaController implements Initializable {
    private final String title = "VENDITA";

    private final Stage stage;
    private final ProdottoManager prodottoManager;
    private final List<Ordine> listaOrdini;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXTableView table;

    @FXML
    private Label titleLabel;

    public void onCancelButtonClick(ActionEvent actionEvent) {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void setupTable() {
        MFXTableColumn<Ordine> codiceOrdineColumn = new MFXTableColumn<>("Codice Ordine", true, Comparator.comparing(Ordine::getCodiceOrdine));
        codiceOrdineColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> indirizzoFarmaciaColumn = new MFXTableColumn<>("Indirizzo Farmacia", true, Comparator.comparing(Ordine::getIndirizzoFarmacia));
        indirizzoFarmaciaColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> nomeFarmaciaColumn = new MFXTableColumn<>("Nome Farmacia", true, Comparator.comparing(Ordine::getNomeFarmacia));
        nomeFarmaciaColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> dataConsegnaColumn = new MFXTableColumn<>("Data di Consegna", true, Comparator.comparing(Ordine::getDataConsegna));
        dataConsegnaColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> statoColumn = new MFXTableColumn<>("Stato Ordine", true, Comparator.comparing(Ordine::getStatoOrdine));
        statoColumn.setPrefWidth(179);
        MFXTableColumn<Ordine> infoOrderColumn = new MFXTableColumn<>("", false);
        MFXTableColumn<Ordine> cancelOrderColumn = new MFXTableColumn<>("", false);
        infoOrderColumn.setRowCellFactory(param -> new MFXTableRowCell<>(order -> order) {
            private final Button infoOrderButton = new MFXButton("");
            private final Button cancelOrderButton = new MFXButton("");

            @Override
            public void update(Ordine ordine) {
                if (ordine == null) {
                    setGraphic(null);
                    return;
                }

                Image infoButtonImage = new Image((getClass().getResourceAsStream("Info.png"))); // FIXME: 07/09/2022
                ImageView imageView = new ImageView(infoButtonImage);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                infoOrderButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0)"); //trasparent
                infoOrderButton.setGraphic(imageView);
                setGraphic(infoOrderButton);
                infoOrderButton.setOnAction(event -> prodottoManager.creaInfoOrdine(ordine));
            }
        });
        cancelOrderColumn.setRowCellFactory(param -> new MFXTableRowCell<>(order -> order) {
                    private final Button cancelOrderButton = new MFXButton("");

                    @Override
                    public void update(Ordine ordine) {
                        if (ordine == null) {
                            setGraphic(null);
                            return;
                        }
                    Image cancelButtonImage = new Image((getClass().getResourceAsStream("cancel.png"))); // FIXME: 07/09/2022
                    ImageView imageView = new ImageView(cancelButtonImage);
                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    cancelOrderButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0)"); //trasparent
                    cancelOrderButton.setGraphic(imageView);
                    setGraphic(cancelOrderButton);
                    cancelOrderButton.setOnAction(event -> prodottoManager.cancellaOrdine(ordine));
                    }
                });



        codiceOrdineColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getCodiceOrdine));
        indirizzoFarmaciaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getIndirizzoFarmacia));
        nomeFarmaciaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getNomeFarmacia));
        dataConsegnaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getDataConsegna));
        statoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getStatoOrdine));
        table.getTableColumns().addAll(codiceOrdineColumn, indirizzoFarmaciaColumn, nomeFarmaciaColumn, dataConsegnaColumn, statoColumn, infoOrderColumn, cancelOrderColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Codice Ordine", Ordine::getCodiceOrdine),
                new StringFilter<>("Indirizzo Farmacia", Ordine::getIndirizzoFarmacia),
                new StringFilter<>("Nome Farmacia", Ordine::getNomeFarmacia),
                new IntegerFilter<>("Stato Ordine", Ordine::getStatoOrdine)
        );
        table.setTableRowFactory(resource -> new MFXTableRow<>(table, resource) {{
            setPrefHeight(40);
            setAlignment(Pos.CENTER);
        }});
        table.autosizeColumnsOnInitialization();
        table.setItems(FXCollections.observableArrayList(listaOrdini));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        titleLabel.setText(title);
        setupTable();
    }
}
