
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.ChiTietDonHangController;
import Controller.DonHangController;
import Controller.SanPhamController;
import Controller.ThongTinDonHangController;
import DTO.CurrentAccount;
import DTO.ThongTinDonHang;
import DangNhap.DangNhapForm;
import Entity.ChiTietDonHang;
import Entity.DonHang;
import Entity.SanPham;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class DoiHangForm extends javax.swing.JFrame {

    /**
     * Creates new form XemDonHangForm
     */
    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
    String header[] = {"Mã ĐH", "Mã KH", "Thông tin KH", "Ngày tạo", "Chi tiết", "Tổng tiền", "Loại đơn", "Trạng thái"};
    DefaultTableModel tb = new DefaultTableModel();
    private List<ThongTinDonHang> lst;
    private List<SanPham> lstFullSP = SanPhamController.getList();
    private List<DonHang> listFullDH = DonHangController.getListDHByMaKH(CurrentAccount.account.getMaKh());
    static int dong = 0;
    private int currentPage = 1;
    private int recordsPerPage = 3;
    private JButton[] pageButtons;

    public DoiHangForm() {
        initComponents();
        tb = (DefaultTableModel) tblDonHang.getModel();
        tb.setColumnCount(9);
        tb.setColumnIdentifiers(header); // Thêm dòng này để đặt tên cột
        //loadComboBox();
        loadTableDH();
        labelUsername.setText(CurrentAccount.kh.getHoTen());
        updateTable();
        updatePageButtons(); // Thêm dòng này để tạo các nút trang
        pnlButtons.setLayout(new java.awt.FlowLayout());

    }

    private void updateTable() {
        lst = ThongTinDonHangController.getListByMaKH(CurrentAccount.account.getMaKh());
        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);
        for (int i = start; i < end; i++) {
            String chiTiet = "";

            String tensp = "";
            String size = "";
            String mau = "";
            for (ChiTietDonHang ctdh : ChiTietDonHangController.getList()) {
                if (lst.get(i).getMaDH().equals(ctdh.getMaDH())) {
                    for (SanPham sp : lstFullSP) {
                        if (ctdh.getMaSp().equals(sp.getMaSp())) {
                            tensp = sp.getTenSp();
                            size = sp.getSize();
                            mau = sp.getMau();
                        }
                    }
                    chiTiet += "Sản phẩm: " + tensp + "-Số lượng:" + ctdh.getSoLuongMua() + "-Size: " + size + "-Màu: " + mau + ";";
                }
            }

            String loaiDon = null;
            if (lst.get(i).getLoaiDon().equals("0")) {
                loaiDon = "Mua ";
            }
            if (lst.get(i).getLoaiDon().equals("1")) {
                loaiDon = "Thuê ";
            }
            String tongTien = decimalFormat.format(lst.get(i).getTongTien());
            String ngayTaoString = lst.get(i).getNgayDat();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ngayTao = LocalDate.parse(ngayTaoString, formatter);
            String ngayTaoMoiString = ngayTao.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            Object[] rowData = {lst.get(i).getMaDH(), lst.get(i).getMaKh(), lst.get(i).getHoTen() + "-" + lst.get(i).getDiaChi() + "-" + lst.get(i).getSoDt(),
                ngayTaoMoiString, chiTiet, tongTien, loaiDon, lst.get(i).getTrangThai()};
            tb.addRow(rowData);
        }
        updatePageButtons();
    }

    private void btnPageActionPerformed(int page) {
        currentPage = page;
        updateTable();
    }

    private void updatePageButtons() {
        pnlButtons.removeAll();
        int totalPages = getTotalPages();
        pageButtons = new JButton[totalPages];
        for (int i = 0; i < totalPages; i++) {
            final int page = i + 1;
            pageButtons[i] = new JButton(Integer.toString(page));
            Dimension buttonSize = new Dimension(50, 29);
            pageButtons[i].setPreferredSize(buttonSize);
            pageButtons[i].setMaximumSize(buttonSize);
            pageButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    btnPageActionPerformed(page);
                }
            });
            pnlButtons.add(pageButtons[i]);
        }
        pnlButtons.revalidate();
        pnlButtons.repaint();
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) lst.size() / recordsPerPage);
    }

    public void loadTableDH() {
        lst = ThongTinDonHangController.getListByMaKH(CurrentAccount.account.getMaKh());
        tb.setRowCount(0);
        for (ThongTinDonHang ttdh : lst) {

            String chiTiet = "";
            String tenSP = "";
            String size = "";
            String mau = "";

            for (ChiTietDonHang ctdh : ChiTietDonHangController.getList()) {
                if (ttdh.getMaDH().equals(ctdh.getMaDH())) {
                    for (SanPham sp : lstFullSP) {
                        if (ctdh.getMaSp().equals(sp.getMaSp())) {
                            tenSP = sp.getTenSp();
                            size = sp.getSize();
                            mau = sp.getMau();
                        }
                    }
                    chiTiet += "Sản phẩm: " + tenSP + "-Số lượng:" + ctdh.getSoLuongMua() + "-Size: " + size + "-Màu: " + mau + ";";
                }
            }

            String loaiDon = null;
            if (ttdh.getLoaiDon().equals("0")) {
                loaiDon = "Mua ";
            }
            if (ttdh.getLoaiDon().equals("1")) {
                loaiDon = "Thuê ";
            }

            Object[] rowData = {ttdh.getMaDH(), ttdh.getMaKh(), ttdh.getHoTen() + "-" + ttdh.getDiaChi() + "-" + ttdh.getSoDt(),
                ttdh.getNgayDat(), chiTiet, ttdh.getTongTien(), loaiDon, ttdh.getTrangThai()};
            tb.addRow(rowData);
        }
    }

