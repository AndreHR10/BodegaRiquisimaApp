package com.riquisima.repository;

import com.riquisima.model.Pago;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.riquisima.config.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PagoRepository {

    public List<Pago> listarPagos() {

    List<Pago> lista =
            new ArrayList<>();

    String sql =
            "SELECT * FROM pagos ORDER BY id DESC";

    try {

        Connection con =
                ConexionBD.getConexion();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ResultSet rs =
                ps.executeQuery();

        while (rs.next()) {

            Pago pago =
                    new Pago();

            pago.setId(
                    rs.getInt("id")
            );

            pago.setMetodo(
                    rs.getString("metodo")
            );

            pago.setMonto(
                    rs.getDouble("monto")
            );

            pago.setFecha(
                    rs.getTimestamp("fecha")
            );

            lista.add(pago);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return lista;
}
    public void guardarPago(
            String metodo,
            double monto
    ) {

        String sql = """
                INSERT INTO pagos(
                    metodo,
                    monto
                )
                VALUES(?,?)
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, metodo);

            ps.setDouble(2, monto);

            ps.executeUpdate();

            System.out.println(
                    "Pago procesado"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}