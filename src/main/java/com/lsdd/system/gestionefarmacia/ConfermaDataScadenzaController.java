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
public class ConfermaDataScadenzaController implements Initializable {
    private final Prodotto prodotto;
    private final Stage stage;
    private final Richiesta richiesta;
    private final String stringaErrore;

    private final ControlProdottiF controlProdottiF;

    private final Integer qta;
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
            if (prodotto.getQuantitá() - richiesta.getFirst().getQuantitá() < 0)
                prodotto.setQuantitá(0);
            else
                prodotto.setQuantitá(prodotto.getQuantitá() - richiesta.getFirst().getQuantitá());
            controlProdottiF.checkDateDiverse(richiesta, qta);
        } else {
            richiesta.pop();
        }
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messaggioErrore.setText(stringaErrore);
        //FXML edit code here
    }
}
