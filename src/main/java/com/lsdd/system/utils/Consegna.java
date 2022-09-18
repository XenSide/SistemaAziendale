package com.lsdd.system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@AllArgsConstructor
@Getter
public class Consegna {
    Integer iDConsegna;
    Date dataConsegna;
    Integer iDFarmacia;
    String nomeFarmacia;
    String cap;
    String indirizzo;

}
