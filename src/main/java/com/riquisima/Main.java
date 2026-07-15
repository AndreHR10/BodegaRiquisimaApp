package com.riquisima;

import com.formdev.flatlaf.FlatLightLaf;
import com.riquisima.view.MenuPrincipal;
import com.riquisima.view.LoginForm;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new LoginForm();

        });
    }
}