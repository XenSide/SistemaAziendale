package com.lsdd.system.gestioneazienda;


import com.lsdd.system.utils.Ordine;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GUIListaProdottoController implements Initializable {
    private final Stage stage;
    private final ProdottoManager prodottoManager;
    private final List<Ordine> listaOrdini;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXTableView table;

    public void onCancelButtonClick(ActionEvent actionEvent) {
        System.out.println(actionEvent.getSource());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void setupTable() {
        MFXTableColumn<Ordine> codiceOrdineColumn = new MFXTableColumn<>("Codice Ordine", true, Comparator.comparing(Ordine::getCodiceOrdine));
        codiceOrdineColumn.setPrefWidth(130);
        MFXTableColumn<Ordine> indirizzoFarmaciaColumn = new MFXTableColumn<>("Indirizzo Farmacia", true, Comparator.comparing(Ordine::getIndirizzoFarmacia));
        indirizzoFarmaciaColumn.setPrefWidth(130);
        MFXTableColumn<Ordine> nomeFarmaciaColumn = new MFXTableColumn<>("Nome Farmacia", true, Comparator.comparing(Ordine::getNomeFarmacia));
        nomeFarmaciaColumn.setPrefWidth(130);
        MFXTableColumn<Ordine> dataConsegnaColumn = new MFXTableColumn<>("Data di Consegna", true, Comparator.comparing(Ordine::getDataConsegna));
        dataConsegnaColumn.setPrefWidth(130);
        MFXTableColumn<Ordine> statoColumn = new MFXTableColumn<>("Stato Ordine", true, Comparator.comparing(Ordine::getStatoOrdine));
        statoColumn.setPrefWidth(130);
        MFXTableColumn<Ordine> infoOrderColumn = new MFXTableColumn<>("", false);
        infoOrderColumn.setRowCellFactory(param -> new MFXTableRowCell<>(order -> order) {
            private final MFXButton infoOrderButton = new MFXButton("");

            @Override
            public void update(Ordine ordine) {
                if (ordine == null) {
                    setGraphic(null);
                    return;
                }
              /*  Image buttonImage = new Image(getClass().getResourceAsStream("/images/info.png"));
                ImageView imageView = new ImageView(buttonImage);
                imageView.setFitWidth(15);
                imageView.setFitHeight(17);
                infoOrderButton.setTextFill(Paint.valueOf("WHITE"));
                infoOrderButton.setGraphic(imageView);

                setGraphic(infoOrderButton);
                infoOrderButton.setOnAction(event -> ordersC.showInfoOrder(ordine));*/
            }
        });


        codiceOrdineColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getCodiceOrdine));
        indirizzoFarmaciaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getIndirizzoFarmacia));
        nomeFarmaciaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getNomeFarmacia));
        dataConsegnaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getDataConsegna));
        statoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Ordine::getStatoOrdine));

        table.getTableColumns().addAll(codiceOrdineColumn, indirizzoFarmaciaColumn, nomeFarmaciaColumn, dataConsegnaColumn, statoColumn, infoOrderColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Codice Ordine", Ordine::getCodiceOrdine),
                new StringFilter<>("Indirizzo Farmacia", Ordine::getIndirizzoFarmacia),
                new StringFilter<>("Nome Farmacia", Ordine::getNomeFarmacia),
                new IntegerFilter<>("Stato Ordine", Ordine::getStatoOrdine)
        );

        table.setItems(FXCollections.observableArrayList(listaOrdini));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        setupTable();
    }
}
