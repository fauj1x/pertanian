/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Faujixx
 */
public class koneksi {
public static Connection koneksi;
 public static Connection GetConnection(){
     try {
         String url="jdbc:mysql://localhost:3306/pertanian_db";
         String user="root";
         String pass="";
         koneksi = DriverManager.getConnection(url, user, pass);
         
     } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "koneksi gagal "+e);
     }
        return koneksi; 
     }

 }

 
    

