/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package revisi_kntl;

/**
 *
 * @author Faujixx
 */
public class admin_main extends javax.swing.JFrame {
 public static String username;
    /**
     * Creates new form admin_main
     */
    public admin_main(String username) {
        initComponents();
        this .username = username;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jenis = new rojerusan.RSMaterialButtonRectangle();
        transaksi = new rojerusan.RSMaterialButtonRectangle();
        status = new rojerusan.RSMaterialButtonRectangle();
        logout = new rojerusan.RSMaterialButtonRectangle();
        setoran = new rojerusan.RSMaterialButtonRectangle();
        data1 = new rojerusan.RSMaterialButtonRectangle();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jenis.setBackground(new java.awt.Color(34, 87, 122));
        jenis.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jenis.setLabel("Tentukan jenis dan harga beras");
        jenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jenisActionPerformed(evt);
            }
        });
        getContentPane().add(jenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 557, 290, 40));
        jenis.getAccessibleContext().setAccessibleName("Tentukan jenis dan berat beras");

        transaksi.setBackground(new java.awt.Color(34, 87, 122));
        transaksi.setText("lihat seluruh transaksi beras");
        transaksi.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 452, 290, 40));

        status.setBackground(new java.awt.Color(34, 87, 122));
        status.setText("lihat seluruh transaksi sewa alat");
        status.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        getContentPane().add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 290, 40));

        logout.setBackground(new java.awt.Color(87, 204, 153));
        logout.setForeground(new java.awt.Color(255, 0, 0));
        logout.setText("log out");
        logout.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        getContentPane().add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 610, 280, 40));

        setoran.setBackground(new java.awt.Color(34, 87, 122));
        setoran.setText("lihat seluruh setoran beras");
        setoran.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        setoran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setoranActionPerformed(evt);
            }
        });
        getContentPane().add(setoran, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 290, 40));

        data1.setBackground(new java.awt.Color(34, 87, 122));
        data1.setText("lihat seluruh data user");
        data1.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        data1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                data1ActionPerformed(evt);
            }
        });
        getContentPane().add(data1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 505, 290, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Frame 8.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisActionPerformed
        jenis_beras jenis = new jenis_beras(username);
        jenis.setVisible(true);
        dispose();
    }//GEN-LAST:event_jenisActionPerformed

    private void transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiActionPerformed
        data_transaksi transaksi = new data_transaksi(username);
        transaksi.setVisible(true);
        dispose();
    }//GEN-LAST:event_transaksiActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        status_alat status = new status_alat(username);
        status.setVisible(true);
       dispose();
    }//GEN-LAST:event_statusActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        pertanian_main home = new pertanian_main(username);
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_logoutActionPerformed


    private void setoranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setoranActionPerformed
        data_setoran setoran = new data_setoran(username);
        setoran.setVisible(true);
       dispose();
    }//GEN-LAST:event_setoranActionPerformed

    private void data1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_data1ActionPerformed
data_user user = new data_user(username);
user.setVisible(true);
dispose();
    }//GEN-LAST:event_data1ActionPerformed

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
            java.util.logging.Logger.getLogger(admin_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin_main(username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle data1;
    private javax.swing.JLabel jLabel1;
    private rojerusan.RSMaterialButtonRectangle jenis;
    private rojerusan.RSMaterialButtonRectangle logout;
    private rojerusan.RSMaterialButtonRectangle setoran;
    private rojerusan.RSMaterialButtonRectangle status;
    private rojerusan.RSMaterialButtonRectangle transaksi;
    // End of variables declaration//GEN-END:variables
}
