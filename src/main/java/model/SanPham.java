/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class SanPham {
    private String maSp;
    private String tenSp;
    private String Loai;
    private String ram,pin,rom,manHinh;
    private int soLuong,tonKho,gia;
    private String HDH,CPU,kichThuoc,hang,xuatxu,camera,mausac,Imel;
    private int trangthai=1;
    private String sacNhanh;
    private String hinhAnh;
   
    public SanPham() {
    }

    

    public SanPham(String maSp, String tenSp, String Loai, String ram, String pin, String rom, String manHinh, int soLuong, int tonKho, int gia, String HDH, String CPU, String kichThuoc, String hang, String xuatxu, String camera, String mausac, String Imel) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.Loai = Loai;
        this.ram = ram;
        this.pin = pin;
        this.rom = rom;
        this.manHinh = manHinh;
        this.soLuong = soLuong;
        this.tonKho = tonKho;
        this.gia = gia;
        this.HDH = HDH;
        this.CPU = CPU;
        this.kichThuoc = kichThuoc;
        this.hang = hang;
        this.xuatxu = xuatxu;
        this.camera = camera;
        this.mausac = mausac;
        this.Imel = Imel;
    }

    
    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getSacNhanh() {
        return sacNhanh;
    }

    public void setSacNhanh(String sacNhanh) {
        this.sacNhanh = sacNhanh;
    }

   

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

   

    public String getHDH() {
        return HDH;
    }

    public void setHDH(String HDH) {
        this.HDH = HDH;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        this.xuatxu = xuatxu;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }

    public String getImel() {
        return Imel;
    }

    public void setImel(String Imel) {
        this.Imel = Imel;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String Loai) {
        this.Loai = Loai;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getManHinh() {
        return manHinh;
    }

    public void setManHinh(String manHinh) {
        this.manHinh = manHinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
    
    
}
