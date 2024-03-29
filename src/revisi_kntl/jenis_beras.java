package revisi_kntl;

/**
 *
 * @author Faujixx
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import koneksi.koneksi;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public class jenis_beras extends javax.swing.JFrame {
private static String username;
private static Map<String, String> jenisBerasMap = new HashMap<>();

    /**
     * Creates new form status_alat
     */
    public jenis_beras(String username) {
        initComponents();
        this.username = username;
        DefaultTableModel tableModel = loadDataToTable();
        jTable1.setModel(tableModel);
        initComboBoxValues();
        
    }
   
    public static DefaultTableModel loadDataToTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Setor");
        model.addColumn("Berat");
        model.addColumn("Tanggal");
        model.addColumn("Username");
        model.addColumn("ID Beras");

        try (Connection connection = koneksi.GetConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM menyetorkan where id_beras is null")) {
                 
            while (resultSet.next()) {
                String idSetor = resultSet.getString("id_Setor");
                String berat = resultSet.getString("jumlah_setor");
                String tanggal = resultSet.getString("Tgl_Setor");
                String username = resultSet.getString("username");
                String idBeras = resultSet.getString("id_beras");

                model.addRow(new Object[]{idSetor, berat, tanggal, username, idBeras});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data from database: " + e.getMessage());
        }

       return model;
        
    }
   

   
  private void initComboBoxValues() {
    // Inisialisasi nilai ComboBox dan set nilai ID setor sebagai nilai item ComboBox
    String[] jenisBerasLabels = {"Rojolele", "Pera", "Beras merah", "pandan wangi", "Beras ketan"};
    String[] jenisBerasIDs = {"J0001", "J0002", "J0003", "J0004", "J0005"};

    for (int i = 0; i < jenisBerasLabels.length; i++) {
        jComboBox1.addItem(jenisBerasLabels[i]);
        jenisBerasMap.put(jenisBerasLabels[i], jenisBerasIDs[i]);
    }
 jComboBox1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Panggil metode untuk menampilkan harga berdasarkan jenis beras yang dipilih
             // Ambil jenis beras yang dipilih dari ComboBox
    String jenisBerasLabel = jComboBox1.getSelectedItem().toString();
    
    // Ambil ID beras dari Map
    String idBeras = jenisBerasMap.get(jenisBerasLabel);

    try {
        // Buat koneksi ke database
        Connection connection = koneksi.GetConnection();

        // Buat query SQL untuk mengambil harga beras
        String sql = "SELECT harga FROM beras WHERE id_beras =?";

        // Buat prepared statement
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameter pada prepared statement
            preparedStatement.setString(1, idBeras);

            // Eksekusi query untuk mengambil harga beras
            ResultSet resultSet = preparedStatement.executeQuery();

            // Tampilkan harga beras di JTextField2 jika ada hasil
            if (resultSet.next()) {
                String harga = resultSet.getString("harga");
                jTextField2.setText(harga);
            } else {
                // Jika tidak ada hasil, set JTextField2 menjadi kosong
                jTextField2.setText("");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace(); // Gantilah dengan penanganan exception yang sesuai
        JOptionPane.showMessageDialog(rootPane, "Error: " + ex.getMessage());
    }


        }
    });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        simpan_btn = new rojerusan.RSMaterialButtonRectangle();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
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

        jTable1.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 340, 290));

        simpan_btn.setBackground(new java.awt.Color(34, 87, 122));
        simpan_btn.setText("simpan");
        simpan_btn.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        simpan_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan_btnActionPerformed(evt);
            }
        });
        getContentPane().add(simpan_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 170, 40));

        jTextField1.setBorder(null);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 198, 270, 20));

        jTextField2.setBorder(null);
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 190, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Group 17.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 195, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rojolele", "Pera", "Beras merah", "pandan wangi", "Beras ketan", " " }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 180, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Frame 28.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
admin_main admin = new admin_main(username);
        admin.setVisible(true);
        dispose();
    }//GEN-LAST:event_backMouseClicked

    private void homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseClicked
       admin_main admin = new admin_main(username);
        admin.setVisible(true);
        dispose();
    }//GEN-LAST:event_homeMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
