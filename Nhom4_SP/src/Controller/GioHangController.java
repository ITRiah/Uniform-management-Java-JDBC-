/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Controller.TaiKhoanController.cn;
import DBConnect.DBConnect;
import DTO.SanPhamDat;
import Entity.GioHang;
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
public class GioHangController {

    static DBConnect cn = new DBConnect();

    public static List<SanPhamDat> getList(String maKH) {
        Connection conn = null;
        Statement statement = null;
        List<SanPhamDat> lst = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from giohang inner join sanpham on giohang.masp = sanpham.masp where makh = '" + maKH + "'";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                SanPhamDat tk;
                tk = new SanPhamDat(resultSet.getString("maGioHang"),resultSet.getString("maSP"), resultSet.getString("tenSP"),resultSet.getString("mau"), resultSet.getString("size"),
                        resultSet.getDouble("dongia"), resultSet.getInt("soluongdat"), resultSet.getDate("ngaytao").toLocalDate());
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
    
    public static List<GioHang> getAll() {
        Connection conn = null;
        Statement statement = null;
        List<GioHang> lst = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from giohang";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                GioHang tk;
                tk = new GioHang(resultSet.getString("maGioHang"),resultSet.getString("maKH"), resultSet.getString("maSP"),
                       resultSet.getInt("soluongdat"), resultSet.getDate("ngaytao").toLocalDate());
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
    
    public static List<SanPhamDat> getAll(String maKH) {
        Connection conn = null;
        Statement statement = null;
        List<SanPhamDat> lst = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from giohang inner join sanpham on giohang.masp = sanpham.masp where makh = '" + maKH + "'";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                SanPhamDat tk = new SanPhamDat(resultSet.getString("maGioHang"),resultSet.getString("maSP"), resultSet.getString("tenSP"),resultSet.getString("mau"), resultSet.getString("size"),
                        resultSet.getDouble("dongia"), resultSet.getInt("soluongdat"), resultSet.getDate("ngaytao").toLocalDate());

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
    
    public static void delete(SanPhamDat sp , String maKH) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "delete from giohang  where masp = ? and makh =? ";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, sp.getMaSp());
            preparedStatement.setString(2, maKH);
            
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
    
    public static void huyGH(String maGH) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "delete from giohang where magiohang = ?";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, maGH);
            
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
    
    public static void deleteAll(String maKH) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "delete from giohang  where makh =? ";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, maKH);
            
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
