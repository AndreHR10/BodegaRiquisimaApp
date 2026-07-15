package com.riquisima.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.riquisima.config.ConexionBD;
import com.riquisima.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductoRepository {

    public List<Producto> listar() {

        List<Producto> lista
                = new ArrayList<>();

        String sql
                = "SELECT * FROM productos";

        try {

            Connection con
                    = ConexionBD.getConexion();

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ResultSet rs
                    = ps.executeQuery();

            while (rs.next()) {

                Producto p
                        = new Producto();

                p.setId(rs.getInt("id"));

                p.setNombre(
                        rs.getString("nombre")
                );

                p.setCategoria(
                        rs.getString("categoria")
                );

                p.setPrecio(
                        rs.getDouble("precio")
                );

                p.setStock(
                        rs.getInt("stock")
                );

                lista.add(p);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    public void guardar(Producto producto) {

        String sqlBuscar
                = """
            SELECT id, stock
            FROM productos
            WHERE LOWER(nombre) = LOWER(?)
            """;

        String sqlInsert
                = """
            INSERT INTO productos(
                nombre,
                categoria,
                precio,
                stock
            )
            VALUES(?,?,?,?)
            """;

        String sqlUpdate
                = """
            UPDATE productos
            SET stock = stock + ?,
                precio = ?,
                categoria = ?
            WHERE id = ?
            """;

        try {

            Connection con
                    = ConexionBD.getConexion();

            // BUSCAR PRODUCTO
            PreparedStatement psBuscar
                    = con.prepareStatement(sqlBuscar);

            psBuscar.setString(
                    1,
                    producto.getNombre()
            );

            ResultSet rs
                    = psBuscar.executeQuery();

            // SI EXISTE
            if (rs.next()) {

                int id
                        = rs.getInt("id");

                PreparedStatement psUpdate
                        = con.prepareStatement(sqlUpdate);

                psUpdate.setInt(
                        1,
                        producto.getStock()
                );

                psUpdate.setDouble(
                        2,
                        producto.getPrecio()
                );

                psUpdate.setString(
                        3,
                        producto.getCategoria()
                );

                psUpdate.setInt(4, id);

                psUpdate.executeUpdate();

                System.out.println(
                        "Stock actualizado"
                );

            } else {

                // INSERTAR NUEVO
                PreparedStatement psInsert
                        = con.prepareStatement(sqlInsert);

                psInsert.setString(
                        1,
                        producto.getNombre()
                );

                psInsert.setString(
                        2,
                        producto.getCategoria()
                );

                psInsert.setDouble(
                        3,
                        producto.getPrecio()
                );

                psInsert.setInt(
                        4,
                        producto.getStock()
                );

                psInsert.executeUpdate();

                System.out.println(
                        "Producto guardado"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public boolean actualizarStock(
            int productoId,
            int cantidad
    ) {

        String sqlBuscar
                = "SELECT stock FROM productos WHERE id = ?";

        String sqlActualizar
                = """
            UPDATE productos
            SET stock = stock - ?
            WHERE id = ?
            """;

        try {

            Connection con
                    = ConexionBD.getConexion();

            // BUSCAR STOCK ACTUAL
            PreparedStatement psBuscar
                    = con.prepareStatement(sqlBuscar);

            psBuscar.setInt(1, productoId);

            ResultSet rs
                    = psBuscar.executeQuery();

            if (rs.next()) {

                int stockActual
                        = rs.getInt("stock");

                // VALIDAR STOCK
                if (cantidad > stockActual) {

                    return false;
                }

                // ACTUALIZAR
                PreparedStatement psActualizar
                        = con.prepareStatement(sqlActualizar);

                psActualizar.setInt(1, cantidad);

                psActualizar.setInt(2, productoId);

                psActualizar.executeUpdate();

                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public Producto buscarPorId(int id) {

        String sql
                = "SELECT * FROM productos WHERE id = ?";

        try {

            Connection con
                    = ConexionBD.getConexion();

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs
                    = ps.executeQuery();

            if (rs.next()) {

                Producto p
                        = new Producto();

                p.setId(rs.getInt("id"));

                p.setNombre(
                        rs.getString("nombre")
                );

                p.setCategoria(
                        rs.getString("categoria")
                );

                p.setPrecio(
                        rs.getDouble("precio")
                );

                p.setStock(
                        rs.getInt("stock")
                );

                return p;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}
