package com.riquisima.service.impl;

import com.riquisima.model.Producto;
import com.riquisima.repository.ProductoRepository;
import com.riquisima.service.ProductoService;

import java.util.List;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;

    public ProductoServiceImpl() {

        repository = new ProductoRepository();
    }

    @Override
    public void registrarProducto(Producto producto) {

        if (producto == null) {

            throw new IllegalArgumentException(
                    "Producto inválido"
            );
        }

        if (producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Ingrese el nombre del producto"
            );
        }

        if (producto.getPrecio() <= 0) {

            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero"
            );
        }

        if (producto.getStock() < 0) {

            throw new IllegalArgumentException(
                    "El stock no puede ser negativo"
            );
        }

        repository.guardar(producto);
    }


    @Override
    public List<Producto> listarProductos() {

        return repository.listar();
    }


    @Override
    public Producto buscarPorId(int id) {

        return repository.buscarPorId(id);
    }


    @Override
    public Producto buscarPorNombre(String nombre) {

        return repository.buscarPorNombre(nombre);
    }


    @Override
    public List<String> listarNombres() {

        return repository.listarNombres();
    }


    @Override
    public void actualizarProducto(Producto producto) {

        if (producto == null) {

            throw new IllegalArgumentException(
                    "Producto inválido"
            );
        }

        repository.actualizarProducto(producto);
    }


    @Override
    public void eliminarProducto(int id) {

        repository.eliminar(id);
    }


    @Override
    public List<Producto> productosStockBajo() {

        return repository.productosStockBajo();
    }


    @Override
    public int contarProductos() {

        return repository.contarProductos();
    }

}