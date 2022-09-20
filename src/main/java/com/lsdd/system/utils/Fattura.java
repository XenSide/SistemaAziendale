package com.lsdd.system.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Fattura {
    List<Prodotto> prodotti;
    private Integer idFarmacia;
    private Date data;

    public Fattura(List<Prodotto> prodotti, Integer idFarmacia, Date data) {
        this.prodotti = prodotti;
        this.idFarmacia = idFarmacia;
        this.data = data;
    }

    public Fattura(int idFarmacia) {
        this.idFarmacia=idFarmacia;
    }
    public void aggiungiProdotto(Prodotto prodotto) {
        prodotti.add(0,prodotto);
    }

    public Prodotto pop() {
        return prodotti.remove(prodotti.size()-1);
    }

    public Prodotto getFirst() {
        return prodotti.get(0);
    }

    public Integer getSize() {
        return prodotti.size();
    }

    public void setQtaProdotto(int qta) {
        prodotti.get(0).setQuantitá(qta);
    }

    public void subtractQtaProdotto(int qta) {
        Prodotto prodotto = prodotti.get(0);
        prodotto.setQuantitá(prodotto.getQuantitá() - qta);
    }

    @Override
    public String toString() {
        String temp = null;
        for (int i = 0; i < prodotti.size(); i++) {
            if (i == 0)
                temp = prodotti.get(i).toString();
            else
                temp = temp + " " + prodotti.get(i);
        }
        return temp;
    }
}
