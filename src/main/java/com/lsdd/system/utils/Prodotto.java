package com.lsdd.system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Prodotto {
    private Integer codiceUID;
    private String nome;
    private String lotto;
    private boolean daBanco;
    private Integer quantitá;
    private Integer costo;
    private String principioAttivo;
    private Date dataProduzione;
    private Date dataScadenza;

   /* @Override
    public String toString() {
        return "UID: "+codiceUID+" Nome: "+nome+" Lotto: "+lotto+" daBanco: "+daBanco+" quantita: "+quantitá+" costo: "
                +costo+" Principio Attivo: "+principioAttivo+" Data Di Produzione: "+dataProduzione
                +" Data Di Scadenza: "+dataScadenza;
    }*/

    @Override
    public String toString(){
        nome = Utils.toDisplayCase(nome);
        return nome+" "+quantitá;
    }

}

