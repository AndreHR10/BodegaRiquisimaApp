package com.riquisima.factory;

public class PagoEfectivo implements MetodoPago {

    @Override
    public void pagar(double monto) {
        System.out.println("Pago en efectivo: S/ " + monto);
    }
}