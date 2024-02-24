/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DBConnect.DBConnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author admin
 */
public class GenerateID {

    static DBConnect cn = new DBConnect();

    public static int getNextID(String tableName, String PK, int stringLength) {
        Connection conn = null;
        Statement statement = null;
        int idMax = 0;
        try {
            conn = cn.getConnection();
            statement = conn.createStatement();

            String selectSql = "SELECT " + PK + " FROM " + tableName;

            ResultSet resultset = statement.executeQuery(selectSql);

            // Lấy giá trị lớn nhất từ kết quả truy vấn
            while (resultset.next()) {
                String id = resultset.getString(1);
                int idNumber = Integer.parseInt(id.substring(stringLength));
                if (idNumber > idMax) {
                    idMax = idNumber;
                }
            }

            idMax += 1;

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
        return idMax;
    }
}
