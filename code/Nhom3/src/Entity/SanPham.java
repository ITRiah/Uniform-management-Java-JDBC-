/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Utils.GenerateID;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author admin
 */
public class SanPham {

    private String maSp;
    private String tenSp;
    private String mau;
    private String size;
    private double donGia;
    private int soLuongCon;
    private String avatar;
    private String loai;
    private String gioiTinh;
    private String tinhTrang;
    

    public static int nextID = GenerateID.getNextID("sanpham", "masp",2);

    private static String generateID() {
        // Tạo mã dựa trên loạt số tự tăng
        String ID = "SP" + nextID;
        nextID++;
        return ID;
    }

    public SanPham() {
    }

    public SanPham(String maSp, String tenSp, String mau, String size, double donGia, int soLuongCon, String avatar, String loai, String gioiTinh, String tinhTrang) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.mau = mau;
        this.size = size;
        this.donGia = donGia;
        this.soLuongCon = soLuongCon;
        this.avatar = avatar;
        this.loai = loai;
        this.gioiTinh = gioiTinh;
        this.tinhTrang = tinhTrang;
    }
    
    public SanPham( String tenSp, String mau, String size, double donGia, int soLuongCon, String avatar, String loai, String gioiTinh, String tinhTrang) {
        this.maSp = generateID();
        this.tenSp = tenSp;
        this.mau = mau;
        this.size = size;
        this.donGia = donGia;
        this.soLuongCon = soLuongCon;
        this.avatar = avatar;
        this.loai = loai;
        this.gioiTinh = gioiTinh;
        this.tinhTrang = tinhTrang;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongCon() {
        return soLuongCon;
    }

    public void setSoLuongCon(int soLuongCon) {
        this.soLuongCon = soLuongCon;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.tenSp);
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
        final SanPham other = (SanPham) obj;
        return Objects.equals(this.tenSp, other.tenSp);
    }
    
    
   
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 97 * hash + Objects.hashCode(this.tenSp);
//        hash = 97 * hash + Objects.hashCode(this.size);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final SanPham other = (SanPham) obj;
//        if (!Objects.equals(this.tenSp, other.tenSp)) {
//            return false;
//        }
//        return Objects.equals(this.size, other.size);
//    }

    public static SanPham getSanPhamByID(String maSp, List<SanPham> listSp) {
        SanPham sanpham = new SanPham();
        for (SanPham sp : listSp) {
            if (sp.getMaSp().equals(maSp)) {
                sanpham = sp;
                break;
            }
        }
        return sanpham;
    }

    @Override
    public String toString() {
        return "SanPham{" + "maSp=" + maSp + ", tenSp=" + tenSp + ", mau=" + mau + ", size=" + size + ", donGia=" + donGia + ", soLuongCon=" + soLuongCon + ", avatar=" + avatar + ", loai=" + loai + ", gioiTinh=" + gioiTinh + ", tinhTrang=" + tinhTrang + '}';
    }    
}
