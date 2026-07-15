package com.riquisima.view;

import com.riquisima.model.Producto;
import com.riquisima.repository.ProductoRepository;
import com.riquisima.service.ProductoService;
import com.riquisima.service.impl.ProductoServiceImpl;

import java.awt.Color;
import java.awt.Font;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

public class PanelProductos extends JPanel {

    private JTextField txtNombre;

    private JTextField txtPrecio;

    private JTextField txtStock;

    private JComboBox<String> cbCategoria;

    private JTable tabla;

    private DefaultTableModel modelo;

    private JButton btnGuardar;

    public PanelProductos() {

        iniciarComponentes();

        cargarProductos();
    }

    private void iniciarComponentes() {

        setLayout(null);

        setBackground(Color.WHITE);

        JLabel titulo
                = new JLabel("GESTION DE PRODUCTOS");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setBounds(320, 20, 400, 30);

        add(titulo);

        JLabel lblNombre
                = new JLabel("Nombre:");

        lblNombre.setBounds(50, 100, 100, 30);

        add(lblNombre);

        txtNombre
                = new JTextField();

        txtNombre.setBounds(150, 100, 220, 30);

        add(txtNombre);

        JLabel lblCategoria
                = new JLabel("Categoria:");

        lblCategoria.setBounds(50, 150, 100, 30);

        add(lblCategoria);

        cbCategoria
                = new JComboBox<>();

        cbCategoria.addItem("Abarrotes");
        cbCategoria.addItem("Frutas");
        cbCategoria.addItem("Verduras");
        cbCategoria.addItem("Bebidas");
        cbCategoria.addItem("Alcohol");
        cbCategoria.addItem("Galletas");

        cbCategoria.setBounds(150, 150, 220, 30);

        add(cbCategoria);

        JLabel lblPrecio
                = new JLabel("Precio:");

        lblPrecio.setBounds(50, 200, 100, 30);

        add(lblPrecio);

        txtPrecio
                = new JTextField();

        txtPrecio.setBounds(150, 200, 220, 30);

        add(txtPrecio);

        JLabel lblStock
                = new JLabel("Stock:");

        lblStock.setBounds(50, 250, 100, 30);

        add(lblStock);

        txtStock
                = new JTextField();

        txtStock.setBounds(150, 250, 220, 30);

        add(txtStock);

        btnGuardar
                = new JButton("Guardar Producto");

        btnGuardar.setBounds(130, 330, 220, 45);

        add(btnGuardar);

        modelo
                = new DefaultTableModel();

        modelo.addColumn("ID");

        modelo.addColumn("Nombre");

        modelo.addColumn("Categoria");

        modelo.addColumn("Precio");

        modelo.addColumn("Stock");

        tabla
                = new JTable(modelo);

        JScrollPane scroll
                = new JScrollPane(tabla);

        scroll.setBounds(450, 100, 600, 350);

        add(scroll);

        btnGuardar.addActionListener(e -> {

            guardarProducto();
        });
    }

    private void cargarProductos() {

        ProductoRepository repo
                = new ProductoRepository();

        List<Producto> lista
                = repo.listar();

        modelo.setRowCount(0);

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

    private void guardarProducto() {

        try {

            String nombre
                    = txtNombre.getText().trim();

            String categoria
                    = cbCategoria
                            .getSelectedItem()
                            .toString();

            if (nombre.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese nombre"
                );

                return;
            }

            double precio
                    = Double.parseDouble(
                            txtPrecio.getText()
                    );

            int stock
                    = Integer.parseInt(
                            txtStock.getText()
                    );

            Producto producto
                    = new Producto(
                            nombre,
                            categoria,
                            precio,
                            stock
                    );

            ProductoService service
                    = new ProductoServiceImpl();

            service.registrarProducto(
                    producto
            );

            cargarProductos();

            limpiarCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Producto guardado correctamente"
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Precio o stock invalidos"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void limpiarCampos() {

        txtNombre.setText("");

        txtPrecio.setText("");

        txtStock.setText("");

        cbCategoria.setSelectedIndex(0);

        txtNombre.requestFocus();
    }
}
