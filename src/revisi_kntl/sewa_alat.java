/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package revisi_kntl;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import koneksi.koneksi;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.*;
import java.time.temporal.ChronoUnit;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Faujixx
 */
public class sewa_alat extends javax.swing.JFrame {
private static String username;
    /**
     * Creates new form sewa_alat
     */
    public sewa_alat(String username) {
        initComponents();
        this.username = username;
        tabel();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> checkTenggatWaktu(), 0, 24, TimeUnit.HOURS);
    }
     private void tabel() {
        // ... Kode inisialisasi komponen yang lain ...

        jScrollPane1.setViewportView(jTable1);

        // ... Bagian lain dari metode initComponents ...

        // Panggil method showDataOnTable() pada saat inisialisasi komponen
        showDataOnTable();
        showDataOnTable2();
    }

    // ...

    // Method untuk memuat data alat ke dalam model tabel
    public static DefaultTableModel loadDataToTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Alat");
        model.addColumn("Nama Alat");
        model.addColumn("Harga / hari");

        try (Connection connection = koneksi.GetConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_Alat, Nama_alat, Harga_sewa FROM alat")) {

            while (resultSet.next()) {
                String idAlat = resultSet.getString("id_Alat");
                String namaAlat = resultSet.getString("Nama_alat");
                String hargaSewa = resultSet.getString("Harga_sewa");

                model.addRow(new Object[]{idAlat, namaAlat, hargaSewa});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data from database: " + e.getMessage());
        }

        return model;
    }
     public static DefaultTableModel loadDataToTable2() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Alat");
        model.addColumn("Tanggal Pinjam");
         model.addColumn("Status");

        try (Connection connection = koneksi.GetConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT  Nama_alat, Tgl_pinjam,Status_peminjaman  FROM meminjam join alat where status_peminjaman = 'aktif' and username = '"+username+"'")) {

            while (resultSet.next()) {
                String namaAlat = resultSet.getString("Nama_alat");
                String tglpinjam = resultSet.getString("Tgl_pinjam");
                String status = resultSet.getString("Status_peminjaman");
                
                model.addRow(new Object[]{namaAlat, tglpinjam,status});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data from database: " + e.getMessage());
        }

        return model;
    }
     private void showDataOnTable() {
        jTable1.setModel(loadDataToTable());
    }
         private void showDataOnTable2() {
        paneluse.setModel(loadDataToTable2());
    }
     
    public static String generateNewID() {
      String newID = "SE0000";

        try (Connection connection = koneksi.GetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_Hpinjam) FROM meminjam");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_Hpinjam)");

                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newID = String.format("SE%04d", sequence);
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
      String newID = "DSE0000";

        try (Connection connection = koneksi.GetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id_Detpinjam) FROM detail_peminjaman");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("MAX(id_Detpinjam)");

                if (lastID != null) {
                    try {
                        int sequence = Integer.parseInt(lastID.replaceAll("\\D", "")) + 1;
                        newID = String.format("DSE%04d", sequence);
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
     
 private static void checkTenggatWaktu() {
    try (Connection connection = koneksi.GetConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM meminjam WHERE Tenggat_peminjaman < NOW() AND status_peminjaman = 'aktif'");
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            // Mendapatkan data transaksi yang tenggat waktunya telah berakhir
            String id_Hpinjam = resultSet.getString("id_Hpinjam");

            // Mengembalikan jumlah alat yang telah disewa ke tabel alat
            returnJumlahAlat(id_Hpinjam);

            // Mengupdate status transaksi yang tenggat waktunya telah berakhir
            updateStatusTransaksi(id_Hpinjam);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


  
 private static void returnJumlahAlat(String id_Hpinjam) {
        try (Connection connection = koneksi.GetConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT dp.jumlah_Alatpinjam, dp.id_alat FROM detail_peminjaman dp " +
                             "JOIN meminjam m ON dp.id_Detpinjam = m.id_Detpinjam " +
                             "WHERE m.id_Hpinjam = ?")) {

            preparedStatement.setString(1, id_Hpinjam);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int jumlahAlatPinjam = resultSet.getInt("jumlah_Alatpinjam");
                    String idAlat = resultSet.getString("id_alat");

                    // Mengembalikan jumlah alat ke tabel alat
                    PreparedStatement updateStatement = connection.prepareStatement("UPDATE alat SET jumlah_alat = jumlah_alat + ? WHERE id_Alat = ?");
                    updateStatement.setInt(1, jumlahAlatPinjam);
                    updateStatement.setString(2, idAlat);
                    updateStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
   private static void updateStatusTransaksi(String id_Hpinjam) {
    String updateQuery = "UPDATE meminjam SET status_peminjaman = 'selesai' WHERE id_Hpinjam = ?";
    try (Connection connection = koneksi.GetConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

        preparedStatement.setString(1, id_Hpinjam);
        preparedStatement.executeUpdate();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error updating status: " + e.getMessage());
    }
}
   
   
   
   
   private double hitungHargaSewa() {
    int selectedRow = jTable1.getSelectedRow();

    // Memeriksa apakah ada baris yang dipilih
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(rootPane, "Pilih alat terlebih dahulu");
        return 0;
    }

    // Mengambil nilai dari kolom 'Harga / hari' pada baris yang dipilih
    double hargaSewaPerHari = Double.parseDouble(jTable1.getValueAt(selectedRow, 2).toString());

    // Mengambil nilai jumlah hari sewa dari komponen DatePicker
    String tanggal1 = tanggal.getDateStringOrEmptyString();
    String tenggat1 = tenggat.getDateStringOrEmptyString();

    // Menghitung selisih hari antara tanggal peminjaman dan tenggat waktu
    long selisihHari = ChronoUnit.DAYS.between(LocalDate.parse(tanggal1), LocalDate.parse(tenggat1));

    // Menghitung total harga sewa
    double totalHargaSewa = hargaSewaPerHari * selisihHari;

    return totalHargaSewa;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        paneluse = new javax.swing.JTable();
        sewa_btn = new rojerusan.RSMaterialButtonRectangle();
        ketersediaan_btn = new rojerusan.RSMaterialButtonRectangle();
        logout = new javax.swing.JLabel();
        home = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        tanggal = new com.github.lgooddatepicker.components.DatePicker();
        tenggat = new com.github.lgooddatepicker.components.DatePicker();
        banyak_alat = new javax.swing.JTextField();
        cari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        jLabel3.setText("Alat yang sedang disewa");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, -1, -1));

        paneluse.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(paneluse);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 310, 160));

        sewa_btn.setText("Sewa");
        sewa_btn.setBackground(new java.awt.Color(34, 87, 122));
        sewa_btn.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        sewa_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sewa_btnActionPerformed(evt);
            }
        });
        getContentPane().add(sewa_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, 140, 40));

        ketersediaan_btn.setText("Cek ketersediaan");
        ketersediaan_btn.setBackground(new java.awt.Color(34, 87, 122));
        ketersediaan_btn.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        ketersediaan_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ketersediaan_btnActionPerformed(evt);
            }
        });
        getContentPane().add(ketersediaan_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 550, 140, 40));

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
        getContentPane().add(tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 253, 190, 30));
        getContentPane().add(tenggat, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 190, 30));

        banyak_alat.setBorder(null);
        banyak_alat.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        getContentPane().add(banyak_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 180, 30));

        cari.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        cari.setBorder(null);
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        getContentPane().add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 210, 30));

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
        jTable1.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, 280, 290));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Group 17.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 190, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Frame 4.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void ketersediaan_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ketersediaan_btnActionPerformed
 int selectedRow = jTable1.getSelectedRow();

    // Memeriksa apakah ada baris yang dipilih
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(rootPane, "Pilih alat terlebih dahulu");
        return;
    }

    // Mengambil nilai dari kolom 'jumlah_Alat' pada baris yang dipilih
    int jumlahAlat = Integer.parseInt(jTable1.getValueAt(selectedRow, 2).toString());

    // Memeriksa ketersediaan alat
    if (jumlahAlat == 0) {
        JOptionPane.showMessageDialog(rootPane, "Alat tidak tersedia.");
    } else {
        JOptionPane.showMessageDialog(rootPane, "Alat tersedia.");
    }

    }//GEN-LAST:event_ketersediaan_btnActionPerformed

    private void sewa_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sewa_btnActionPerformed
        String id = generateNewID();
    String id_det = generateDetailNewID();
    String tanggal1 = tanggal.getDateStringOrEmptyString();
    String tenggat1 = tenggat.getDateStringOrEmptyString();
    String jumlah = banyak_alat.getText();
    String cari1 = cari.getText();
     int selectedRow = jTable1.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(rootPane, "Pilih alat terlebih dahulu");
        return;
        
    }


    // Mengambil nilai dari kolom 'id_alat' pada baris yang dipilih
    String id_alat = jTable1.getValueAt(selectedRow, 0).toString();
     
    Connection connection = null;
    try {
        connection = koneksi.GetConnection();
        connection.setAutoCommit(false);  // Mulai transaksi

        // Query untuk tabel detail_peminjaman
        PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO detail_peminjaman (id_Detpinjam, Total_alat, jumlah_Alatpinjam, id_alat) VALUES (?, ?, ?, ?)");
        preparedStatement2.setString(1, id_det);
        preparedStatement2.setInt(2, Integer.parseInt(jumlah));
        preparedStatement2.setInt(3, Integer.parseInt(jumlah));
        preparedStatement2.setString(4, id_alat);  // Gunakan nilai id_alat yang diambil dari jTable1
        preparedStatement2.executeUpdate();

        // Query untuk tabel meminjam
        PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO meminjam (id_Hpinjam, Tgl_pinjam, Tenggat_peminjaman, Username, id_Detpinjam) VALUES (?, ?, ?, ?, ?)");
        preparedStatement1.setString(1, id);
        preparedStatement1.setString(2, tanggal1);
        preparedStatement1.setString(3, tenggat1);
        preparedStatement1.setString(4, username);
        preparedStatement1.setString(5, id_det);
        preparedStatement1.executeUpdate();

        // Commit transaksi jika kedua query berhasil
        connection.commit();
        
        
        double totalHargaSewa = hitungHargaSewa();

        if (totalHargaSewa > 0) {
        JOptionPane.showMessageDialog(rootPane, "Total Harga Sewa:  Rp" + totalHargaSewa);
        
        JOptionPane.showMessageDialog(rootPane, "Anda berhasil menyewa");
    }
   
    } catch (SQLException e) {
        try {
            if (connection != null) {
                connection.rollback();  // Rollback transaksi jika terjadi kesalahan
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error rolling back transaction: " + ex.getMessage());
        }
        JOptionPane.showMessageDialog(rootPane, "Error: " + e.getMessage());
    } finally {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);  // Set kembali ke otomatis commit setelah transaksi selesai
                connection.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error closing connection: " + ex.getMessage());
        }
    }
             tabel();
        
        
    }//GEN-LAST:event_sewa_btnActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
  // Get the search input from jTextField1
    String searchInput = cari.getText().trim();

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
            java.util.logging.Logger.getLogger(sewa_alat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sewa_alat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sewa_alat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sewa_alat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sewa_alat(username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel back;
    private javax.swing.JTextField banyak_alat;
    private javax.swing.JTextField cari;
    private javax.swing.JLabel home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private rojerusan.RSMaterialButtonRectangle ketersediaan_btn;
    private javax.swing.JLabel logout;
    private javax.swing.JTable paneluse;
    private rojerusan.RSMaterialButtonRectangle sewa_btn;
    private com.github.lgooddatepicker.components.DatePicker tanggal;
    private com.github.lgooddatepicker.components.DatePicker tenggat;
    // End of variables declaration//GEN-END:variables
}
