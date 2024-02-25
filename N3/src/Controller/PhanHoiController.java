/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.PhanHoi;
import java.sql.Connection;
import java.sql.Date;
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
public class PhanHoiController {

    static DBConnect cn = new DBConnect();

    public static List<PhanHoi> getList() {
        Connection conn = null;
        Statement statement = null;
        List<PhanHoi> lst = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from phanhoi";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                PhanHoi tk = new PhanHoi(resultSet.getString("maph"), resultSet.getString("makh"),
                        resultSet.getString("hinhanh"), resultSet.getString("diemdg"), resultSet.getString("nhanxet")
                            ,resultSet.getString("madh"),resultSet.getDate("ngaytao").toLocalDate());

                // Thêm đối tượng SanPham vào danh sách
                lst.add(tk);
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
        return lst;
    }
    
    public static List<PhanHoi> searchByDiem(String diem) {
        Connection conn = null;
        Statement statement = null;
        List<PhanHoi> lst = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from phanhoi where diemdg= '" + diem + "'";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                PhanHoi tk = new PhanHoi(resultSet.getString("maph"), resultSet.getString("makh"),
                        resultSet.getString("hinhanh"), resultSet.getString("diemdg"), resultSet.getString("nhanxet")
                            ,resultSet.getString("madh"),resultSet.getDate("ngaytao").toLocalDate());

                // Thêm đối tượng SanPham vào danh sách
                lst.add(tk);
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
        return lst;
    }

    public static void insert(PhanHoi tk) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String sql = "insert into phanHoi values(?,?,?,?,?,?,?) ";

            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, tk.getMaPH());
            preparedStatement.setString(2, tk.getMaKH());
            preparedStatement.setString(3, tk.getHinhAnh());
            preparedStatement.setString(4, tk.getDiemDG());
            preparedStatement.setString(5, tk.getNhanXet());
            preparedStatement.setString(6, tk.getMaDH());
            preparedStatement.setDate(7, Date.valueOf(tk.getNgayTao()));

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
