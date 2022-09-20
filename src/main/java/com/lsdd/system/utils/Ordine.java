package com.lsdd.system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Ordine {
    private Integer codiceOrdine;
    private Integer idFarmacia;
    private String nomeFarmacia;
    private String cap;
    private String indirizzoFarmacia;
    private List<Prodotto> prodotto;
    private Date dataConsegna;
    private Date dataOrdine;
    private Integer statoOrdine;
    private Integer tipoOrdine; //se 0 non periodico, sennò numero di giorni di periodicitá

    public Ordine() {

    }

    public Ordine(Ordine ordine) {
        this.codiceOrdine = ordine.getCodiceOrdine();
        this.idFarmacia = ordine.getIdFarmacia();
        this.nomeFarmacia = ordine.getNomeFarmacia();
        this.cap = ordine.getCap();
        this.indirizzoFarmacia = ordine.getIndirizzoFarmacia();
        this.prodotto = ordine.getProdotto();
        this.dataConsegna = ordine.getDataConsegna();
        this.dataOrdine = ordine.getDataOrdine();
        this.statoOrdine = ordine.getStatoOrdine();
        this.tipoOrdine = ordine.getTipoOrdine();
    }
    public String ordineprodottiToString(){
        String stringa=new String();
        for(int i=0;i< prodotto.size();i++)
            stringa+="id: "+prodotto.get(i).getCodiceUID()+" lotto: "+prodotto.get(i).getLotto()+" qta: "+prodotto.get(i).getQuantitá();
        return stringa;
    }
}
