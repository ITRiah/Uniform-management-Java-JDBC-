/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controller.ChiTietDonHangController;
import Controller.DonHangController;
import DangNhap.DangNhapForm;
import Entity.ChiTietDonHang;
import Entity.DonHang;
import com.raven.card.ModelCard;
import com.raven.chart.ModelChart;
import java.awt.Color;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Dashboard() {
        initComponents();
        getContentPane().setBackground((new Color(0, 0, 0, 0)));
        initData();
    }

    private void initData() {
        List<DonHang> lst = DonHangController.getList();
        List<ChiTietDonHang> lstCTDH = ChiTietDonHangController.getList();
        double tongTien = 0;
        int tongSP = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (DonHang donHang : lst) {
            if (donHang.getTrangThai().equals("Hoàn thành")) {
                LocalDate ngayDat = LocalDate.parse(donHang.getNgayDat(), formatter);
                // Lấy ra tháng từ đối tượng LocalDate
                if (ngayDat.getYear() == LocalDate.now().getYear()) {
                    tongTien += donHang.getTongTien();
                    for (ChiTietDonHang chiTietDonHang : lstCTDH) {
                        if (chiTietDonHang.getMaDH().equals(donHang.getMaDH())) {
                            tongSP += chiTietDonHang.getSoLuongMua();
                        }
                    }
                }
            }
        }

        double thang1 = 0;
        double thang2 = 0;
        double thang3 = 0;
        double thang4 = 0;
        double thang5 = 0;
        double thang6 = 0;
        double thang7 = 0;
        double thang8 = 0;
        double thang9 = 0;
        double thang10 = 0;
        double thang11 = 0;
        double thang12 = 0;
        for (DonHang donHang : lst) {
            if (donHang.getTrangThai().equals("Hoàn thành")) {
                LocalDate ngayDat = LocalDate.parse(donHang.getNgayDat(), formatter);
                // Lấy ra tháng từ đối tượng LocalDate
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 1) {
                    thang1 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 2) {
                    thang2 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 3) {
                    thang3 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 4) {
                    thang4 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 5) {
                    thang5 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 6) {
                    thang6 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 7) {
                    thang7 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 8) {
                    thang8 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 9) {
                    thang9 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 10) {
                    thang10 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 11) {
                    thang11 += donHang.getTongTien();
                }
                if (ngayDat.getYear() == LocalDate.now().getYear() && ngayDat.getMonthValue() == 12) {
                    thang12 += donHang.getTongTien();
                }
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        String formattedTongTien = decimalFormat.format(tongTien);

        card1.setData(new ModelCard("Tổng doanh thu", formattedTongTien + " vnđ", "VNĐ"));
        card2.setData(new ModelCard("Tổng số sản phẩm bán", tongSP + "", "Sản phẩm"));
        chart.addLegend("Cost", new Color(186, 37, 37), new Color(241, 100, 120));
        chart.addData(new ModelChart("Tháng 1", new double[]{thang1}));
        chart.addData(new ModelChart("Tháng 2", new double[]{thang2}));
        chart.addData(new ModelChart("Tháng 3", new double[]{thang3}));
        chart.addData(new ModelChart("Tháng 4", new double[]{thang4}));
        chart.addData(new ModelChart("Tháng 5", new double[]{thang5}));
        chart.addData(new ModelChart("Tháng 6", new double[]{thang6}));
        chart.addData(new ModelChart("Tháng 7", new double[]{thang7}));
        chart.addData(new ModelChart("Tháng 8", new double[]{thang8}));
        chart.addData(new ModelChart("Tháng 9", new double[]{thang9}));
        chart.addData(new ModelChart("Tháng 10", new double[]{thang10}));
        chart.addData(new ModelChart("Tháng 11", new double[]{thang11}));
        chart.addData(new ModelChart("Tháng 12", new double[]{thang12}));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new view.Panel();
        panel2 = new view.Panel();
        jLabel2 = new javax.swing.JLabel();
        chart = new com.raven.chart.Chart();
        card1 = new com.raven.card.Card();
        jLabel1 = new javax.swing.JLabel();
        card2 = new com.raven.card.Card();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(0, 0, 0));
        panel1.setForeground(new java.awt.Color(255, 255, 255));
        panel1.setAutoscrolls(true);

        panel2.setBackground(new java.awt.Color(255, 255, 255));
        panel2.setForeground(new java.awt.Color(102, 153, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TRANG CHỦ");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Biểu đồ:");

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

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(card2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel2Layout.createSequentialGroup()
                    .addComponent(PnSlideMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 710, Short.MAX_VALUE)))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PnSlideMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        chart.start();
    }//GEN-LAST:event_formWindowOpened

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        // TODO add your handling code here:
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

    private void lbDangxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDangxuatMouseClicked
        // TODO add your handling code here:
        DangNhapForm sp = new DangNhapForm();
        sp.setVisible(true);
        sp.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        this.dispose();
    }//GEN-LAST:event_lbDangxuatMouseClicked

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnSlideMenu;
    private com.raven.card.Card card1;
    private com.raven.card.Card card2;
    private com.raven.chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbClose;
    private javax.swing.JLabel lbDangxuat;
    private javax.swing.JLabel lbQLDonHang;
    private javax.swing.JLabel lbQLPH;
    private javax.swing.JLabel lbQLSP;
    private javax.swing.JLabel lbQLTK;
    private javax.swing.JLabel lbThongKe;
    private javax.swing.JLabel lbTrangChu;
    private view.Panel panel1;
    private view.Panel panel2;
    // End of variables declaration//GEN-END:variables
}
