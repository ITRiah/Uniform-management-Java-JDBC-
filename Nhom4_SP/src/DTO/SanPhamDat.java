/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class SanPhamDat {

    private String maGh;
    private String maSp;
    private String tenSp;
    private String size;
    private String mau;
    private double donGia;
    private int soLuongDat;
    private LocalDate ngayTao;

    public String getMaGh() {
        return maGh;
    }

    public void setMaGh(String maGh) {
        this.maGh = maGh;
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

    public int getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(int soLuongDat) {
        this.soLuongDat = soLuongDat;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public SanPhamDat() {
    }

    public SanPhamDat(String maGh, String maSp, String tenSp, String size, String mau, double donGia, int soLuongDat, LocalDate ngayTao) {
        this.maGh = maGh;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.size = size;
        this.mau = mau;
        this.donGia = donGia;
        this.soLuongDat = soLuongDat;
        this.ngayTao = ngayTao;
    }

    public SanPhamDat(String tenSp, String size, String mau, double donGia, int soLuongDat, LocalDate ngayTao) {
        this.tenSp = tenSp;
        this.size = size;
        this.mau = mau;
        this.donGia = donGia;
        this.soLuongDat = soLuongDat;
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return maSp + " - " + tenSp + " - size: " + size + " - giá : " + donGia + " - số lượng : " + soLuongDat + "- ngày đặt: " + ngayTao;
    }

}
