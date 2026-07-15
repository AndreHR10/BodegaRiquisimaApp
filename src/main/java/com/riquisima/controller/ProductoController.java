package com.riquisima.controller;

import com.riquisima.model.Producto;
import com.riquisima.service.ProductoService;
import com.riquisima.service.impl.ProductoServiceImpl;

public class ProductoController {

    private final ProductoService service;

    public ProductoController() {
        service = new ProductoServiceImpl();
    }

    public void registrarProducto(String nombre, String categoria,
            double precio, int stock) {

        Producto producto = new Producto();

        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setStock(stock);

        service.registrarProducto(producto);
    }
}