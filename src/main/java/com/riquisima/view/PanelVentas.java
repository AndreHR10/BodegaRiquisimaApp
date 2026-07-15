package com.riquisima.view;

import com.riquisima.model.Producto;
import com.riquisima.repository.PagoRepository;
import com.riquisima.repository.ProductoRepository;
import com.riquisima.repository.VentaRepository;
import com.riquisima.util.GeneradorBoleta;
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

public class PanelVentas extends JPanel {

    private JTable tabla;

    private DefaultTableModel modelo;

    private JComboBox<Producto> cbProductos;

    private JComboBox<String> cbMetodoPago;

    private JTextField txtCantidad;

    private JLabel lblTotal;

    private JButton btnAgregar;

    private JButton btnVender;

    public PanelVentas() {

        iniciarComponentes();

        cargarProductos();
    }

    private void iniciarComponentes() {

        setLayout(null);

        setBackground(Color.WHITE);

        JLabel titulo
                = new JLabel("MODULO DE VENTAS");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setBounds(350, 20, 400, 30);

        add(titulo);

        JLabel lblProducto
                = new JLabel("Producto:");

        lblProducto.setBounds(80, 70, 100, 30);

        add(lblProducto);

        cbProductos
                = new JComboBox<>();

        cbProductos.setBounds(160, 70, 250, 30);

        add(cbProductos);

        JLabel lblCantidad
                = new JLabel("Cantidad:");

        lblCantidad.setBounds(450, 70, 100, 30);

        add(lblCantidad);

        txtCantidad
                = new JTextField();

        txtCantidad.setBounds(530, 70, 100, 30);

        add(txtCantidad);

        modelo
                = new DefaultTableModel();

        modelo.addColumn("ID");

        modelo.addColumn("Producto");

        modelo.addColumn("Cantidad");

        modelo.addColumn("Precio");

        modelo.addColumn("Subtotal");

        tabla
                = new JTable(modelo);

        JScrollPane scroll
                = new JScrollPane(tabla);

        scroll.setBounds(80, 130, 700, 350);

        add(scroll);

        btnAgregar
                = new JButton("Agregar");

        btnAgregar.setBounds(850, 140, 180, 40);

        add(btnAgregar);

        JLabel lblMetodoPago
                = new JLabel("Metodo Pago:");

        lblMetodoPago.setBounds(
                850,
                220,
                120,
                30
        );

        add(lblMetodoPago);

        cbMetodoPago
                = new JComboBox<>();

        cbMetodoPago.addItem("EFECTIVO");
        cbMetodoPago.addItem("YAPE");
        cbMetodoPago.addItem("PLIN");
        cbMetodoPago.addItem("TARJETA");

        cbMetodoPago.setBounds(
                850,
                250,
                180,
                35
        );

        add(cbMetodoPago);

        btnVender
                = new JButton("Realizar Venta");

        btnVender.setBounds(
                850,
                320,
                180,
                45
        );

        add(btnVender);

        lblTotal
                = new JLabel("TOTAL: S/ 0.00");

        lblTotal.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        lblTotal.setBounds(
                820,
                400,
                250,
                40
        );

        add(lblTotal);

        btnAgregar.addActionListener(e -> {

            agregarProducto();
        });

        btnVender.addActionListener(e -> {

            realizarVenta();
        });
    }

    private void cargarProductos() {

        ProductoRepository repo
                = new ProductoRepository();

        List<Producto> lista
                = repo.listar();

        cbProductos.removeAllItems();

        for (Producto p : lista) {

            cbProductos.addItem(p);
        }
    }

    private void agregarProducto() {

        try {

            Producto producto
                    = (Producto) cbProductos
                            .getSelectedItem();

            if (producto == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione un producto"
                );

                return;
            }

            int cantidad
                    = Integer.parseInt(
                            txtCantidad.getText()
                    );

            if (cantidad <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cantidad invalida"
                );

                return;
            }

            if (cantidad > producto.getStock()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Stock insuficiente"
                );

                return;
            }

            double subtotal
                    = producto.getPrecio() * cantidad;

            modelo.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                cantidad,
                producto.getPrecio(),
                subtotal
            });

            calcularTotal();

            txtCantidad.setText("");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al agregar producto"
            );
        }
    }

    private void calcularTotal() {

        double total = 0;

        for (int i = 0;
                i < modelo.getRowCount();
                i++) {

            total += Double.parseDouble(
                    modelo.getValueAt(i, 4)
                            .toString()
            );
        }

        lblTotal.setText(
                "TOTAL: S/ " + total
        );
    }

    private void realizarVenta() {

        try {

            if (modelo.getRowCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No hay productos en la venta"
                );

                return;
            }

            double total = 0;

            for (int i = 0;
                    i < modelo.getRowCount();
                    i++) {

                total += Double.parseDouble(
                        modelo.getValueAt(i, 4)
                                .toString()
                );
            }

            String metodoPago
                    = cbMetodoPago
                            .getSelectedItem()
                            .toString();

            VentaRepository ventaRepo
                    = new VentaRepository();

            int ventaId
                    = ventaRepo.guardarVenta(total);

            GeneradorBoleta boleta
                    = new GeneradorBoleta();

            boleta.generarBoleta(
                    modelo,
                    metodoPago,
                    total
            );
            ProductoRepository productoRepo
                    = new ProductoRepository();

            for (int i = 0;
                    i < modelo.getRowCount();
                    i++) {

                int productoId
                        = Integer.parseInt(
                                modelo.getValueAt(i, 0)
                                        .toString()
                        );

                int cantidad
                        = Integer.parseInt(
                                modelo.getValueAt(i, 2)
                                        .toString()
                        );

                boolean actualizado
                        = productoRepo.actualizarStock(
                                productoId,
                                cantidad
                        );

                if (!actualizado) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Stock insuficiente"
                    );

                    return;
                }
            }

            PagoRepository pagoRepo
                    = new PagoRepository();

            pagoRepo.guardarPago(
                    metodoPago,
                    total
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Venta realizada correctamente"
            );

            modelo.setRowCount(0);

            lblTotal.setText(
                    "TOTAL: S/ 0.00"
            );

            cargarProductos();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al realizar venta"
            );
        }
    }
}
