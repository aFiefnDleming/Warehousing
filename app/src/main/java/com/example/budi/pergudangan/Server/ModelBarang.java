package com.example.budi.pergudangan.Server;

public class ModelBarang {

    String idb, namab, lebar, panjang, tinggi, berat, harga, tgl_masuk, tujuan, qty, total;
    public ModelBarang(){}
    public ModelBarang(String idb, String namab, String lebar, String panjang, String tinggi, String berat, String harga, String tgl_masuk, String tujuan, String qty, String total) {

        this.idb = idb;
        this.namab = namab;
        this.lebar = lebar;
        this.panjang = panjang;
        this.tinggi = tinggi;
        this.berat = berat;
        this.harga = harga;
        this.tgl_masuk = tgl_masuk;
        this.tujuan = tujuan;
        this.qty = qty;
        this.total = total;
    }

    public String getIdb() {
        return idb;
    }

    public void setIdb(String idb) {
        this.idb = idb;
    }

    public String getNamab() {
        return namab;
    }

    public void setNamab(String namab) {
        this.namab = namab;
    }

    public String getLebar() {
        return lebar;
    }

    public void setLebar(String lebar) {
        this.lebar = lebar;
    }

    public String getPanjang() {
        return panjang;
    }

    public void setPanjang(String panjang) {
        this.panjang = panjang;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
