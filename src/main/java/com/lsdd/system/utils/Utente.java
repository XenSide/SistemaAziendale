package com.lsdd.system.utils;

import lombok.Getter;

@Getter
public class Utente {
    @Getter
    private static Utente user = null;
    private final int id;
    private final int type;
    private final String email;

    private final String password;
    private final String nome;
    private final String cognome;
    private Integer UIDFarmacia;

    public Utente(int id, int type, String email, String nome, String cognome, String password) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Utente(int id, int type, String email, String nome, String password, String cognome, Integer UIDFarmacia) {
        this(id, type, email, password, nome, cognome);
        this.UIDFarmacia = UIDFarmacia;
    }

    public static Utente createInstance(int id, int type, String email, String name, String surname, String password) {
        user = new Utente(id, type, email, name, surname, password, null);
        return user;
    }

    public static Utente createInstance(int id, int type, String email, String name, String cognome, String password, Integer UIDFarmacia) {
        user = new Utente(id, type, email, name, cognome, password, UIDFarmacia);
        return user;
    }

    public static boolean isAuthenticated() {
        return user != null;
    }

    public void logout() {
        user = null;
    }
}