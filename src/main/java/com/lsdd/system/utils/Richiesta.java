package com.lsdd.system.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Richiesta {
    List<Prodotto> prodotti;
    private Integer idFarmacia;
    private Date data;

    public Richiesta(List<Prodotto> prodotti, Integer idFarmacia, Date data) {
        this.prodotti = prodotti;
        this.idFarmacia = idFarmacia;
        this.data = data;
    }

    public Richiesta() {

    }

    private void aggiungiProdotto(Prodotto prodotto) {
        prodotti.add(prodotto);
    }
}
