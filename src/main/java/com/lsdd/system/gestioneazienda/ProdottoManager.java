package com.lsdd.system.gestioneazienda;

import com.lsdd.system.utils.MyClassLoader;
import com.lsdd.system.utils.Ordine;
import com.lsdd.system.utils.Prodotto;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProdottoManager {

    public ClassLoader cachingClassLoader = new MyClassLoader(FXMLLoader.getDefaultClassLoader());

    private final Stage stage;
    public ProdottoManager(Stage stage) {
        this.stage = stage;
    }

    public void onClickRegistraProdotti() {
        FXMLLoader fxmlLoader = new FXMLLoader(GUICaricamentoNuovoProdottoController.class.getResource("registrazioneProdotti.fxml"));
        Stage stage = new Stage();
        fxmlLoader.setClassLoader(cachingClassLoader); // FIXME: 9/6/2022 Tentativo di caching FXML
        fxmlLoader.setController(new GUICaricamentoNuovoProdottoController(stage, this));
        new GUICaricamentoNuovoProdottoBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void onClickRicerca() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(GUIListaProdottoController.class.getResource("ricerca.fxml"));
        Date data = new Date(1662477550);
        Prodotto augmentin = new Prodotto("augmentin", "augmentin", 123, "ABC123", data, data, 2, 3, true);
        List<Ordine> ordines = new ArrayList<>();
        List<Prodotto> prodottos = new ArrayList<>();
        prodottos.add(augmentin);
        ordines.add(new Ordine(1,"bobbina", "viavai", prodottos, data, 1, 1));
        ordines.add(new Ordine(1,"Antonina", "viavai", prodottos, data, 1, 1));
        fxmlLoader.setController(new GUIListaProdottoController(stage, this, ordines));
        new GUIListaProdottoBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
    }

    public void controlloCaricoDelFarmaco(String uid, String nome, String lotto, String peso,
                                          String quantita, String costo, String pAttivo, String dataProduzione,
                                          String dataScadenza) {
        System.out.println(uid + " " + nome + " " + lotto + " " + peso + " " + quantita + " " + costo + " " + pAttivo +
                " " + dataProduzione + " " + dataScadenza);
        // FIXME: 9/2/2022 QUERY PER SALVARE

    }
}