//    public void loadComboBox() {
//        cboSanPham.removeAllItems();
//        cboSize.removeAllItems();
//
//        int selectedRow = tblSanPham.getSelectedRow();
//        ThongTinDonHang ttdh = new ThongTinDonHang();
//        dong = tblDonHang.getSelectedRow();
//
//        if (dong < 0 || dong >= lst.size()) {
//            return;
//        }
//
//        ttdh = lst.get(dong);
//        List<SanPham> lstSP = ttdh.getListSp();
//
//        if (selectedRow < 0 || selectedRow >= lstSP.size()) {
//            // Handle the case when no row is selected or the selected row is out of bounds
//            return;
//        }
//
//        SanPham sp = lstSP.get(selectedRow);
//
//        HashSet<SanPham> hs = new HashSet<>();
//        HashSet<SanPham> hs2 = new HashSet<>();
//
//        for (SanPham sanPham : lstFullSP) {
//            if (sanPham.getTenSp().equals(sp.getTenSp())) {
//                cboSanPham.addItem(sanPham.getMau());
//                cboSize.addItem(sanPham.getSize());
//            }
//        }
//    }
    
    String maDH = "";
    public void loadTBLSanPham() {
        dong = tblDonHang.getSelectedRow();
        String header2[] = {"Tên sản phẩm", "Màu sắc", "Size", "Số lượng đặt"};
        DefaultTableModel tb2 = new DefaultTableModel();
        tb2 = (DefaultTableModel) tblSanPham.getModel();
        tb2.setColumnCount(4);
        tb2.setColumnIdentifiers(header2); // Thêm dòng này để đặt tên cột
        tb2.setRowCount(0); // Xóa hết dữ liệu cũ

        if (dong != -1) {
            dong += currentPage * recordsPerPage - recordsPerPage;
            ThongTinDonHang ttdh = lst.get(dong);
            List<SanPham> lstSP = ttdh.getListSp();
            maDH = lst.get(dong).getMaDH();

            for (ChiTietDonHang ctdh : ChiTietDonHangController.getList()) {
                if (ttdh.getMaDH().equals(ctdh.getMaDH())) {
                    for (SanPham sanPham : lstSP) {
                        if (ctdh.getMaSp().equals(sanPham.getMaSp())) {
                            Object[] rowData = {sanPham.getTenSp(), sanPham.getMau(), sanPham.getSize(), ctdh.getSoLuongMua()};
                            tb2.addRow(rowData);
                            break;
                        }
                    }
                }
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PnTitile = new javax.swing.JPanel();
        PnSlideMenu = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbClose = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbDatHang = new javax.swing.JLabel();
        lbPhanHoi = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbDangxuat = new javax.swing.JLabel();
        lbTrangChu = new javax.swing.JLabel();
        lbDoiHang = new javax.swing.JLabel();
        lbDat = new javax.swing.JLabel();
        lbGioHang = new javax.swing.JLabel();
        PnMenuBar = new javax.swing.JPanel();
        lbOpenMenu = new javax.swing.JLabel();
        labelUsername = new javax.swing.JLabel();
        pnMain = new javax.swing.JPanel();
        cardTrangChu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDonHang = new javax.swing.JTable();
        btnHuy = new javax.swing.JButton();
        pnlButtons = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbMaDH = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnTitile.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout PnTitileLayout = new javax.swing.GroupLayout(PnTitile);
        PnTitile.setLayout(PnTitileLayout);
        PnTitileLayout.setHorizontalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        PnTitileLayout.setVerticalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(PnTitile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 30));

        PnSlideMenu.setBackground(new java.awt.Color(102, 153, 255));
        PnSlideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lbClose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbClose.setText("X");
        lbClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbCloseMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/avatar.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClose, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        PnSlideMenu.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 100));

        lbDatHang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbDatHang.setForeground(new java.awt.Color(255, 255, 255));
        lbDatHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDatHang.setText("Danh sách sản phẩm");
        lbDatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDatHangMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbDatHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 160, 30));

        lbPhanHoi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbPhanHoi.setForeground(new java.awt.Color(255, 255, 255));
        lbPhanHoi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPhanHoi.setText("Phản hồi");
        lbPhanHoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbPhanHoiMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbPhanHoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 160, 30));
        PnSlideMenu.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 160, 10));

        lbDangxuat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbDangxuat.setForeground(new java.awt.Color(255, 255, 255));
        lbDangxuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDangxuat.setText("Đăng xuất");
        lbDangxuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDangxuatMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbDangxuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 160, -1));

        lbTrangChu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lbTrangChu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangChu.setText("Trang chủ");
        lbTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTrangChuMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 30));

        lbDoiHang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbDoiHang.setForeground(new java.awt.Color(255, 255, 255));
        lbDoiHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDoiHang.setText("Xem đơn hàng");
        lbDoiHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDoiHangMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbDoiHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 160, 30));

        lbDat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbDat.setForeground(new java.awt.Color(255, 255, 255));
        lbDat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDat.setText("Đặt hàng");
        lbDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDatMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbDat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 160, 30));

        lbGioHang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbGioHang.setForeground(new java.awt.Color(255, 255, 255));
        lbGioHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbGioHang.setText("Xem giỏ hàng");
        lbGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbGioHangMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbGioHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 160, 30));

        jPanel1.add(PnSlideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 160, 550));

        lbOpenMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/menu.png"))); // NOI18N
        lbOpenMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpenMenuMouseClicked(evt);
            }
        });

        labelUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelUsername.setText("username");
        labelUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelUsernameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PnMenuBarLayout = new javax.swing.GroupLayout(PnMenuBar);
        PnMenuBar.setLayout(PnMenuBarLayout);
        PnMenuBarLayout.setHorizontalGroup(
            PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnMenuBarLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lbOpenMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 859, Short.MAX_VALUE)
                .addComponent(labelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        PnMenuBarLayout.setVerticalGroup(
            PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnMenuBarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbOpenMenu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PnMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1090, 50));

        pnMain.setLayout(new java.awt.CardLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("XEM ĐƠN HÀNG");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDonHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDonHang);

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/remove (1).png"))); // NOI18N
        btnHuy.setText("Hủy đơn hàng");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 857, Short.MAX_VALUE)
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Đơn hàng");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Chi tiết đơn hàng");

        lbMaDH.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbMaDH.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout cardTrangChuLayout = new javax.swing.GroupLayout(cardTrangChu);
        cardTrangChu.setLayout(cardTrangChuLayout);
        cardTrangChuLayout.setHorizontalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(cardTrangChuLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cardTrangChuLayout.createSequentialGroup()
                                .addComponent(btnHuy)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(cardTrangChuLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbMaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(70, 70, 70))
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))))
        );
        cardTrangChuLayout.setVerticalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lbMaDH))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pnMain.add(cardTrangChu, "card2");

        jPanel1.add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1090, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1084, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    int x = 160;
    int y = 550;

    void openMenu() {
        x = 0;
        if (x == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i <= 160; i++) {
                            PnSlideMenu.setSize(i, y);
                            Thread.sleep(2);
                        }
                    } catch (Exception e) {
                    }
                }
            }).start();
            x = 160;
        }
    }

    void closeMenu() {
        PnSlideMenu.setSize(x, y);
        if (x == 160) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 160; i >= 0; i--) {
                            PnSlideMenu.setSize(i, y);
                            Thread.sleep(2);
                        }
                    } catch (Exception e) {
                    }
                }
            }).start();
            x = 0;
        }
    }
    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        // TODO add your handling code here:
        closeMenu();
    }//GEN-LAST:event_lbCloseMouseClicked

    private void lbGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbGioHangMouseClicked
        // TODO add your handling code here:
        XemGioHangForm sp = new XemGioHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbGioHangMouseClicked

    private void lbDatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatHangMouseClicked
        // TODO add your handling code here:
        ThemVaoGioHangForm sp = new ThemVaoGioHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDatHangMouseClicked

    private void lbPhanHoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbPhanHoiMouseClicked
        // TODO add your handling code here:
        PhanHoi_UI sp = new PhanHoi_UI();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbPhanHoiMouseClicked

    private void lbDangxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangxuatMouseClicked
        // TODO add your handling code here:
        DangNhapForm sp = new DangNhapForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDangxuatMouseClicked

    private void lbTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTrangChuMouseClicked
        // TODO add your handling code here:
        XemSanPham sp = new XemSanPham();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbTrangChuMouseClicked

    private void lbOpenMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpenMenuMouseClicked
        // TODO add your handling code here:
        openMenu();
    }//GEN-LAST:event_lbOpenMenuMouseClicked

    List<SanPham> listSP = SanPhamController.getList();

    private void tblDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDonHangMouseClicked
        // TODO add your handling code here:
        loadTBLSanPham();
        lbMaDH.setText(maDH);
    }//GEN-LAST:event_tblDonHangMouseClicked

    private void lbDoiHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDoiHangMouseClicked
        // TODO add your handling code here:
        DoiHangForm sp = new DoiHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDoiHangMouseClicked

    private void lbDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatMouseClicked
        // TODO add your handling code here:
        DatHangForm sp = new DatHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDatMouseClicked

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        int dongChon = tblDonHang.getSelectedRow();

        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;
            ThongTinDonHang tt = lst.get(dongChon);

            List<SanPham> lstFullSP = SanPhamController.getList();
            List<SanPham> lstSP = tt.getListSp();

            if (!tt.getTrangThai().equals("Chờ duyệt")) {
                JOptionPane.showMessageDialog(rootPane, "Bạn chỉ có thể hủy đơn hàng khi đơn hàng đang được chờ duyệt", "Thông báo", JOptionPane.ERROR_MESSAGE);
            } else {
                DonHang dhToRemove = null;
                for (DonHang donHang : listFullDH) {
                    if (donHang.getMaDH().equals(tt.getMaDH())) {
                        dhToRemove = donHang;
                        break;
                    }
                }

                List<ChiTietDonHang> listCTDH = ChiTietDonHangController.getList();
                for (int i = 0; i < listCTDH.size(); i++) {
                    if (tt.getMaDH().equals(listCTDH.get(i).getMaDH())) {
                        ChiTietDonHangController.delete(listCTDH.get(i).getMaChiTietDonHang());
                        for (SanPham sp : lstFullSP) {
                            if (listCTDH.get(i).getMaSp().equals(sp.getMaSp())) {
                                sp.setSoLuongCon(sp.getSoLuongCon() + listCTDH.get(i).getSoLuongMua());
                                SanPhamController.update(sp);
                                break;
                            }
                        }
                    }
                }

                if (dhToRemove != null) {
                    DonHangController.delete(dhToRemove.getMaDH());
                }

                JOptionPane.showMessageDialog(rootPane, "Hủy đơn hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                updateTable();
            }
        } else
            JOptionPane.showMessageDialog(rootPane, "Chưa chọn đơn hàng");
    }//GEN-LAST:event_btnHuyActionPerformed

    private void labelUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelUsernameMouseClicked
        ThongTinKhachHang sp = new ThongTinKhachHang();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_labelUsernameMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        //loadComboBox();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DoiHangForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoiHangForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoiHangForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoiHangForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DoiHangForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnMenuBar;
    private javax.swing.JPanel PnSlideMenu;
    private javax.swing.JPanel PnTitile;
    private javax.swing.JButton btnHuy;
    private javax.swing.JPanel cardTrangChu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel lbClose;
    private javax.swing.JLabel lbDangxuat;
    private javax.swing.JLabel lbDat;
    private javax.swing.JLabel lbDatHang;
    private javax.swing.JLabel lbDoiHang;
    private javax.swing.JLabel lbGioHang;
    private javax.swing.JLabel lbMaDH;
    private javax.swing.JLabel lbOpenMenu;
    private javax.swing.JLabel lbPhanHoi;
    private javax.swing.JLabel lbTrangChu;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JTable tblDonHang;
    private javax.swing.JTable tblSanPham;
    // End of variables declaration//GEN-END:variables
}
