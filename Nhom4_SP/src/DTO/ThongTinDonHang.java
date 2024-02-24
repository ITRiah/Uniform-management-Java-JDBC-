/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import Entity.ChiTietDonHang;
import Entity.SanPham;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ThongTinDonHang {

    private String maDH;
    private String maKh;
    private String hoTen;
    private String soDt;
    private String diaChi;
    private String ghiChu;
    private String trangThai; //Chờ duyệt, Đã duyệt, Đã hoàn thành, Khách hủy, Từ chối
    private double tongTien;
    private String ngayDat;
    private String loaiDon;
    private List<SanPham> listSp;

    public ThongTinDonHang() {

    }

    public ThongTinDonHang(String maDH, String maKh, String hoTen, String soDt, String diaChi, String ghiChu, String trangThai, double tongTien, String ngayDat, String loaiDon,List<SanPham> listSp) {
        this.maDH = maDH;
        this.maKh = maKh;
        this.hoTen = hoTen;
        this.soDt = soDt;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.ngayDat = ngayDat;
        this.loaiDon = loaiDon;
        this.listSp = listSp;
    }

    public String getMaDH() {
        return maDH;
    }

    public String getLoaiDon() {
        return loaiDon;
    }

    public void setLoaiDon(String loaiDon) {
        this.loaiDon = loaiDon;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaKh() {
        return maKh;
    }

    public List<SanPham> getListSp() {
        return listSp;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public void setListSp(List<SanPham> listSp) {
        this.listSp = listSp;
    }

    public static List<ChiTietDonHang> getListChiTietDhByMaDh(String maDh, List<ChiTietDonHang> listCTDH) {
        List<ChiTietDonHang> result = new ArrayList<>();
        for (ChiTietDonHang item : listCTDH) {
            if (item.getMaDH().equals(maDh)) {
                result.add(item);
            }
        }
        return result;
    }
}
