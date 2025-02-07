package com.github.jcapitanmoreno.utils;

import com.github.jcapitanmoreno.entities.Habito;
import com.github.jcapitanmoreno.entities.Huella;
import com.github.jcapitanmoreno.entities.Recomendacion;
import com.github.jcapitanmoreno.entities.Usuario;
import com.github.jcapitanmoreno.services.HabitoService;
import com.github.jcapitanmoreno.services.HuellaService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


public class InformeUtils {
    private HabitoService habitoService;
    private HuellaService huellaService;

    public InformeUtils() {
        habitoService = new HabitoService();
        huellaService = new HuellaService();
    }

    /**
     * Genera un informe en formato PDF o CSV para un usuario específico.
     *
     * @param usuario el usuario para el cual se generará el informe.
     * @param stage el escenario de la aplicación para mostrar el diálogo de guardado.
     * @throws IOException si ocurre un error al generar el informe.
     */
    public void generarInforme(Usuario usuario, Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Informe");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            String extension = getFileExtension(file);
            if ("pdf".equalsIgnoreCase(extension)) {
                generarInformePDF(usuario, file);
            } else if ("csv".equalsIgnoreCase(extension)) {
                generarInformeCSV(usuario, file);
            } else {
                throw new IOException("Formato de archivo no soportado");
            }
        }
    }

    /**
     * Genera un informe en formato PDF para un usuario específico.
     *
     * @param usuario el usuario para el cual se generará el informe.
     * @param file el archivo donde se guardará el informe.
     * @throws IOException si ocurre un error al generar el informe.
     */
    private void generarInformePDF(Usuario usuario, File file) throws IOException {
        List<Habito> habitos = habitoService.getHabitosByUsuario(usuario.getId());
        List<Object[]> huellasConImpacto = huellaService.getHuellasConImpactoByUsuario(usuario.getId());
        List<Recomendacion> recomendaciones = habitoService.getRecomendacionesByUsuario(usuario.getId());

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Informe del Usuario"));
        document.add(new Paragraph("==================="));
        document.add(new Paragraph("Nombre de Usuario: " + usuario.getNombre()));
        document.add(new Paragraph("Correo Electrónico: " + usuario.getEmail()));
        document.add(new Paragraph("\nHábitos:"));

        Table habitosTable = new Table(3);
        habitosTable.addCell(new Cell().add(new Paragraph("Tipo")));
        habitosTable.addCell(new Cell().add(new Paragraph("Frecuencia")));
        habitosTable.addCell(new Cell().add(new Paragraph("Última Fecha")));
        for (Habito habito : habitos) {
            habitosTable.addCell(new Cell().add(new Paragraph(habito.getTipo())));
            habitosTable.addCell(new Cell().add(new Paragraph(habito.getFrecuencia().toString())));
            habitosTable.addCell(new Cell().add(new Paragraph(habito.getUltimaFecha().toString())));
        }
        document.add(habitosTable);

        document.add(new Paragraph("\nHuellas:"));
        Table huellasTable = new Table(4);
        huellasTable.addCell(new Cell().add(new Paragraph("Actividad")));
        huellasTable.addCell(new Cell().add(new Paragraph("Valor")));
        huellasTable.addCell(new Cell().add(new Paragraph("Unidad")));
        huellasTable.addCell(new Cell().add(new Paragraph("Impacto de Huella de Carbono")));
        for (Object[] huella : huellasConImpacto) {
            huellasTable.addCell(new Cell().add(new Paragraph((String) huella[0])));
            huellasTable.addCell(new Cell().add(new Paragraph(huella[1].toString())));
            huellasTable.addCell(new Cell().add(new Paragraph((String) huella[2])));
            huellasTable.addCell(new Cell().add(new Paragraph(huella[3].toString())));
        }
        document.add(huellasTable);

        document.add(new Paragraph("\nRecomendaciones:"));
        for (Recomendacion recomendacion : recomendaciones) {
            document.add(new Paragraph("- " + recomendacion.getDescripcion()));
        }

        document.close();
    }

    /**
     * Genera un informe en formato CSV para un usuario específico.
     *
     * @param usuario el usuario para el cual se generará el informe.
     * @param file el archivo donde se guardará el informe.
     * @throws IOException si ocurre un error al generar el informe.
     */
    private void generarInformeCSV(Usuario usuario, File file) throws IOException {
        List<Habito> habitos = habitoService.getHabitosByUsuario(usuario.getId());
        List<Huella> huellas = huellaService.getHuellasByUsuario(usuario.getId());
        List<Recomendacion> recomendaciones = habitoService.getRecomendacionesByUsuario(usuario.getId());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Nombre de Usuario," + usuario.getNombre() + "\n");
            writer.write("Correo Electrónico," + usuario.getEmail() + "\n\n");

            writer.write("Hábitos:\n");
            for (Habito habito : habitos) {
                writer.write(habito.getTipo() + "," + habito.getFrecuencia() + "," + habito.getUltimaFecha() + "\n");
            }

            writer.write("\nHuellas:\n");
            for (Huella huella : huellas) {
                BigDecimal impacto = huella.getValor().multiply(huella.getIdActividad().getIdCategoria().getFactorEmision());
                writer.write(huella.getIdActividad().getNombre() + "," + huella.getValor() + "," + huella.getUnidad() + "," + impacto + "\n");
            }

            writer.write("\nRecomendaciones:\n");
            for (Recomendacion recomendacion : recomendaciones) {
                writer.write(recomendacion.getDescripcion() + "\n");
            }
        }
    }

    /**
     * Obtiene la extensión de un archivo.
     *
     * @param file el archivo del cual se obtendrá la extensión.
     * @return la extensión del archivo.
     */
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
