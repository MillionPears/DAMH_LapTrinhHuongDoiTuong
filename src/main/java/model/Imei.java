/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Imei {
    private String soSeri;
    private String maSp;
    private int trangThai;

    public Imei() {
        this.trangThai=1;
    }

    public String getSoSeri() {
        return soSeri;
    }

    public Imei(String soSeri, String maSp) {
        this.soSeri = soSeri;
        this.maSp = maSp;
        this.trangThai=1;
    }
    

    public void setSoSeri(String soSeri) {
        this.soSeri = soSeri;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
}
