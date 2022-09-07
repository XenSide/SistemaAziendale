package com.lsdd.system.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Utente {
    @Getter
    private static Utente user = null;
    private final int id;
    private final int type;
    private final String email;
    private final String nome;
    private final String cognome;
    private final String UIDFarmacia;

    public static Utente createInstance(int id, int type, String email, String name, String surname) {
        user = new Utente(id, type, email, name, surname, null);
        return user;
    }

    public static Utente createInstance(int id, int type, String email, String name, String cognome, String UIDFarmacia) {
        user = new Utente(id, type, email, name, cognome, UIDFarmacia);
        return user;
    }

    public static boolean isAuthenticated() {
        return user != null;
    }

    public void logout() {
        user = null;
    }
}