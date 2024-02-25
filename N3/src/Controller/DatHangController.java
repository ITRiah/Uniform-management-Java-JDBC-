/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import Entity.ChiTietDonHang;
import Entity.DonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DatHangController {

    static DBConnect cn = new DBConnect();

    public static void insertDH(DonHang dh) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String insertSql = "INSERT INTO donhang VALUES (?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = conn.prepareStatement(insertSql);
            preparedStatement.setString(1, dh.getMaDH());
            preparedStatement.setString(2, dh.getMaKh());
            preparedStatement.setString(3, dh.getHoTen());
            preparedStatement.setString(4, dh.getSoDt());
            preparedStatement.setString(5, dh.getDiaChi());
            preparedStatement.setString(6, dh.getGhiChu());
            preparedStatement.setString(7, dh.getTrangThai());
            preparedStatement.setDouble(8, dh.getTongTien());
            preparedStatement.setString(9, dh.getNgayDat());
            preparedStatement.setString(10, dh.getLoaiDon());
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

    public static void insertCTDH(ChiTietDonHang ctdh) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();
            String sql2 = "insert into chitietdonhang values(?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setString(1, ctdh.getMaChiTietDonHang());
            preparedStatement.setString(2, ctdh.getMaDH());
            preparedStatement.setString(3, ctdh.getMaSp());
            preparedStatement.setInt(4, ctdh.getSoLuongMua());

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

    public static void deleteGH(String maKH) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = cn.getConnection();

            String updateSql = "delete from giohang where makh = ?";
            preparedStatement = conn.prepareStatement(updateSql);
            preparedStatement.setString(1, maKH); // Sét giá trị cho tham số

            preparedStatement.executeUpdate();

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
