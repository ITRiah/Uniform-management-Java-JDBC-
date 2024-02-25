/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.SanPhamController;
import DangNhap.DangNhapForm;
import Entity.SanPham;
import ExportExcel.ExportExcel;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class SanPhamForm extends javax.swing.JFrame {

    /**
     * Creates new form SanPhamForms
     */
    DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
    List<SanPham> lst;
    String header[] = {"Mã sản phẩm", "Tên sản phẩm", "Màu", "Size", "Đơn giá", "Số lượng còn", "Loại", "Giới tính", "Tình trạng"};
    DefaultTableModel tb = new DefaultTableModel();
    private int currentPage = 1;
    private int recordsPerPage = 10;
    private JButton[] pageButtons;
    int dongChon = -1;//Dòng chọn bảng sản phẩm
    int x = 160;
    int y = 530;

    public SanPhamForm() {
        initComponents();
        tb = (DefaultTableModel) tblSanPham.getModel();
        tb.setColumnCount(3);
        tb.setColumnIdentifiers(header); // Thêm dòng này để đặt tên cột
        // Tạo ImageIcon từ đường dẫn
        ImageIcon imageIcon = new ImageIcon("src/image/SanPham/" + "product.png");
        // Chỉnh kích thước hình ảnh
        Image scaledImage = imageIcon.getImage().getScaledInstance(170, 138, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        loadTableSanPham();
        LoadComboBox();
        // Hiển thị hình ảnh trên lbAnh
        lbAnh.setIcon(scaledIcon);
        setLocationRelativeTo(null); // chinh cua so xuat hien chinh giua man hinh
        updateTable();
        updatePageButtons(); // Thêm dòng này để tạo các nút trang
        pnlButtons.setLayout(new java.awt.FlowLayout());
    }

    private void updateTable() {
        lst = SanPhamController.getList();

        //sort
        Collections.sort(lst, new Comparator<SanPham>() {
            @Override
            public int compare(SanPham sp1, SanPham sp2) {
                // Lấy phần số của mã sản phẩm
                int maSP1 = Integer.parseInt(sp1.getMaSp().substring(2));
                int maSP2 = Integer.parseInt(sp2.getMaSp().substring(2));

                // So sánh phần số của mã sản phẩm
                return Integer.compare(maSP1, maSP2);
            }
        });

        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);
        for (int i = start; i < end; i++) {
            SanPham sp = lst.get(i);
            String dg = decimalFormat.format(sp.getDonGia());
            tb.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getMau(), sp.getSize(), dg, sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh(), sp.getTinhTrang()});
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
            tb.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getMau(), sp.getSize(), sp.getDonGia(), sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh(), sp.getTinhTrang()});
        }
    }

    public void LoadComboBox() {
        cboGioiTinh.removeAllItems();
        cboLoai.removeAllItems();
        cboTinhTrang.removeAllItems();

        cboTinhTrang.addItem("Đang bán");
        cboTinhTrang.addItem("Ngưng bán");

        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");

        cboLoai.addItem("Thuê");
        cboLoai.addItem("Mua");
    }

    private boolean dieukienSL(String soLuong) {
        try {
            int value = Integer.parseInt(soLuong);
            if (value < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0 !", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là một số !", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        PnTitile = new javax.swing.JPanel();
        PnSlideMenu = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbClose = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
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
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuongCon = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        txtTenSp = new javax.swing.JTextField();
        btnThemMoi = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnExportExcel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        lbAnh = new javax.swing.JLabel();
        btnHinhAnh = new javax.swing.JButton();
        txtSize = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMau = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboTinhTrang = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        pnlButtons = new javax.swing.JPanel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnTitile.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout PnTitileLayout = new javax.swing.GroupLayout(PnTitile);
        PnTitile.setLayout(PnTitileLayout);
        PnTitileLayout.setHorizontalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        PnTitileLayout.setVerticalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel2.add(PnTitile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 30));

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

        jPanel2.add(PnSlideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 160, 660));

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
                .addContainerGap(914, Short.MAX_VALUE))
        );
        PnMenuBarLayout.setVerticalGroup(
            PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnMenuBarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lbOpenMenu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(PnMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1000, 50));

        pnMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnMain.setLayout(new java.awt.CardLayout());

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("QUẢN LÝ SẢN PHẨM");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Đơn giá :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Số lượng có : ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Size :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Tên sản phẩm : ");

        txtSoLuongCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongConActionPerformed(evt);
            }
        });

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        txtTenSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSpActionPerformed(evt);
            }
        });

        btnThemMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThemMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        btnThemMoi.setText("Thêm mới");
        btnThemMoi.setBorder(null);
        btnThemMoi.setPreferredSize(null);
        btnThemMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMoiActionPerformed(evt);
            }
        });

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/edit.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setBorder(null);
        btnCapNhat.setPreferredSize(null);
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnExportExcel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnExportExcel.setText("Xuất Excel");
        btnExportExcel.setBorder(null);
        btnExportExcel.setPreferredSize(null);
        btnExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh (1).png"))); // NOI18N
        btnRefresh.setText("Làm mới");
        btnRefresh.setBorder(null);
        btnRefresh.setPreferredSize(null);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/searching.png"))); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.setBorder(null);
        btnSearch.setPreferredSize(null);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnHinhAnh.setText("Hình ảnh");
        btnHinhAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhAnhActionPerformed(evt);
            }
        });

        txtSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSizeActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Màu:");

        txtMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMauActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Loại:");

        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tình trạng:");

        cboTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Giới tính:");

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThemMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHinhAnh)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtSoLuongCon, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                            .addComponent(txtTenSp)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11))
                                        .addGap(46, 46, 46)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(34, 34, 34)
                                        .addComponent(txtMau, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHinhAnh))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTenSp, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtSoLuongCon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtMau, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
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
        jScrollPane1.setViewportView(tblSanPham);

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

        javax.swing.GroupLayout cardTrangChuLayout = new javax.swing.GroupLayout(cardTrangChu);
        cardTrangChu.setLayout(cardTrangChuLayout);
        cardTrangChuLayout.setHorizontalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        cardTrangChuLayout.setVerticalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pnMain.add(cardTrangChu, "card2");

        jPanel2.add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1000, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSoLuongConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongConActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongConActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void txtTenSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSpActionPerformed

    private void btnThemMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMoiActionPerformed
        // TODO add your handling code here:
        String tenSp = txtTenSp.getText();
        double donGia = Double.parseDouble(txtDonGia.getText());
        int soLuong = Integer.parseInt(txtSoLuongCon.getText());
        String loaiSP = (String) cboLoai.getSelectedItem();
        String gioiTinh = (String) cboGioiTinh.getSelectedItem();
        String trangThai = (String) cboTinhTrang.getSelectedItem();
        String mau = txtMau.getText();
        String msg = "";
        String size = txtSize.getText();
        String hinhAnh = btnHinhAnh.getText();

        if (tenSp.equals("")) {
            msg += "Tên sản phẩm không được để trống\n";
        }
        System.out.println(msg);
        if (donGia <= 0) {
            msg += "Đơn giá phải lớn hơn 0\n";
        }
        if (soLuong <= 0) {
            msg += "Số lượng phải lớn hơn 0\n";
        }
        if (loaiSP.equals("")) {
            msg += "Loại sản phẩm không được để trống\n";
        }
        if (gioiTinh.equals("")) {
            msg += "Giới tính sản phẩm không được để trống\n";
        }
        if (trangThai.equals("")) {
            msg += "Trạng thái sản phẩm không được để trống\n";
        }
        if (mau.equals("")) {
            msg += "Màu sản phẩm không được để trống\n";
        }
        if (size.equals("")) {
            msg += "Size sản phẩm không được để trống\n";
        }
        if (hinhAnh.equals("")) {
            msg += "Hình ảnh sản phẩm không được để trống\n";
        }

        SanPham sp = null;
        sp = new SanPham(tenSp, mau, size, donGia, soLuong, hinhAnh, loaiSP, gioiTinh, trangThai);

        if (msg.equals("")) {
            for (SanPham sanPham : lst) {
                if (sanPham.getTenSp().equals(sp.getTenSp()) && sanPham.getMau().equals(sp.getMau())
                        && sanPham.getSize().equals(sp.getSize()) && sanPham.getGioiTinh().equals(sp.getGioiTinh())) {
                    JOptionPane.showMessageDialog(rootPane, "Sản phẩm này đã tồn tại!");
                    return;
                }
            }
            if (selectedFile != null) {
                saveImageToFolder(selectedFile.getAbsolutePath());
            }
            SanPhamController.insert(sp);
            updateTable();
            JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
        } else {
            JOptionPane.showMessageDialog(rootPane, msg, "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThemMoiActionPerformed

    private void saveImageToFolder(String imagePath) {
        try {
            // Extract the file name from the full path
            String fileName = new File(imagePath).getName();

            // Define the destination directory within the Source Package
            String destinationDirectory = "src/image/SanPham/";

            // Create a file object for the destination path
            File destinationPath = new File(destinationDirectory + fileName);

            // Copy the selected image to the destination path
            Files.copy(Paths.get(imagePath), destinationPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Handle any exceptions that may occur during the image saving process
            e.printStackTrace();
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuongCon.setText("");
        txtSize.setText("");
        txtMau.setText("");

        // Tạo ImageIcon từ đường dẫn
        ImageIcon imageIcon = new ImageIcon("src/image/SanPham/" + "product.png");

        // Chỉnh kích thước hình ảnh
        Image scaledImage = imageIcon.getImage().getScaledInstance(170, 138, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Hiển thị hình ảnh trên lbAnh
        lbAnh.setIcon(scaledIcon);

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        lst = SanPhamController.searchByName(txtTenSp.getText());
        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);
        for (int i = start; i < end; i++) {
            SanPham sp = lst.get(i);
            String dg = decimalFormat.format(sp.getDonGia());
            tb.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getMau(), sp.getSize(), dg, sp.getSoLuongCon(), sp.getLoai(), sp.getGioiTinh(), sp.getTinhTrang()});
        }
        updatePageButtons();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        dongChon = tblSanPham.getSelectedRow();
        SanPham sanpham = new SanPham();
        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;
            sanpham = lst.get(dongChon);
            txtTenSp.setText(sanpham.getTenSp() + "");
            String dg = decimalFormat.format(sanpham.getDonGia());
            txtDonGia.setText(dg);
            txtSize.setText(sanpham.getSize() + "");
            txtSoLuongCon.setText(sanpham.getSoLuongCon() + "");
            txtMau.setText(sanpham.getMau());
            cboGioiTinh.setSelectedItem(sanpham.getGioiTinh());
            cboLoai.setSelectedItem(sanpham.getLoai());
            cboTinhTrang.setSelectedItem(sanpham.getTinhTrang());

            String path = sanpham.getAvatar();
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
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        String tenSp = txtTenSp.getText();
        double donGia = Double.parseDouble(txtDonGia.getText());
        int soLuong = Integer.parseInt(txtSoLuongCon.getText());
        String loaiSP = (String) cboLoai.getSelectedItem();
        String gioiTinh = (String) cboGioiTinh.getSelectedItem();
        String trangThai = (String) cboTinhTrang.getSelectedItem();
        String mau = txtMau.getText();
        String msg = "";
        String size = txtSize.getText();
        String hinhAnh = btnHinhAnh.getText();
        dongChon = tblSanPham.getSelectedRow();
        if (dongChon != -1) {
            dongChon += currentPage * recordsPerPage - recordsPerPage;

            if (tenSp.equals("")) {
                msg += "Tên sản phẩm không được để trống\n";
            }
            System.out.println(msg);
            if (donGia <= 0) {
                msg += "Đơn giá phải lớn hơn 0\n";
            }
            if (soLuong <= 0) {
                msg += "Số lượng phải lớn hơn 0\n";
            }
            if (loaiSP.equals("")) {
                msg += "Loại sản phẩm không được để trống\n";
            }
            if (gioiTinh.equals("")) {
                msg += "Giới tính sản phẩm không được để trống\n";
            }
            if (trangThai.equals("")) {
                msg += "Trạng thái sản phẩm không được để trống\n";
            }
            if (mau.equals("")) {
                msg += "Màu sản phẩm không được để trống\n";
            }
            if (size.equals("")) {
                msg += "Size sản phẩm không được để trống\n";
            }
            if (hinhAnh.equals("")) {
                msg += "Hình ảnh sản phẩm không được để trống\n";
            }
            if (!msg.equals("")) {
                JOptionPane.showMessageDialog(rootPane, msg, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int choice = JOptionPane.showConfirmDialog(this, "Xác nhận cập nhật phẩm? ", "Xác Nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                return;
            }
            SanPham sp = lst.get(dongChon);
            try {
                sp.setTenSp(txtTenSp.getText());
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            try {
                sp.setMau(mau);
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            try {
                sp.setSize(size);
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            try {
                sp.setDonGia(Double.parseDouble(txtDonGia.getText()));
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            try {
                sp.setSoLuongCon(Integer.parseInt(txtSoLuongCon.getText()));
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            String avatar = "";
            if (selectedFile == null) {
                avatar = sp.getAvatar();
            } else {
                avatar = selectedFile.getName();
                saveImageToFolder(selectedFile.getAbsolutePath());
            }
            sp.setAvatar(avatar);
            try {
                sp.setLoai(loaiSP);
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            try {
                sp.setGioiTinh(gioiTinh);
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            try {
                sp.setTinhTrang(trangThai);
            } catch (Exception ex) {
                msg += ex.getMessage();
            }
            SanPhamController.update(sp);
            updateTable();
        } else
            JOptionPane.showMessageDialog(this,
                    "Chưa chọn dòng cập nhật", "Thông báo", WIDTH);
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        // TODO add your handling code here:
        closeMenu();
    }//GEN-LAST:event_lbCloseMouseClicked

    private void lbQLSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLSPMouseClicked
        // TODO add your handling code here:
        SanPhamForm sp = new SanPhamForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLSPMouseClicked

    private void lbQLTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLTKMouseClicked
        // TODO add your handling code here:
        QuanLyTaiKhoan sp = new QuanLyTaiKhoan();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLTKMouseClicked

    private void lbQLDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLDonHangMouseClicked
        // TODO add your handling code here:
        QLDonHangForm sp = new QLDonHangForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLDonHangMouseClicked

    private void lbThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThongKeMouseClicked
        // TODO add your handling code here:
        ThongKe sp = new ThongKe();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbThongKeMouseClicked

    private void lbQLPHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbQLPHMouseClicked
        // TODO add your handling code here:
        QuanLyPhanHoi sp = new QuanLyPhanHoi();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbQLPHMouseClicked

    private void lbTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTrangChuMouseClicked
        // TODO add your handling code here:
        Dashboard menuform = new Dashboard();
        menuform.setVisible(true);
        menuform.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_lbTrangChuMouseClicked

    private void lbOpenMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOpenMenuMouseClicked
        // TODO add your handling code here:
        openMenu();
    }//GEN-LAST:event_lbOpenMenuMouseClicked

    private void lbDangxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangxuatMouseClicked
        // TODO add your handling code here:
        DangNhapForm sp = new DangNhapForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDangxuatMouseClicked

    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelActionPerformed
        ExportExcel.xuatFile(tblSanPham, "Sản phẩm");
    }//GEN-LAST:event_btnExportExcelActionPerformed

    private File selectedFile;
    private void btnHinhAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhAnhActionPerformed
        JFileChooser fileChooser = new JFileChooser();

        // Thiết lập chỉ cho phép chọn file hình ảnh
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Người dùng đã chọn một file
            selectedFile = fileChooser.getSelectedFile();

            // Hiển thị tên file trên button
            btnHinhAnh.setText(selectedFile.getName());

            // Hiển thị hình ảnh trong lbAnh và đặt kích thước là 180x180
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(170, 138, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lbAnh.setIcon(scaledIcon);
        }
    }//GEN-LAST:event_btnHinhAnhActionPerformed

    private void txtSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSizeActionPerformed

    private void txtMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMauActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamForm.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnMenuBar;
    private javax.swing.JPanel PnSlideMenu;
    private javax.swing.JPanel PnTitile;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnHinhAnh;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnThemMoi;
    private javax.swing.JPanel cardTrangChu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboTinhTrang;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JLabel lbClose;
    private javax.swing.JLabel lbDangxuat;
    private javax.swing.JLabel lbOpenMenu;
    private javax.swing.JLabel lbQLDonHang;
    private javax.swing.JLabel lbQLPH;
    private javax.swing.JLabel lbQLSP;
    private javax.swing.JLabel lbQLTK;
    private javax.swing.JLabel lbThongKe;
    private javax.swing.JLabel lbTrangChu;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMau;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtSoLuongCon;
    private javax.swing.JTextField txtTenSp;
    // End of variables declaration//GEN-END:variables
}