pertanian_main main =new pertanian_main(username);       
main.setVisible(true);
dispose();
    }//GEN-LAST:event_logoutMouseClicked

    private void simpan_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan_btnActionPerformed
 int selectedRowIndex = jTable1.getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Pilih baris yang ingin disimpan.");
            return;
        }

        // Mendapatkan nilai ID Setor dari kolom 'ID Setor' pada baris yang dipilih
        Object idSetorObject = jTable1.getValueAt(selectedRowIndex, 0);

        // Memastikan bahwa nilai ID Setor tidak null
        if (idSetorObject == null) {
            JOptionPane.showMessageDialog(rootPane, "ID Setor pada baris yang dipilih tidak valid.");
            return;
        }

        String idSetor = idSetorObject.toString();

        try {
            // Mengambil nilai dari komponen GUI
            String jenisBerasLabel = jComboBox1.getSelectedItem().toString();
            String idBeras = jenisBerasMap.get(jenisBerasLabel);
            String harga = jTextField2.getText();

            // Pastikan input tidak kosong
            if (idSetor.isEmpty() || harga.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Isi semua field sebelum menyimpan.");
                return;
            }

            // Buat koneksi ke database
            try (Connection connection = koneksi.GetConnection()) {
                // Buat query SQL untuk menyimpan data
                String sql = "UPDATE menyetorkan SET id_beras=? WHERE id_Setor=?";

                // Buat prepared statement
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Set parameter pada prepared statement
                    preparedStatement.setString(1, idBeras);
                    preparedStatement.setString(2, idSetor);

                    // Eksekusi query untuk menyimpan data
                    preparedStatement.executeUpdate();

                    // Tampilkan pesan berhasil
                    JOptionPane.showMessageDialog(rootPane, "Data berhasil disimpan.");

                    // Refresh tabel
                    DefaultTableModel tableModel = loadDataToTable();
                    jTable1.setModel(tableModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "Error: " + e.getMessage());
        }
   
        // Setelah menyimpan data pada Tabel Menyetorkan
    try {
        Connection connection = koneksi.GetConnection();

        // Ambil jenis beras yang dipilih dari ComboBox
        String jenisBerasLabel = jComboBox1.getSelectedItem().toString();

        // Hitung total jumlah setor untuk jenis beras yang dipilih
        String sqlSum = "SELECT SUM(jumlah_setor) as total_setor FROM menyetorkan WHERE id_beras = ?";
        try (PreparedStatement preparedStatementSum = connection.prepareStatement(sqlSum)) {
            // Ambil ID beras dari Map
            String idBeras = jenisBerasMap.get(jenisBerasLabel);
            preparedStatementSum.setString(1, idBeras);

            ResultSet resultSetSum = preparedStatementSum.executeQuery();

            // Ambil nilai hasil penjumlahan
            int totalSetor = 0;
            if (resultSetSum.next()) {
                totalSetor = resultSetSum.getInt("total_setor");
            }

            // Update stok pada Tabel Beras
            String sqlUpdateStok = "UPDATE beras SET stok = ? WHERE id_beras = ?";
            try (PreparedStatement preparedStatementUpdateStok = connection.prepareStatement(sqlUpdateStok)) {
                // Ambil stok sebelumnya
                String sqlGetStok = "SELECT stok FROM beras WHERE id_beras = ?";
                try (PreparedStatement preparedStatementGetStok = connection.prepareStatement(sqlGetStok)) {
                    preparedStatementGetStok.setString(1, idBeras);
                    ResultSet resultSetGetStok = preparedStatementGetStok.executeQuery();

                    int stokSebelumnya = 0;
                    if (resultSetGetStok.next()) {
                        stokSebelumnya = resultSetGetStok.getInt("stok");
                    }

                    // Hitung stok baru
                    int stokBaru = stokSebelumnya + totalSetor;

                    // Update stok pada Tabel Beras
                    preparedStatementUpdateStok.setInt(1, stokBaru);
                    preparedStatementUpdateStok.setString(2, idBeras);

                    // Eksekusi query untuk mengupdate stok
                    preparedStatementUpdateStok.executeUpdate();
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(rootPane, "Error: " + e.getMessage());
    }
    
    }//GEN-LAST:event_simpan_btnActionPerformed

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
            java.util.logging.Logger.getLogger(jenis_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jenis_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jenis_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jenis_beras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jenis_beras(username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel back;
    private javax.swing.JLabel home;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel logout;
    private rojerusan.RSMaterialButtonRectangle simpan_btn;
    // End of variables declaration//GEN-END:variables
}
