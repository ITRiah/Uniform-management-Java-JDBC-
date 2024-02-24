/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.KhachHangController;
import Controller.PhanHoiController;
import DangNhap.DangNhapForm;
import Entity.KhachHang;
import Entity.PhanHoi;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QuanLyPhanHoi extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyPhanHoi
     */
    String header[] = {"Mã PH", "Mã KH", "Hình ảnh", "Điểm DG", "Nhận xét", "Mã đơn", "Thời gian"};
    DefaultTableModel tb = new DefaultTableModel();
    private List<PhanHoi> lst;
    private int currentPage = 1;
    private int recordsPerPage = 5;
    private JButton[] pageButtons;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    int x = 160;
    int y = 560;

    public QuanLyPhanHoi() {
        initComponents();
        loadComboBox();
        ButtonGroup btn = new ButtonGroup();
        btn.add(rdGiam);
        btn.add(rdTang);
        tb = (DefaultTableModel) dgvQLPH.getModel();
        tb.setColumnCount(6);
        tb.setColumnIdentifiers(header); // Thêm dòng này để đặt tên cột
        loadTable();
        updateTable();
        updatePageButtons(); // Thêm dòng này để tạo các nút trang
        pnlButtons.setLayout(new java.awt.FlowLayout());
    }

    private void updateTable() {
        lst = PhanHoiController.getList();
        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, lst.size());
        tb.setRowCount(0);
        for (int i = start; i < end; i++) {
            PhanHoi sp = lst.get(i);
           if (sp.getNgayTao() != null) {
                String ngayTao = sp.getNgayTao().format(formatter);
                tb.addRow(new Object[]{sp.getMaPH(), sp.getMaKH(), sp.getHinhAnh(), sp.getDiemDG(), sp.getNhanXet(), sp.getMaDH(), ngayTao});
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

    public void sendEmail(String maKH, String maDH) {
        String body = JOptionPane.showInputDialog(null, "Phản hồi cho khách hàng:");
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

            String tieuDe = "Phản hồi về đơn hàng có mã: " + maDH;

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(body, "text/html; charset=utf-8");

            message.setSubject(tieuDe);
            message.setText(body);

//            //sendFile
//            MimeBodyPart bodyPart = new MimeBodyPart();
//            File file = new File("src/HoaDon/" + maDH + ".pdf");
//            FileDataSource dataSource = new FileDataSource(file);
//            bodyPart.setDataHandler(new DataHandler(dataSource));
//            bodyPart.setFileName(file.getName());
//
//            MimeMultipart mimeMultipart = new MimeMultipart();
//            mimeMultipart.addBodyPart(contentPart);
//            mimeMultipart.addBodyPart(bodyPart);
//            message.setContent(mimeMultipart);
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace(); // In thông báo lỗi cụ thể
        } catch (Exception e) {
            System.out.println("Lỗi " + e.getMessage());
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
        jLabel5 = new javax.swing.JLabel();
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
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgvQLPH = new javax.swing.JTable();
        cboDiemDG = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        lbAnh = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPhanHoi = new javax.swing.JTextArea();
        btnXuatFile = new javax.swing.JButton();
        btnXem = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        rdTang = new javax.swing.JRadioButton();
        rdGiam = new javax.swing.JRadioButton();
        pnlButtons = new javax.swing.JPanel();
        btnReply = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnTitile.setBackground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout PnTitileLayout = new javax.swing.GroupLayout(PnTitile);
        PnTitile.setLayout(PnTitileLayout);
        PnTitileLayout.setHorizontalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        PnTitileLayout.setVerticalGroup(
            PnTitileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(PnTitile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 30));

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

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/avatar.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel1.add(PnSlideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 160, 610));

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
                .addContainerGap(854, Short.MAX_VALUE))
        );
        PnMenuBarLayout.setVerticalGroup(
            PnMenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbOpenMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(PnMenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 940, 50));

        pnMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnMain.setLayout(new java.awt.CardLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("QUẢN LÝ PHẢN HỒI");

        jLabel1.setText("Hình ảnh:");

        dgvQLPH.setModel(new javax.swing.table.DefaultTableModel(
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
        dgvQLPH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgvQLPHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dgvQLPH);

        cboDiemDG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDiemDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDiemDGActionPerformed(evt);
            }
        });

        jLabel2.setText("Điểm đánh giá:");

        jLabel4.setText("Phản hồi của khách hàng:");

        txtPhanHoi.setColumns(20);
        txtPhanHoi.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtPhanHoi.setRows(5);
        jScrollPane2.setViewportView(txtPhanHoi);

        btnXuatFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnXuatFile.setText("Xuất File");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/searching.png"))); // NOI18N
        btnXem.setText("Xem");
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        jLabel3.setText("Sắp xếp theo số sao:");

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

        btnReply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/feedback (1).png"))); // NOI18N
        btnReply.setText("Reply");
        btnReply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReplyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cardTrangChuLayout = new javax.swing.GroupLayout(cardTrangChu);
        cardTrangChu.setLayout(cardTrangChuLayout);
        cardTrangChuLayout.setHorizontalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                                .addComponent(cboDiemDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXem))
                            .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(cardTrangChuLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdTang, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnReply, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnXuatFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardTrangChuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        cardTrangChuLayout.setVerticalGroup(
            cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardTrangChuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboDiemDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(12, 12, 12)
                        .addGroup(cardTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cardTrangChuLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdTang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdGiam)
                        .addGap(33, 33, 33)
                        .addComponent(btnReply, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnMain.add(cardTrangChu, "card2");

        jPanel1.add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 940, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboDiemDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDiemDGActionPerformed

    }//GEN-LAST:event_cboDiemDGActionPerformed

    private void dgvQLPHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgvQLPHMouseClicked
        int selectedRow = dgvQLPH.getSelectedRow();

        // Kiểm tra nếu có dòng được chọn
        if (selectedRow >= 0) {
            // Lấy đường dẫn hình ảnh từ cột 2 (giả sử cột 2 chứa đường dẫn hình ảnh)
            String path = dgvQLPH.getValueAt(selectedRow, 2).toString();
            // Kiểm tra nếu đường dẫn hình ảnh không rỗng
            if (!path.isEmpty()) {
                // Tạo ImageIcon từ đường dẫn
                ImageIcon imageIcon = new ImageIcon("src/image/PhanHoi/" + path);

                // Chỉnh kích thước hình ảnh
                Image scaledImage = imageIcon.getImage().getScaledInstance(170, 138, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Hiển thị hình ảnh trên lbAnh
                lbAnh.setIcon(scaledIcon);
            }
            String ph = dgvQLPH.getValueAt(selectedRow, 4).toString();
            txtPhanHoi.setText(ph);

            String diemDG = dgvQLPH.getValueAt(selectedRow, 3).toString();
            cboDiemDG.setSelectedItem(diemDG);
        }
    }//GEN-LAST:event_dgvQLPHMouseClicked

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // TODO add your handling code here:
        ExportExcel.ExportExcel.xuatFile(dgvQLPH, "Phản hồi");
    }//GEN-LAST:event_btnXuatFileActionPerformed

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
        QuanLyTaiKhoan sp = null;
        try {
            sp = new QuanLyTaiKhoan();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
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
        QuanLyPhanHoi sp = new QuanLyPhanHoi();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();        // TODO add your handling code here:
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

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        // TODO add your handling code here:
        if (cboDiemDG.getSelectedItem() != null) {
            String diemDG = cboDiemDG.getSelectedItem().toString();
            if (diemDG.equalsIgnoreCase("Tất cả")) {
                updateTable();
            } else {
                // Tạo danh sách để chứa các phản hồi có điểm đánh giá tương ứng
                lst = PhanHoiController.searchByDiem(diemDG);
                int start = (currentPage - 1) * recordsPerPage;
                int end = Math.min(start + recordsPerPage, lst.size());
                tb.setRowCount(0);
                for (int i = start; i < end; i++) {
                    PhanHoi sp = lst.get(i);
                    tb.addRow(new Object[]{sp.getMaPH(), sp.getMaKH(), sp.getHinhAnh(), sp.getDiemDG(), sp.getNhanXet(), sp.getMaDH()});
                }
                updatePageButtons();
            }
        }
    }//GEN-LAST:event_btnXemActionPerformed

    private void lbDangxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangxuatMouseClicked
        // TODO add your handling code here:
        DangNhapForm sp = new DangNhapForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDangxuatMouseClicked

    private void rdTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdTangActionPerformed
        // TODO add your handling code here:
        Comparator<PhanHoi> c = new Comparator<PhanHoi>() {
            @Override
            public int compare(PhanHoi o1, PhanHoi o2) {
                int diemDG1 = Integer.parseInt(o1.getDiemDG().split(" ")[0]);
                int diemDG2 = Integer.parseInt(o2.getDiemDG().split(" ")[0]);
                return Integer.compare(diemDG1, diemDG2);
            }
        };
        sort(lst, c);
        tb.setRowCount(0);

        for (PhanHoi tk : lst) {
            tb.addRow(new Object[]{tk.getMaPH(), tk.getMaKH(), tk.getHinhAnh(), tk.getDiemDG(), tk.getNhanXet(), tk.getMaKH()});
        }
    }//GEN-LAST:event_rdTangActionPerformed

    private void rdGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdGiamActionPerformed
        // TODO add your handling code here:
        Comparator<PhanHoi> c = new Comparator<PhanHoi>() {
            @Override
            public int compare(PhanHoi o1, PhanHoi o2) {
                int diemDG1 = Integer.parseInt(o1.getDiemDG().split(" ")[0]);
                int diemDG2 = Integer.parseInt(o2.getDiemDG().split(" ")[0]);
                return Integer.compare(diemDG1, diemDG2);
            }
        };
        sort(lst, c.reversed());
        tb.setRowCount(0);

        for (PhanHoi tk : lst) {
            tb.addRow(new Object[]{tk.getMaPH(), tk.getMaKH(), tk.getHinhAnh(), tk.getDiemDG(), tk.getNhanXet(), tk.getMaKH()});
        }
    }//GEN-LAST:event_rdGiamActionPerformed
    static String maKH = "";
    static String maDH = "";

    private void btnReplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplyActionPerformed
        int selectedRow = dgvQLPH.getSelectedRow();
        if (selectedRow >= 0) {
            selectedRow += currentPage * recordsPerPage - recordsPerPage;
            maKH = dgvQLPH.getValueAt(selectedRow, 1).toString();
            maDH = dgvQLPH.getValueAt(selectedRow, 5).toString();
        }

        PhanHoiKhachHang sp = new PhanHoiKhachHang();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_btnReplyActionPerformed

    private void loadComboBox() {
        cboDiemDG.removeAllItems();

        String[] danhGia = {"Tất cả", "1 sao", "2 sao", "3 sao", "4 sao", "5 sao"};

        for (String dg : danhGia) {
            cboDiemDG.addItem(dg);
        }
    }

    private void loadTable() {
        lst = PhanHoiController.getList();
        tb.setRowCount(0);

        for (PhanHoi sp : lst) {
            if (sp.getNgayTao() != null) {
                String ngayTao = sp.getNgayTao().format(formatter);
                tb.addRow(new Object[]{sp.getMaPH(), sp.getMaKH(), sp.getHinhAnh(), sp.getDiemDG(), sp.getNhanXet(), sp.getMaDH(), ngayTao});
            }
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
            java.util.logging.Logger.getLogger(QuanLyPhanHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyPhanHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyPhanHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyPhanHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyPhanHoi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnMenuBar;
    private javax.swing.JPanel PnSlideMenu;
    private javax.swing.JPanel PnTitile;
    private javax.swing.JButton btnReply;
    private javax.swing.JButton btnXem;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JPanel cardTrangChu;
    private javax.swing.JComboBox<String> cboDiemDG;
    private javax.swing.JTable dgvQLPH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
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
    private javax.swing.JRadioButton rdGiam;
    private javax.swing.JRadioButton rdTang;
    private javax.swing.JTextArea txtPhanHoi;
    // End of variables declaration//GEN-END:variables
}
