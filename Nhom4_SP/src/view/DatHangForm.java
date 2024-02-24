/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.DatHangController;
import Controller.GioHangController;
import Controller.KhachHangController;
import Controller.SanPhamController;
import DBConnect.DBConnect;
import DTO.SanPhamDat;
import DangNhap.DangNhapForm;
import DTO.CurrentAccount;
import Entity.DonHang;
import Entity.ChiTietDonHang;
import Entity.KhachHang;
import Entity.SanPham;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author admin
 */
public class DatHangForm extends javax.swing.JFrame {

    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
    List<SanPhamDat> lst;
    String header[] = {"Tên sản phẩm", "Size", "Đơn giá", "Số lượng đặt"};
    DefaultTableModel tb = new DefaultTableModel();
    private int currentPage = 1;
    private int recordsPerPage = 10;
    private JButton[] pageButtons;

    int dongChon = -1;

    /**
     * Creates new form KhachHangForm
     */
    public DatHangForm() {
        initComponents();
        tb = (DefaultTableModel) tblSanPhamKh.getModel();
        tb.setColumnCount(4);
        tb.setColumnIdentifiers(header); // Thêm dòng này để đặt tên cột
        loadTableSanPham();
        labelUsername.setText(CurrentAccount.kh.getHoTen());

        KhachHang kh = KhachHangController.getThongTin(CurrentAccount.account.getMaKh());
        if (kh != null) {
            txtHoTen.setText(kh.getHoTen());
            txtDiaChi.setText(kh.getDiaChi());
            txtSoDT.setText(kh.getSoDT());
        }
        viewThanhTien();
        setLocationRelativeTo(null); // chinh cua so xuat hien chinh giua man hinh
        updateTable();
        updatePageButtons(); // Thêm dòng này để tạo các nút trang
        pnlButtons.setLayout(new java.awt.FlowLayout());
    }

