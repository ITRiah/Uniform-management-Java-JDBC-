/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.SanPham;
import Entity.TaiKhoan;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DoiHangController {
    static DBConnect cn = new DBConnect();
     public static void DoiHang(TaiKhoan tk) {
         List<SanPham> lstSP = SanPhamController.getList();
         
    }
}
