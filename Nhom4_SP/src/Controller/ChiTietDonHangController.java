/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.ChiTietDonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChiTietDonHangController {

    static DBConnect cn = new DBConnect();

    public static List<ChiTietDonHang> getList() {
        Connection conn = null;
        Statement statement = null;
        List<ChiTietDonHang> lstCTDH = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from chitietdonhang";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                ChiTietDonHang tk = new ChiTietDonHang(resultSet.getString("mactdh"), resultSet.getString("madh"),
                        resultSet.getString("masp"), resultSet.getInt("soluongmua"));
                // Thêm đối tượng SanPham vào danh sách
                lstCTDH.add(tk);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            // Đóng tất cả các resource sau khi sử dụng
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Lỗi khi đóng kết nối hoặc statement: " + ex.toString());
            }
        }

        // Trả về danh sách SanPham
        return lstCTDH;
    }

    public static void insert(ChiTietDonHang tk) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String sql = "insert into chitietdonhang values(?,?,?,?) ";

            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, tk.getMaChiTietDonHang());
            preparedStatement.setString(2, tk.getMaDH());
            preparedStatement.setString(3, tk.getMaSp());
            preparedStatement.setInt(4, tk.getSoLuongMua());

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
    
    public static void delete( String maCTDH) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "delete from chitietdonhang  where maCTDH = ?";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, maCTDH);
            
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
