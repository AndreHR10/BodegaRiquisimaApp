package com.riquisima.repository;

import com.riquisima.config.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioRepository {

    public boolean validarLogin(
            String usuario,
            String password
    ) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE usuario = ?
                AND password = ?
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, usuario);

            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}