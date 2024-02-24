/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.SanPhamController;
import Controller.ThemVaoGioHangController;
import DangNhap.DangNhapForm;
import DTO.CurrentAccount;
import Entity.SanPham;
import Entity.GioHang;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class ThemVaoGioHangForm extends javax.swing.JFrame {

    List<SanPham> lst;
    HashSet<SanPham> hs = new HashSet<>();
    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");

    String header[] = {"Tên sản phẩm", "Màu", "Size", "Đơn giá", "Số lượng còn", "Loại", "Giới tính"};
    DefaultTableModel tb = new DefaultTableModel();
    private int currentPage = 1;
    private int recordsPerPage = 10;
    private JButton[] pageButtons;
    private Map<Integer, SanPham> indexToSanPham;
    int dongChon = -1;

    /**
     * Creates new form KhachHangForm
     */
    public ThemVaoGioHangForm() {
        initComponents();
        // Tạo ImageIcon từ đường dẫn
        ImageIcon imageIcon = new ImageIcon("src/image/SanPham/product.png");

        // Chỉnh kích thước hình ảnh
        Image scaledImage = imageIcon.getImage().getScaledInstance(170, 138, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Hiển thị hình ảnh trên lbAnh
        lbAnh.setIcon(scaledIcon);
        tb = (DefaultTableModel) tblSanPhamKh.getModel();
        tb.setColumnCount(4);
        tb.setColumnIdentifiers(header); // Thêm dòng này để đặt tên cột

        updateTable();
        updatePageButtons(); // Thêm dòng này để tạo các nút trang
        pnlButtons.setLayout(new java.awt.FlowLayout());

        loadComBoxBox();
        labelUsername.setText(CurrentAccount.kh.getHoTen());
        ButtonGroup b = new ButtonGroup();
        b.add(rdGiam);
        b.add(rdTang);
        setLocationRelativeTo(null); // chinh cua so xuat hien chinh giua man hinh
    }

    private void updateTable() {
        lst = SanPhamController.getList();
        for (SanPham sp : lst) {
            if (sp.getTinhTrang().equals("Đang bán")) {
                hs.add(sp);
            }
        }

        indexToSanPham = new HashMap<>();
        int index = 0;

        for (SanPham sp : hs) {
            indexToSanPham.put(index++, sp);
        }

        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, hs.size());
        tb.setRowCount(0);

        for (int i = start; i < end; i++) {
            SanPham sp = indexToSanPham.get(i);
            String dg = decimalFormat.format(sp.getDonGia());

            tb.addRow(new Object[]{sp.getTenSp(), sp.getMau(), sp.getSize(), dg, sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh()});
        }

        updatePageButtons();
    }

    public void loadComBoxBox() {
        HashSet<String> hs1 = new HashSet<>();
        HashSet<String> hs2 = new HashSet<>();

        for (SanPham sp : lst) {
            hs1.add(sp.getSize());
            hs2.add(sp.getMau());
        }

        cboSize.removeAllItems();
        cboMau.removeAllItems();
        cboGioiTinh.removeAllItems();

        for (String s : hs1) {
            cboSize.addItem(s);
        }

        for (String s : hs2) {
            cboMau.addItem(s);
        }
        
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");
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
        return (int) Math.ceil((double) hs.size() / recordsPerPage);
    }

    int x = 160, y = 630;

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

    public void loadTableSanPham() {
        lst = SanPhamController.getList();
        tb.setRowCount(0);

        for (SanPham sp : lst) {
            if (sp.getTinhTrang().equals("Đang bán")) {
                hs.add(sp);
            }
        }

        indexToSanPham = new HashMap<>();
        int index = 0;

        for (SanPham sp : hs) {
            indexToSanPham.put(index++, sp);
        }

        for (SanPham sp : hs) {
            tb.addRow(new Object[]{sp.getTenSp(), sp.getMau(), sp.getSize(), sp.getDonGia(), sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh()});
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
        jLabel5 = new javax.swing.JLabel();
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
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSoLuongDat = new javax.swing.JTextField();
        txtTenSp = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lbAnh = new javax.swing.JLabel();
        cboSize = new javax.swing.JComboBox<>();
        cboMau = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtSoLuongCon = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        txtViewCart = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        rdTang = new javax.swing.JRadioButton();
        rdGiam = new javax.swing.JRadioButton();
        pnlButtons = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnTitile.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout PnTitileLayout = new javax.swing.GroupLayout(PnTitile);
        PnTitile.setLayout(PnTitileLayout);
        PnTitileLayout.setHorizontalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1130, Short.MAX_VALUE)
        );
        PnTitileLayout.setVerticalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel2.add(PnTitile, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1130, 30));

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

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/avatar.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel2.add(PnSlideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 160, 630));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 891, Short.MAX_VALUE)
                .addComponent(labelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
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

        jPanel2.add(PnMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1120, 50));

        pnMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnMain.setLayout(new java.awt.CardLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DANH SÁCH SẢN PHẨM");

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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Đơn giá :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Số lượng đặt :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Size:");

        txtSoLuongDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongDatActionPerformed(evt);
            }
        });

        txtTenSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSpActionPerformed(evt);
            }
        });

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Tên sản phẩm :");

        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSizeActionPerformed(evt);
            }
        });

        cboMau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Màu:");

        txtSoLuongCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongConActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Số lượng còn :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Giới tính:");

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGioiTinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuongDat, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenSp, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(txtSoLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboSize, 0, 85, Short.MAX_VALUE)
                            .addComponent(cboMau, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(cboGioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTenSp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboMau))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSoLuongDat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSoLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        jButton1.setText("Thêm vào giỏ hàng");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtViewCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shopping-cart (1).png"))); // NOI18N
        txtViewCart.setText("Xem giỏ hàng");
        txtViewCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViewCartActionPerformed(evt);
            }
        });

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh (1).png"))); // NOI18N
        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        jLabel1.setText("Sắp xếp: ");

        rdTang.setText("Tăng");
        rdTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdTangActionPerformed(evt);
            }
        });

        rdGiam.setText("Giảm");
        rdGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdGiamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/searching.png"))); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cardTrangChuLayout = new javax.swing.GroupLayout(cardTrangChu);
        cardTrangChu.setLayout(cardTrangChuLayout);
        cardTrangChuLayout.setHorizontalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                        .addComponent(btnLoad)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdTang, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)
                        .addGap(79, 79, 79)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtViewCart, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(pnlButtons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(58, 58, 58))))
        );
        cardTrangChuLayout.setVerticalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtViewCart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(rdTang)
                    .addComponent(rdGiam)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pnMain.add(cardTrangChu, "card2");

        jPanel2.add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1120, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 651, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSoLuongDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongDatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongDatActionPerformed

    private void txtTenSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSpActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void tblSanPhamKhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamKhMouseClicked
        // TODO add your handling code here:
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        dongChon = tblSanPhamKh.getSelectedRow();

        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;

            SanPham sp = indexToSanPham.get(dongChon);

            txtTenSp.setText(sp.getTenSp());
            txtSoLuongDat.setText("1");

            HashSet<String> hs1 = new HashSet<>();
            HashSet<String> hs2 = new HashSet<>();

            for (SanPham sanPham : lst) {
                if (sanPham.getTenSp().equals(sp.getTenSp())) {
                    hs1.add(sanPham.getSize());
                    hs2.add(sanPham.getMau());
                }
            }

            cboSize.removeAllItems();
            for (String size : hs1) {
                cboSize.addItem(size);
            }

            cboMau.removeAllItems();
            for (String mau : hs2) {
                cboMau.addItem(mau);
            }
            
            cboGioiTinh.setSelectedItem(sp.getGioiTinh());
            

            cboSize.setSelectedItem(sp.getSize());
            cboMau.setSelectedItem(sp.getMau());
            String dg = decimalFormat.format(sp.getDonGia());
            txtDonGia.setText(dg);
            txtSoLuongCon.setText(sp.getSoLuongCon() + "");

            String path = "";
            for (SanPham sanPham : lst) {
                if (sp.getTenSp().equals(sanPham.getTenSp()) && sp.getMau().equals(sanPham.getMau()) && sp.getSize().equals(sanPham.getSize())) {
                    path = sp.getAvatar();
                }
            }

            // Kiểm tra nếu đường dẫn hình ảnh không rỗng
            if (!path.isEmpty()) {
                // Tạo ImageIcon từ đường dẫn
                ImageIcon imageIcon = new ImageIcon("src/image/SanPham/" + path);

                // Chỉnh kích thước hình ảnh
                Image scaledImage = imageIcon.getImage().getScaledInstance(170, 138, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Hiển thị hình ảnh trên lbAnh
                lbAnh.setIcon(scaledIcon);
            }
        }
    }//GEN-LAST:event_tblSanPhamKhMouseClicked

    private boolean dieukienSL(String soLuong) {

        try {
            int value = Integer.parseInt(soLuong);
            if (value < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng đặt phải lớn hơn 0 !", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng đặt phải là một số !", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dongChon = tblSanPhamKh.getSelectedRow();
        SanPham sp = new SanPham();
        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;
            size = (String) cboSize.getSelectedItem();
            mau = (String) cboMau.getSelectedItem();
            gt = (String) cboGioiTinh.getSelectedItem();
            sp = indexToSanPham.get(dongChon);
            String soLuongDat = txtSoLuongDat.getText();
            if (dieukienSL(soLuongDat)) {
                int countOrder = Integer.parseInt(soLuongDat);
                if (countOrder <= sp.getSoLuongCon()) {
                    for (SanPham sanPham : lst) {
                        if (sanPham.getTenSp().equals(sp.getTenSp()) && sanPham.getMau().equals(mau) 
                                && sanPham.getSize().equals(size) && sanPham.getGioiTinh().equals(gt)) {
                            GioHang gioHang = new GioHang(CurrentAccount.account.getMaKh(), sanPham.getMaSp(), countOrder);
                            ThemVaoGioHangController.insert(gioHang);
                        }
                    }
                    JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Số lượng đặt lớn hơn số lượng hiện có !", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm !", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtViewCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtViewCartActionPerformed
        // TODO add your handling code here:
        XemGioHangForm sp = new XemGioHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_txtViewCartActionPerformed

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        // TODO add your handling code here:
        closeMenu();
    }//GEN-LAST:event_lbCloseMouseClicked

    private void lbOpenMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpenMenuMouseClicked
        // TODO add your handling code here:
        openMenu();
    }//GEN-LAST:event_lbOpenMenuMouseClicked

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
        updateTable();
    }//GEN-LAST:event_btnLoadActionPerformed

    private void rdTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTangActionPerformed
        // TODO add your handling code herez
        Comparator<SanPham> c = new Comparator<SanPham>() {
            @Override
            public int compare(SanPham o1, SanPham o2) {
                return Double.compare(o1.getDonGia(), o2.getDonGia());
            }
        };

        ArrayList<SanPham> list = new ArrayList<>(hs);
        sort(list, c);
        tb.setRowCount(0);
        for (SanPham sp : list) {
            String dg = decimalFormat.format(sp.getDonGia());
            tb.addRow(new Object[]{sp.getTenSp(), sp.getMau(), sp.getSize(), dg, sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh()});
        }
    }//GEN-LAST:event_rdTangActionPerformed

    private void rdGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdGiamActionPerformed
        // TODO add your handling code here:
        Comparator<SanPham> c = new Comparator<SanPham>() {
            @Override
            public int compare(SanPham o1, SanPham o2) {
                return Double.compare(o1.getDonGia(), o2.getDonGia());
            }
        };
        ArrayList<SanPham> list = new ArrayList<>(hs);
        sort(list, c.reversed());
        tb.setRowCount(0);
        for (SanPham sp : list) {
            String dg = decimalFormat.format(sp.getDonGia());
            tb.addRow(new Object[]{sp.getTenSp(), sp.getMau(), sp.getSize(), dg, sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh()});
        }
    }//GEN-LAST:event_rdGiamActionPerformed

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
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_labelUsernameMouseClicked

    private void txtSoLuongConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongConActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongConActionPerformed

    String size;
    String mau;
    String gt;
    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeActionPerformed
        // TODO add your handling code here:
        dongChon = tblSanPhamKh.getSelectedRow();
        SanPham sp = new SanPham();
        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;
            sp = indexToSanPham.get(dongChon);
            size = (String) cboSize.getSelectedItem();
            mau = (String) cboMau.getSelectedItem();
            gt = (String) cboGioiTinh.getSelectedItem();
            int count = 0;
            for (SanPham sanPham : lst) {
                if (sanPham.getTenSp().equals(sp.getTenSp()) && sanPham.getMau().equals(mau) 
                        && sanPham.getSize().equals(size) && sanPham.getGioiTinh().equals(gt)) {
                    txtSoLuongCon.setText(sanPham.getSoLuongCon() + "");
                    count++;
                    break;
                }
            }
            if (count == 0) {
                txtSoLuongCon.setText("0");
            }
        }
    }//GEN-LAST:event_cboSizeActionPerformed

    private void cboMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauActionPerformed
        dongChon = tblSanPhamKh.getSelectedRow();
        dongChon = tblSanPhamKh.getSelectedRow();
        SanPham sp = new SanPham();
        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;
            sp = indexToSanPham.get(dongChon);
            size = (String) cboSize.getSelectedItem();
            mau = (String) cboMau.getSelectedItem();
            gt = (String) cboGioiTinh.getSelectedItem();
            int count = 0;
            for (SanPham sanPham : lst) {
                if (sanPham.getTenSp().equals(sp.getTenSp()) && sanPham.getMau().equals(mau) 
                        && sanPham.getSize().equals(size) && sanPham.getGioiTinh().equals(gt)) {
                    txtSoLuongCon.setText(sanPham.getSoLuongCon() + "");
                    count++;
                    break;
                }
            }
            if (count == 0) {
                txtSoLuongCon.setText("0");
            }
        }
    }//GEN-LAST:event_cboMauActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if (txtTenSp.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Nhập tên sản phẩm trước khi tìm");
            return;
        }

        lst = SanPhamController.searchByName(txtTenSp.getText());
        hs = new HashSet<>();

        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);

        for (int i = start; i < end; i++) {
            SanPham sp = lst.get(i);
            hs.add(sp);
        }

        for (SanPham sp : hs) {
            String dg = decimalFormat.format(sp.getDonGia());
            tb.addRow(new Object[]{sp.getTenSp(), sp.getMau(), sp.getSize(), dg, sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh()});
        }
        updatePageButtons();

    }//GEN-LAST:event_btnSearchActionPerformed

    private void cboGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGioiTinhActionPerformed
        // TODO add your handling code here:
        dongChon = tblSanPhamKh.getSelectedRow();
        SanPham sp = new SanPham();
        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;
            sp = indexToSanPham.get(dongChon);
            size = (String) cboSize.getSelectedItem();
            mau = (String) cboMau.getSelectedItem();
            gt = (String) cboGioiTinh.getSelectedItem();
            int count = 0;
            for (SanPham sanPham : lst) {
                if (sanPham.getTenSp().equals(sp.getTenSp()) && sanPham.getMau().equals(mau) 
                        && sanPham.getSize().equals(size) && sanPham.getGioiTinh().equals(gt)) {
                    txtSoLuongCon.setText(sanPham.getSoLuongCon() + "");
                    count++;
                    break;
                }
            }
            if (count == 0) {
                txtSoLuongCon.setText("0");
            }
        }
    }//GEN-LAST:event_cboGioiTinhActionPerformed

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
            java.util.logging.Logger.getLogger(ThemVaoGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThemVaoGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThemVaoGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThemVaoGioHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ThemVaoGioHangForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnMenuBar;
    private javax.swing.JPanel PnSlideMenu;
    private javax.swing.JPanel PnTitile;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel cardTrangChu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JComboBox<String> cboMau;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel lbAnh;
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
    private javax.swing.JRadioButton rdGiam;
    private javax.swing.JRadioButton rdTang;
    private javax.swing.JTable tblSanPhamKh;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtSoLuongCon;
    private javax.swing.JTextField txtSoLuongDat;
    private javax.swing.JTextField txtTenSp;
    private javax.swing.JButton txtViewCart;
    // End of variables declaration//GEN-END:variables
}
