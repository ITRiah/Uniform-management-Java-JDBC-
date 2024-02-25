/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.DonHang;
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
public class DonHangController {

    static DBConnect cn = new DBConnect();

    public static List<DonHang> getList() {
        Connection conn = null;
        Statement statement = null;
        List<DonHang> lstDonHang = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from donhang";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                DonHang tk = new DonHang(resultSet.getString("madh"), resultSet.getString("makh"),
                        resultSet.getString("hoten"), resultSet.getString("sodt"),
                        resultSet.getString("diachi"), resultSet.getString("ghichu"),
                        resultSet.getString("trangthai"), resultSet.getDouble("tongtien"),
                        resultSet.getString("ngaydat"), resultSet.getString("loaidon"));
                // Thêm đối tượng SanPham vào danh sách
                lstDonHang.add(tk);
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
        return lstDonHang;
    }
    
    public static List<DonHang> getListDHByMaKH(String maKH) {
        Connection conn = null;
        Statement statement = null;
        List<DonHang> lstDonHang = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from donhang where makh = '" + maKH + "'";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                DonHang tk = new DonHang(resultSet.getString("madh"), resultSet.getString("makh"),
                        resultSet.getString("hoten"), resultSet.getString("sodt"),
                        resultSet.getString("diachi"), resultSet.getString("ghichu"),
                        resultSet.getString("trangthai"), resultSet.getDouble("tongtien"),
                        resultSet.getString("ngaydat"), resultSet.getString("loaidon"));
                // Thêm đối tượng SanPham vào danh sách
                lstDonHang.add(tk);
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
        return lstDonHang;
    }
    
    public static DonHang getDonHangByID(String maDH) {
        Connection conn = null;
        Statement statement = null;
        DonHang dh =  null;
        try {
            conn = cn.getConnection();
            String sql = "select * from donhang where madh = '" +maDH + "'";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                dh= new DonHang(resultSet.getString("madh"), resultSet.getString("makh"),
                        resultSet.getString("hoten"), resultSet.getString("sodt"),
                        resultSet.getString("diachi"), resultSet.getString("ghichu"),
                        resultSet.getString("trangthai"), resultSet.getDouble("tongtien"),
                        resultSet.getString("ngaydat"), resultSet.getString("loaidon"));
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
        return dh;
    }
    
     public static void updateDoiHang(DonHang sp) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "update donhang set trangthai = ? where madh =? ";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, "Chờ duyệt");
            preparedStatement.setString(2, sp.getMaDH());
            
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
     
     public static void delete(String maDH) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "delete from donhang  where madh =? ";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, maDH);
            
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
