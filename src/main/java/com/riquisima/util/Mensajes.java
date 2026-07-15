package com.riquisima.util;

import javax.swing.JOptionPane;

public class Mensajes {

    public static void mostrarMensaje(String mensaje) {

        JOptionPane.showMessageDialog(null, mensaje);
    }

    public static void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(
                null,
                mensaje,
                "ERROR",
                JOptionPane.ERROR_MESSAGE
        );
    }
}