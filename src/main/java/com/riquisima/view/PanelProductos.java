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
    
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnNuevo;
    private JComboBox<String> cbProductosExistentes;
    private int idProductoSeleccionado = -1;

    public PanelProductos() {
        iniciarComponentes();
        cargarProductos();
        cargarNombres();
    }

    private void iniciarComponentes() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("GESTION DE PRODUCTOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(320, 20, 400, 30);
        add(titulo);

        JLabel lblBuscar = new JLabel("Producto:");
        lblBuscar.setBounds(50, 60, 100, 30);
        add(lblBuscar);

        cbProductosExistentes = new JComboBox<>();
        cbProductosExistentes.setBounds(150, 60, 220, 30);
        add(cbProductosExistentes);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 100, 100, 30);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 100, 220, 30);
        add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(50, 150, 100, 30);
        add(lblCategoria);

        cbCategoria = new JComboBox<>();
        cbCategoria.addItem("Abarrotes");
        cbCategoria.addItem("Frutas");
        cbCategoria.addItem("Verduras");
        cbCategoria.addItem("Bebidas");
        cbCategoria.addItem("Alcohol");
        cbCategoria.addItem("Galletas");
        cbCategoria.setBounds(150, 150, 220, 30);
        add(cbCategoria);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(50, 200, 100, 30);
        add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(150, 200, 220, 30);
        add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(50, 250, 100, 30);
        add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(150, 250, 220, 30);
        add(txtStock);

        btnGuardar = new JButton("Guardar Producto");
        btnGuardar.setBounds(130, 330, 220, 45);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(130, 390, 220, 40);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(130, 440, 220, 40);
        add(btnEliminar);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(130, 490, 220, 40);
        add(btnNuevo);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Categoria");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(450, 100, 600, 430); 
        add(scroll);

        btnGuardar.addActionListener(e -> {
            guardarProducto();
        });

        cbProductosExistentes.addActionListener(e -> {
            cargarProductoSeleccionado();
        });

        btnActualizar.addActionListener(e -> {
            actualizarProducto();
        });

        btnEliminar.addActionListener(e -> {
            eliminarProducto();
        });

        btnNuevo.addActionListener(e -> {
            limpiarCampos();
            idProductoSeleccionado = -1;
            cbProductosExistentes.setSelectedIndex(0);
        });
    }

    private void cargarProductos() {
        ProductoRepository repo = new ProductoRepository();
        List<Producto> lista = repo.listar();
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

    private void cargarNombres() {
        cbProductosExistentes.removeAllItems();
        cbProductosExistentes.addItem("-- Nuevo Producto --");

        ProductoService service = new ProductoServiceImpl();
        for (String nombre : service.listarNombres()) {
            cbProductosExistentes.addItem(nombre);
        }
    }

    private void cargarProductoSeleccionado() {
        if (cbProductosExistentes.getSelectedIndex() <= 0) {
            limpiarCampos();
            idProductoSeleccionado = -1;
            return;
        }

        ProductoService service = new ProductoServiceImpl();
        Producto p = service.buscarPorNombre(
                cbProductosExistentes.getSelectedItem().toString()
        );

        if (p != null) {
            idProductoSeleccionado = p.getId();
            txtNombre.setText(p.getNombre());
            txtPrecio.setText(String.valueOf(p.getPrecio()));
            txtStock.setText(String.valueOf(p.getStock()));
            cbCategoria.setSelectedItem(p.getCategoria());
        }
    }

    private void guardarProducto() {
        try {
            String nombre = txtNombre.getText().trim();
            String categoria = cbCategoria.getSelectedItem().toString();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese nombre");
                return;
            }

            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Producto producto = new Producto(nombre, categoria, precio, stock);
            ProductoService service = new ProductoServiceImpl();
            service.registrarProducto(producto);

            cargarProductos();
            cargarNombres(); 
            limpiarCampos();
            
            if (cbProductosExistentes.getItemCount() > 0) {
                cbProductosExistentes.setSelectedIndex(0);
            }

            JOptionPane.showMessageDialog(this, "Producto guardado correctamente");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio o stock invalidos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void actualizarProducto() {
        if (idProductoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }

        try {
            Producto p = new Producto();
            p.setId(idProductoSeleccionado);
            p.setNombre(txtNombre.getText().trim());
            p.setCategoria(cbCategoria.getSelectedItem().toString());
            p.setPrecio(Double.parseDouble(txtPrecio.getText()));
            p.setStock(Integer.parseInt(txtStock.getText()));

            ProductoService service = new ProductoServiceImpl();
            service.actualizarProducto(p);

            JOptionPane.showMessageDialog(this, "Producto actualizado");

            cargarProductos();
            cargarNombres();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio o stock inválidos");
        }
    }

    private void eliminarProducto() {
        if (idProductoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar producto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            ProductoService service = new ProductoServiceImpl();
            service.eliminarProducto(idProductoSeleccionado);

            JOptionPane.showMessageDialog(this, "Producto eliminado");

            limpiarCampos();
            cargarProductos();
            cargarNombres();
            cbProductosExistentes.setSelectedIndex(0);
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