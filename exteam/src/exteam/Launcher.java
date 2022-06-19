/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exteam;

import bbdd.ConectorBBDD;
import java.awt.Color;
import javax.swing.ImageIcon;
import pang.main.WindowPang;
import bubble_shooter.windowMain.WindowBubble;
import duck_hunt.window.WindowDuck;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static pang.states.State.user;

/**
 *
 * @author elale
 */
public class Launcher extends javax.swing.JFrame {

    boolean banderaPang, banderaDuck, banderaBubble;
    int precio_pang, precio_bubble, precio_duck;
    String juego;
    int x, y;
    String username;
    int mostrarScore;
    private Connection c;
    int score;
    private boolean super_pang, duck_hunt, bubble_shooter;

    /**
     * Creates new form Launcher
     */
    public Launcher(String username) {
        initComponents();
        juego = "pang";
        this.username = username;
        jlUsuario.setText("Hola, " + username);

        try {

            // Recupero todos los valores necesarios de la base de datos
            recuperarValores();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Recupero la descripción del juego seleccionado
        recuperarDetalles();
    }

    // Hace la acción de comprar el juego
    public void comprarJuego(String game) {

        // Diálogo preguntando si quieres comprarlo
        int dialogButton = JOptionPane.showConfirmDialog(null, "El juego está bloqueado, ¿quieres comprarlo?", "Juego Bloqueado", JOptionPane.YES_NO_OPTION);
        ;

        // Si eliges que si, inicia el proceso de transacción, si elegimos no nos cierra el diálogo
        if (dialogButton == JOptionPane.YES_OPTION) {
            try {
                hacerTransaccion(game);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Nos indica si se puede comprar o no el juego
    public boolean sePuedeComprar(String game) {
        boolean comprar = false;
        switch (game) {
            case "pang":
                System.out.println(precio_pang);
                if (score >= precio_pang) {
                    comprar = true;
                } else {
                    comprar = false;
                }
                break;
            case "bubble":
                System.out.println(precio_bubble);
                if (score >= precio_bubble) {
                    comprar = true;
                } else {
                    comprar = false;
                }
                break;
            case "duck":
                System.out.println(precio_duck);
                if (score >= precio_duck) {
                    comprar = true;
                } else {
                    comprar = false;
                }
                break;
        }
        return comprar;
    }

    // Realiza la transacción del juego
    public void hacerTransaccion(String game) throws SQLException {

        // Atributo para guardar el nombre del booleano que indica si el juego está bloqueado
        String game_name = "";
        String query;

        // Si el juego se puede comprar, restamos el precio del juego al crédito del jugador
        // y guardamos la variable game_name. Si no se puede, informamos al usuario de que no puede
        // comprarlo y le informamos del precio.
        if (sePuedeComprar(game)) {
            switch (game) {
                case "pang":
                    score -= precio_pang;
                    game_name = "super_pang";
                    break;
                case "bubble":
                    score -= precio_bubble;
                    game_name = "bubble_shooter";
                    break;
                case "duck":
                    score -= precio_duck;
                    game_name = "duck_hunt";
                    break;
            }

            // Iniciamos la conexión y hacemos update
            setConexion();
            Statement stmnt;
            query = "UPDATE player SET game_credit = " + score + ", " + game_name + " = true";
            stmnt = c.createStatement();
            stmnt.executeUpdate(query);
            c.close();

            // Recuperamos todos los valores de la base de datos
            recuperarValores();

            // Informamos al usuario de que se ha completado la transacción
            JOptionPane.showMessageDialog(this, "Juego comprado con éxito", "Confirmación", JOptionPane.INFORMATION_MESSAGE);

        } else {

            switch (game) {
                case ("pang"):
                    JOptionPane.showMessageDialog(this, "No tienes crédito suficiente, necesitas " + precio_pang + " para comprarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case ("bubble"):
                    JOptionPane.showMessageDialog(this, "No tienes crédito suficiente, necesitas " + precio_bubble + " para comprarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case ("duck"):
                    JOptionPane.showMessageDialog(this, "No tienes crédito suficiente, necesitas " + precio_duck + " para comprarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    public void setConexion() {
        ConectorBBDD bbdd = new ConectorBBDD();
        c = bbdd.getConexion();
    }

    // Recupera todos los valores necesarios de la base de datos
    public void recuperarValores() throws SQLException {
        retrieveScore();
        setUnlocked();
        setPrecios("super pang");
        System.out.println(precio_pang);
        setPrecios("duck hunt");
        System.out.println(precio_duck);
        setPrecios("bubble shooter");
        System.out.println(precio_bubble);
        // Cambiar valores
        if (super_pang) {
            System.out.println("SUperpang comprado true");
            jlCompradoBloqueado.setText("COMPRADO");
            jlCompradoBloqueado.setForeground(Color.GREEN);
        }
        if (!super_pang) {
            System.out.println("SUperpang comprado false");

            jlCompradoBloqueado.setText("BLOQUEADO");
            jlCompradoBloqueado.setForeground(Color.RED);
        }
        if (bubble_shooter) {
            System.out.println("bubble shooter comprado true");

            jlCompradoBloqueado.setText("COMPRADO");
            jlCompradoBloqueado.setForeground(Color.GREEN);
        }
        if (!bubble_shooter) {
            System.out.println("bubble shooter comprado false");

            jlCompradoBloqueado.setText("BLOQUEADO");
            jlCompradoBloqueado.setForeground(Color.RED);
        }
        if (duck_hunt) {
            System.out.println("duck hunt comprado true");

            jlCompradoBloqueado.setText("COMPRADO");
            jlCompradoBloqueado.setForeground(Color.GREEN);
        }
        if (!duck_hunt) {
            System.out.println("duck hunt comprado false");

            jlCompradoBloqueado.setText("BLOQUEADO");
            jlCompradoBloqueado.setForeground(Color.RED);
        }
    }

    public void setUnlocked() throws SQLException {
        super_pang = false;
        duck_hunt = false;
        bubble_shooter = false;
        setConexion();
        String query = "SELECT super_pang, bubble_shooter, duck_hunt FROM player WHERE username = '" + username + "';";
        Statement stmnt = c.createStatement();
        ResultSet rs = stmnt.executeQuery(query);
        while (rs.next()) {
            super_pang = rs.getBoolean(1);
            bubble_shooter = rs.getBoolean(2);
            duck_hunt = rs.getBoolean(3);
        }
        c.close();
    }

    // Recupera los precios de los juegos de la base de datos
    public void setPrecios(String game) throws SQLException {

        int valor = 0;

        // Abre la conexión y ejecuta la consulta
        setConexion();
        String query = "SELECT price FROM game WHERE game_name = '" + game + "';";;
        Statement stmnt = c.createStatement();
        ResultSet rs = stmnt.executeQuery(query);

        // Guardamos el valor de la consulta
        while (rs.next()) {
            valor = rs.getInt(1);
        }

        // En función del juego que hayamos llamado, asignamos el precio.
        switch (game) {
            case "super pang":
                precio_pang = valor;
                break;
            case "bubble shooter":
                precio_bubble = valor;
                break;
            case "duck hunt":
                precio_duck = valor;
                break;
        }

        // Cerramos la conexión
        c.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpBackground = new javax.swing.JPanel();
        jpHeader = new javax.swing.JPanel();
        jpExit = new javax.swing.JPanel();
        jlExit = new javax.swing.JLabel();
        jpSidebar = new javax.swing.JPanel();
        jlUsuario = new javax.swing.JLabel();
        jpPang = new javax.swing.JPanel();
        jlPang = new javax.swing.JLabel();
        jpDuck = new javax.swing.JPanel();
        jlDuck = new javax.swing.JLabel();
        jpBubble = new javax.swing.JPanel();
        jlBubble = new javax.swing.JLabel();
        jpJuego4 = new javax.swing.JPanel();
        jlUsuario4 = new javax.swing.JLabel();
        jpJuego5 = new javax.swing.JPanel();
        jlUsuario5 = new javax.swing.JLabel();
        jpGameDescrp = new javax.swing.JPanel();
        jlJuegoSeleccionado = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Descripcion = new javax.swing.JLabel();
        Imagen = new javax.swing.JLabel();
        jlPrecioJuego = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlJuegoSeleccionado1 = new javax.swing.JLabel();
        jlScore = new javax.swing.JLabel();
        jlCompradoBloqueado = new javax.swing.JLabel();
        jlFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBackground.setBackground(new java.awt.Color(255, 255, 255));
        jpBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpHeader.setOpaque(false);
        jpHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpHeaderMouseDragged(evt);
            }
        });
        jpHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpHeaderMousePressed(evt);
            }
        });
        jpHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpExit.setBackground(new java.awt.Color(0, 100, 177));
        jpExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jpExitMouseEntered(evt);
            }
        });
        jpExit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlExit.setBackground(new java.awt.Color(0, 100, 177));
        jlExit.setFont(new java.awt.Font("Berlin Sans FB", 0, 30)); // NOI18N
        jlExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlExit.setText("×");
        jlExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlExitMouseExited(evt);
            }
        });
        jpExit.add(jlExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 25));

        jpHeader.add(jpExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 50, 25));

        jpBackground.add(jpHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 30));

        jpSidebar.setBackground(new java.awt.Color(220, 230, 0));
        jpSidebar.setOpaque(false);
        jpSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlUsuario.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jlUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jlUsuario.setText("Hola, ");
        jpSidebar.add(jlUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 30));

        jpPang.setBackground(new java.awt.Color(53, 184, 230));
        jpPang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlPang.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jlPang.setForeground(new java.awt.Color(255, 255, 255));
        jlPang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlPang.setText("Super Pang");
        jlPang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlPang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlPangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlPangMouseExited(evt);
            }
        });
        jpPang.add(jlPang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 90));

        jpSidebar.add(jpPang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 280, 90));

        jpDuck.setBackground(new java.awt.Color(53, 184, 230));

        jlDuck.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jlDuck.setForeground(new java.awt.Color(255, 255, 255));
        jlDuck.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDuck.setText("Duck Hunt");
        jlDuck.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlDuck.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlDuckMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlDuckMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlDuckMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpDuckLayout = new javax.swing.GroupLayout(jpDuck);
        jpDuck.setLayout(jpDuckLayout);
        jpDuckLayout.setHorizontalGroup(
            jpDuckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
            .addGroup(jpDuckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpDuckLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlDuck, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jpDuckLayout.setVerticalGroup(
            jpDuckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
            .addGroup(jpDuckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpDuckLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlDuck, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jpSidebar.add(jpDuck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 280, 90));

        jpBubble.setBackground(new java.awt.Color(53, 184, 230));

        jlBubble.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jlBubble.setForeground(new java.awt.Color(255, 255, 255));
        jlBubble.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlBubble.setText("Bubble Shooter");
        jlBubble.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlBubble.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlBubbleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlBubbleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlBubbleMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpBubbleLayout = new javax.swing.GroupLayout(jpBubble);
        jpBubble.setLayout(jpBubbleLayout);
        jpBubbleLayout.setHorizontalGroup(
            jpBubbleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
            .addGroup(jpBubbleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBubbleLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlBubble, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jpBubbleLayout.setVerticalGroup(
            jpBubbleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
            .addGroup(jpBubbleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpBubbleLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlBubble, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jpSidebar.add(jpBubble, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 280, 90));

        jpJuego4.setBackground(new java.awt.Color(53, 184, 230));

        jlUsuario4.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jlUsuario4.setForeground(new java.awt.Color(255, 255, 255));
        jlUsuario4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlUsuario4.setText("Elder Ring");
        jlUsuario4.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        jlUsuario4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlUsuario4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpJuego4Layout = new javax.swing.GroupLayout(jpJuego4);
        jpJuego4.setLayout(jpJuego4Layout);
        jpJuego4Layout.setHorizontalGroup(
            jpJuego4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
            .addGroup(jpJuego4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpJuego4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlUsuario4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jpJuego4Layout.setVerticalGroup(
            jpJuego4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
            .addGroup(jpJuego4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpJuego4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlUsuario4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jpSidebar.add(jpJuego4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 280, 90));

        jpJuego5.setBackground(new java.awt.Color(53, 184, 230));

        jlUsuario5.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jlUsuario5.setForeground(new java.awt.Color(255, 255, 255));
        jlUsuario5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlUsuario5.setText("Counter Strike Source");
        jlUsuario5.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        jlUsuario5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jlUsuario5MouseDragged(evt);
            }
        });
        jlUsuario5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlUsuario5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpJuego5Layout = new javax.swing.GroupLayout(jpJuego5);
        jpJuego5.setLayout(jpJuego5Layout);
        jpJuego5Layout.setHorizontalGroup(
            jpJuego5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
            .addGroup(jpJuego5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpJuego5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlUsuario5, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jpJuego5Layout.setVerticalGroup(
            jpJuego5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
            .addGroup(jpJuego5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpJuego5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jlUsuario5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jpSidebar.add(jpJuego5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 280, 90));

        jpBackground.add(jpSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 300, 570));

        jpGameDescrp.setBackground(new java.awt.Color(255, 0, 0));
        jpGameDescrp.setOpaque(false);
        jpGameDescrp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpGameDescrpKeyPressed(evt);
            }
        });
        jpGameDescrp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlJuegoSeleccionado.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        jlJuegoSeleccionado.setForeground(new java.awt.Color(255, 255, 255));
        jlJuegoSeleccionado.setText("Super Pang");
        jpGameDescrp.add(jlJuegoSeleccionado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 150, 30));

        jPanel1.setBackground(new java.awt.Color(210, 210, 210));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Descripcion.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        Descripcion.setForeground(java.awt.Color.white);
        jPanel1.add(Descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 290, 350));
        jPanel1.add(Imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(314, 214, 220, 170));

        jlPrecioJuego.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jlPrecioJuego.setForeground(new java.awt.Color(255, 255, 255));
        jlPrecioJuego.setText("jlPrecioJuego");
        jlPrecioJuego.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlPrecioJuego.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlPrecioJuegoMouseClicked(evt);
            }
        });
        jPanel1.add(jlPrecioJuego, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 170, -1));

        jpGameDescrp.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 680, 390));

        jPanel2.setBackground(new java.awt.Color(53, 184, 230));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlJuegoSeleccionado1.setFont(new java.awt.Font("Berlin Sans FB", 0, 36)); // NOI18N
        jlJuegoSeleccionado1.setForeground(new java.awt.Color(255, 255, 255));
        jlJuegoSeleccionado1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlJuegoSeleccionado1.setText("JUGAR");
        jlJuegoSeleccionado1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlJuegoSeleccionado1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlJuegoSeleccionado1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlJuegoSeleccionado1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlJuegoSeleccionado1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlJuegoSeleccionado1MouseExited(evt);
            }
        });
        jPanel2.add(jlJuegoSeleccionado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 90));

        jpGameDescrp.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 680, 90));

        jlScore.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        jlScore.setForeground(java.awt.Color.white);
        jlScore.setText("Score : ");
        jpGameDescrp.add(jlScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jlCompradoBloqueado.setFont(new java.awt.Font("Berlin Sans FB", 0, 22)); // NOI18N
        jlCompradoBloqueado.setForeground(new java.awt.Color(255, 255, 255));
        jlCompradoBloqueado.setText("jlComprado");
        jpGameDescrp.add(jlCompradoBloqueado, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 230, 50));

        jpBackground.add(jpGameDescrp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 700, 570));

        jlFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/exteam/bk.png"))); // NOI18N
        jlFondo.setText("jLabel2");
        jpBackground.add(jlFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        getContentPane().add(jpBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jlExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlExitMouseExited
        // TODO add your handling code here:
        jpExit.setBackground(new Color(0, 107, 180));
    }//GEN-LAST:event_jlExitMouseExited

    private void jlExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlExitMouseEntered
        // TODO add your handling code here:
        jpExit.setBackground(new Color(248, 118, 118));
    }//GEN-LAST:event_jlExitMouseEntered

    private void jlPangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPangMouseEntered
        // TODO add your handling code here:
        jpPang.setBackground(Color.decode("#2082c4"));
    }//GEN-LAST:event_jlPangMouseEntered

    private void jlPangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPangMouseExited
        // TODO add your handling code here:
        if (banderaPang) {

        } else {
            jpPang.setBackground(Color.decode("#35b8e6"));

        }

    }//GEN-LAST:event_jlPangMouseExited

    private void jlDuckMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDuckMouseEntered
        // TODO add your handling code here:
        jpDuck.setBackground(Color.decode("#2082c4"));
    }//GEN-LAST:event_jlDuckMouseEntered

    private void jlBubbleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlBubbleMouseEntered
        // TODO add your handling code here:
        jpBubble.setBackground(Color.decode("#2082c4"));
    }//GEN-LAST:event_jlBubbleMouseEntered

    private void jlDuckMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDuckMouseExited
        // TODO add your handling code here:
        if (banderaDuck) {

        } else {
            jpDuck.setBackground(Color.decode("#35b8e6"));

        }
    }//GEN-LAST:event_jlDuckMouseExited

    private void jlBubbleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlBubbleMouseExited
        // TODO add your handling code here:
        if (banderaBubble) {

        } else {
            jpBubble.setBackground(Color.decode("#35b8e6"));
        }
    }//GEN-LAST:event_jlBubbleMouseExited

    private void jlJuegoSeleccionado1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlJuegoSeleccionado1MouseEntered
        // TODO add your handling code here:
        jPanel2.setBackground(Color.decode("#2082c4"));
    }//GEN-LAST:event_jlJuegoSeleccionado1MouseEntered

    private void jlJuegoSeleccionado1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlJuegoSeleccionado1MouseExited
        // TODO add your handling code here:
        jPanel2.setBackground(Color.decode("#35b8e6"));
    }//GEN-LAST:event_jlJuegoSeleccionado1MouseExited

    private void jlPangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPangMouseClicked
        // TODO add your handling code here:
        juego = "pang";
        recuperarDetalles();
        banderaPang = true;
        banderaBubble = false;
        banderaDuck = false;
        jpPang.setBackground(Color.decode("#2082c4"));
        jpDuck.setBackground(Color.decode("#35b8e6"));
        jpBubble.setBackground(Color.decode("#35b8e6"));

    }//GEN-LAST:event_jlPangMouseClicked

    private void jlDuckMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlDuckMouseClicked
        // TODO add your handling code here:
        juego = "duck";
        recuperarDetalles();
        banderaPang = false;
        banderaBubble = false;
        banderaDuck = true;
        jpPang.setBackground(Color.decode("#35b8e6"));
        jpDuck.setBackground(Color.decode("#2082c4"));
        jpBubble.setBackground(Color.decode("#35b8e6"));
    }//GEN-LAST:event_jlDuckMouseClicked

    private void jlBubbleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlBubbleMouseClicked
        // TODO add your handling code here:
        juego = "bubble";
        recuperarDetalles();
        banderaPang = false;
        banderaBubble = true;
        banderaDuck = false;
        jpPang.setBackground(Color.decode("#35b8e6"));
        jpDuck.setBackground(Color.decode("#35b8e6"));
        jpBubble.setBackground(Color.decode("#2082c4"));
    }//GEN-LAST:event_jlBubbleMouseClicked

    private void jpHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpHeaderMousePressed
        // TODO add your handling code here:
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jpHeaderMousePressed

    private void jpHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpHeaderMouseDragged
        // TODO add your handling code here:
        int x2 = evt.getXOnScreen();
        int y2 = evt.getYOnScreen();
        this.setLocation(x2 - x, y2 - y);
    }//GEN-LAST:event_jpHeaderMouseDragged

    private void jlExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jlExitMouseClicked

    private void jpExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpExitMouseEntered

        jpExit.setBackground(new Color(53, 184, 230));
    }//GEN-LAST:event_jpExitMouseEntered

    private void jlJuegoSeleccionado1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlJuegoSeleccionado1MouseClicked
        // TODO add your handling code here:
        switch (juego) {
            case "pang":
                if (super_pang) {
                    pang.main.WindowPang pang = new WindowPang(username);
                    pang.setLocationRelativeTo(this);
                    pang.setVisible(true);
                    pang.start();
                    this.dispose();
                    while (pang.isActive()) {
                        try {
                            retrieveScore();
                        } catch (SQLException ex) {
                            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    this.dispose();
                } else {
                    comprarJuego(juego);
                }
                break;
            case "bubble":
                if (bubble_shooter) {
                    bubble_shooter.windowMain.WindowBubble bubble = new WindowBubble(username);
                    bubble.setLocationRelativeTo(this);
                    bubble.setVisible(true);
                    bubble.start();
                    this.dispose();

                } else {
                    comprarJuego(juego);
                }

                break;
            case "duck":
                if (duck_hunt) {
                    duck_hunt.window.WindowDuck duck = new WindowDuck(username);
                    duck.setLocationRelativeTo(this);
                    duck.setVisible(true);
                    duck.start();
                    this.dispose();

                } else {
                    comprarJuego(juego);
                }

                break;
        }
    }//GEN-LAST:event_jlJuegoSeleccionado1MouseClicked

    private void jlUsuario4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlUsuario4MouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Juego En Desarrollo ", "NO DISPONIBLE", JOptionPane.ERROR_MESSAGE);

    }//GEN-LAST:event_jlUsuario4MouseClicked

    private void jlUsuario5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlUsuario5MouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Juego En Desarrollo ", "NO DISPONIBLE", JOptionPane.ERROR_MESSAGE);

    }//GEN-LAST:event_jlUsuario5MouseClicked

    private void jpGameDescrpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpGameDescrpKeyPressed


    }//GEN-LAST:event_jpGameDescrpKeyPressed

    private void jlUsuario5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlUsuario5MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jlUsuario5MouseDragged

    private void jlPrecioJuegoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlPrecioJuegoMouseClicked
        try {
            setConexion();
            String query = "UPDATE player SET game_credit = 99999 WHERE username = '" + username + "';";
            Statement stmnt = c.createStatement();
            stmnt.executeUpdate(query);
            System.out.println("Chetos");
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jlPrecioJuegoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Descripcion;
    private javax.swing.JLabel Imagen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jlBubble;
    private javax.swing.JLabel jlCompradoBloqueado;
    private javax.swing.JLabel jlDuck;
    private javax.swing.JLabel jlExit;
    private javax.swing.JLabel jlFondo;
    private javax.swing.JLabel jlJuegoSeleccionado;
    private javax.swing.JLabel jlJuegoSeleccionado1;
    private javax.swing.JLabel jlPang;
    private javax.swing.JLabel jlPrecioJuego;
    private javax.swing.JLabel jlScore;
    private javax.swing.JLabel jlUsuario;
    private javax.swing.JLabel jlUsuario4;
    private javax.swing.JLabel jlUsuario5;
    private javax.swing.JPanel jpBackground;
    private javax.swing.JPanel jpBubble;
    private javax.swing.JPanel jpDuck;
    private javax.swing.JPanel jpExit;
    private javax.swing.JPanel jpGameDescrp;
    private javax.swing.JPanel jpHeader;
    private javax.swing.JPanel jpJuego4;
    private javax.swing.JPanel jpJuego5;
    private javax.swing.JPanel jpPang;
    private javax.swing.JPanel jpSidebar;
    // End of variables declaration//GEN-END:variables

    private void recuperarDetalles() {
        String texto = "<html><body>Se trata de un joven explorador, hermano de otro,"
                + "<br>que tiene que usar un arma, con diferentes tipos de disparo<br> "
                + "(solo en Modo Tour) para eliminar gradualmente unas bolas que <br>"
                + "aparecen por toda la pantalla. Cuando el disparo del jugador <br>"
                + "impacta contra una bola, dicha burbuja se divide en dos burbujas de menor tamaño.<br>"
                + " Cuando las burbujas tienen el tamaño mínimo, si vuelven a ser disparadas, desaparecen.<br> "
                + "Además de las burbujas redondas convencionales, existen un tipo especial de forma hexagonal<br> "
                + "y que desafían las leyes de la gravedad, ya que su movimiento por la pantalla es debido al <br>"
                + "choque con los límites de esta. Además de dichas burbujas, en la pantalla también (solo en Modo Tour) <br>"
                + "hay muros, items u objetos y criaturas que nos ayudarán o entorpecerán en nuestra misión.</body></html>";
        String texto1 = "<html><body>Duck Hunt (lit. cacería de patos) es un videojuego creado y desarrollado <br>"
                + " por Nintendo para la Nintendo Entertainment System (NES). <br>"
                + "También fue publicado en los arcades en el año 1984 bajo el nombre Vs. Duck Hunt.<br>"
                + " El título fue uno de los dos juegos incluidos con el primer lanzamiento de la consola.<br>"
                + "1​ El jugador debe utilizar una pistola de luz, normalmente <br> "
                + "la Nintendo Zapper, para disparar a los patos que vuelan por la pantalla,<br>"
                + " obteniendo puntos y avanzando niveles conforme acierta a los objetivos.<br> "
                + "Existe un modo adicional en el que los objetivos a impactar se cambian por discos <br></body></html>";

        String texto2 = "<html><body>Space Invaders es un videojuego de arcade diseñado<br> "
                + "por Toshihiro Nishikado y lanzado al mercado en 1978. <br>"
                + "En un principio fue fabricado y vendido por Taito Co.<br>"
                + " en Japón, para posteriormente ser licenciado para producción y distribución en Estados Unidos <br>"
                + "por Midway Games, división de Bally Technologies. Space Invaders es uno de los primeros juegos matamarcianos.<br>"
                + " Es uno de los videojuegos más importantes de la historia.<br>"
                + " Su objetivo es eliminar oleadas de alienígenas con un cañón láser y obtener la mayor <br>"
                + "cantidad de puntos posible.<br>"
                + " Para el diseño del juego, Nishikado se inspiró en Breakout, <br>"
                + "La guerra de los mundos y Star Wars Aunque es un juego simple para los estándares actuales,<br> "
                + "fue uno de los precursores de los videojuegos modernos y ayudó a expandir la industria del sector,<br>"
                + " desde una mera novedad a una industria global.</body></html>";

        ImageIcon Img = new ImageIcon(getClass().getResource("/res/exteam/pang.png"));
        ImageIcon Img1 = new ImageIcon(getClass().getResource("/res/exteam/hunter.png"));
        ImageIcon Img2 = new ImageIcon(getClass().getResource("/res/exteam/shooter.png"));
        switch (juego) {
            case "pang":
                jlJuegoSeleccionado.setText("Super Pang");
                Descripcion.setText(texto);
                Imagen.setIcon(Img);
                jlPrecioJuego.setText("200 Creditos");
                if (super_pang) {
                    jlCompradoBloqueado.setText("COMPRADO");
                    jlCompradoBloqueado.setForeground(Color.GREEN);
                } else {
                    jlCompradoBloqueado.setText("BLOQUEADO");
                    jlCompradoBloqueado.setForeground(Color.RED);
                }
                break;
            case "bubble":
                jlJuegoSeleccionado.setText("Bubble Shooter");
                Descripcion.setText(texto2);
                Imagen.setIcon(Img2);
                jlPrecioJuego.setText("10000 Creditos");
                if (bubble_shooter) {
                    jlCompradoBloqueado.setText("COMPRADO");
                    jlCompradoBloqueado.setForeground(Color.GREEN);
                } else {
                    jlCompradoBloqueado.setText("BLOQUEADO");
                    jlCompradoBloqueado.setForeground(Color.RED);
                }
                break;
            case "duck":
                jlJuegoSeleccionado.setText("Duck Hunting");
                Descripcion.setText(texto1);
                Imagen.setIcon(Img1);
                jlPrecioJuego.setText("5000 Creditos");
                if (duck_hunt) {
                    jlCompradoBloqueado.setText("COMPRADO");
                    jlCompradoBloqueado.setForeground(Color.GREEN);
                } else {
                    jlCompradoBloqueado.setText("BLOQUEADO");
                    jlCompradoBloqueado.setForeground(Color.RED);
                }
                break;
        }
    }

    public void retrieveScore() throws SQLException {
        setConexion();
        score = 0;
        String query = "SELECT game_credit FROM player WHERE username ='" + username + "';";
        Statement stmnt = c.createStatement();
        ResultSet rs = stmnt.executeQuery(query);
        while (rs.next()) {
            score = rs.getInt(1);
        }
        c.close();
        jlScore.setText("Score: " + score);
    }

}
