/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


package revisi_kntl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class TestKoneksi {
 private static Connection koneksi;
 public static Connection GetConnection(){
     try {
         String url="jdbc:mysql://localhost:3306/pertanian";
         String user="root";
         String pass="";
         koneksi = DriverManager.getConnection(url, user, pass);
         
     } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "koneksi gagal "+e);
     }
        return koneksi; 
     }

 }
 
    

