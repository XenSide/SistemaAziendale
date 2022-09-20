package com.lsdd.system.utils;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TempoC extends Thread{

    @Getter(lazy = true)
    private static final TempoC instance = new TempoC(getInstance().idFarmacia);

    @Getter
    @Setter
    private boolean alertsToRead = false;
    private final List<Prodotto> prodottos = new ArrayList<>();
    private final Integer idFarmacia;


    private TempoC(Integer idFarmacia) {
        this.checksAzienda();
        this.removeProdottiScaduti();
        //this.checkAlerts();
        this.checksFarmacia();
        this.idFarmacia=idFarmacia;
    }

    private void checksAzienda() {// metodo controllo magazzino farmaco < 50
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Timer time = new Timer();
        time.schedule(new TimerTask() {
                          @Override
                          public void run() {/*
                              DDBMS.getAzienda().getListaOrdiniPeriodiciRicevuti().thenAccept(ordines -> {
                                  if (ordines.isEmpty()) return;
                                      DDBMS.getAzienda().getListaOrdiniRicevuti().thenAccept(ordines1 -> {
                                          if (ordines1.isEmpty()) return;
                                          ordines1.addAll(ordines);
                                          for (int i=0;i<ordines.size();i++) {
                                              if(ordines.get(i).getStatoOrdine()<3)
                                                  for (int j=0;j<ordines.get(i).getProdotto().size();j++) {
                                                    ordines1.get(i).getProdotto().get(j).getCodiceUID();
                                                  addTo
                                              }
                                          }
                                      });
                              });*/
                              DDBMS.getAzienda().getListaOrdiniPeriodiciInAttesa().thenAccept(ordines -> {
                                  if (ordines.isEmpty()) return;
                                  for (int i=0;i<ordines.size();i++) {
                                      DDBMS.getAzienda().confermaOrdine(ordines.get(i).getCodiceOrdine(),true);
                                  }
                              });

                              DDBMS.getAzienda().getNotifiche().thenAccept(notificas -> {
                                  if (notificas.isEmpty()) return;
                                  for (int i=0;i<notificas.size();i++) {
                                          for (int j=0;j<notificas.size();j++) {
                                              Utils.showAlert("Hai un ordine parziale da revisionare id: "+notificas.get(i).getIdOrdine());

                                          }
                                  }
                              });
                          }
                      },
                calendar.getTime(), TimeUnit.HOURS.toMillis(24));
    }
    private void removeProdottiScaduti() {// metodo controllo magazzino farmaco < 50
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Timer time = new Timer();
        time.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              if(idFarmacia!=null)
                                DDBMS.getFarmacia().rimuoviProdottiScaduti();
                              else
                                  DDBMS .getAzienda().rimuoviProdottiScaduti();
                          }
                      },
                calendar.getTime(), TimeUnit.HOURS.toMillis(24));
    }


    private void checksFarmacia() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Timer time = new Timer();
        time.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              Utils.showAlert("Ricordati di caricare i prodotti!");
                          }
                      },
                calendar.getTime(), TimeUnit.HOURS.toMillis(24));
    }



}