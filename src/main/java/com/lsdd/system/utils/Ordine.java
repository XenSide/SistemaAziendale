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
    private String nomeFarmacia;
    private String indirizzoFarmacia;
    private List<Prodotto> prodotto;
    private Date dataConsegna;
    private Date dataOrdine;
    private Integer statoOrdine;
    private Integer tipoOrdine; //se 0 non periodico, sennò numero di giorni di periodicitá
}
