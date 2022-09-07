package com.lsdd.system.autenticazione;

import com.lsdd.system.gestioneazienda.GUIPrincipaleBoundary;
import com.lsdd.system.gestioneazienda.GUIPrincipaleController;
import com.lsdd.system.utils.AlertBoundary;
import com.lsdd.system.utils.AlertController;
import com.lsdd.system.utils.Utils;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.kosprov.jargon2.api.Jargon2.*;


public class AuthManager {
    private final Stage stage;

    public AuthManager(Stage stage) {
        this.stage = stage;
    }

    public boolean controlloCredenziali(String email, String password) {
        String emaildb = "testquery"; //email from db
        if (email.equals(emaildb)) {
            byte[] pass = password.getBytes();
            //String encodedHash = //Hash from DB
            String encodedHash = "$argon2id$v=19$m=65536,t=3,p=4$MZ7a00EJJnnLl+D+X57Bqw$SpB3kSZAkmjIBfMjqaYUTw"; //Hash for 123
            //System.out.printf("Hash: %s%n", encodedHash);
            Verifier verifier = jargon2Verifier();
            return verifier.hash(encodedHash).password(pass).verifyEncoded();
        } else {
            return false;
        }
    }

    public void createHash(String password) {
        Hasher hasher = jargon2Hasher()
                .type(Type.ARGON2id)
                .memoryCost(65536)
                .timeCost(3)
                .parallelism(4)
                .saltLength(16)
                .hashLength(16);
        String encodedHash = hasher.password(password.getBytes()).encodedHash(); //GENERATE PASSWORD HASH FOR REGISTRATION
        System.out.println(encodedHash);
    }

    public void confirmLogin(String email, String password) {
        if (controlloCredenziali(email, password)) {
            FXMLLoader fxmlLoader = new FXMLLoader(GUIPrincipaleBoundary.class.getResource("HPAzienda.fxml"));
            fxmlLoader.setController(new GUIPrincipaleController(stage, fxmlLoader));
            new GUIPrincipaleBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
        } else {
            Utils.showAlert("I dati inseriti sono errati!");
        }
    }

    public void deleteStage(Stage stage) {
        stage.close();
    }
}
