/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.GioHang;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ThemVaoGioHangController {

    static DBConnect cn = new DBConnect();

    public static void insert(GioHang gh) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String insertSql = "INSERT INTO giohang VALUES (?, ?, ?, ?,?)";

            preparedStatement = conn.prepareStatement(insertSql);
            preparedStatement.setString(1, gh.getMaGioHang());
            preparedStatement.setString(2, gh.getMaKh());
            preparedStatement.setString(3, gh.getMaSp());
            preparedStatement.setInt(4, gh.getSoLuongDat());
            preparedStatement.setDate(5, Date.valueOf(gh.getNgayTao()));

            preparedStatement.execute();         
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            // Đóng tất cả các resource sau khi sử dụng
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Lỗi khi đóng kết nối hoặc statement: " + ex.toString());
            }
        }
    }
}

