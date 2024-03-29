package com.lsdd.system.gestionefarmacia;

import com.lsdd.system.utils.DDBMS;
import com.lsdd.system.utils.Ordine;
import com.lsdd.system.utils.Prodotto;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class FormSelezionaProdottoController implements Initializable {
    private final Ordine ordine;
    private final ControlOrdiniF controlOrdiniF;
    private final Stage stage;
    private final boolean modifica;


    private Ordine ordinemod;
    @FXML
    private MFXButton cancelButton;
    @FXML
    private MFXButton confirmButton;
    @FXML
    private MFXTableView table;

    public FormSelezionaProdottoController(boolean modifica, Ordine ordine, ControlOrdiniF controlOrdiniF, Stage stage) {
        this.ordine=ordine;
        this.controlOrdiniF = controlOrdiniF;
        this.stage = stage;
        this.modifica=modifica;
        this.ordinemod = new Ordine(ordine);
    }

    public void onClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cancelButton){
            ordinemod=new Ordine();
            stage.close();}
        else if (actionEvent.getSource() == confirmButton) {
            if(modifica){
                //controlOrdiniF.richiestaProdotti(listaProdottimod, stage, ));
                if(ordine.ordineprodottiToString()!=ordinemod.ordineprodottiToString())
                    controlOrdiniF.modificaOrdine(ordinemod);
                else Utils.showAlert("non è stato modificato nulla.");
                System.out.println("testina");
                stage.close();
            }
            else{
                if(ordine.ordineprodottiToString()!=ordinemod.ordineprodottiToString())
                    DDBMS.getAzienda().creaNotifica(ordinemod.getCodiceOrdine());

                controlOrdiniF.confermaRicezione(ordinemod);
                System.out.println("testone");

                stage.close();}
        }
    }

    public void setupTable() {

        MFXTableColumn<Prodotto> codiceLottoColumn = new MFXTableColumn<>("Codice Lotto", true, Comparator.comparing(Prodotto::getLotto));
        codiceLottoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> nomeFarmacoColumn = new MFXTableColumn<>("Nome Farmaco", true, Comparator.comparing(Prodotto::getNome));
        nomeFarmacoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> codiceUIDColumn = new MFXTableColumn<>("Codice UID", true, Comparator.comparing(Prodotto::getCodiceUID));
        codiceUIDColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> quantitaColumn = new MFXTableColumn<>("Quantitá", true, Comparator.comparing(Prodotto::getQuantitá));
        quantitaColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> pAttivoColumn = new MFXTableColumn<>("Principio Attivo", true, Comparator.comparing(Prodotto::getPrincipioAttivo));
        pAttivoColumn.setPrefWidth(179);
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
                infoOrderButton.setOnAction(event -> controlOrdiniF.creaModificaQta(prodotto));
            }
        });


        codiceLottoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getLotto));
        nomeFarmacoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getNome));
        codiceUIDColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getCodiceUID));
        quantitaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getQuantitá));
        pAttivoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getPrincipioAttivo));
        table.getTableColumns().addAll(codiceLottoColumn, nomeFarmacoColumn, /*pAttivoColumn,*/ codiceUIDColumn, quantitaColumn, infoOrderColumn);
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
        table.setItems(FXCollections.observableArrayList(ordinemod.getProdotto()));
        table.setFooterVisible(false);

    }
    public void setupTable2() {

        //MFXTableColumn<Prodotto> codiceLottoColumn = new MFXTableColumn<>("Codice Lotto", true, Comparator.comparing(Prodotto::getLotto));
        //codiceLottoColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> nomeFarmacoColumn = new MFXTableColumn<>("Nome Farmaco", true, Comparator.comparing(Prodotto::getNome));
        nomeFarmacoColumn.setPrefWidth(179);
        //MFXTableColumn<Prodotto> codiceUIDColumn = new MFXTableColumn<>("Codice UID", true, Comparator.comparing(Prodotto::getCodiceUID));
        //codiceUIDColumn.setPrefWidth(179);
        MFXTableColumn<Prodotto> quantitaColumn = new MFXTableColumn<>("Quantitá", true, Comparator.comparing(Prodotto::getQuantitá));
        quantitaColumn.setPrefWidth(179);
        //MFXTableColumn<Prodotto> pAttivoColumn = new MFXTableColumn<>("Principio Attivo", true, Comparator.comparing(Prodotto::getPrincipioAttivo));
        //pAttivoColumn.setPrefWidth(179);
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
                infoOrderButton.setOnAction(event -> controlOrdiniF.creaModificaQta(prodotto));
            }
        });


        //codiceLottoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getLotto));
        nomeFarmacoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getNome));
        //codiceUIDColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getCodiceUID));
        quantitaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getQuantitá));
        //pAttivoColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Prodotto::getPrincipioAttivo));
        table.getTableColumns().addAll( nomeFarmacoColumn, /*pAttivoColumn, codiceUIDColumn,*/ quantitaColumn, infoOrderColumn);
        table.getFilters().addAll(
                new StringFilter<>("Codice Lotto", Prodotto::getLotto),
                new StringFilter<>("Nome Farmaco", Prodotto::getNome),
                new IntegerFilter<>("Codice UID", Prodotto::getCodiceUID),
                new IntegerFilter<>("Quantitá", Prodotto::getQuantitá)
        //new StringFilter<>("Principio Attivo", Prodotto::getPrincipioAttivo)
        );
        table.setTableRowFactory(resource -> new MFXTableRow<>(table, resource) {{
            setPrefHeight(40);
            setAlignment(Pos.CENTER_LEFT);
        }});
        table.autosizeColumnsOnInitialization();
        table.setItems(FXCollections.observableArrayList(ordinemod.getProdotto()));
        table.setFooterVisible(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        /*if(modifica)
            setupTable();
        else*/
            setupTable2();
    }
}

