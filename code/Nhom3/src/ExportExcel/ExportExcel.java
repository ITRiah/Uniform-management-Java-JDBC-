/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExportExcel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ASUS
 */
public class ExportExcel {

    public static void xuatFile(JTable table, String Title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save as");

        int i = chooser.showSaveDialog(chooser);
        if (i == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();

                // Kiểm tra nếu tên file không có đuôi ".xlsx" thì thêm vào
                if (!file.getName().toLowerCase().endsWith(".xlsx")) {
                    file = new File(file.getParentFile(), file.getName() + ".xlsx");
                }

                // Tạo một Workbook mới (đối với file Excel 2007 trở lên, sử dụng XSSFWorkbook)
                Workbook wb = new XSSFWorkbook();

                // Tạo một Sheet với tên "SanPham"
                Sheet sheet = wb.createSheet(Title);
                CellStyle boldStyle = wb.createCellStyle();
                Font boldFont = wb.createFont();
                boldFont.setBold(true);
                boldStyle.setFont(boldFont);

                // Tạo hàng header
                Row headerRow = sheet.createRow(0);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Cell headerCell = headerRow.createCell(j);
                    headerCell.setCellValue(table.getColumnName(j));
                    headerCell.setCellStyle(boldStyle); // Áp dụng style in đậm cho ô header
                }

                // Đổ dữ liệu từ JTable vào Workbook
                for (int j = 0; j < table.getRowCount(); j++) {
                    Row r = sheet.createRow(j + 1);
                    for (int k = 0; k < table.getColumnCount(); k++) {
                        Cell cell = r.createCell(k);
                        Object value = table.getValueAt(j, k);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }

                // Ghi Workbook vào file
                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    wb.write(fileOut);
                    // Đóng luồng ghi file
                    wb.close();
                    JOptionPane.showMessageDialog(null, "Ghi file thành công!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi ghi file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi ghi file!", "Lỗi", JOptionPane.ERROR_MESSAGE);

            }
        }
    }
}
