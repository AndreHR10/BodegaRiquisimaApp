package com.riquisima.view;

import com.riquisima.model.Producto;
import com.riquisima.repository.ProductoRepository;

import java.awt.Color;
import java.awt.Font;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

public class PanelInventario extends JPanel {

    private JTable tabla;

    private DefaultTableModel modelo;

    private JButton btnActualizar;

    public PanelInventario() {

        iniciarComponentes();

        cargarInventario();
    }

    private void iniciarComponentes() {

        setLayout(null);

        setBackground(Color.WHITE);

        // TITULO

        JLabel titulo =
                new JLabel("CONTROL DE INVENTARIO");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setBounds(
                300,
                20,
                400,
                30
        );

        add(titulo);

        // TABLA

        modelo =
                new DefaultTableModel();

        modelo.addColumn("ID");

        modelo.addColumn("Producto");

        modelo.addColumn("Categoria");

        modelo.addColumn("Precio");

        modelo.addColumn("Stock");

        tabla =
                new JTable(modelo);

        JScrollPane scroll =
                new JScrollPane(tabla);

        scroll.setBounds(
                100,
                100,
                850,
                350
        );

        add(scroll);

        // BOTON ACTUALIZAR

        btnActualizar =
                new JButton("Actualizar Stock");

        btnActualizar.setBounds(
                400,
                480,
                220,
                40
        );

        add(btnActualizar);

        // EVENTO

        btnActualizar.addActionListener(e -> {

            cargarInventario();
        });
    }

    // CARGAR INVENTARIO REAL

    private void cargarInventario() {

        ProductoRepository repo =
                new ProductoRepository();

        List<Producto> lista =
                repo.listar();

        // LIMPIAR TABLA

        modelo.setRowCount(0);

        // RECORRER PRODUCTOS

        for (Producto p : lista) {

            modelo.addRow(new Object[]{

                p.getId(),
                p.getNombre(),
                p.getCategoria(),
                p.getPrecio(),
                p.getStock()
            });
        }
    }
}