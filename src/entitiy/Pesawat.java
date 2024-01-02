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
public class Pesawat {
    private int id;
    private String namaPesawat;
    private float hargaPesawat;
    private Blob fotoPesawat;

    public Pesawat() {
    }

    public Pesawat(int id, String namaPesawat, float hargaPesawat, Blob fotoPesawat) {
        this.id = id;
        this.namaPesawat = namaPesawat;
        this.hargaPesawat = hargaPesawat;
        this.fotoPesawat = fotoPesawat;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPesawat() {
        return namaPesawat;
    }

    public void setNamaPesawat(String namaPesawat) {
        this.namaPesawat = namaPesawat;
    }

    public float getHargaPesawat() {
        return hargaPesawat;
    }

    public void setHargaPesawat(float hargaPesawat) {
        this.hargaPesawat = hargaPesawat;
    }

    public Blob getFotoPesawat() {
        return fotoPesawat;
    }

    public void setFotoPesawat(Blob fotoPesawat) {
        this.fotoPesawat = fotoPesawat;
    }
    
//    public Pesawat (int id,String namaPesawat,float hargaPesawat,Blob fotoPesawat){
//        this.id = id;
//        this.namaPesawat = namaPesawat;
//        this.hargaPesawat = hargaPesawat;
//        this.fotoPesawat = fotoPesawat;
//    }
}
