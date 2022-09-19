package com.lsdd.system.gestionefarmacia;


import com.lsdd.system.utils.DDBMS;
import com.lsdd.system.utils.Prodotto;
import com.lsdd.system.utils.Richiesta;
import com.lsdd.system.utils.Utils;
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

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


public class GUIRichiestaProdottoController implements Initializable {
    private final String title = "RICHIESTA PRODOTTI";
    private final Stage stage;
    private final Richiesta richiesta;
    private final ControlProdottiF controlProdottiF;
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
    private MFXButton confirmButton;
    @FXML
    private MFXButton cancelButton;
    @FXML
    private Label username;

    public GUIRichiestaProdottoController(Stage stage, ControlProdottiF controlProdottiF, List<Prodotto> listaProdotti) {
        this.stage = stage;
        this.controlProdottiF = controlProdottiF;
        this.listaProdotti = listaProdotti;
        this.richiesta = new Richiesta(controlProdottiF.getFarmaciaID());
    }

    public void onClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == confirmButton) {
            System.out.println(richiesta);
            if (richiesta.getSize() != 0) {
                // DONE: 15/09/2022 QUERY PER SALVARE RICHIESTA
                DDBMS.getAzienda().richiestaProdotti(richiesta,false);
            } else {
                Utils.showAlert("La selezione dei prodotti era vuota");
            }
        }
        stage.close();
    }


    public void setupTable() {

        MFXTableColumn<Prodotto> codiceUIDColumn = new MFXTableColumn<>("Codice UID", true, Comparator.comparing(Prodotto::getCodiceUID));
        codiceUIDColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> nomeFarmacoColumn = new MFXTableColumn<>("Nome Farmaco", true, Comparator.comparing(Prodotto::getNome));
        nomeFarmacoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> pAttivoColumn = new MFXTableColumn<>("Principio Attivo", true, Comparator.comparing(Prodotto::getPrincipioAttivo));
        pAttivoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> costoColumn = new MFXTableColumn<>("Costo", true, Comparator.comparing(Prodotto::getCosto));
        costoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> infoOrderColumn = new MFXTableColumn<>("", false);
        infoOrderColumn.setRowCellFactory(param -> new MFXTableRowCell<>(prodotto -> prodotto) {
            private final Button infoOrderButton = new MFXButton("");

            @Override
            public void update(Prodotto prodotto) {
                if (prodotto == null) {
                    setGraphic(null);
                    return;
                }

                Image infoButtonImage = new Image((getClass().getResourceAsStream("checkmark.png"))); // FIXME: 07/09/2022
                ImageView imageView = new ImageView(infoButtonImage);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                infoOrderButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0)"); //trasparent
                infoOrderButton.setGraphic(imageView);
                setGraphic(infoOrderButton);
                infoOrderButton.setOnAction(event -> controlProdottiF.creaFormSelezionaUnita(prodotto, richiesta));
            }
        });


        costoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getCosto));
        nomeFarmacoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getNome));
        codiceUIDColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getCodiceUID));
        pAttivoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getPrincipioAttivo));
        table.getTableColumns().addAll(codiceUIDColumn, nomeFarmacoColumn, pAttivoColumn, costoColumn, infoOrderColumn);
        table.getFilters().addAll(
                new StringFilter<>("Codice Lotto", Prodotto::getLotto),
                new StringFilter<>("Nome Farmaco", Prodotto::getNome),
                new IntegerFilter<>("Codice UID", Prodotto::getCodiceUID),
                new IntegerFilter<>("Quantitá", Prodotto::getQuantitá),
                new StringFilter<>("Principio Attivo", Prodotto::getPrincipioAttivo)
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
        username.setText(controlProdottiF.getUsername());
        titleLabel.setText(title);
        setupTable();
    }
}
