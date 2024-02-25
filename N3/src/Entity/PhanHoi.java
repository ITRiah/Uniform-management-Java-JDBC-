/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Utils.GenerateID;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class PhanHoi{
    private static int maPHCounter = 0; 

    private String maPH;
    private String maKH;
    private String hinhAnh;
    private String diemDG;
    private String nhanXet;
    private String maDH;
    private LocalDate ngayTao;

    public static int nextID = GenerateID.getNextID("phanhoi", "maPH",2);
    private static String generateID() {
        // Tạo mã dựa trên loạt số tự tăng
        String ID = "PH" + nextID;
        nextID++;
        return ID;
    }

    public PhanHoi() {
        
    }

    public PhanHoi(String maPH, String maKH, String hinhAnh, String diemDG, String nhanXet, String maDH,LocalDate ngayTao) {
        this.maPH = maPH;
        this.maKH = maKH;
        this.hinhAnh = hinhAnh;
        this.diemDG = diemDG;
        this.nhanXet = nhanXet;
        this.maDH = maDH;
        this.ngayTao = ngayTao;
    }
    
    public PhanHoi( String maKH, String hinhAnh, String diemDG, String nhanXet, String maDH,LocalDate ngayTao) {
        this.maPH = generateID();
        this.maKH = maKH;
        this.hinhAnh = hinhAnh;
        this.diemDG = diemDG;
        this.nhanXet = nhanXet;
        this.maDH = maDH;
        this.ngayTao = ngayTao;
    }

    public static int getMaPHCounter() {
        return maPHCounter;
    }

    public static void setMaPHCounter(int maPHCounter) {
        PhanHoi.maPHCounter = maPHCounter;
    }

    public String getMaPH() {
        return maPH;
    }

    public void setMaPH(String maPH) {
        this.maPH = maPH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getDiemDG() {
        return diemDG;
    }

    public void setDiemDG(String diemDG) {
        this.diemDG = diemDG;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    public static int getNextID() {
        return nextID;
    }

    public static void setNextID(int nextID) {
        PhanHoi.nextID = nextID;
    }
}
