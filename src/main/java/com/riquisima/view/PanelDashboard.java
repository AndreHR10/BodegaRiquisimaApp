package com.riquisima.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelDashboard extends JPanel {

    public PanelDashboard() {

        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("SISTEMA DE GESTION - BODEGA RIQUISIMA", SwingConstants.CENTER);
        titulo.setBounds(200, 30, 800, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        add(titulo);

        JLabel labelImagen = new JLabel("", SwingConstants.CENTER);
        
        labelImagen.setBounds(200, 90, 800, 440); 
        
        try {
            URL urlImagen = getClass().getResource("/img/logo.png");
            
            if (urlImagen != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlImagen);
                
                Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(420, 420, Image.SCALE_SMOOTH);
                labelImagen.setIcon(new ImageIcon(imagenRedimensionada));
            } else {
                labelImagen.setText("[ Coloca tu logo en src/main/resources/img/logo.png ]");
                labelImagen.setForeground(Color.GRAY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        add(labelImagen);

        JLabel info = new JLabel("Bienvenido al sistema", SwingConstants.CENTER);
        
        info.setBounds(200, 550, 800, 30);
        info.setFont(new Font("Arial", Font.PLAIN, 20));
        add(info);
    }
}