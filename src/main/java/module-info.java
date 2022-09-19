module com.sysaz.sistemaaziendale {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.zaxxer.hikari;
    requires lombok;
    requires java.sql;
    requires org.slf4j;
    requires jargon2.api;


    opens com.lsdd.system.autenticazione to javafx.fxml;
    exports com.lsdd.system.autenticazione;
    exports com.lsdd.system.gestioneazienda;
    opens com.lsdd.system.gestioneazienda to javafx.fxml;
    exports com.lsdd.system.utils;
    opens com.lsdd.system.utils to javafx.fxml;
    opens com.lsdd.system.gestionefarmacia to javafx.fxml;
    opens com.lsdd.system.gestioneconsegna to javafx.fxml;

}