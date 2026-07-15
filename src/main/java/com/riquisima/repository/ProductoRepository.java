package com.riquisima.repository;

import com.riquisima.config.ConexionBD;
import com.riquisima.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {



    public List<Producto> listar() {

        List<Producto> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM productos
                ORDER BY nombre
                """;

        try {

            Connection con = ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Producto p = new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));

                lista.add(p);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }


    public Producto buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM productos
                WHERE id = ?
                """;

        try {

            Connection con = ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                Producto p = new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));

                return p;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    public Producto buscarPorNombre(String nombre) {

        String sql = """
                SELECT *
                FROM productos
                WHERE LOWER(nombre)=LOWER(?)
                """;

        try {

            Connection con = ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, nombre);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                Producto p = new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));

                return p;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    public List<String> listarNombres() {

        List<String> nombres =
                new ArrayList<>();

        String sql = """
                SELECT nombre
                FROM productos
                ORDER BY nombre
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                nombres.add(
                        rs.getString("nombre")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return nombres;
    }

    public List<Producto> productosStockBajo() {

        List<Producto> lista =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM productos
                WHERE stock <= 5
                ORDER BY stock ASC
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Producto p =
                        new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));

                lista.add(p);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }



    public void guardar(Producto producto) {

        Producto existente =
                buscarPorNombre(producto.getNombre());


        if (existente != null) {

            aumentarStock(
                    existente.getId(),
                    producto.getStock()
            );

            return;
        }

        String sql = """
                INSERT INTO productos(
                    nombre,
                    categoria,
                    precio,
                    stock
                )
                VALUES(?,?,?,?)
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, producto.getNombre());

            ps.setString(2, producto.getCategoria());

            ps.setDouble(3, producto.getPrecio());

            ps.setInt(4, producto.getStock());

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public boolean actualizarStock(
            int productoId,
            int cantidad) {

        Producto producto =
                buscarPorId(productoId);

        if (producto == null) {

            return false;
        }

        if (cantidad > producto.getStock()) {

            return false;
        }

        String sql = """
                UPDATE productos
                SET stock = stock - ?
                WHERE id = ?
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, cantidad);

            ps.setInt(2, productoId);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public void aumentarStock(
            int id,
            int cantidad) {

        String sql = """
                UPDATE productos
                SET stock = stock + ?
                WHERE id = ?
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, cantidad);

            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void actualizarProducto(
            Producto producto) {

        String sql = """
                UPDATE productos
                SET nombre = ?,
                    categoria = ?,
                    precio = ?,
                    stock = ?
                WHERE id = ?
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, producto.getNombre());

            ps.setString(2, producto.getCategoria());

            ps.setDouble(3, producto.getPrecio());

            ps.setInt(4, producto.getStock());

            ps.setInt(5, producto.getId());

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void eliminar(int id) {

        String sql = """
                DELETE FROM productos
                WHERE id = ?
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public int contarProductos() {

        String sql = """
                SELECT COUNT(*)
                FROM productos
                """;

        try {

            Connection con =
                    ConexionBD.getConexion();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

}