package com.riquisima.factory;

import com.riquisima.adapter.PlinAdapter;
import com.riquisima.adapter.YapeAdapter;

public class PagoFactory {

    public static MetodoPago crearPago(String tipo) {

        switch (tipo.toUpperCase()) {

            case "EFECTIVO":
                return new PagoEfectivo();

            case "TARJETA":
                return new PagoTarjeta();

            case "YAPE":
                return new YapeAdapter();

            case "PLIN":
                return new PlinAdapter();

            default:
                throw new IllegalArgumentException(
                        "Metodo de pago invalido"
                );
        }
    }
}