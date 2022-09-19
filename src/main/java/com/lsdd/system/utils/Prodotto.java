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
    private Double costo;
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

    public Prodotto(Prodotto prodotto) {
        this.codiceUID = prodotto.getCodiceUID();
        this.nome = prodotto.getNome();
        this.lotto = prodotto.getLotto();
        this.daBanco = prodotto.isDaBanco();
        this.quantitá = prodotto.getQuantitá();
        this.costo = prodotto.getCosto();
        this.principioAttivo = prodotto.getPrincipioAttivo();
        this.dataProduzione = prodotto.getDataProduzione();
        this.dataScadenza = prodotto.getDataScadenza();
    }


    public Prodotto(Integer codiceUID, String nome, String lotto, boolean daBanco, Integer quantitá, Double costo, String principioAttivo, Date dataProduzione, Date dataScadenza) {
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
    public String toString2() {
        return "codice: "+codiceUID+"; nome: "+nome+"; lotto: "+lotto+"; da banco: "+daBanco+ "; quantità: "+quantitá+"; costo: "+costo+"; produzione: "+dataProduzione+"; scadenza: "+dataScadenza  ;

    }

}

