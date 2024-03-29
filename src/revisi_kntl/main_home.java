/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package revisi_kntl;
import javax.swing.JOptionPane;
import koneksi.user;
import static revisi_kntl.setor_beras.user;

/**
 *
 * @author Faujixx
 */
public class main_home extends javax.swing.JFrame {
private static String username;
    /**
     * Creates new form main_home
     */
    public main_home(String username) {
    
        initComponents();
        this.username = username;
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
        setor_btn = new rojerusan.RSMaterialButtonRectangle();
        sewa_btn = new rojerusan.RSMaterialButtonRectangle();
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
        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 27, -1, -1));

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/mingcute_back-2-fill.png"))); // NOI18N
        getContentPane().add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 27, -1, -1));

        beli_btn.setBackground(new java.awt.Color(34, 87, 122));
        beli_btn.setText("beli sekarang");
        beli_btn.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        beli_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beli_btnActionPerformed(evt);
            }
        });
        getContentPane().add(beli_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 545, 180, 40));

        setor_btn.setBackground(new java.awt.Color(34, 87, 122));
        setor_btn.setText("setor sekarang");
        setor_btn.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        setor_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setor_btnActionPerformed(evt);
            }
        });
        getContentPane().add(setor_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 545, 180, 40));

        sewa_btn.setBackground(new java.awt.Color(34, 87, 122));
        sewa_btn.setText("sewa sekarang");
        sewa_btn.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        sewa_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sewa_btnActionPerformed(evt);
            }
        });
        getContentPane().add(sewa_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 545, 180, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png_ui/Frame 3.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void beli_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beli_btnActionPerformed
        beli_beras beli = new beli_beras(username);
        beli.setVisible(true);
        dispose();
    }//GEN-LAST:event_beli_btnActionPerformed

    private void setor_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setor_btnActionPerformed
        setor_beras setor = new setor_beras(username);
        setor.setVisible(true);
        setor.loadDataToTable();
        
        dispose();
    }//GEN-LAST:event_setor_btnActionPerformed

    private void sewa_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sewa_btnActionPerformed
        sewa_alat sewa =new sewa_alat(username);
        sewa.setVisible(true);
        dispose();
    }//GEN-LAST:event_sewa_btnActionPerformed

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
pertanian_main main =new pertanian_main(username);
main.setVisible(true );
dispose();
    }//GEN-LAST:event_logoutMouseClicked

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
            java.util.logging.Logger.getLogger(main_home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main_home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main_home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main_home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main_home(username).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel back;
    private rojerusan.RSMaterialButtonRectangle beli_btn;
    private javax.swing.JLabel home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel logout;
    private rojerusan.RSMaterialButtonRectangle setor_btn;
    private rojerusan.RSMaterialButtonRectangle sewa_btn;
    // End of variables declaration//GEN-END:variables
}
