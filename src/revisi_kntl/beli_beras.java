/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package revisi_kntl;

/**
 *
 * @author Faujixx
 */

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import koneksi.koneksi;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import koneksi.*;
import java.util.Date;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public class beli_beras extends javax.swing.JFrame {
    private static String username;
    private static Map<String, String> jenisBerasMap = new HashMap<>();
    private double totalPembelian = 0.0;
    String idberas;
    String sekarang;
    
    /**
     * Creates new form beli_beras
     */
     
    public beli_beras(String username) {
        this.username = username;
        initComponents();
        jTable1.setModel(loadTotalSetorByJenisBerasToTable());
        initComboBoxValues();
        jTable1.setModel(loadTotalSetorByJenisBerasToTable());
       //TotalPembelian();
    }
public DefaultTableModel loadTotalSetorByJenisBerasToTable() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Jenis Beras");
    model.addColumn("Stok");

    try (Connection connection = koneksi.GetConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT jenis_beras, stok FROM beras")) {

        while (resultSet.next()) {
            String jenisBeras = resultSet.getString("jenis_beras");
            String stok = resultSet.getString("stok");

            model.addRow(new Object[]{jenisBeras, stok+"kg"});
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error loading data from database: " + e.getMessage());
    }

    return model;
}

 private void initComboBoxValues() {
   
    String[] jenisBerasLabels = {"Rojolele", "Pera", "Beras merah", "pandan wangi", "Beras ketan"};
    String[] jenisBerasIDs = {"J0001", "J0002", "J0003", "J0004", "J0005"};

    for (int i = 0; i < jenisBerasLabels.length; i++) {
        jenis.addItem(jenisBerasLabels[i]);
        jenisBerasMap.put(jenisBerasLabels[i], jenisBerasIDs[i]);
    }

   jenis.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
     
        String jenisBerasLabel = jenis.getSelectedItem().toString();
        String idBeras = jenisBerasMap.get(jenisBerasLabel); 
        String berat1 = Berat.getText();
        if (berat1.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Isi berat terlebih dahulu!");
            
            return;
        }

        try {
            
            Connection connection = koneksi.GetConnection();
            String sql = "SELECT harga FROM beras WHERE id_beras =?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, idBeras);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                         if (resultSet.next()) {
                                double harga = resultSet.getDouble("harga");
                                double berat = Double.parseDouble(berat1);
                                double total = harga*berat;
                                String total1 =Double.toString(total);
                                jLabel4.setText( "Rp"+total1);
                    } else {
            
                        jLabel4.setText("");
                    }
                }
            } finally {
                
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
        }

                jTable1.setModel(loadTotalSetorByJenisBerasToTable());
    }
});}


    
       public static String generateNewID() {
      String newID = "BE0000";

        try (Connection connection = koneksi.GetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_nota) FROM membeli");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_nota)");
                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newID = String.format("BE%04d", sequence);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error parsing ID: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error generating ID: " + e.getMessage());
        }

        return newID;
    }
    
     
    
    public static String generateDetailNewID() {
      String newID = "DBE0000";

        try (Connection connection = koneksi.GetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_detailnota) FROM detail_pembelian");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_detailnota)");
            
                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newID = String.format("DBE%04d", sequence);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error parsing ID: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error generating ID: " + e.getMessage());
        }

        return newID;
    }
    
   public void generateTanggalTransaksi() {
    // Mendapatkan tanggal saat ini
    LocalDate currentDate = LocalDate.now();
    
    // Mengatur format tanggal
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // Menghasilkan tanggal dalam format yang diinginkan
    sekarang = formatter.format(currentDate);
}
    

