package com.riquisima.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaUtil {

    public static String obtenerFechaActual() {

        LocalDateTime fecha = LocalDateTime.now();

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return fecha.format(formato);
    }
}