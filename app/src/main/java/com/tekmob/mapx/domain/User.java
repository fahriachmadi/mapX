package com.tekmob.mapx.domain;

/**
 * Created by Conqueror on 10/29/2017.
 */
public class User {
    private int id;
    private int idAkun;
    private String tanggalLahir;
    private String noTelepon;
    private String jenisKelamin;
    private String negara;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private String alamat;
    private String foto;

    public User(){}

    public User(int idAkun, String tanggalLahir, String noTelepon, String jenisKelamin, String negara, String provinsi, String kota, String kecamatan, String alamat, String foto) {
        this.idAkun = idAkun;
        this.tanggalLahir = tanggalLahir;
        this.noTelepon = noTelepon;
        this.jenisKelamin = jenisKelamin;
        this.negara = negara;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.alamat = alamat;
        this.foto = foto;
    }

    public User(int id, int idAkun, String tanggalLahir, String noTelepon, String jenisKelamin, String negara, String provinsi, String kota, String kecamatan, String alamat, String foto) {
        this.id = id;
        this.idAkun = idAkun;
        this.tanggalLahir = tanggalLahir;
        this.noTelepon = noTelepon;
        this.jenisKelamin = jenisKelamin;
        this.negara = negara;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.alamat = alamat;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public int getIdAkun() {
        return idAkun;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getNegara() {
        return negara;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getKota() {
        return kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