private void simpanDataPembelian(Connection connection, String idNota, String tanggalTransaksi, double totalPembelian, String idDetailNota) throws SQLException {
    String sql = "INSERT INTO membeli (id_nota, tgl_transaksi, total_pembelian, username, id_detailnota) VALUES (?, ?, ?, ?, ?)";
    String harga1 = jLabel4.getText();
generateTanggalTransaksi();
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, idNota);  // Use the provided idNota
        preparedStatement.setString(2, sekarang);
        preparedStatement.setString(3, harga1);
        preparedStatement.setString(4, username);
        preparedStatement.setString(5, idDetailNota);  // Use the provided idDetailNota

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows > 0) {
            // Call the method to insert into the 'detail_pembelian' table
            simpanDetailPembelian(connection);
        } else {
            throw new SQLException("Failed to insert data into membeli");
        }
    }
}


    private void simpanDetailPembelian(Connection connection) {
    String sql = "INSERT INTO detail_pembelian (id_detailnota, tgl_transaksi, total_pembelian, id_beras) VALUES (?, ?, ?, ?)";
    String berat = Berat.getText();

    String jenisBerasLabel = jenis.getSelectedItem().toString();
    String idberas;
generateTanggalTransaksi();
    if (jenisBerasLabel.equals("Rojolele")) {
        idberas = "J0001";
    } else if (jenisBerasLabel.equals("Pera")) {
        idberas = "J0002";
    } else if (jenisBerasLabel.equals("Beras merah")) {
        idberas = "J0003";
    } else if (jenisBerasLabel.equals("pandan wangi")) {
        idberas = "J0004";
    } else if (jenisBerasLabel.equals("Beras ketan")) {
        idberas = "J0005";
    } else {
        // Handle the case when jenisBerasLabel doesn't match any expected value
        return;
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    preparedStatement.setString(1, generateDetailNewID());  // Use the generated ID for id_detailnota
    preparedStatement.setString(2, sekarang);
    preparedStatement.setString(3, berat);
    preparedStatement.setString(4, idberas);

    preparedStatement.executeUpdate();
} catch (Exception e) {
    // Handle the exception
    e.printStackTrace();
}

}

    private void kurangiStokBeras(String idBeras, double berat) {
    try (Connection connection = koneksi.GetConnection()) {
        // Query SQL untuk mengurangi stok berdasarkan id_beras
        String updateStokQuery = "UPDATE beras SET stok = stok - ? WHERE id_beras = ?";

        // Persiapkan dan eksekusi query
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStokQuery)) {
            preparedStatement.setDouble(1, berat);
            preparedStatement.setString(2, idBeras);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Jika stok berhasil dikurangi, tampilkan pesan sukses
                System.out.println("Stok berhasil dikurangi.");
            } else {
                // Jika tidak ada baris yang terpengaruh, tampilkan pesan kesalahan
                System.out.println("Gagal mengurangi stok.");
            }
        }
    } catch (SQLException ex) {
        // Tangani pengecualian SQL jika terjadi kesalahan
        ex.printStackTrace();
        JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
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

        logout = new javax.swing.JLabel();
        home = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        beli_btn = new rojerusan.RSMaterialButtonRectangle();
        jTextField1 = new javax.swing.JTextField();
        Berat = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jenis = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Vector (1).png"))); // NOI18N
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });
        getContentPane().add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(467, 28, -1, -1));

        home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Vector.png"))); // NOI18N
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
        });
        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 27, -1, -1));

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/mingcute_back-2-fill.png"))); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        getContentPane().add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 27, -1, -1));

        beli_btn.setBackground(new java.awt.Color(34, 87, 122));
        beli_btn.setText("beli");
        beli_btn.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        beli_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beli_btnActionPerformed(evt);
            }
        });
        getContentPane().add(beli_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 505, 210, 40));

        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, 280, 30));

        Berat.setBorder(null);
        Berat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeratActionPerformed(evt);
            }
        });
        getContentPane().add(Berat, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 253, 180, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Group 17.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(726, 190, -1, -1));

        jTable1.setFont(new java.awt.Font("Montserrat SemiBold", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 330, 300));

        jenis.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rojolele", "Pera", "Beras merah", "pandan wangi", "Beras ketan", " " }));
        jenis.setBorder(null);
        getContentPane().add(jenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 180, 30));

        jLabel4.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, -1, -1));

        jLabel3.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Frame 17.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void beli_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beli_btnActionPerformed
 // Mendapatkan nilai-nilai yang diperlukan dari antarmuka pengguna
    String jenisBerasLabel = jenis.getSelectedItem().toString();
