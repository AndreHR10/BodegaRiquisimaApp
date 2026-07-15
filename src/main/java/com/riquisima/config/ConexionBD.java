package com.riquisima.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/bodega_riquisima";

    private static final String USER = "postgres";

    private static final String PASSWORD = "AndreHilario10";

    private static Connection conexion;

    public static Connection getConexion() {

        try {

            if (conexion == null || conexion.isClosed()) {

                conexion = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

                System.out.println("Conexion exitosa");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return conexion;
    }
}