package com.riquisima.adapter;

import com.riquisima.factory.MetodoPago;

public class PlinAdapter implements MetodoPago {

    private final PlinAPI plinAPI;

    public PlinAdapter() {
        plinAPI = new PlinAPI();
    }

    @Override
    public void pagar(double monto) {

        plinAPI.enviarPago(monto);
    }
}