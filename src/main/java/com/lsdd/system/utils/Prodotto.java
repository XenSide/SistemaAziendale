package com.lsdd.system.utils;

import lombok.AllArgsConstructor;

import java.util.Date;
@AllArgsConstructor
public class Prodotto {
    private String nome;
    private String principioAttivo;
    private int codiceUID;
    private String lotto;
    private Date dataScadenza;
    private Date dataProduzione;
    private int costo;
    private int quantitá;
    private boolean DaBanco;
}
