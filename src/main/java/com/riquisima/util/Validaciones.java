package com.riquisima.util;

public class Validaciones {

    public static boolean textoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean precioValido(double precio) {
        return precio > 0;
    }

    public static boolean stockValido(int stock) {
        return stock >= 0;
    }
}