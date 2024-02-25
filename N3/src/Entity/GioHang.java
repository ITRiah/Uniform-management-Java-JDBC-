/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Utils.GenerateID;
import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class GioHang {

    private String maGioHang;
    private String maKh;
    private String maSp;
    private int soLuongDat;
    private LocalDate ngayTao;

    public static int nextID = GenerateID.getNextID("giohang", "magiohang", 2);

    private static String generateID() {
        // Tạo mã dựa trên loạt số tự tăng
        String ID = "GH" + nextID;
        nextID++;
        return ID;
    }

    public String getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(String maGioHang) {
        this.maGioHang = maGioHang;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public int getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(int soLuongDat) {
        this.soLuongDat = soLuongDat;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    

    public GioHang() {
    }

    public GioHang(String maKh, String maSp, int soLuongDat) {
        this.maGioHang = generateID();
        this.maKh = maKh;
        this.maSp = maSp;
        this.soLuongDat = soLuongDat;
        ngayTao = LocalDate.now();
    }

    public GioHang(String maGioHang, String maKh, String maSp, int soLuongDat, LocalDate ngayTao) {
        this.maGioHang = maGioHang;
        this.maKh = maKh;
        this.maSp = maSp;
        this.soLuongDat = soLuongDat;
        this.ngayTao = ngayTao;
    }

    public GioHang(String maGh, String maKh, String maSp, int soLuongDat) {
        this.maGioHang = maGh;
        this.maKh = maKh;
        this.maSp = maSp;
        this.soLuongDat = soLuongDat;
        ngayTao = LocalDate.now();
    }

    @Override
    public String toString() {
        return maGioHang + "," + maKh + "," + maSp + "," + soLuongDat + "," +ngayTao;
    }
}
