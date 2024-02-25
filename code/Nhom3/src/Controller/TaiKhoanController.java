/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.TaiKhoan;
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
public class TaiKhoanController {
    static DBConnect cn = new DBConnect();

    public static List<TaiKhoan> getList() {
        Connection conn = null;
        Statement statement = null;
        List<TaiKhoan> lst = new ArrayList<>();
        
        try {
            conn = cn.getConnection();
            String sql = "select * from taikhoan";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                TaiKhoan tk = new TaiKhoan(resultSet.getString("tenTK"), resultSet.getString("matKhau"),
                                            resultSet.getString("role"), resultSet.getString("tinhtrang"),resultSet.getString("makh"));

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
    
    public static void insert(TaiKhoan tk) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "insert into taikhoan values(?,?,?,?,?)";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, tk.getTenTK());
            preparedStatement.setString(2, tk.getMatKhau());
            preparedStatement.setString(3, tk.getRole());
            preparedStatement.setString(4, tk.getTinhTrang());
            preparedStatement.setString(5, tk.getMaKh());
            
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
    
    public static void update(String matkhau, String role, String tenTK) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "update taikhoan set matkhau=?, role=? where tentk =? ";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(3, tenTK);
            preparedStatement.setString(1, matkhau);
            preparedStatement.setString(2, role);
            
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
    
    public static void updateStatus(String status, String tenTK) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = cn.getConnection();
            String sql = "update taikhoan set tinhtrang = ? where tentk =? ";
            
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, tenTK);
            
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

    public static List<TaiKhoan> searchByName(String tenTK) {
        Connection conn = null;
        PreparedStatement statement = null;
        List<TaiKhoan> lst = new ArrayList<>();
        try {
            conn = cn.getConnection();
            String sql = "select * from taikhoan where tentk like ?";
            statement = conn.prepareCall(sql);
            statement.setString(1, "%" + tenTK + "%");
            
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                TaiKhoan sp = new TaiKhoan(resultSet.getString("tentk"), resultSet.getString("matkhau"), 
                        resultSet.getString("role"), resultSet.getString("tinhtrang"),resultSet.getString("makh"));

                // Thêm đối tượng SanPham vào danh sách
                lst.add(sp);
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
}
