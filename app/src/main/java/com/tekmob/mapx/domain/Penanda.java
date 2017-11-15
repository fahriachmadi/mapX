package com.tekmob.mapx.domain;

/**
 * Created by Conqueror on 10/29/2017.
 */
public class Penanda {
    private int id;
    private int idMaps;
    private int idUser;

    private String nama;
    private String keterangan;
    private String kategori;
    private String timestamp;

    public Penanda(){}

    public Penanda(int idMaps, String nama, String keterangan, String kategori, String timestamp, int idUser) {
        this.idMaps = idMaps;
        this.nama = nama;
        this.keterangan = keterangan;
        this.kategori = kategori;
        this.timestamp = timestamp;
        this.idUser = idUser;
    }

    public Penanda(int id, int idMaps, String nama, String keterangan, String kategori, String timestamp, int idUser) {
        this.id = id;
        this.idMaps = idMaps;
        this.nama = nama;
        this.keterangan = keterangan;
        this.kategori = kategori;
        this.timestamp = timestamp;
        this.idUser = idUser;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    public int getIdMaps() {
        return idMaps;
    }

    public void setIdMaps(int idMaps) {
        this.idMaps = idMaps;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
