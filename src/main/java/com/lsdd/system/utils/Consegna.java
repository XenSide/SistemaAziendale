package com.lsdd.system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
public class Consegna {
    Integer iDConsegna;
    Date dataConsegna;
    Integer iDFarmacia;
    String nomeFarmacia;
    String cap;
    String indirizzo;
    String destinatario;

    public Consegna(Integer iDConsegna, Date dataConsegna, Integer iDFarmacia, String nomeFarmacia, String cap, String indirizzo) {
        this.iDConsegna = iDConsegna;
        this.dataConsegna = dataConsegna;
        this.iDFarmacia = iDFarmacia;
        this.nomeFarmacia = nomeFarmacia;
        this.cap = cap;
        this.indirizzo = indirizzo;
    }
    public void setDestinatario(String destinatario){
        this.destinatario=destinatario;
    }


}
