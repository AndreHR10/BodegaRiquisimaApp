package com.riquisima.command;

public class RegistrarVentaCommand implements Command {

    @Override
    public void ejecutar() {
        System.out.println("Venta registrada");
    }
}