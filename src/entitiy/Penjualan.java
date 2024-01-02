/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitiy;

import java.sql.Blob;

/**
 *
 * @author Rifqi
 */
public class Penjualan {
    private int id;
    private String tanggal;
    private String namaPenumpang;
    private String asal;
    private int jumlahTiket;
    private String jenisTiket;
    private float total;
    private Pesawat pesawat;
    private Tujuan tujuan;

    public Penjualan() {
    }

    public Penjualan(int id,String tanggal, String namaPenumpang, String asal, int jumlahTiket,String jenisTiket, float total, int idPesawat, String namaPesawat, float hargaPesawat, 
            Blob fotoPesawat, int idTujuan, String namaTujuan, float hargaTujuan ) {
        pesawat = new Pesawat();
		pesawat.setId(idPesawat);
        pesawat.setNamaPesawat(namaPesawat);
        pesawat.setHargaPesawat(hargaPesawat);
          pesawat.setFotoPesawat(fotoPesawat);
          
          tujuan = new Tujuan();
          tujuan.setId(idTujuan);
          tujuan.setNamaTujuan(namaTujuan);
          tujuan.setHargaTujuan(hargaTujuan);
          
          
        this.id = id;
        this.tanggal = tanggal;
        this.namaPenumpang = namaPenumpang;
        this.asal = asal;
        this.jumlahTiket = jumlahTiket;
        this.jenisTiket = jenisTiket;
        this.total = total;
//        this.pesawat = pesawat;
//        this.tujuan = tujuan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPenumpang() {
        return namaPenumpang;
    }

    public void setNamaPenumpang(String namaPenumpang) {
        this.namaPenumpang = namaPenumpang;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public String getJenisTiket() {
        return jenisTiket;
    }

    public void setJenisTiket(String jenisTiket) {
        this.jenisTiket = jenisTiket;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Pesawat getPesawat() {
        return pesawat;
    }

    public void setPesawat(Pesawat pesawat) {
        this.pesawat = pesawat;
    }

    public Tujuan getTujuan() {
        return tujuan;
    }

    public void setTujuan(Tujuan tujuan) {
        this.tujuan = tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    
}
