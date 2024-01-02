/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitiy;

/**
 *
 * @author Rifqi
 */
public class Tujuan {
    private int id;
    private String namaTujuan;
    private float hargaTujuan;

    public Tujuan() {
    }

    public Tujuan(int id, String namaTujuan, float hargaTujuan) {
        this.id = id;
        this.namaTujuan = namaTujuan;
        this.hargaTujuan = hargaTujuan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaTujuan() {
        return namaTujuan;
    }

    public void setNamaTujuan(String namaTujuan) {
        this.namaTujuan = namaTujuan;
    }

    public float getHargaTujuan() {
        return hargaTujuan;
    }

    public void setHargaTujuan(float hargaTujuan) {
        this.hargaTujuan = hargaTujuan;
    }
    
}
