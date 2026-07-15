package com.riquisima.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GeneradorBoleta {

    public void generarBoleta(DefaultTableModel modelo,
                              String metodoPago,
                              double total) {

        PDDocument documento = new PDDocument();

        try {

            PDPage pagina = new PDPage();

            documento.addPage(pagina);

            PDPageContentStream contenido =
                    new PDPageContentStream(documento, pagina);

            PDType1Font fuente =
                    new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            PDType1Font fuenteNegrita =
                    new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            contenido.beginText();

            contenido.setFont(fuenteNegrita, 16);

            contenido.newLineAtOffset(60, 760);

            contenido.showText("BODEGA RIQUISIMA");

            contenido.endText();

            contenido.beginText();

            contenido.setFont(fuente, 11);

            contenido.newLineAtOffset(60, 740);

            contenido.showText("RUC: 20123456789");

            contenido.newLineAtOffset(0, -15);

            contenido.showText("Direccion: Av. Principal 123");

            contenido.newLineAtOffset(0, -15);

            contenido.showText("Telefono: 987654321");

            contenido.newLineAtOffset(0, -15);

            contenido.showText("------------------------------------------------");

            contenido.newLineAtOffset(0, -20);

            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            contenido.showText(
                    "Fecha: "
                    + LocalDateTime.now().format(formato));

            contenido.newLineAtOffset(0, -20);

            contenido.showText(
                    "Metodo de pago: "
                    + metodoPago);

            contenido.newLineAtOffset(0, -20);

            contenido.showText(
                    "------------------------------------------------");

            contenido.endText();

            float y = 610;

            for (int i = 0; i < modelo.getRowCount(); i++) {

                String producto =
                        modelo.getValueAt(i, 1).toString();

                String cantidad =
                        modelo.getValueAt(i, 2).toString();

                String precio =
                        modelo.getValueAt(i, 3).toString();

                String subtotal =
                        modelo.getValueAt(i, 4).toString();

                contenido.beginText();

                contenido.setFont(fuente, 10);

                contenido.newLineAtOffset(60, y);

                contenido.showText(producto);

                contenido.newLineAtOffset(0, -15);

                contenido.showText(
                        cantidad
                        + " x S/ "
                        + precio
                        + " = S/ "
                        + subtotal);

                contenido.endText();

                y -= 35;
            }

            contenido.beginText();

            contenido.setFont(fuenteNegrita, 14);

            contenido.newLineAtOffset(60, y - 10);

            contenido.showText(
                    "TOTAL: S/ "
                    + String.format("%.2f", total));

            contenido.newLineAtOffset(0, -30);

            contenido.showText("Gracias por su compra.");

            contenido.endText();

            contenido.close();

            File carpeta = new File("Boletas");

            if (!carpeta.exists()) {

                carpeta.mkdir();
            }

            String nombreArchivo =
                    "Boletas/Boleta_"
                    + System.currentTimeMillis()
                    + ".pdf";

            documento.save(nombreArchivo);

            documento.close();

            Desktop.getDesktop().open(
                    new File(nombreArchivo));

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}