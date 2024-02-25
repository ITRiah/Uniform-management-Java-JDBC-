/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Utils.GenerateID;
import java.util.Objects;

/**
 *
 * @author Administrator
 */
public class TaiKhoan {
    private String tenTK;
    private String matKhau;
    private String role;
    private String tinhTrang;
    private String maKh;
    
    public static int nextID = GenerateID.getNextID("khachhang", "makh",2);
    private static String generateID() {
        // Tạo mã dựa trên loạt số tự tăng
        String ID = "KH" + nextID;
        nextID++;
        return ID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
    public TaiKhoan() {
    }

    public TaiKhoan(String tenTK, String matKhau, String role, String tinhTrang, String maKh) {
        this.tenTK = tenTK;
        this.matKhau = matKhau;
        this.role = role;
        this.tinhTrang = tinhTrang;
        this.maKh = maKh;
    }

    public TaiKhoan(String tenTK, String matKhau, String role, String tinhTrang) {
        this.tenTK = tenTK;
        this.matKhau = matKhau;
        this.role = role;
        this.tinhTrang = tinhTrang;
        this.maKh = generateID();
    }
    
    @Override
    public String toString() {
        return "TaiKhoan{" + "tenTK=" + tenTK + ", matKhau=" + matKhau + ", role=" + role + ", tinhTrang=" + tinhTrang + ", maKh=" + maKh + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenTK);
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
        final TaiKhoan other = (TaiKhoan) obj;
        return Objects.equals(this.tenTK, other.tenTK);
    }
}
