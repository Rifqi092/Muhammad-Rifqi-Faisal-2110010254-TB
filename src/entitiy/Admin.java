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
public class Admin {
      private int id;
    private String username;
    private String password;
    private String namaAdmin;
    private Blob fotoAdmin;

    public Admin() {
    }

    public Admin(int id, String username, String password, String namaAdmin, Blob fotoAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.namaAdmin = namaAdmin;
        this.fotoAdmin = fotoAdmin;
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

    public String getNamaAdmin() {
        return namaAdmin;
    }

    public void setNamaAdmin(String namaAdmin) {
        this.namaAdmin = namaAdmin;
    }

    public Blob getFotoAdmin() {
        return fotoAdmin;
    }

    public void setFotoAdmin(Blob fotoAdmin) {
        this.fotoAdmin = fotoAdmin;
    }

  
    
}
