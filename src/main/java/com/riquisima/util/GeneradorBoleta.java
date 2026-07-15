package com.riquisima.util;

import java.time.LocalDateTime;

public class GeneradorBoleta {

    public static void generarBoleta(String producto,
            int cantidad,
            double total) {

        System.out.println("========== BOLETA ==========");
        System.out.println("Bodega Riquisima");
        System.out.println("Fecha: " + LocalDateTime.now());
        System.out.println("Producto: " + producto);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Total: S/ " + total);
        System.out.println("============================");
    }
}