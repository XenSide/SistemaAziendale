package com.lsdd.system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;
@Getter
@AllArgsConstructor
public class Ordine {
    private Integer codiceOrdine;
    private String nomeFarmacia;
    private String indirizzoFarmacia;
    private List<Prodotto> prodotto;
    private Date dataConsegna;
    private Date dataOrdine;
    private Integer statoOrdine;
    private Integer tipoOrdine;
}
