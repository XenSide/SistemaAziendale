package com.lsdd.system.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Prodotto {
    private Integer periodicita = null;
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
    public Prodotto() {

    }

    public Prodotto(Integer codiceUID, String nome, String lotto, boolean daBanco, Integer quantitá, Integer costo, String principioAttivo, Date dataProduzione, Date dataScadenza) {
        this.codiceUID = codiceUID;
        this.nome = nome;
        this.lotto = lotto;
        this.daBanco = daBanco;
        this.quantitá = quantitá;
        this.costo = costo;
        this.principioAttivo = principioAttivo;
        this.dataProduzione = dataProduzione;
        this.dataScadenza = dataScadenza;
    }

    @Override
    public String toString() {
        nome = Utils.toDisplayCase(nome);
        return nome + " " + quantitá;
    }

}

