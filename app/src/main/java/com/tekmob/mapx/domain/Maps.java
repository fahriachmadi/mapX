package com.tekmob.mapx.domain;

/**
 * Created by Conqueror on 10/29/2017.
 */
public class Maps {
    private int id;
    private String koordinatX;
    private String koordinatY;
    private String nama;

    public Maps(){}

    public Maps(String koordinatX, String koordinatY, String nama) {
        this.koordinatX = koordinatX;
        this.koordinatY = koordinatY;
        this.nama = nama;
    }

    public Maps(int id, String koordinatX, String koordinatY, String nama) {
        this.id = id;
        this.koordinatX = koordinatX;
        this.koordinatY = koordinatY;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKoordinatX() {
        return koordinatX;
    }

    public void setKoordinatX(String koordinatX) {
        this.koordinatX = koordinatX;
    }

    public String getKoordinatY() {
        return koordinatY;
    }

    public void setKoordinatY(String koordinatY) {
        this.koordinatY = koordinatY;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}