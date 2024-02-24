/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class KhachHangController {

    static DBConnect cn = new DBConnect();

    public static void insert(KhachHang tk) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String sql = "insert into khachhang values(?,?,?,?,?,?,?,?,?) ";

            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, tk.getMaKh());
            preparedStatement.setString(2, tk.getHoTen());
            preparedStatement.setString(3, tk.getNgaySinh());
            preparedStatement.setString(4, tk.getDiaChi());
            preparedStatement.setString(5, tk.getSoDT());
            preparedStatement.setString(6, tk.getEmail());
            preparedStatement.setString(7, tk.getKhoa());
            preparedStatement.setString(8, tk.getLop());
            preparedStatement.setString(9, tk.getAvatar());

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
    
    public static void update(KhachHang tk) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String sql = "update khachhang set hoten =?,ngaysinh=?, diachi=?, sodt=?, email =?, khoa=?,lop=?, avatar =? where makh = ?";

            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(9, tk.getMaKh());
            preparedStatement.setString(1, tk.getHoTen());
            preparedStatement.setString(2, tk.getNgaySinh());
            preparedStatement.setString(3, tk.getDiaChi());
            preparedStatement.setString(4, tk.getSoDT());
            preparedStatement.setString(5, tk.getEmail());
            preparedStatement.setString(6, tk.getKhoa());
            preparedStatement.setString(7, tk.getLop());
            preparedStatement.setString(8, tk.getAvatar());

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
    
    
    public static KhachHang getThongTin(String maKH) {
        Connection conn = null;
        Statement statement = null;
        KhachHang kh = null;

        try {
            conn = cn.getConnection();
            String sql = "select * from khachhang where makh = '" + maKH + "'";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                kh = new KhachHang(resultSet.getString("makh"),resultSet.getString("hoten"),resultSet.getString("ngaysinh"),
                        resultSet.getString("diachi"),resultSet.getString("sodt"),resultSet.getString("email"),resultSet.getString("khoa"),
                        resultSet.getString("lop"),resultSet.getString("avatar"));
            }
            System.out.println(kh.toString());
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
        return kh;
    }
}
