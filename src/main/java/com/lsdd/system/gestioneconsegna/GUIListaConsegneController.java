package com.lsdd.system.gestioneconsegna;


import com.lsdd.system.utils.Consegna;
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
import java.sql.Date;
import java.sql.Time;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GUIListaConsegneController implements Initializable {
    private final boolean vendita; //if true = vendita, false = listaOrdiniRicevuti

    private final Stage stage;
    private final ControlConsegna controlConsegna;
    private final List<Consegna> listaConsegne;
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

//TODO: sistemare tutto


    public void setupTable() {
        MFXTableColumn<Consegna> nomeFarmacia = new MFXTableColumn<>("Nome Farmacia", true, Comparator.comparing(Consegna::getNomeFarmacia));
        nomeFarmacia.setPrefWidth(179);
        MFXTableColumn<Consegna> indirizzo = new MFXTableColumn<>("Indirizzo", true, Comparator.comparing(Consegna::getIndirizzo));
        indirizzo.setPrefWidth(179);
        MFXTableColumn<Consegna> cap = new MFXTableColumn<>("cap", true, Comparator.comparing(Consegna::getCap));
        cap.setPrefWidth(179);
        MFXTableColumn<Consegna> dataConsegnaColumn = new MFXTableColumn<>("Data di Consegna", true, Comparator.comparing(Consegna::getDataConsegna));
        dataConsegnaColumn.setPrefWidth(179);
        MFXTableColumn<Consegna> infoOrderColumn = new MFXTableColumn<>("", false);
        infoOrderColumn.setRowCellFactory(param -> new MFXTableRowCell<>(consegna -> consegna) {
            private final Button infoOrderButton = new MFXButton("");

            @Override
            public void update(Consegna consegna) {
                System.out.println("aaaaa"+ consegna.getDestinatario());
                if (consegna == null) {
                    setGraphic(null);
                    return;
                }
                Image infoButtonImage;

                    infoButtonImage = new Image((getClass().getResourceAsStream("info.png")));

                ImageView imageView = new ImageView(infoButtonImage);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                infoOrderButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0)"); //trasparent
                infoOrderButton.setGraphic(imageView);
                setGraphic(infoOrderButton);
                infoOrderButton.setOnAction(event -> controlConsegna.creaInfoConsegna(consegna, true));//(consegna.getDataConsegna()== new Date(System.currentTimeMillis()))));
            }

        });

        nomeFarmacia.setRowCellFactory(order -> new MFXTableRowCell<>(Consegna::getNomeFarmacia));
        indirizzo.setRowCellFactory(order -> new MFXTableRowCell<>(Consegna::getIndirizzo));
        cap.setRowCellFactory(order -> new MFXTableRowCell<>(Consegna::getCap));
        dataConsegnaColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Consegna::getDataConsegna));
        table.getTableColumns().addAll(nomeFarmacia , indirizzo, cap, dataConsegnaColumn, infoOrderColumn);
        table.getFilters().addAll(
                new IntegerFilter<>("Codice Consegna", Consegna::getIDConsegna),
                new StringFilter<>("Nome Farmacia", Consegna::getNomeFarmacia),
                new StringFilter<>("Indirrizzo", Consegna::getIndirizzo),
                new StringFilter<>("Destinatario", Consegna::getDestinatario)
        );
        table.setTableRowFactory(resource -> new MFXTableRow<>(table, resource) {{
            setPrefHeight(40);
            setAlignment(Pos.CENTER_LEFT);
        }});
        table.autosizeColumnsOnInitialization();
        table.setItems(FXCollections.observableArrayList(listaConsegne));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FXML edit code here
        if (vendita)
            titleLabel.setText("VENDITA");
        else
            titleLabel.setText("LISTA CONSEGNE");
        username.setText(controlConsegna.getUsername());
        setupTable();
    }
}
