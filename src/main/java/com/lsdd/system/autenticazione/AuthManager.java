package com.lsdd.system.autenticazione;

import com.lsdd.system.gestioneazienda.GUIPrincipaleAziendaBoundary;
import com.lsdd.system.gestioneazienda.GUIPrincipaleAziendaController;
import com.lsdd.system.gestioneconsegna.GUIPrincipaleCorriereBoundary;
import com.lsdd.system.gestioneconsegna.GUIPrincipaleCorriereController;
import com.lsdd.system.gestionefarmacia.GUIPrincipaleFarmaciaBoundary;
import com.lsdd.system.gestionefarmacia.GUIPrincipaleFarmaciaController;
import com.lsdd.system.utils.DDBMS;
import com.lsdd.system.utils.TempoB;
import com.lsdd.system.utils.TempoC;
import com.lsdd.system.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import static com.kosprov.jargon2.api.Jargon2.*;


public class AuthManager {
    private final Stage stage;

    public AuthManager(Stage stage) {
        this.stage = stage;
    }

    /*public Utente controlloCredenziali(String email, String password) {
        String emaildb = "1"; //email from db
        if (email.equals(emaildb)) {
            byte[] pass = password.getBytes();
            //String encodedHash = //Hash from DB
            String encodedHash = "$argon2id$v=19$m=65536,t=3,p=4$MZ7a00EJJnnLl+D+X57Bqw$SpB3kSZAkmjIBfMjqaYUTw"; //Hash for 123
            //System.out.printf("Hash: %s%n", encodedHash);
            //Verifier verifier = jargon2Verifier();
            //return verifier.hash(encodedHash).password(pass).verifyEncoded();
            // DONE: 12/09/2022 return Utente from DBMS
            //return new Utente()=DDBMS.getAzienda().controlloCredenziali(email,createHash(password));
            //DDBMS.getAzienda()
            return new Utente(12, 2, "testquery", "Claudio", "Dalfino", "$argon2id$v=19$m=65536,t=3,p=4$MZ7a00EJJnnLl+D+X57Bqw$SpB3kSZAkmjIBfMjqaYUTw");
        } else {
            return null;
        }

    }*/

    public String createHash(String password) {
        Hasher hasher = jargon2Hasher()
                .type(Type.ARGON2id)
                .memoryCost(65536)
                .timeCost(3)
                .parallelism(4)
                .saltLength(16)
                .hashLength(16);
        String encodedHash = hasher.password(password.getBytes()).encodedHash(); //GENERATE PASSWORD HASH FOR REGISTRATION
       // System.out.println(encodedHash);
        return encodedHash;
    }

    /*public void confirmLogin(String email, String password) {
        Utente utente = controlloCredenziali(email, password);
        if (utente instanceof Utente) {
            switch (utente.getType()) {
                case 1: //Amministratore
                    System.out.println(utente.getType());
                    FXMLLoader fxmlLoader = new FXMLLoader(GUIPrincipaleAziendaBoundary.class.getResource("HPAzienda.fxml"));
                    fxmlLoader.setController(new GUIPrincipaleAziendaController(this, utente, stage, fxmlLoader));
                    new GUIPrincipaleAziendaBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
                    break;
                case 2: //Farmacista
                    fxmlLoader = new FXMLLoader(GUIPrincipaleFarmaciaBoundary.class.getResource("HPFarmacia.fxml"));
                    fxmlLoader.setController(new GUIPrincipaleFarmaciaController(this, utente, stage, fxmlLoader));
                    new GUIPrincipaleFarmaciaBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
                    break;

                case 3: //Corriere
                    break;
            }
        } else {
            Utils.showAlert("I dati inseriti sono errati!");
        }
    }
    */

    public void confirmLogin(String email, String password){
        System.out.println(createHash(password));
        DDBMS.getAzienda().controlloCredenziali(email,password).whenComplete((utente, throwable) -> {
            if (throwable != null)
                throwable.printStackTrace();
        }).thenAccept(utente -> {
            Platform.runLater(() -> {
                if (utente != null) {
                    FXMLLoader fxmlLoader;
                    //System.out.println(utente.getPassword()+":"+ utente.getEmail());
                    //new TempoB();
                    switch (utente.getType()) {
                        case 1 -> {
                            System.out.println(utente.getType());
                            fxmlLoader = new FXMLLoader(GUIPrincipaleAziendaBoundary.class.getResource("HPAzienda.fxml"));
                            fxmlLoader.setController(new GUIPrincipaleAziendaController(this, utente, stage, fxmlLoader));
                            new GUIPrincipaleAziendaBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
                            //new TempoB(utente.getUIDFarmacia());
                            break;
                        }
                        case 2 -> {
                            fxmlLoader = new FXMLLoader(GUIPrincipaleFarmaciaBoundary.class.getResource("HPFarmacia.fxml"));
                            fxmlLoader.setController(new GUIPrincipaleFarmaciaController(this, utente, stage, fxmlLoader));
                            new GUIPrincipaleFarmaciaBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
                            //new TempoB(utente.getUIDFarmacia());
                            break;
                        }
                        case 3 -> {
                            fxmlLoader = new FXMLLoader(GUIPrincipaleCorriereBoundary.class.getResource("HPCorriere.fxml"));
                            fxmlLoader.setController(new GUIPrincipaleCorriereController(this, utente, stage, fxmlLoader));
                            new GUIPrincipaleCorriereBoundary(stage, fxmlLoader); //new Stage() per creare una nuova finestra
                            //new TempoB(utente.getUIDFarmacia());
                            break;
                        }//DONE:GUI PRINCIPALE CORRIERE BOUNDARY,  CONTROLLER,CONTROL E CONTROLLER SPEDIZIONI, CONTROLLER INFO SPEDIZIONE
                        default -> {
                            Utils.showAlert("Errore, contatta un amministratore di sistema.");
                        }
                    }
                    Utils.showAlert("Login effettuato con successo.");
                } else
                    Utils.showAlert("I dati inseriti sono errati!");
            });
        });
    }

    public void deleteStage(Stage stage) {
        stage.close();
        System.exit(1);
    }
}
