package com.riquisima.adapter;

import com.riquisima.factory.MetodoPago;

public class YapeAdapter implements MetodoPago {

    private final YapeAPI yapeAPI;

    public YapeAdapter() {
        yapeAPI = new YapeAPI();
    }

    @Override
    public void pagar(double monto) {

        yapeAPI.realizarTransferencia(monto);
    }
}