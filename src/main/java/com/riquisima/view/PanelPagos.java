package com.riquisima.view;

import com.riquisima.model.Pago;
import com.riquisima.repository.PagoRepository;

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

public class PanelPagos extends JPanel {

    private JTable tabla;

    private DefaultTableModel modelo;

    private JLabel lblTotalPagos;

    private JButton btnActualizar;

    public PanelPagos() {

        iniciarComponentes();

        cargarPagos();
    }

    private void iniciarComponentes() {

        setLayout(null);

        setBackground(Color.WHITE);

        // TITULO

        JLabel titulo =
                new JLabel("REGISTRO DE PAGOS");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titulo.setBounds(
                320,
                20,
                400,
                30
        );

        add(titulo);

        // TABLA

        modelo =
                new DefaultTableModel();

        modelo.addColumn("ID");

        modelo.addColumn("Metodo");

        modelo.addColumn("Monto");

        modelo.addColumn("Fecha");

        tabla =
                new JTable(modelo);

        JScrollPane scroll =
                new JScrollPane(tabla);

        scroll.setBounds(
                120,
                100,
                800,
                320
        );

        add(scroll);

        // TOTAL PAGOS

        lblTotalPagos =
                new JLabel("TOTAL PAGOS: S/ 0.00");

        lblTotalPagos.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        lblTotalPagos.setBounds(
                120,
                450,
                300,
                40
        );

        add(lblTotalPagos);

        // BOTON ACTUALIZAR

        btnActualizar =
                new JButton("Actualizar");

        btnActualizar.setBounds(
                760,
                450,
                160,
                40
        );

        add(btnActualizar);

        // EVENTO

        btnActualizar.addActionListener(e -> {

            cargarPagos();

            JOptionPane.showMessageDialog(
                    this,
                    "Pagos actualizados"
            );
        });
    }

    // CARGAR PAGOS DESDE POSTGRESQL

    private void cargarPagos() {

        try {

            PagoRepository repo =
                    new PagoRepository();

            List<Pago> lista =
                    repo.listarPagos();

            // LIMPIAR TABLA

            modelo.setRowCount(0);

            double totalPagos = 0;

            // RECORRER PAGOS

            for (Pago p : lista) {

                modelo.addRow(new Object[]{

                    p.getId(),
                    p.getMetodo(),
                    p.getMonto(),
                    p.getFecha()
                });

                totalPagos += p.getMonto();
            }

            // MOSTRAR TOTAL

            lblTotalPagos.setText(
                    "TOTAL PAGOS: S/ " + totalPagos
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar pagos"
            );
        }
    }
}