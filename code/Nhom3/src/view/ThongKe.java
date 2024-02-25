/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.DonHangController;
import DangNhap.DangNhapForm;
import Entity.DonHang;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ThongKe extends javax.swing.JFrame {

    /**
     * Creates new form ThongKe
     */
    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
    String header[] = {"Mã ĐH", "Mã KH", "Ngày đặt", "Tổng tiền"};
    DefaultTableModel tb = new DefaultTableModel();
    private List<DonHang> lst;
    private int currentPage = 1;
    private int recordsPerPage = 2;
    private JButton[] pageButtons;
    int x = 160;
    int y = 510;

    public ThongKe() {
        initComponents();

        tb = (DefaultTableModel) dgvThongKe.getModel();
        tb.setColumnCount(4);
        tb.setColumnIdentifiers(header); // Thêm dòng này để đặt tên cột
        loadTable();
        loadComboBox();
        loadComboBox2();

        ButtonGroup b = new ButtonGroup();
        b.add(rdGiam);
        b.add(rdTang);

        Object obj = cboThang.getSelectedItem();
        if (obj != null) {
            double tongTien = 0;

            if (obj.equals("Tất cả")) {
                for (DonHang dh : lst) {
                    if (dh.getTrangThai().equals("Hoàn thành")) {
                        tongTien += dh.getTongTien();
                    }
                }
                loadTable();
                DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
                String formattedTongTien = decimalFormat.format(tongTien);
                lbTongTien.setText(formattedTongTien + " vnđ");
            }
        }

        updateTable();
        updatePageButtons(); // Thêm dòng này để tạo các nút trang
        pnlButtons.setLayout(new java.awt.FlowLayout());
    }

    private void updateTable() {
        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);
        for (int i = start; i < end; i++) {
            DonHang tk = lst.get(i);
            String tongTien = decimalFormat.format(tk.getTongTien());
            if (tk.getTrangThai().equals("Hoàn thành")) {
                tb.addRow(new Object[]{tk.getMaDH(), tk.getMaKh(), tk.getNgayDat(), tongTien});
            }
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

    private void loadTable() {
        lst = DonHangController.getList();
        tb.setRowCount(0);

        for (DonHang tk : lst) {
            if (tk.getTrangThai().equals("Hoàn thành")) {
                tb.addRow(new Object[]{tk.getMaDH(), tk.getMaKh(), tk.getNgayDat(), tk.getTongTien()});
            }
        }
    }

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PnTitile = new javax.swing.JPanel();
        PnSlideMenu = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbClose = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbQLSP = new javax.swing.JLabel();
        lbQLTK = new javax.swing.JLabel();
        lbQLDonHang = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbThongKe = new javax.swing.JLabel();
        lbQLPH = new javax.swing.JLabel();
        lbTrangChu = new javax.swing.JLabel();
        lbDangxuat = new javax.swing.JLabel();
        PnMenuBar = new javax.swing.JPanel();
        lbOpenMenu = new javax.swing.JLabel();
        pnMain = new javax.swing.JPanel();
        cardTrangChu = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboThang = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnXuatFile = new javax.swing.JButton();
        lbTongTien = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgvThongKe = new javax.swing.JTable();
        btnXem = new javax.swing.JButton();
        rdGiam = new javax.swing.JRadioButton();
        rdTang = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        pnlButtons = new javax.swing.JPanel();
        cboNam = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnTitile.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout PnTitileLayout = new javax.swing.GroupLayout(PnTitile);
        PnTitile.setLayout(PnTitileLayout);
        PnTitileLayout.setHorizontalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        PnTitileLayout.setVerticalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel1.add(PnTitile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 40));

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

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/avatar.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbClose, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbClose)
                .addContainerGap(74, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        PnSlideMenu.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 100));

        lbQLSP.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbQLSP.setForeground(new java.awt.Color(255, 255, 255));
        lbQLSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbQLSP.setText("Quản lý sản phẩm");
        lbQLSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbQLSPMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbQLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 160, 30));

        lbQLTK.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbQLTK.setForeground(new java.awt.Color(255, 255, 255));
        lbQLTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbQLTK.setText("Quản lý tài khoản");
        lbQLTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbQLTKMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbQLTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 160, 30));

        lbQLDonHang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbQLDonHang.setForeground(new java.awt.Color(255, 255, 255));
        lbQLDonHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbQLDonHang.setText("Quản lý đơn hàng");
        lbQLDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbQLDonHangMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbQLDonHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 160, 30));
        PnSlideMenu.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 190, -1));

        lbThongKe.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lbThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbThongKe.setText("Thống kê");
        lbThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThongKeMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 160, 30));

        lbQLPH.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbQLPH.setForeground(new java.awt.Color(255, 255, 255));
        lbQLPH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbQLPH.setText("Quản lý phản hồi");
        lbQLPH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbQLPHMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbQLPH, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 160, 30));

        lbTrangChu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        lbTrangChu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangChu.setText("Trang chủ");
        lbTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTrangChuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbTrangChuMouseEntered(evt);
            }
        });
        PnSlideMenu.add(lbTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 160, 30));

        lbDangxuat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbDangxuat.setForeground(new java.awt.Color(255, 255, 255));
        lbDangxuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDangxuat.setText("Đăng xuất");
        lbDangxuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDangxuatMouseClicked(evt);
            }
        });
        PnSlideMenu.add(lbDangxuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 390, 150, -1));

        jPanel1.add(PnSlideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, 510));

        lbOpenMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/menu.png"))); // NOI18N
        lbOpenMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOpenMenuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PnMenuBarLayout = new javax.swing.GroupLayout(PnMenuBar);
        PnMenuBar.setLayout(PnMenuBarLayout);
        PnMenuBarLayout.setHorizontalGroup(
            PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnMenuBarLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lbOpenMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(824, Short.MAX_VALUE))
        );
        PnMenuBarLayout.setVerticalGroup(
            PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnMenuBarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lbOpenMenu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PnMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 30, 910, 50));

        pnMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnMain.setLayout(new java.awt.CardLayout());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("THỐNG KÊ");

        jLabel2.setText("Tháng: ");

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThangActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("Doanh Thu:");

        btnXuatFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnXuatFile.setText("Xuất File");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        lbTongTien.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lbTongTien.setForeground(new java.awt.Color(255, 51, 51));
        lbTongTien.setText("0 vnđ");

        dgvThongKe.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(dgvThongKe);

        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/searching.png"))); // NOI18N
        btnXem.setText("Xem");
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        rdGiam.setText("Giảm");
        rdGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdGiamActionPerformed(evt);
            }
        });

        rdTang.setText("Tăng");
        rdTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTangActionPerformed(evt);
            }
        });

        jLabel1.setText("Sắp xếp theo tổng tiền:");

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Năm:");

        javax.swing.GroupLayout cardTrangChuLayout = new javax.swing.GroupLayout(cardTrangChu);
        cardTrangChu.setLayout(cardTrangChuLayout);
        cardTrangChuLayout.setHorizontalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(lbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(cardTrangChuLayout.createSequentialGroup()
                                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                                        .addComponent(btnXem)
                                        .addGap(377, 377, 377)))
                                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdTang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXuatFile)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        cardTrangChuLayout.setVerticalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(rdTang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdGiam))
                .addGap(18, 18, 18)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pnMain.add(cardTrangChu, "card2");

        jPanel1.add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 80, 910, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbOpenMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpenMenuMouseClicked
        // TODO add your handling code here:
        openMenu();
    }//GEN-LAST:event_lbOpenMenuMouseClicked

    private void cboThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangActionPerformed

    }//GEN-LAST:event_cboThangActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        // TODO add your handling code here:
        String thangSTR = (String) cboThang.getSelectedItem();
        String namSTR = (String) cboNam.getSelectedItem();
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");

        if (thangSTR != null && namSTR != null) {
            double tongTien = 0;
            if (thangSTR.equals("Tất cả") && namSTR.equals("Tất cả")) {
                lst = DonHangController.getList();
                for (int i = 0; i < lst.size(); i++) {
                    tongTien += 0;
                }
            }

            if (thangSTR.matches("\\d+") && namSTR.equals("Tất cả")) {
                int thang = Integer.parseInt(thangSTR);
                lst = DonHangController.getList();

                // Duyệt qua danh sách cần lọc
                for (int i = 0; i < lst.size(); i++) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // Chuyển đổi chuỗi thành LocalDate
                    LocalDate ngayDat = LocalDate.parse(lst.get(i).getNgayDat(), formatter);

                    // Lấy ra tháng từ đối tượng LocalDate
                    int thang1 = ngayDat.getMonthValue();

                    if (thang == thang1) {
                        tongTien += 0;
                    } else {
                        lst.remove(lst.get(i));
                        i--;
                    }
                }
            }

            if (thangSTR.equals("Tất cả") && namSTR.matches("\\d+")) {
                int nam = Integer.parseInt((String) cboNam.getSelectedItem());
                lst = DonHangController.getList();

                // Duyệt qua danh sách cần lọc
                for (int i = 0; i < lst.size(); i++) {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // Chuyển đổi chuỗi thành LocalDate
                    LocalDate ngayDat = LocalDate.parse(lst.get(i).getNgayDat(), formatter);

                    // Lấy ra tháng từ đối tượng LocalDate
                    int nam1 = ngayDat.getYear();

                    if (nam == nam1) {
                        tongTien += 0;
                    } else {
                        lst.remove(lst.get(i));
                        i--;
                    }
                }

            }

            if (thangSTR.matches("\\d+") && namSTR.matches("\\d+")) {
                int thang = Integer.parseInt(thangSTR);
                int nam = Integer.parseInt((String) cboNam.getSelectedItem());
                lst = DonHangController.getList();

                for (int i = 0; i < lst.size(); i++) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // Chuyển đổi chuỗi thành LocalDate
                    LocalDate ngayDat = LocalDate.parse(lst.get(i).getNgayDat(), formatter);

                    // Lấy ra tháng từ đối tượng LocalDate
                    int nam1 = ngayDat.getYear();
                    int thang1 = ngayDat.getMonthValue();

                    if (nam == nam1 && thang == thang1) {
                        tongTien += 0;
                    } else {
                        lst.remove(lst.get(i));
                        i--;
                    }
                }

            }

            // Hiển thị kết quả trong dgvThongKe
            tb.setRowCount(0);
            for (DonHang tk : lst) {
                if (tk.getTrangThai().equals("Hoàn thành")) {
                    tb.addRow(new Object[]{tk.getMaDH(), tk.getMaKh(), tk.getNgayDat(), tk.getTongTien()});
                    tongTien += tk.getTongTien();
                }
            }
            lbTongTien.setText(decimalFormat.format(tongTien));
        }
        updateTable();
    }//GEN-LAST:event_btnXemActionPerformed

    private void lbTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTrangChuMouseClicked
        // TODO add your handling code here:
        Dashboard menuform = new Dashboard();
        menuform.setVisible(true);
        menuform.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_lbTrangChuMouseClicked

    private void lbQLPHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLPHMouseClicked
        // TODO add your handling code here:
        QuanLyPhanHoi sp = new QuanLyPhanHoi();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLPHMouseClicked

    private void lbThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongKeMouseClicked
        // TODO add your handling code here:
        ThongKe sp = new ThongKe();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbThongKeMouseClicked

    private void lbQLDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLDonHangMouseClicked
        // TODO add your handling code here:
        QLDonHangForm sp = new QLDonHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLDonHangMouseClicked

    private void lbQLTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLTKMouseClicked
        // TODO add your handling code here:
        QuanLyTaiKhoan sp = new QuanLyTaiKhoan();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLTKMouseClicked

    private void lbQLSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLSPMouseClicked
        // TODO add your handling code here:
        SanPhamForm sp = new SanPhamForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLSPMouseClicked

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        // TODO add your handling code here:
        closeMenu();
    }//GEN-LAST:event_lbCloseMouseClicked

    private void lbDangxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangxuatMouseClicked
        // TODO add your handling code here:
        DangNhapForm sp = new DangNhapForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDangxuatMouseClicked

    private void rdTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTangActionPerformed
        // TODO add your handling code here:
        Comparator<DonHang> c = new Comparator<DonHang>() {
            @Override
            public int compare(DonHang o1, DonHang o2) {
                return Double.compare(o1.getTongTien(), o2.getTongTien());
            }
        };
        sort(lst, c);
        tb.setRowCount(0);

        for (DonHang tk : lst) {
            if (tk.getTrangThai().equals("Hoàn thành")) {
                String tongTien = decimalFormat.format(tk.getTongTien());
                tb.addRow(new Object[]{tk.getMaDH(), tk.getMaKh(), tk.getNgayDat(), tongTien});
            }
        }
    }//GEN-LAST:event_rdTangActionPerformed

    private void rdGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdGiamActionPerformed
        Comparator<DonHang> c = new Comparator<DonHang>() {
            @Override
            public int compare(DonHang o1, DonHang o2) {
                return Double.compare(o1.getTongTien(), o2.getTongTien());
            }
        };
        sort(lst, c.reversed());
        tb.setRowCount(0);

        for (DonHang tk : lst) {
            if (tk.getTrangThai().equals("Hoàn thành")) {
                String tongTien = decimalFormat.format(tk.getTongTien());
                tb.addRow(new Object[]{tk.getMaDH(), tk.getMaKh(), tk.getNgayDat(), tongTien});
            }
        }
    }//GEN-LAST:event_rdGiamActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // TODO add your handling code here:
        ExportExcel.ExportExcel.xuatFile(dgvThongKe, "Thống kê");
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void lbTrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTrangChuMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbTrangChuMouseEntered
    private void loadComboBox() {
        cboThang.removeAllItems();

        cboThang.addItem("Tất cả");
        for (int i = 1; i <= 12; i++) {
            cboThang.addItem(String.valueOf(i));
        }

    }

    private void loadComboBox2() {
        cboNam.removeAllItems();
        cboNam.addItem("Tất cả");
        HashSet<Integer> lstNam = new HashSet<>();

        for (DonHang dh : lst) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Chuyển đổi chuỗi thành LocalDate
            LocalDate ngayDat = LocalDate.parse(dh.getNgayDat(), formatter);
            // Lấy ra tháng từ đối tượng LocalDate
            int nam = ngayDat.getYear();
            lstNam.add(nam);
        }

        Iterator<Integer> iterator = lstNam.iterator();
        while (iterator.hasNext()) {
            int nam = iterator.next();
            cboNam.addItem(nam + "");
        }
    }

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
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnMenuBar;
    private javax.swing.JPanel PnSlideMenu;
    private javax.swing.JPanel PnTitile;
    private javax.swing.JButton btnXem;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JPanel cardTrangChu;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JTable dgvThongKe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbClose;
    private javax.swing.JLabel lbDangxuat;
    private javax.swing.JLabel lbOpenMenu;
    private javax.swing.JLabel lbQLDonHang;
    private javax.swing.JLabel lbQLPH;
    private javax.swing.JLabel lbQLSP;
    private javax.swing.JLabel lbQLTK;
    private javax.swing.JLabel lbThongKe;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lbTrangChu;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JRadioButton rdGiam;
    private javax.swing.JRadioButton rdTang;
    // End of variables declaration//GEN-END:variables
}
