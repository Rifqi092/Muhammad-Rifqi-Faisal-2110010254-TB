/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitiy;

/**
 *
 * @author Rifqi
 */
public class KeyTujuan {
    private int keyT;
    private String valueT;
    private float hargaT;
    
       @Override
    public String toString(){
        return valueT;
    }
    public KeyTujuan(int keyT, String valueT ,float hargaT){
        this.keyT=keyT;
        this.valueT = valueT;
        this.hargaT=hargaT;
    }
    public KeyTujuan(String valueT){
        this.keyT = 0;
        this.valueT = valueT;
    }
    
    public KeyTujuan(){
    keyT= 0;
    valueT = "";
}

    public int getKeyT() {
        return keyT;
    }

    public void setKeyT(int keyT) {
        this.keyT = keyT;
    }

    public String getValueT() {
        return valueT;
    }

    public void setValueT(String valueT) {
        this.valueT = valueT;
    }

    public float getHargaT() {
        return hargaT;
    }

    public void setHargaT(float hargaT) {
        this.hargaT = hargaT;
    }
    
}
