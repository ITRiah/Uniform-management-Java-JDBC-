/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.GioHangController;
import Controller.SanPhamController;
import DTO.SanPhamDat;
import DangNhap.DangNhapForm;
import DTO.CurrentAccount;
import Entity.GioHang;
import Entity.SanPham;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class XemGioHangForm extends javax.swing.JFrame {

    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    String header[] = {"Tên sản phẩm", "Màu sắc", "Size", "Đơn giá", "Số lượng đặt", "Ngày tạo"};
    DefaultTableModel tb = new DefaultTableModel();
    List<SanPhamDat> lst;
    List<SanPham> lstSP = SanPhamController.getList();
    private int currentPage = 1;
    private int recordsPerPage = 10;
    private JButton[] pageButtons;

    int dongChon = -1;

    /**
     * Creates new form KhachHangForm
     */
    public XemGioHangForm() {
        initComponents();
        tb = (DefaultTableModel) tblSanPhamKh.getModel();
        tb.setColumnCount(5);
        tb.setColumnIdentifiers(header); // Thêm dòng này để đặt tên cột
        HuyGH();
        loadTableSanPham();
        labelUsername.setText(CurrentAccount.kh.getHoTen());
        viewThanhTien();
        updateTable();
        updatePageButtons(); // Thêm dòng này để tạo các nút trang
        pnlButtons.setLayout(new java.awt.FlowLayout());
        setLocationRelativeTo(null); // chinh cua so xuat hien chinh giua man hinh
    }

    private void updateTable() {
        lst = GioHangController.getList(CurrentAccount.account.getMaKh());
        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);
        for (int i = start; i < end; i++) {
            SanPhamDat sp = lst.get(i);
            String dg = decimalFormat.format(sp.getDonGia());
            String ngayTao = sp.getNgayTao().format(formatter);

            tb.addRow(new Object[]{sp.getTenSp(), sp.getSize(), sp.getMau(), dg, sp.getSoLuongDat(), ngayTao});
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

    int x = 160, y = 590;

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

    public void viewThanhTien() {
        DecimalFormat f = new DecimalFormat("###,###");
        labelThanhTien.setText(f.format(tinhThanhTien()) + " VND");
    }

    public double tinhThanhTien() {
        double coin = 0;
        for (SanPhamDat sp : lst) {
            coin += sp.getDonGia() * sp.getSoLuongDat();
        }
        return coin;
    }

    public void loadTableSanPham() {
        lst = GioHangController.getList(CurrentAccount.account.getMaKh());
        tb.setRowCount(0);
        for (SanPhamDat sp : lst) {
            String dg = decimalFormat.format(sp.getDonGia());
            String ngayTao = sp.getNgayTao().format(formatter);
            tb.addRow(new Object[]{sp.getTenSp(), sp.getSize(), sp.getMau(), dg, sp.getSoLuongDat(), ngayTao});
        }
    }

    public void HuyGH() {
        List<GioHang> lstGH = GioHangController.getAll();
        LocalDate ht = LocalDate.now();

        for (GioHang gh : lstGH) {
            LocalDate ngayTao = gh.getNgayTao();
            LocalDate ngayHuy = ngayTao.plusDays(7); // Thêm 7 ngày vào ngày tạo

            if (ht.isAfter(ngayHuy)) {
                GioHangController.huyGH(gh.getMaGioHang());
            }
            updateTable();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        PnTitile = new javax.swing.JPanel();
        PnSlideMenu = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
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
        tblSanPhamKh = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        labelThanhTien = new javax.swing.JLabel();
        btnOrder = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDeleteAll = new javax.swing.JButton();
        pnlButtons = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnTitile.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout PnTitileLayout = new javax.swing.GroupLayout(PnTitile);
        PnTitile.setLayout(PnTitileLayout);
        PnTitileLayout.setHorizontalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1180, Short.MAX_VALUE)
        );
        PnTitileLayout.setVerticalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel2.add(PnTitile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 30));

        PnSlideMenu.setBackground(new java.awt.Color(102, 153, 255));
        PnSlideMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        lbClose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbClose.setText("X");
        lbClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbCloseMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/avatar.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClose, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        PnSlideMenu.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 100));

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

        jPanel2.add(PnSlideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 160, 590));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 959, Short.MAX_VALUE)
                .addComponent(labelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
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

        jPanel2.add(PnMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1180, 50));

        pnMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnMain.setLayout(new java.awt.CardLayout());

        cardTrangChu.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("GIỎ HÀNG");

        tblSanPhamKh.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSanPhamKh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamKhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamKh);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Thành tiền :");

        labelThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelThanhTien.setForeground(new java.awt.Color(255, 0, 0));
        labelThanhTien.setText("Tổng tiền");

        btnOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shopping-cart (1).png"))); // NOI18N
        btnOrder.setText("Đặt hàng");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(labelThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOrder)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelThanhTien)
                    .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnDeleteAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/remove (1).png"))); // NOI18N
        btnDeleteAll.setText("Xóa tất cả");
        btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllActionPerformed(evt);
            }
        });

        pnlButtons.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 857, Short.MAX_VALUE)
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout cardTrangChuLayout = new javax.swing.GroupLayout(cardTrangChu);
        cardTrangChu.setLayout(cardTrangChuLayout);
        cardTrangChuLayout.setHorizontalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1014, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteAll))
                .addGap(34, 34, 34))
        );
        cardTrangChuLayout.setVerticalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93))))
        );

        pnMain.add(cardTrangChu, "card2");

        jPanel2.add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1180, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1186, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 617, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamKhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamKhMouseClicked
        dongChon = tblSanPhamKh.getSelectedRow();
    }//GEN-LAST:event_tblSanPhamKhMouseClicked


    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        DatHangForm sp = new DatHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_btnOrderActionPerformed

    public void clearForm() {

        labelThanhTien.setText("");
    }
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá sản phẩm khỏi giỏ hàng không? ", "Xác Nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                return;
            }
            if (choice == JOptionPane.YES_OPTION) {
                SanPhamDat spDat = lst.get(dongChon);
                GioHangController.delete(spDat, CurrentAccount.account.getMaKh());
                viewThanhTien();
//
//                //int soLuong = 0;
//                for (SanPham sanPham : lstSP) {
//                    if (sanPham.getMaSp().equals(spDat.getMaSp())) {
//                        //soLuong += sanPham.getSoLuongCon();
//                        sanPham.setSoLuongCon(sanPham.getSoLuongCon() + spDat.getSoLuongDat());
//                        SanPhamController.update(sanPham);
//                        break;
//                    }
//                }
//                //soLuong += spDat.getSoLuongDat();
//
////                String avatar = SanPhamController.getByID(spDat.getMaSp()).getAvatar();
////                SanPham sp = new SanPham(spDat.getMaSp(), spDat.getTenSp(),spDat.getMau(), spDat.getSize(), spDat.getDonGia(), soLuong, avatar);
//                
//                //SanPhamController.update(sp);
                updateTable();
            }
        } else
            JOptionPane.showMessageDialog(this,
                    "Chưa chọn dòng xóa", "Thông báo", WIDTH);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá tất cả sản phẩm khỏi giỏ hàng không? ", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            return;
        }
        if (choice == JOptionPane.YES_OPTION) {
//            for (SanPhamDat sanPhamDat : lst) {
//                int soLuong = 0;
//                lstSP = SanPhamController.getList();
//                for (SanPham sanPham : lstSP) {
//                    if (sanPham.getMaSp().equals(sanPhamDat.getMaSp())) {
//                        soLuong += sanPham.getSoLuongCon();
//                        soLuong += sanPhamDat.getSoLuongDat();
//                        String avatar = SanPhamController.getByID(sanPham.getMaSp()).getAvatar();
//                        //SanPham sp = new SanPham(sanPhamDat.getMaSp(), sanPhamDat.getTenSp(),sanPhamDat.getMau(), sanPhamDat.getSize(),sp sanPhamDat.getDonGia(), soLuong, avatar);
//                        sanPham.setSoLuongCon(soLuong);
//                        GioHangController.delete(sanPhamDat, CurrentAccount.account.getMaKh());
//                        SanPhamController.update(sanPham);
//                    }
//                }
//            }
            viewThanhTien();
            GioHangController.deleteAll(CurrentAccount.account.getMaKh());
            updateTable();
        }
    }//GEN-LAST:event_btnDeleteAllActionPerformed

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        // TODO add your handling code here:
        closeMenu();
    }//GEN-LAST:event_lbCloseMouseClicked

    private void lbOpenMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpenMenuMouseClicked
        // TODO add your handling code here:
        openMenu();
    }//GEN-LAST:event_lbOpenMenuMouseClicked

    private void lbDatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDatHangMouseClicked
        // TODO add your handling code here:
        ThemVaoGioHangForm sp = new ThemVaoGioHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDatHangMouseClicked

    private void lbPhanHoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbPhanHoiMouseClicked
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

    private void lbGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbGioHangMouseClicked
        // TODO add your handling code here:
        XemGioHangForm sp = new XemGioHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbGioHangMouseClicked

    private void labelUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelUsernameMouseClicked
        // TODO add your handling code here:
        ThongTinKhachHang sp = new ThongTinKhachHang();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_labelUsernameMouseClicked

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
            java.util.logging.Logger.getLogger(XemGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XemGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XemGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XemGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new XemGioHangForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnMenuBar;
    private javax.swing.JPanel PnSlideMenu;
    private javax.swing.JPanel PnTitile;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteAll;
    private javax.swing.JButton btnOrder;
    private javax.swing.JPanel cardTrangChu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelThanhTien;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel lbClose;
    private javax.swing.JLabel lbDangxuat;
    private javax.swing.JLabel lbDat;
    private javax.swing.JLabel lbDatHang;
    private javax.swing.JLabel lbDoiHang;
    private javax.swing.JLabel lbGioHang;
    private javax.swing.JLabel lbOpenMenu;
    private javax.swing.JLabel lbPhanHoi;
    private javax.swing.JLabel lbTrangChu;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JTable tblSanPhamKh;
    // End of variables declaration//GEN-END:variables
}
