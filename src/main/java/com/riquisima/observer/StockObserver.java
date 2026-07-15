package com.riquisima.observer;

public class StockObserver implements Observer {

    @Override
    public void actualizar(String mensaje) {
        System.out.println("ALERTA: " + mensaje);
    }
}