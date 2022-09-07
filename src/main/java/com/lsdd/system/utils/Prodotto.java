package com.lsdd.system.utils;

import lombok.AllArgsConstructor;

import java.util.Date;
@AllArgsConstructor
public class Prodotto {
    private int codiceUID;
    private String nome;
    private String lotto;
    private boolean daBanco;
    private int quantitá;
    private int costo;
    private String principioAttivo;
    private Date dataProduzione;
    private Date dataScadenza;

    @Override
    public String toString() {
        return "UID: "+codiceUID+" Nome: "+nome+" Lotto: "+lotto+" daBanco: "+daBanco+" quantita: "+quantitá+" costo: "
                +costo+" Principio Attivo: "+principioAttivo+" Data Di Produzione: "+dataProduzione
                +" Data Di Scadenza: "+dataScadenza;
    }

}

