package com.riquisima.service;

import com.riquisima.model.Producto;

import java.util.List;

public interface ProductoService {

    void registrarProducto(Producto producto);

    List<Producto> listarProductos();

    Producto buscarPorId(int id);

    Producto buscarPorNombre(String nombre);

    List<String> listarNombres();

    void actualizarProducto(Producto producto);

    void eliminarProducto(int id);

    List<Producto> productosStockBajo();

    int contarProductos();
}