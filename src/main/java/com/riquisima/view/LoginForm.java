package com.riquisima.view;

import java.awt.Color;
import java.awt.Font;
import com.riquisima.repository.UsuarioRepository;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JFrame {

    private JTextField txtUsuario;

    private JPasswordField txtPassword;

    public LoginForm() {

        iniciarComponentes();

        setVisible(true);
    }

    private void iniciarComponentes() {

        setTitle("Login - Bodega Riquisima");

        setSize(500, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);


        JLabel titulo =
                new JLabel("BODEGA RIQUISIMA");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titulo.setBounds(90, 40, 320, 40);

        add(titulo);


        JLabel lblUsuario =
                new JLabel("Usuario:");

        lblUsuario.setBounds(80, 130, 100, 30);

        add(lblUsuario);

        txtUsuario = new JTextField();

        txtUsuario.setBounds(180, 130, 180, 30);

        add(txtUsuario);


        JLabel lblPassword =
                new JLabel("Contraseña:");

        lblPassword.setBounds(80, 190, 100, 30);

        add(lblPassword);

        txtPassword = new JPasswordField();

        txtPassword.setBounds(180, 190, 180, 30);

        add(txtPassword);


        JButton btnIngresar =
                new JButton("INGRESAR");

        btnIngresar.setBounds(100, 280, 120, 40);

        add(btnIngresar);


        JButton btnSalir =
                new JButton("SALIR");

        btnSalir.setBounds(250, 280, 120, 40);

        add(btnSalir);


        btnIngresar.addActionListener(e -> {

            login();
        });


        btnSalir.addActionListener(e -> {

            System.exit(0);
        });
    }

   private void login() {

    String usuario =
            txtUsuario.getText();

    String password =
            String.valueOf(
                    txtPassword.getPassword()
            );

    UsuarioRepository repo =
            new UsuarioRepository();

    boolean existe =
            repo.validarLogin(
                    usuario,
                    password
            );

    if (existe) {

        JOptionPane.showMessageDialog(
                this,
                "Bienvenido"
        );

        new MenuPrincipal();

        dispose();

    } else {

        JOptionPane.showMessageDialog(
                this,
                "Credenciales incorrectas"
        );
    }
}
}