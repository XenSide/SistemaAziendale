package com.lsdd.system.gestioneazienda;


import com.lsdd.system.utils.Ordine;
import com.lsdd.system.utils.Utils;
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
public class ConfermaAnnullamentoController implements Initializable {
    private final Stage stage;
    private final String stringaErrore;

    private final ControlOrdiniA controlOrdiniA;

    private final Ordine ordine;

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
        if(actionEvent.getSource() == cancelButton) {
            stage.close();
        } else if (actionEvent.getSource() == confirmButton) {
            try {
                boolean success = controlOrdiniA.annullaOrdine(ordine);
                if (controlOrdiniA.annullaOrdine(ordine)) // FIXME: 10/09/2022
                {
                    Utils.showAlertInSameWindow("L'ordine è stato cancellato correttamente", stage);
                } else {
                    Utils.showAlertInSameWindow("Impossibile annullare l'ordine, poiché la consegna è prevista nei prossimi 2 giorni", stage);
                }

            }catch (Exception exception){
                System.err.println(exception);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messaggioErrore.setText(stringaErrore);
        //FXML edit code here
    }
}
