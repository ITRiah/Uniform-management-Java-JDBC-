/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConnect;

import java.sql.*;

/**
 *
 * @author Admin
 */
public class DBConnect {

    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/quanlydongphuc";
        String user = "hai";
        String password = "123";
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công");
        } catch (Exception e) {
            System.err.println("Lỗi kết nối đến cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

}
