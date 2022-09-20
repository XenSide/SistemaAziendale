package com.lsdd.system.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;

@Getter
@Setter
public class Richiesta {
    LinkedList<Prodotto> prodotti = new LinkedList<Prodotto>();

    public Richiesta(Integer idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    private Integer idFarmacia;
    private Date data;
    private boolean fattura=false;

    public Richiesta(LinkedList<Prodotto> prodotti, Integer idFarmacia, Date data) {
        this.prodotti = prodotti;
        this.idFarmacia = idFarmacia;
        this.data = data;
    }

    public void aggiungiProdotto(Prodotto prodotto) {
        prodotti.push(prodotto);
    }

    public Prodotto pop() {
        return prodotti.pop();
    }

    public Prodotto getFirst() {
        return prodotti.getFirst();
    }

    public Integer getSize() {
        return prodotti.size();
    }

    public void setQtaProdotto(int qta) {
        prodotti.getFirst().setQuantitá(qta);
    }

    public void subtractQtaProdotto(int qta) {
        Prodotto prodotto = prodotti.getFirst();
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
