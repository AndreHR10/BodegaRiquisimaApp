package com.riquisima.repository;

import com.riquisima.config.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VentaRepository {

    public int guardarVenta(double total) {

        String sql = """
                INSERT INTO ventas(total)
                VALUES(?)
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );

            ps.setDouble(1, total);

            ps.executeUpdate();

            ResultSet rs =
                    ps.getGeneratedKeys();

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}