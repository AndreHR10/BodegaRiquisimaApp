package com.riquisima.facade;

import com.riquisima.factory.MetodoPago;
import com.riquisima.factory.PagoFactory;

public class VentaFacade {

    public void realizarVenta(double total, String metodo) {

        MetodoPago pago = PagoFactory.crearPago(metodo);

        pago.pagar(total);

        System.out.println("Venta realizada correctamente");
    }
}