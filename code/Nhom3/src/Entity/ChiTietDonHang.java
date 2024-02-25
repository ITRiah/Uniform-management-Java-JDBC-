/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Utils.GenerateID;
import java.util.Objects;

/**
 *
 * @author admin
 */
public class ChiTietDonHang {
    private String maChiTietDonHang;
    private String maDH;
    private String maSp;
    private  int soLuongMua;
    
    public static int nextID = GenerateID.getNextID("chitietdonhang", "mactdh",2); 
    private static String generateID() {
        // Tạo mã dựa trên loạt số tự tăng
        String ID = "CT" + nextID;
        nextID++;
        return ID;
    }
    public String getMaChiTietDonHang() {
        return maChiTietDonHang;
    }

    public void setMaChiTietDonHang(String maChiTietDonHang) {
        this.maChiTietDonHang = maChiTietDonHang;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
    

    

    public ChiTietDonHang() {
    }

    public ChiTietDonHang(String maChiTietDonHang, String maDH, String maSp, int soLuongMua) {
        this.maChiTietDonHang = maChiTietDonHang;
        this.maDH = maDH;
        this.maSp = maSp;
        this.soLuongMua = soLuongMua;
    }
    public ChiTietDonHang(String maDH, String maSp, int soLuongMua) {
        this.maChiTietDonHang = generateID();
        this.maDH = maDH;
        this.maSp = maSp;
        this.soLuongMua = soLuongMua;
    }

    public ChiTietDonHang(String maDH, String maSp) {
        this.maChiTietDonHang = generateID();
        this.maDH = maDH;
        this.maSp = maSp;
    }
    
    @Override
    public String toString() {
        return maChiTietDonHang + "," + maDH + "," + maSp + "," + soLuongMua;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.maDH);
        hash = 67 * hash + Objects.hashCode(this.maSp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChiTietDonHang other = (ChiTietDonHang) obj;
        if (!Objects.equals(this.maDH, other.maDH)) {
            return false;
        }
        return Objects.equals(this.maSp, other.maSp);
    }
}
