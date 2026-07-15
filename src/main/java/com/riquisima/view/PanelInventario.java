package com.riquisima.view;

import com.riquisima.model.Producto;
import com.riquisima.repository.ProductoRepository;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

        verificarStockBajo();
    }

    private void iniciarComponentes() {

        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("CONTROL DE INVENTARIO");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(300, 20, 400, 30);
        add(titulo);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Producto");
        modelo.addColumn("Categoría");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(80, 100, 930, 350);

        add(scroll);

        btnActualizar = new JButton("Actualizar Stock");
        btnActualizar.setBounds(420, 480, 200, 40);

        add(btnActualizar);

        btnActualizar.addActionListener(e -> {

            cargarInventario();

            verificarStockBajo();

        });
    }

    private void verificarStockBajo() {

        ProductoRepository repo = new ProductoRepository();

        List<Producto> lista = repo.productosStockBajo();

        if (!lista.isEmpty()) {

            StringBuilder mensaje = new StringBuilder();

            mensaje.append("⚠ PRODUCTOS POR AGOTARSE\n\n");

            for (Producto p : lista) {

                mensaje.append("• ")
                        .append(p.getNombre())
                        .append("  (Stock: ")
                        .append(p.getStock())
                        .append(")\n");
            }

            JOptionPane.showMessageDialog(
                    this,
                    mensaje.toString(),
                    "Alerta de Inventario",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void cargarInventario() {

        ProductoRepository repo = new ProductoRepository();

        List<Producto> lista = repo.listar();

        modelo.setRowCount(0);

        for (Producto p : lista) {

            String estado;

            if (p.getStock() == 0) {

                estado = "❌ AGOTADO";

            } else if (p.getStock() <= 5) {

                estado = "🔴 POR AGOTARSE";

            } else if (p.getStock() <= 10) {

                estado = "🟡 STOCK BAJO";

            } else {

                estado = "🟢 DISPONIBLE";
            }

            modelo.addRow(new Object[]{

                p.getId(),
                p.getNombre(),
                p.getCategoria(),
                String.format("S/ %.2f", p.getPrecio()),
                p.getStock(),
                estado

            });
        }
    }
}