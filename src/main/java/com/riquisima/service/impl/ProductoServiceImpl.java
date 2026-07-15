package com.riquisima.service.impl;

import com.riquisima.model.Producto;
import com.riquisima.repository.ProductoRepository;
import com.riquisima.service.ProductoService;

public class ProductoServiceImpl
        implements ProductoService {

    private final ProductoRepository repository;

    public ProductoServiceImpl() {

        repository = new ProductoRepository();
    }

    @Override
    public void registrarProducto(
            Producto producto
    ) {

        if (producto.getNombre().isEmpty()) {

            throw new IllegalArgumentException(
                    "Nombre obligatorio"
            );
        }

        if (producto.getPrecio() <= 0) {

            throw new IllegalArgumentException(
                    "Precio invalido"
            );
        }

        if (producto.getStock() < 0) {

            throw new IllegalArgumentException(
                    "Stock invalido"
            );
        }

        repository.guardar(producto);
    }
}
