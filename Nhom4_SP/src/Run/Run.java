/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Run;

import DangNhap.DangNhapForm;
import splashscreen.SplashScreen;


    
public class Run {
    public static void main(String[] args) {
        splashscreen.SplashScreen sp = new SplashScreen(null, true);
        sp.setVisible(true);
        DangNhapForm UI = new DangNhapForm();
        UI.setVisible(true);
    }
}
