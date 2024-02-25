/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DBConnect.DBConnect;
import DTO.ThongTinDonHang;
import static DTO.ThongTinDonHang.getListChiTietDhByMaDh;
import Entity.ChiTietDonHang;
import Entity.DonHang;
import Entity.SanPham;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongTinDonHangController {
    
    static DBConnect cn = new DBConnect();
    public static List<ThongTinDonHang> getList() {
        List<ThongTinDonHang> lst = new ArrayList<>();
        List<SanPham> lstSP = SanPhamController.getList();
        List<ChiTietDonHang> lstCTDH = ChiTietDonHangController.getList();
        List<DonHang> lstDonHang = DonHangController.getList();

        try {
            for (DonHang donHang : lstDonHang) {
                List<ChiTietDonHang> listCTDHByDH = getListChiTietDhByMaDh(donHang.getMaDH(), lstCTDH);
                List<SanPham> listSpByDh = new ArrayList<>();
                for (ChiTietDonHang chiTietDonHang : listCTDHByDH) {
                    SanPham sp = SanPham.getSanPhamByID(chiTietDonHang.getMaSp(), lstSP);
//                    try {
//                        sp.setSoLuongCon(chiTietDonHang.getSoLuongMua());
//                    } catch (Exception ex) {
//                        System.out.println(ex.toString());
//                    }
                    listSpByDh.add(sp);
                }
                ThongTinDonHang thongTinDonHang = new ThongTinDonHang(donHang.getMaDH(), donHang.getMaKh(),
                        donHang.getHoTen(), donHang.getSoDt(), donHang.getDiaChi(),
                        donHang.getGhiChu(), donHang.getTrangThai(), donHang.getTongTien(),
                        donHang.getNgayDat(), donHang.getLoaiDon(), listSpByDh);
                lst.add(thongTinDonHang);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return lst;
    }
    
    public static List<ThongTinDonHang> getListByMaKH(String maKH) {
        List<ThongTinDonHang> lst = new ArrayList<>();
        List<SanPham> lstSP = SanPhamController.getList();
        List<ChiTietDonHang> lstCTDH = ChiTietDonHangController.getList();
        List<DonHang> lstDonHang = DonHangController.getListDHByMaKH(maKH);                
        try {
            for (DonHang donHang : lstDonHang) {
                List<ChiTietDonHang> listCTDHByDH = getListChiTietDhByMaDh(donHang.getMaDH(), lstCTDH);
                List<SanPham> listSpByDh = new ArrayList<>();
                for (ChiTietDonHang chiTietDonHang : listCTDHByDH) {
                    SanPham sp = SanPham.getSanPhamByID(chiTietDonHang.getMaSp(), lstSP);
//                    try {
//                        sp.setSoLuongCon(chiTietDonHang.getSoLuongMua());
//                    } catch (Exception ex) {
//                        System.out.println(ex.toString());
//                    }
                    listSpByDh.add(sp);
                }
                ThongTinDonHang thongTinDonHang = new ThongTinDonHang(donHang.getMaDH(), donHang.getMaKh(),
                        donHang.getHoTen(), donHang.getSoDt(), donHang.getDiaChi(),
                        donHang.getGhiChu(), donHang.getTrangThai(), donHang.getTongTien(),
                        donHang.getNgayDat(), donHang.getLoaiDon(), listSpByDh);
                lst.add(thongTinDonHang);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Trả về danh sách SanPham
        return lst;
    }
    
    public static void updateTrangThai(DonHang tk, String trangThai) {
        Connection conn = null;
        Statement statement = null;
        
        try {
            conn = cn.getConnection();
            statement = conn.createStatement();
            String sql = "update donhang set trangthai = '" + trangThai + "' where madh = '" + tk.getMaDH() + "'" ;
            statement.execute(sql);
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
    }
    
    public static ThongTinDonHang search(String maDH) {
        ThongTinDonHang thongTinDonHang = null;
        List<SanPham> lstSP = SanPhamController.getList();
        List<ChiTietDonHang> lstCTDH = ChiTietDonHangController.getList();
        DonHang donHang = DonHangController.getDonHangByID( maDH);

        try {
                List<ChiTietDonHang> listCTDHByDH = getListChiTietDhByMaDh(maDH, lstCTDH);
                List<SanPham> listSpByDh = new ArrayList<>();
                
                for (ChiTietDonHang chiTietDonHang : listCTDHByDH) {
                    SanPham sp = SanPham.getSanPhamByID(chiTietDonHang.getMaSp(), lstSP);
//                    try {
//                        sp.setSoLuongCon(chiTietDonHang.getSoLuongMua());
//                    } catch (Exception ex) {
//                        System.out.println(ex.toString());
//                    }
                    listSpByDh.add(sp);
                }
                
                thongTinDonHang = new ThongTinDonHang(donHang.getMaDH(), donHang.getMaKh(),
                        donHang.getHoTen(), donHang.getSoDt(), donHang.getDiaChi(),
                        donHang.getGhiChu(), donHang.getTrangThai(), donHang.getTongTien(),
                        donHang.getNgayDat(), donHang.getLoaiDon(), listSpByDh);
                
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Trả về danh sách SanPham
        return thongTinDonHang;
    }
}
