package com.riquisima.factory;

public class PagoYape implements MetodoPago {

    @Override
    public void pagar(double monto) {
        System.out.println("Pago con Yape: S/ " + monto);
    }
}