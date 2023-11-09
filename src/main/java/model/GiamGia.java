/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;


/**
 *
 * @author ACER
 */
public class GiamGia {
    private String maGG, tenGG, loaiMa;
    private Date tgDienRa, tgKetThuc;
    private Integer mucGG, trangThai;
    
    public GiamGia(){
        
    }
    
    public GiamGia(String maGG, String tenGG, String loaiMa, Integer mucGG, Integer trangThai, Date tgDienRa, Date tgKetThuc){
        this.maGG = maGG;
        this.tenGG = tenGG;
        this.loaiMa = loaiMa;
        this.mucGG = mucGG;
        this.trangThai = trangThai;
        this.tgDienRa = tgDienRa;
        this.tgKetThuc = tgKetThuc;
    }

    public String getMaGG() {
        return maGG;
    }

    public void setMaGG(String maGG) {
        this.maGG = maGG;
    }

    public String getTenGG() {
        return tenGG;
    }

    public void setTenGG(String tenGG) {
        this.tenGG = tenGG;
    }

    public String getLoaiMa() {
        return loaiMa;
    }

    public void setLoaiMa(String loaiMa) {
        this.loaiMa = loaiMa;
    }

    public Integer getMucGG() {
        return mucGG;
    }

    public void setMucGG(int mucGG) {
        this.mucGG = mucGG;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Date getTgDienRa() {
        return tgDienRa;
    }

    public void setTgDienRa(Date tgDienRa) {
        this.tgDienRa = tgDienRa;
    }

    public Date getTgKetThuc() {
        return tgKetThuc;
    }

    public void setTgKetThuc(Date tgKetThuc) {
        this.tgKetThuc = tgKetThuc;
    }
    
    
}
