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
public class KeyPesawat {
    private int keyP;
    private String valueP;
    private float hargaP;
    private Blob fotoP;
    
    @Override
    public String toString(){
        return valueP;
    }
    public KeyPesawat(int keyP, String valueP ,float hargaP, Blob fotoP){
        this.keyP=keyP;
        this.valueP = valueP;
        this.hargaP=hargaP;
        this.fotoP = fotoP;
    }
    public KeyPesawat(String valueP){
        this.keyP = 0;
        this.valueP = valueP;
    }
    
    public KeyPesawat(){
    keyP= 0;
    valueP = "";
}

    public int getKeyP() {
        return keyP;
    }

    public void setKeyP(int keyP) {
        this.keyP = keyP;
    }

    public String getValueP() {
        return valueP;
    }

    public void setValueP(String valueP) {
        this.valueP = valueP;
    }

    public float getHargaP() {
        return hargaP;
    }

    public void setHargaP(float hargaP) {
        this.hargaP = hargaP;
    }

    public Blob getFotoP() {
        return fotoP;
    }

    public void setFotoP(Blob fotoP) {
        this.fotoP = fotoP;
    }
    
    
    
}
