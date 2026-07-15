package com.riquisima.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {

    private JPanel panelMenu;

    private JPanel panelContenedor;

    private CardLayout cardLayout;

    public MenuPrincipal() {

        iniciarComponentes();

        setVisible(true);
    }

    private void iniciarComponentes() {

        setTitle("Bodega Riquisima");

        setSize(1300, 750);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());


        cardLayout = new CardLayout();

        panelContenedor =
                new JPanel(cardLayout);


        panelContenedor.add(
                new PanelDashboard(),
                "DASHBOARD"
        );

        panelContenedor.add(
                new PanelProductos(),
                "PRODUCTOS"
        );

        panelContenedor.add(
                new PanelVentas(),
                "VENTAS"
        );

        panelContenedor.add(
                new PanelPagos(),
                "PAGOS"
        );

        panelContenedor.add(
                new PanelInventario(),
                "INVENTARIO"
        );

        crearMenu();

        add(panelContenedor,
                BorderLayout.CENTER);


        cardLayout.show(
                panelContenedor,
                "DASHBOARD"
        );
    }

    private void crearMenu() {

        panelMenu = new JPanel();

        panelMenu.setBackground(
                new Color(33, 47, 61)
        );

        panelMenu.setPreferredSize(
                new Dimension(220, 700)
        );

        panelMenu.setLayout(null);


        JLabel lblTitulo =
                new JLabel("BODEGA");

        lblTitulo.setForeground(Color.WHITE);

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        lblTitulo.setBounds(
                50,
                30,
                200,
                40
        );

        panelMenu.add(lblTitulo);


        JButton btnDashboard =
                crearBoton("Dashboard", 120);

        JButton btnProductos =
                crearBoton("Productos", 180);

        JButton btnVentas =
                crearBoton("Ventas", 240);

        JButton btnPagos =
                crearBoton("Pagos", 300);

        JButton btnInventario =
                crearBoton("Inventario", 360);

        JButton btnCerrarSesion =
                crearBoton("Cerrar Sesion", 550);


        btnDashboard.addActionListener(e -> {

            cardLayout.show(
                    panelContenedor,
                    "DASHBOARD"
            );
        });

        btnProductos.addActionListener(e -> {

            cardLayout.show(
                    panelContenedor,
                    "PRODUCTOS"
            );
        });

        btnVentas.addActionListener(e -> {

            cardLayout.show(
                    panelContenedor,
                    "VENTAS"
            );
        });

        btnPagos.addActionListener(e -> {

            cardLayout.show(
                    panelContenedor,
                    "PAGOS"
            );
        });

        btnInventario.addActionListener(e -> {

            cardLayout.show(
                    panelContenedor,
                    "INVENTARIO"
            );
        });


        btnCerrarSesion.addActionListener(e -> {

            cerrarSesion();
        });


        panelMenu.add(btnDashboard);

        panelMenu.add(btnProductos);

        panelMenu.add(btnVentas);

        panelMenu.add(btnPagos);

        panelMenu.add(btnInventario);

        panelMenu.add(btnCerrarSesion);

        add(panelMenu, BorderLayout.WEST);
    }


    private JButton crearBoton(
            String texto,
            int y
    ) {

        JButton boton =
                new JButton(texto);

        boton.setBounds(
                20,
                y,
                180,
                40
        );

        boton.setFocusPainted(false);

        boton.setBackground(
                new Color(52, 73, 94)
        );

        boton.setForeground(Color.BLACK);

        boton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        return boton;
    }

    private void cerrarSesion() {

        int opcion =
                JOptionPane.showConfirmDialog(

                        this,

                        "¿Desea cerrar sesion?",

                        "Confirmacion",

                        JOptionPane.YES_NO_OPTION
                );

        if (opcion == JOptionPane.YES_OPTION) {


            dispose();


            LoginForm login =
                    new LoginForm();

            login.setVisible(true);
        }
    }
}