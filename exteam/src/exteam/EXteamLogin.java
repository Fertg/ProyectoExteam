/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exteam;

import bbdd.ConectorBBDD;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class EXteamLogin extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private int x;
    private int y;
    private Connection c;

    public EXteamLogin() {
        setConexion();
        initComponents();
        setLocationRelativeTo(null);
    }

    public void setConexion() {
        ConectorBBDD bbdd = new ConectorBBDD();
        c = bbdd.getConexion();
    }

    public boolean checkFields() {
        if (jtfUser.getText().equals("") || jtfUser.getText().equals("Introduce tu usuario...")) {
            JOptionPane.showMessageDialog(this, "Usuario vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (String.valueOf(jpfPwd.getPassword()).equals("") || String.valueOf(jpfPwd.getPassword()).equals("contrasena.......")) {
            JOptionPane.showMessageDialog(this, "Contraseña vacia", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    public boolean iniciarSesion() throws SQLException {

        if (checkFields()) {
            String pwd = "";
            String query = "SELECT user_password FROM player WHERE username = \"" + jtfUser.getText() + "\";";
            Statement stmnt = c.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    pwd = rs.getString(1);
                }
                if (String.valueOf(jpfPwd.getPassword()).equals(pwd)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return false;
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

        jPanel1 = new javax.swing.JPanel();
        jtfUser = new java.awt.TextField();
        btReg = new javax.swing.JPanel();
        jlReg = new javax.swing.JLabel();
        btEntrar = new javax.swing.JPanel();
        jlEntrar = new javax.swing.JLabel();
        jlAbout = new javax.swing.JLabel();
        jlTitle = new javax.swing.JLabel();
        jpfPwd = new javax.swing.JPasswordField();
        jlDrag = new javax.swing.JPanel();
        jpSalir = new javax.swing.JPanel();
        lbsalir = new javax.swing.JLabel();
        jlBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtfUser.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtfUser.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jtfUser.setName(""); // NOI18N
        jtfUser.setText("Introduce tu usuario...");
        jtfUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfUserFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfUserFocusLost(evt);
            }
        });
        jtfUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfUserActionPerformed(evt);
            }
        });
        jPanel1.add(jtfUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 230, 50));

        btReg.setBackground(new java.awt.Color(32, 120, 196));
        btReg.setToolTipText("");
        btReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btRegMouseClicked(evt);
            }
        });
        btReg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlReg.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jlReg.setForeground(new java.awt.Color(255, 255, 255));
        jlReg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlReg.setText("SING UP");
        jlReg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlRegMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlRegMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlRegMouseExited(evt);
            }
        });
        btReg.add(jlReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 60));

        jPanel1.add(btReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 230, 60));

        btEntrar.setBackground(new java.awt.Color(32, 120, 196));
        btEntrar.setToolTipText("");
        btEntrar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlEntrar.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jlEntrar.setForeground(new java.awt.Color(255, 255, 255));
        jlEntrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlEntrar.setText("ENTER");
        jlEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlEntrarMouseExited(evt);
            }
        });
        jlEntrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jlEntrarKeyPressed(evt);
            }
        });
        btEntrar.add(jlEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 60));

        jPanel1.add(btEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 360, 230, 60));

        jlAbout.setFont(new java.awt.Font("Berlin Sans FB", 0, 13)); // NOI18N
        jlAbout.setForeground(new java.awt.Color(255, 255, 255));
        jlAbout.setText("Sobre Nosotros ...?");
        jlAbout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlAboutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlAboutMouseEntered(evt);
            }
        });
        jPanel1.add(jlAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 500, 100, -1));

        jlTitle.setFont(new java.awt.Font("Berlin Sans FB", 0, 48)); // NOI18N
        jlTitle.setForeground(new java.awt.Color(255, 255, 255));
        jlTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitle.setText("Iniciar Sesión");
        jPanel1.add(jlTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 330, 50));

        jpfPwd.setBackground(new java.awt.Color(255, 255, 255));
        jpfPwd.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jpfPwd.setForeground(new java.awt.Color(0, 0, 0));
        jpfPwd.setText("contrasena.......");
        jpfPwd.setToolTipText("");
        jpfPwd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jpfPwdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jpfPwdFocusLost(evt);
            }
        });
        jpfPwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpfPwdActionPerformed(evt);
            }
        });
        jpfPwd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpfPwdKeyPressed(evt);
            }
        });
        jPanel1.add(jpfPwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 230, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 890, 530));

        jlDrag.setBackground(new java.awt.Color(32, 120, 196));
        jlDrag.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jlDragMouseDragged(evt);
            }
        });
        jlDrag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jlDragMousePressed(evt);
            }
        });
        jlDrag.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpSalir.setBackground(new java.awt.Color(32, 120, 196));
        jpSalir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbsalir.setBackground(new java.awt.Color(248, 118, 118));
        lbsalir.setFont(new java.awt.Font("Berlin Sans FB", 0, 30)); // NOI18N
        lbsalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbsalir.setText("×");
        lbsalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbsalir.setPreferredSize(new java.awt.Dimension(50, 30));
        lbsalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbsalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbsalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbsalirMouseExited(evt);
            }
        });
        jpSalir.add(lbsalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, -1));

        jlDrag.add(jpSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 0, 50, 30));

        getContentPane().add(jlDrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 30));

        jlBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/exteam/backgroung.jpg"))); // NOI18N
        jlBackground.setText("jLabel7");
        getContentPane().add(jlBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlRegMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlRegMouseEntered
        // TODO add your handling code here:
        btReg.setBackground(new Color(53, 184, 230));
    }//GEN-LAST:event_jlRegMouseEntered

    private void jlRegMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlRegMouseExited
        // TODO add your handling code here:
        btReg.setBackground(new Color(32, 130, 196));

    }//GEN-LAST:event_jlRegMouseExited

    private void jlEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlEntrarMouseEntered
        // TODO add your handling code here:
        btEntrar.setBackground(new Color(53, 184, 230));
    }//GEN-LAST:event_jlEntrarMouseEntered

    private void jlEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlEntrarMouseExited
        // TODO add your handling code here:
        btEntrar.setBackground(new Color(32, 130, 196));
    }//GEN-LAST:event_jlEntrarMouseExited

    private void jtfUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfUserActionPerformed

    private void jlDragMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDragMouseDragged
        // TODO add your handling code here:
        int x2 = evt.getXOnScreen();
        int y2 = evt.getYOnScreen();
        this.setLocation(x2 - x, y2 - y);
    }//GEN-LAST:event_jlDragMouseDragged

    private void jlDragMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDragMousePressed
        // TODO add your handling code here:
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jlDragMousePressed

    private void lbsalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbsalirMouseExited
        // TODO add your handling code here:
        jpSalir.setBackground(new Color(32, 120, 196));


    }//GEN-LAST:event_lbsalirMouseExited

    private void lbsalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbsalirMouseEntered
        // TODO add your handling code here:
        jpSalir.setBackground(new Color(248, 118, 118));


    }//GEN-LAST:event_lbsalirMouseEntered

    private void lbsalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbsalirMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_lbsalirMouseClicked

    private void jlAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAboutMouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Aplicación Creada por : \n\n Alejandro Rodriguez Hidalgo "
                + "\n Fernando Teodoro Guillén"
                + "\n Alberto López Rodríguez"
                + "\n Sergio Moreno Terán "
                + "\n Eloy Murillo Granado ", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jlAboutMouseClicked

    private void jlAboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlAboutMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_jlAboutMouseEntered

    private void jlEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlEntrarMouseClicked
        try {
            if (iniciarSesion()) {
                c.close();
                Launcher launcher = new Launcher(jtfUser.getText());
                launcher.setLocationRelativeTo(this);
                launcher.setVisible(true);
                this.setVisible(false);
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jlEntrarMouseClicked

    private void btRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btRegMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btRegMouseClicked

    private void jlRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlRegMouseClicked
        // TODO add your handling code here:

        Registro reg = new Registro();
        reg.setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jlRegMouseClicked

    private void jpfPwdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jpfPwdFocusGained
        // TODO add your handling code here:
        if(String.valueOf(jpfPwd.getPassword()).equals("contrasena.......")){
            jpfPwd.setText("");
        }
    }//GEN-LAST:event_jpfPwdFocusGained

    private void jlEntrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jlEntrarKeyPressed
        // TODO add your handling code here:


    }//GEN-LAST:event_jlEntrarKeyPressed

    private void jpfPwdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpfPwdKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (iniciarSesion()) {
                    c.close();
                    Launcher launcher = new Launcher(jtfUser.getText());
                    launcher.setVisible(true);
                    this.setVisible(false);
                    dispose();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jpfPwdKeyPressed

    private void jtfUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfUserFocusGained
        // TODO add your handling code here:
        if(jtfUser.getText().equals("Introduce tu usuario...")){
            jtfUser.setText("");
        }
    }//GEN-LAST:event_jtfUserFocusGained

    private void jtfUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfUserFocusLost
        // TODO add your handling code here:
        if (jtfUser.getText().equals("")) {
            jtfUser.setText("Introduce tu usuario...");
        }
    }//GEN-LAST:event_jtfUserFocusLost

    private void jpfPwdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jpfPwdFocusLost
        // TODO add your handling code here:
        if (String.valueOf(jpfPwd.getPassword()).equals("")) {
            jpfPwd.setText("contrasena.......");
        }
    }//GEN-LAST:event_jpfPwdFocusLost

    private void jpfPwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpfPwdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpfPwdActionPerformed

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
            java.util.logging.Logger.getLogger(EXteamLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EXteamLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EXteamLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EXteamLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EXteamLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btEntrar;
    private javax.swing.JPanel btReg;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlAbout;
    private javax.swing.JLabel jlBackground;
    private javax.swing.JPanel jlDrag;
    private javax.swing.JLabel jlEntrar;
    private javax.swing.JLabel jlReg;
    private javax.swing.JLabel jlTitle;
    private javax.swing.JPanel jpSalir;
    private javax.swing.JPasswordField jpfPwd;
    private java.awt.TextField jtfUser;
    private javax.swing.JLabel lbsalir;
    // End of variables declaration//GEN-END:variables

}
