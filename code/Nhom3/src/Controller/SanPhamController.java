/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.SanPham;
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
public class SanPhamController {

    static DBConnect cn = new DBConnect();

    public static List<SanPham> getList() {
        Connection conn = null;
        Statement statement = null;
        List<SanPham> lst = new ArrayList<>();

        try {
            conn = cn.getConnection();
            String sql = "select * from sanpham";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                SanPham tk = new SanPham(resultSet.getString("masp"), resultSet.getString("tensp"), resultSet.getString("mau"),
                        resultSet.getString("size"), resultSet.getDouble("donGia"), resultSet.getInt("soLuongCon"),
                        resultSet.getString("avatar"), resultSet.getString("loai"), resultSet.getString("gioitinh"), resultSet.getString("tinhtrang"));

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

    public static void insert(SanPham tk) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String sql = "insert into sanpham values(?,?,?,?,?,?,?,?,?,?) ";

            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, tk.getMaSp());
            preparedStatement.setString(2, tk.getTenSp());
            preparedStatement.setString(3, tk.getMau());
            preparedStatement.setString(4, tk.getSize());
            preparedStatement.setDouble(5, tk.getDonGia());
            preparedStatement.setInt(6, tk.getSoLuongCon());
            preparedStatement.setString(7, tk.getAvatar());
            preparedStatement.setString(8, tk.getLoai());
            preparedStatement.setString(9, tk.getGioiTinh());
            preparedStatement.setString(10, tk.getTinhTrang());

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

    public static void update(SanPham sp) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String sql = "UPDATE sanpham SET tensp=?, mau=?, size=?, dongia=?, soluongcon=?, avatar=?, loai=?, gioitinh=?, tinhtrang=? WHERE masp=?";
            preparedStatement = conn.prepareCall(sql);
            preparedStatement.setString(1, sp.getTenSp());
            preparedStatement.setString(2, sp.getMau());
            preparedStatement.setString(3, sp.getSize());
            preparedStatement.setDouble(4, sp.getDonGia());
            preparedStatement.setInt(5, sp.getSoLuongCon());
            preparedStatement.setString(6, sp.getAvatar());
            preparedStatement.setString(7, sp.getLoai());
            preparedStatement.setString(8, sp.getGioiTinh());
            preparedStatement.setString(9, sp.getTinhTrang());
            preparedStatement.setString(10, sp.getMaSp());

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

//    public static void delete(String MaSP) {
//        Connection conn = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            conn = cn.getConnection();
//            String sql = "delete from sanpham  where masp =? ";
//            preparedStatement = conn.prepareCall(sql);
//            preparedStatement.setString(1, MaSP);
//            preparedStatement.execute();
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        } finally {
//            // Đóng tất cả các resource sau khi sử dụng
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Lỗi khi đóng kết nối hoặc statement: " + ex.toString());
//            }
//        }
//    }
//
//    public static void deleteAll() {
//        Connection conn = null;
//        Statement statement = null;
//
//        try {
//            conn = cn.getConnection();
//            String sql1 = "DELETE FROM donhang";
//            statement = conn.createStatement();
//            statement.executeUpdate(sql1);
//
//            String sql = "DELETE FROM sanpham";
//            statement = conn.createStatement();
//            statement.executeUpdate(sql); // Sử dụng executeUpdate thay vì executeQuery
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        } finally {
//            // Đóng tất cả các resource sau khi sử dụng
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Lỗi khi đóng kết nối hoặc statement: " + ex.toString());
//            }
//        }
//    }
    public static List<SanPham> searchByName(String tenSP) {
        Connection conn = null;
        PreparedStatement statement = null;
        List<SanPham> lst = new ArrayList<>();
        try {
            conn = cn.getConnection();
            String sql = "select * from sanpham where tensp like ?";
            statement = conn.prepareCall(sql);
            statement.setString(1, "%" + tenSP + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Tạo đối tượng SanPham từ dữ liệu trong ResultSet
                SanPham sp = new SanPham(resultSet.getString("masp"), resultSet.getString("tensp"), resultSet.getString("mau"),
                        resultSet.getString("size"), resultSet.getDouble("donGia"), resultSet.getInt("soLuongCon"),
                        resultSet.getString("avatar"), resultSet.getString("loai"), resultSet.getString("gioitinh"), resultSet.getString("tinhtrang"));

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

    public static SanPham getByID(String maSP) {
        Connection conn = null;
        Statement statement = null;
        SanPham sp = null;

        try {
            conn = cn.getConnection();
            String sql = "select * from sanpham where masp = '" + maSP + "'";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                sp = new SanPham(resultSet.getString("masp"), resultSet.getString("tensp"), resultSet.getString("mau"),
                        resultSet.getString("size"), resultSet.getDouble("donGia"), resultSet.getInt("soLuongCon"),
                        resultSet.getString("avatar"), resultSet.getString("loai"), resultSet.getString("gioitinh"), resultSet.getString("tinhtrang"));

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
        return sp;
    }
}
