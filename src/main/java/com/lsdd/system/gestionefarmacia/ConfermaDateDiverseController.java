package com.lsdd.system.gestionefarmacia;


import com.lsdd.system.utils.Prodotto;
import com.lsdd.system.utils.Richiesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class ConfermaDateDiverseController implements Initializable {
    private final Integer qta;
    private final Richiesta richiesta;

    private final Stage stage;
    private final String stringaErrore;
    @Setter
    @FXML
    private Label messaggioErrore;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MFXButton cancelButton;

    @FXML
    private MFXButton confirmButton;


    public void onClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == confirmButton) {
            Richiesta richiestaLocal = new Richiesta(richiesta.getIdFarmacia());
            richiestaLocal.aggiungiProdotto(new Prodotto(richiesta.getFirst()));
            if (qta > 0) {
                richiesta.setQtaProdotto(qta);
                richiestaLocal.subtractQtaProdotto(qta);
            } else {
                richiesta.pop();
            }
            // TODO: 15/09/2022 QUERY PER SALVARE RICHIESTAlocal
            stage.close();
        } else {
            //STESSADATA
            stage.close();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messaggioErrore.setText(stringaErrore);
        //FXML edit code here
    }
}