String idBeras = jenisBerasMap.get(jenisBerasLabel);
String berat1 = Berat.getText();

// Validasi berat
if (berat1.isEmpty()) {
    JOptionPane.showMessageDialog(rootPane, "Isi berat terlebih dahulu!");
    return;
}

// Mendapatkan koneksi ke database
try (Connection connection = koneksi.GetConnection()) {
    // Query SQL untuk mengambil harga beras
    String sql = "SELECT harga FROM beras WHERE id_beras =?";

    // Persiapkan dan eksekusi query
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, idBeras);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            // Tampilkan harga beras di JTextField2 jika ada hasil
            if (resultSet.next()) {
                double harga = resultSet.getDouble("harga");
                double berat = Double.parseDouble(berat1);
                double totalPembelian = harga * berat;
                String total1 = Double.toString(totalPembelian);
                jLabel4.setText(total1);
                generateTanggalTransaksi();
                // Simpan detail pembelian ke tabel 'detail_pembelian'
                String idDetailNota = generateDetailNewID();
                simpanDetailPembelian(connection);

                // Simpan data pembelian ke tabel 'membeli
                String idNota = generateNewID();
                simpanDataPembelian(connection, idNota, sekarang, totalPembelian,idDetailNota);
                kurangiStokBeras(idBeras, berat);
                JOptionPane.showMessageDialog(rootPane, "Pembelian berhasil disimpan!");
            } else {
                jLabel4.setText("");
                JOptionPane.showMessageDialog(rootPane, "Data beras tidak ditemukan!");
            }
        }
    }
} catch (SQLException ex) {
    Logger.getLogger(beli_beras.class.getName()).log(Level.SEVERE, null, ex);
    JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
}

// Refresh the table after the operations
jTable1.setModel(loadTotalSetorByJenisBerasToTable());


    }//GEN-LAST:event_beli_btnActionPerformed

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
main_home main= new main_home(username);
main.setVisible(true);
dispose();
    }//GEN-LAST:event_backMouseClicked

    private void homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseClicked
main_home main= new main_home(username);
main.setVisible(true);
dispose();
    }//GEN-LAST:event_homeMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
pertanian_main main =new pertanian_main(username);       
main.setVisible(true);
dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void BeratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BeratActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
// Get the search input from jTextField1
    String searchInput = jTextField1.getText().trim();

    // Check if the search input is not empty
    if (!searchInput.isEmpty()) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
        jTable1.setRowSorter(rowSorter);

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Tambahkan filter untuk setiap kolom (mencakup seluruh kolom)
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            filters.add(RowFilter.regexFilter("(?i)" + searchInput, i));
        }

        // Gabungkan semua filter menggunakan OR (||)
        RowFilter<Object, Object> combinedFilter = RowFilter.orFilter(filters);

        // Terapkan filter ke model tabel
        rowSorter.setRowFilter(combinedFilter);
    } else {
        // If search input is empty, reset the row sorter to show all rows
        jTable1.setRowSorter(null);
    }
    }//GEN-LAST:event_jLabel2MouseClicked

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
            java.util.logging.Logger.getLogger(beli_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(beli_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(beli_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(beli_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new beli_beras(username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Berat;
    private javax.swing.JLabel back;
    private rojerusan.RSMaterialButtonRectangle beli_btn;
    private javax.swing.JLabel home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> jenis;
    private javax.swing.JLabel logout;
    // End of variables declaration//GEN-END:variables
}