    private void updateTable() {
        lst = GioHangController.getList(CurrentAccount.account.getMaKh());
        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);
        for (int i = start; i < end; i++) {
            SanPhamDat sp = lst.get(i);
            String dg = decimalFormat.format(sp.getDonGia());
            tb.addRow(new Object[]{sp.getTenSp(), sp.getSize(), dg, sp.getSoLuongDat()});
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

    private int getTotalPages() {
        return (int) Math.ceil((double) lst.size() / recordsPerPage);
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
            tb.addRow(new Object[]{sp.getTenSp(), sp.getSize(), sp.getDonGia(), sp.getSoLuongDat()});
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
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSoDT = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        labelThanhTien = new javax.swing.JLabel();
        btnOrder = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDeleteAll = new javax.swing.JButton();
        pnlButtons = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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

        PnMenuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PnMenuBarMouseClicked(evt);
            }
        });

        lbOpenMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/menu.png"))); // NOI18N
        lbOpenMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpenMenuMouseClicked(evt);
            }
        });

        labelUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelUsername.setText("username");

        javax.swing.GroupLayout PnMenuBarLayout = new javax.swing.GroupLayout(PnMenuBar);
        PnMenuBar.setLayout(PnMenuBarLayout);
        PnMenuBarLayout.setHorizontalGroup(
            PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnMenuBarLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lbOpenMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 962, Short.MAX_VALUE)
                .addComponent(labelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
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
        jLabel6.setText("ĐẶT HÀNG");

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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Địa chỉ :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Ghi chú :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Số điện thoại :");

        txtHoTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoTenActionPerformed(evt);
            }
        });

        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Họ và tên:");

        txtGhiChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGhiChuActionPerformed(evt);
            }
        });

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

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh (1).png"))); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                                .addComponent(btnOrder)
                                .addGap(48, 48, 48)
                                .addComponent(btnReset))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGhiChu, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtDiaChi))))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelThanhTien)
                            .addComponent(btnOrder)
                            .addComponent(btnReset))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))))
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
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteAll))
                .addGap(7, 7, 7))
        );
        cardTrangChuLayout.setVerticalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))))
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

    private void txtHoTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoTenActionPerformed

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed

    private void tblSanPhamKhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamKhMouseClicked

        dongChon = tblSanPhamKh.getSelectedRow();

    }//GEN-LAST:event_tblSanPhamKhMouseClicked

    private void txtGhiChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGhiChuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGhiChuActionPerformed
    private boolean validEmpty(String hoTen, String soDt, String diaChi) {
        StringBuilder sb = new StringBuilder();
        if (hoTen.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập họ tên người nhận !",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (soDt.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập số điện thoại !",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (diaChi.equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập địa chỉ !",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        // TODO add your handling code here:
        String maKh = CurrentAccount.account.getMaKh();
        String hoTen = txtHoTen.getText();
        String soDt = txtSoDT.getText();
        String diaChi = txtDiaChi.getText();
        String ghiChu = txtGhiChu.getText();
        String createDate = LocalDate.now().toString();
        String maDH = "";

        if (lst.size() < 1) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng của bạn đang trống ! Hãy thêm sản phẩm vào giỏ hàng", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lst = GioHangController.getList(CurrentAccount.account.getMaKh());
        List<SanPham> listSanPhams = SanPhamController.getList();
        if (validEmpty(hoTen, soDt, diaChi)) {
            DonHang donHang = new DonHang(maKh, hoTen, soDt, diaChi, ghiChu, "Chờ duyệt", tinhThanhTien(), createDate, "0");
            maDH = donHang.getMaDH();
            DatHangController.insertDH(donHang);

            for (SanPhamDat sp : lst) {
                ChiTietDonHang chiTietDonHang = new ChiTietDonHang(donHang.getMaDH(), sp.getMaSp(), sp.getSoLuongDat());
                DatHangController.insertCTDH(chiTietDonHang);
                for (SanPham sanPham : listSanPhams) {
                    if (sanPham.getMaSp().equals(sp.getMaSp())) {
                        sanPham.setSoLuongCon(sanPham.getSoLuongCon() - sp.getSoLuongDat());
                        SanPhamController.update(sanPham);
                    }
                }
            }

            DatHangController.deleteGH(maKh);
            loadTableSanPham();
            labelThanhTien.setText("");

            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không? ", "Xác Nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                return;
            } else {
                try {
                    Hashtable map = new Hashtable();
                    JasperReport rpt = JasperCompileManager.compileReport("src/Report/reportHD.jrxml");
                    map.put("sMaDH", maDH);

                    DBConnect cn = new DBConnect();
                    Connection conn = cn.getConnection();

                    JasperPrint p = JasperFillManager.fillReport(rpt, map, conn);
                    String filePath = "src/HoaDon/" + maDH + ".pdf";
                    JasperExportManager.exportReportToPdfFile(p, filePath);
                    JasperViewer.viewReport(p, false);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            sendEmail(maKh, maDH);
            JOptionPane.showMessageDialog(this, "Đặt hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnOrderActionPerformed

    public void sendEmail(String maKH, String maDH) {
        KhachHang kh = KhachHangController.getThongTin(maKH);
        String to = kh.getEmail();
        String from = "vhai31102002@gmail.com";
        String pw = "mzxscbwwwptjintm";
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", 587);

            Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, pw);
                }
            });

            String tieuDe = "Thông tin đơn hàng";
            String body = "Cảm ơn bạn đã đặt hàng, vui lòng kiểm tra lại thông tin đơn hàng ở bên dưới.";

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(body, "text/html; charset=utf-8");

            message.setSubject(tieuDe);
            message.setText(body);

            //sendFile
            MimeBodyPart bodyPart = new MimeBodyPart();
            File file = new File("src/HoaDon/" + maDH + ".pdf");
            FileDataSource dataSource = new FileDataSource(file);
            bodyPart.setDataHandler(new DataHandler(dataSource));
            bodyPart.setFileName(file.getName());

            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(contentPart);
            mimeMultipart.addBodyPart(bodyPart);

            message.setContent(mimeMultipart);

            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace(); // In thông báo lỗi cụ thể
        } catch (Exception e) {
            System.out.println("Lỗi " + e.getMessage());
        }
    }

    public void clearForm() {
        txtHoTen.setText("");
        txtDiaChi.setText("");
        txtGhiChu.setText("");
        txtSoDT.setText("");

    }
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

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
                updateTable();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Chưa chọn dòng xóa", "Thông báo", WIDTH);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá tất cả sản phẩm khỏi giỏ hàng không? ", "Xác Nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            return;
        }
        if (choice == JOptionPane.YES_OPTION) {

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

    private void PnMenuBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnMenuBarMouseClicked
        // TODO add your handling code here:
        ThongTinKhachHang sp = new ThongTinKhachHang();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_PnMenuBarMouseClicked

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
            java.util.logging.Logger.getLogger(DatHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatHangForm().setVisible(true);
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
    private javax.swing.JButton btnReset;
    private javax.swing.JPanel cardTrangChu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtSoDT;
    // End of variables declaration//GEN-END:variables
}
