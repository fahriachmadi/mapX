package com.tekmob.mapx.domain;

/**
 * Created by Conqueror on 10/29/2017.
 */
public class Akun {
    private int id;
    private String username;
    private String password;
    private String email;

    public Akun(){}

    public Akun(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Akun(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
