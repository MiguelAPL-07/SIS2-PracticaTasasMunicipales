/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.Fecha;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.LineasReciboModelo;
import modeloExcel.ContribuyenteExcel;


/**
 *
 * @author Miguel Ángel
 */
public class PdfManager {
    
    // Variable para crear un documento PDF
    private PdfWriter writer;
    // Permite que podamos escribir y manipular el documento PDF
    private PdfDocument pdfDoc;
    // Documento sobre el que se va a escribir
    private Document doc;
    // Carpeta donde se van a guardar todos los recibos 
    private File file;
    
    // Sirve para dar formato de salida
    private DecimalFormat decimalFormat;
    
    
    public PdfManager() {
        decimalFormat = new DecimalFormat("00.00");
    }
    
    public void crearPdfResumen(String fechaPadron) {
        try {
            // Le pasamos la ruta generica donde se va a guardar el PDF
            writer = new PdfWriter(Constantes.RUTA_GENERICA_RECIBO_PDF + "\\" + fechaPadron + "\\resumen.pdf");
            // Asiganmos a pdfDoc el writer que podemos manipular
            pdfDoc = new PdfDocument(writer);
            // Inicializamos el docuemnto sobre el que guardamos todo
            doc = new Document(pdfDoc, PageSize.LETTER);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }
    
    public void generarPdfResumen(String fechaRecibo, double totalBaseImponible, double totalIva, double totalRecibos) {
        // Se crea la tabla con un tres espacios
        Table tabla = new Table(3);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se da borde a la tabla
        tabla.setBorder(new SolidBorder(1));
        
        Cell celda = new Cell();
        celda.add(new Paragraph("RESUMEN PADRON DE AGUA " + fechaRecibo));
        celda.add(new Paragraph("TOTAL BASE IMPONIBLE.........................." + String.format("%.2f", totalBaseImponible)));
        celda.add(new Paragraph("TOTAL IVA....................................." + String.format("%.2f", totalIva)));
        celda.add(new Paragraph("TOTAL RECIBOS................................." + String.format("%.2f", totalRecibos)));
        
        celda.setWidth(500);
        celda.setBorder(Border.NO_BORDER);
        tabla.addCell(celda);

        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(tabla);
        doc.close();
    }
    
    public void crearPdf(String fechaPadron, String dni, String nombre, String apellido1, String apellido2) {
        try {
            // Le pasamos la ruta generica donde se va a guardar el PDF
            writer = new PdfWriter(Constantes.RUTA_GENERICA_RECIBO_PDF + "\\" + fechaPadron + "\\" + dni + nombre + apellido1 + apellido2 + ".pdf");
            // Asiganmos a pdfDoc el writer que podemos manipular
            pdfDoc = new PdfDocument(writer);
            // Inicializamos el docuemnto sobre el que guardamos todo
            doc = new Document(pdfDoc, PageSize.LETTER);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }
    
    public void generarPDF(ContribuyenteExcel contribuyente, List<LineasReciboModelo> lineasRecibo, 
            int consumo, String pueblo, String tipoCalculo,
            String fechaRecibo, double totalBaseImponible, double totalIva, double totalRecibo) {

        // Pasar objeto completo o pasar datos concretos
        
        // Se agregan los datos de la empresa
        doc.add(agregarCabeceraDatosEmpresa(pueblo, contribuyente.getIban(), tipoCalculo, contribuyente.getFechaAlta()));
        // Se agrega linea en blanco
        doc.add(agregarEspacioEnBlanco(15));
        // Se agregan los datos del contribuyente
        doc.add(agregarCabeceraDatosContribuyente(contribuyente.getNombre(), contribuyente.getApellido1(), contribuyente.getApellido2(), contribuyente.getNifnie(), contribuyente.getDireccion(), contribuyente.getNumero(), pueblo));
        // Se agrega linea en blanco
        doc.add(agregarEspacioEnBlanco(25));
        // Se agrega la parte de las lecturas
        doc.add(agregarParteLecturas(contribuyente.getLecturaActual(), contribuyente.getLecturaAnterior(), consumo));
        // Se agrega linea en blanco
        doc.add(agregarEspacioEnBlanco(25));
        // Se agrega la fecha del recibo
        doc.add(agregarFechaRecibo(fechaRecibo));
        // Se agrega linea en blanco
        doc.add(agregarEspacioEnBlanco(25));
        // Se agrega una linea
        doc.add(agregarLinea());
        
        if(contribuyente.getBonificacion() > 0) {
            // Se agrega la barra superior de las lineas del recibo
            doc.add(agregarBarraSuperiorLineasRecibosConDescuento());
            // Se agrega una linea
            doc.add(agregarLinea());
            doc.add(agregarEspacioEnBlanco(10));
            // Tiene descuento
            List<Table> tablas = agregarTablaLineasReciboConDescuento(lineasRecibo);
            for(Table t : tablas) {
                doc.add(t);
            }
        } else {
            // Se agrega la barra superior de las lineas del recibo
            doc.add(agregarBarraSuperiorLineasRecibos());
            // Se agrega una linea
            doc.add(agregarLinea());
            doc.add(agregarEspacioEnBlanco(10));
            // Se agregan las lineas del recibo
            // No tiene descuento
            List<Table> tablas = agregarTablaLineasRecibo(lineasRecibo);
            for(Table t : tablas) {
                doc.add(t);
            }
        }
        doc.add(agregarEspacioEnBlanco(10));
        // Se agrega una linea
        doc.add(agregarLinea());
        // Se agregan los totales
        doc.add(agregarTotales(totalBaseImponible, totalIva));
        // Se agrega linea en blanco
        doc.add(agregarEspacioEnBlanco(25));
        // Se agrega total base imponible
        doc.add(agregarTotalBaseImponible(totalBaseImponible));
        // Se agrega total iva
        doc.add(agregarTotalIva(totalIva));
        // Se agrega linea en blanco
        doc.add(agregarEspacioEnBlanco(25));
        // Se agrega una linea
        doc.add(agregarLinea());
        // Se agrega total recibo
        doc.add(agregarTotalRecibo(totalRecibo));
        
        doc.close();
    }
    
    private Table agregarCabeceraDatosEmpresa(String pueblo, String iban, String tipoCalculo, String fechaAlta) {
        
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(2);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.setBorder(new SolidBorder(1));
        celda1.setWidth(250);
        celda1.setTextAlignment(TextAlignment.CENTER);
        
        celda1.setPadding(10);

        celda1.add(new Paragraph(pueblo));
        celda1.add(new Paragraph("P24001017F"));
        celda1.add(new Paragraph("Calle de la Iglesia, 13"));
        celda1.add(new Paragraph("24280 Astorga León"));
        tabla.addCell(celda1);

        Cell celda2 = new Cell();
        celda2.setBorder(Border.NO_BORDER);
        celda2.setTextAlignment(TextAlignment.RIGHT);
        celda2.setPadding(10);
        
        // Se cambia el formato de fecha
        Fecha f = new Fecha();
        f = f.transformarFechaExcel(fechaAlta);
        
        celda2.add(new Paragraph("IBAN: " + iban));
        celda2.add(new Paragraph("Tipo de cálculo: " + tipoCalculo));
        celda2.add(new Paragraph("Fecha de alta: " + f.toString()));
        tabla.addCell(celda2);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        return tabla;
    }
    
    private Table agregarCabeceraDatosContribuyente(String nombre, String apellido1, String apellido2, String dni, String direccion, String numero, String pueblo) {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(2);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        try {
            
            Image img = new Image(ImageDataFactory.create(Constantes.IMAGEN_GENERICA_PDF));
            img.setBorder(Border.NO_BORDER);
            img.setPadding(10);
            
            Cell celda1 = new Cell();
            celda1.add(img);
            celda1.setBorder(Border.NO_BORDER);
            celda1.setPaddingLeft(23);
            celda1.setPaddingTop(20);
            celda1.setWidth(250);
            tabla.addCell(celda1);
            
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        
        Cell celda2 = new Cell();
        celda2.setBorder(new SolidBorder(1));
        celda2.setWidth(250);
        celda2.setTextAlignment(TextAlignment.RIGHT);

        celda2.add(new Paragraph("Destinatario:").setTextAlignment(TextAlignment.LEFT).setBold());
        celda2.add(new Paragraph(nombre + " " + apellido1 + " " + apellido2));
        celda2.add(new Paragraph("DNI: " + dni));
        celda2.add(new Paragraph(direccion + " " + numero));
        celda2.add(new Paragraph(pueblo));
        tabla.addCell(celda2);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        return tabla;
    }
    
    private Table agregarParteLecturas(int lecturaActual, int lecturaAnterior, int consumo) {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(3);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se da borde a la tabla
        tabla.setBorder(new SolidBorder(1));
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("Lectura actual: " + lecturaActual));
        celda1.setWidth(166);
        celda1.setBorder(Border.NO_BORDER);
        tabla.addCell(celda1);
        
        Cell celda2 = new Cell();
        celda2.add(new Paragraph("Lectura anterior: " + lecturaAnterior));
        celda2.setWidth(166);
        celda2.setBorder(Border.NO_BORDER);
        tabla.addCell(celda2);
        
        Cell celda3 = new Cell();
        celda3.add(new Paragraph("Consumo: " + consumo + " metros cúbicos."));
        celda3.setWidth(168);
        celda3.setBorder(Border.NO_BORDER);
        tabla.addCell(celda3);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        return tabla;
    }
    
    private Table agregarFechaRecibo(String fechaPadron) {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(1);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("Recibo agua: " + fechaPadron));
        celda1.setWidth(500);
        celda1.setTextAlignment(TextAlignment.CENTER);
        celda1.setBorder(Border.NO_BORDER);
        // Hacer que sea escrito en negrita
        celda1.setBold();
        // Hacer que sea escrito un poco girado
        celda1.setItalic();
        tabla.addCell(celda1);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        
        return tabla;
    }
    
    private Table agregarBarraSuperiorLineasRecibos() {
        // Se crea la tabla con 6 columnas
        Table tabla = new Table(6);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("Concepto"));
        celda1.setWidth(83);
        celda1.setBorder(Border.NO_BORDER);
        tabla.addCell(celda1);
        
        Cell celda2 = new Cell();
        celda2.add(new Paragraph("Subconcepto"));
        celda2.setWidth(83);
        celda2.setBorder(Border.NO_BORDER);
        tabla.addCell(celda2);
        
        Cell celda3 = new Cell();
        celda3.add(new Paragraph("M3 incluidos"));
        celda3.setWidth(83);
        celda3.setBorder(Border.NO_BORDER);
        tabla.addCell(celda3);
        
        Cell celda4 = new Cell();
        celda4.add(new Paragraph("Base Imponible"));
        celda4.setWidth(83);
        celda4.setBorder(Border.NO_BORDER);
        tabla.addCell(celda4);
        
        Cell celda5 = new Cell();
        celda5.add(new Paragraph("Porcentaje IVA"));
        celda5.setWidth(83);
        celda5.setBorder(Border.NO_BORDER);
        tabla.addCell(celda5);
        
        Cell celda6 = new Cell();
        celda6.add(new Paragraph("Importe IVA"));
        celda6.setWidth(85);
        celda6.setBorder(Border.NO_BORDER);
        tabla.addCell(celda6);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        tabla.setFontSize(12);
        tabla.setTextAlignment(TextAlignment.CENTER);
                
        return tabla;
    }
    
    private Table agregarBarraSuperiorLineasRecibosConDescuento() {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(7);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("Concepto"));
        celda1.setWidth(71);
        celda1.setBorder(Border.NO_BORDER);
        tabla.addCell(celda1);
        
        Cell celda2 = new Cell();
        celda2.add(new Paragraph("Subconcepto"));
        celda2.setWidth(71);
        celda2.setBorder(Border.NO_BORDER);
        tabla.addCell(celda2);
        
        Cell celda3 = new Cell();
        celda3.add(new Paragraph("M3 incluidos"));
        celda3.setWidth(71);
        celda3.setBorder(Border.NO_BORDER);
        tabla.addCell(celda3);
        
        Cell celda4 = new Cell();
        celda4.add(new Paragraph("B. Imponible"));
        celda4.setWidth(71);
        celda4.setBorder(Border.NO_BORDER);
        tabla.addCell(celda4);
        
        Cell celda5 = new Cell();
        celda5.add(new Paragraph("IVA %"));
        celda5.setWidth(71);
        celda5.setBorder(Border.NO_BORDER);
        tabla.addCell(celda5);
        
        Cell celda6 = new Cell();
        celda6.add(new Paragraph("Importe"));
        celda6.setWidth(71);
        celda6.setBorder(Border.NO_BORDER);
        tabla.addCell(celda6);
        
        Cell celda7 = new Cell();
        celda7.add(new Paragraph("Descuento"));
        celda7.setWidth(74);
        celda7.setBorder(Border.NO_BORDER);
        tabla.addCell(celda7);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        tabla.setBorder(Border.NO_BORDER);
        
        return tabla;
    }
    
    private List<Table> agregarTablaLineasRecibo(List<LineasReciboModelo> lineasRecibo) {
        List<Table> tablas = new ArrayList<>();
        for(LineasReciboModelo lActual : lineasRecibo) {

            Table tabla = new Table(6);
            // Se da tamaño a la tabla
            tabla.setWidth(500);
            // Se quita el borde a la tabla
            tabla.setBorder(Border.NO_BORDER);

            Cell celda1 = new Cell();
            celda1.add(new Paragraph(lActual.getConcepto()));
            celda1.setWidth(83);
            celda1.setBorder(Border.NO_BORDER);
            tabla.addCell(celda1);

            Cell celda2 = new Cell();
            celda2.add(new Paragraph(lActual.getSubconcepto()));
            celda2.setWidth(83);
            celda2.setBorder(Border.NO_BORDER);
            tabla.addCell(celda2);

            Cell celda3 = new Cell();
            celda3.add(new Paragraph(decimalFormat.format(lActual.getM3incluidos())));
            celda3.setWidth(83);
            celda3.setBorder(Border.NO_BORDER);
            tabla.addCell(celda3);

            Cell celda4 = new Cell();
            celda4.add(new Paragraph(decimalFormat.format(lActual.getBaseImponible())));
            celda4.setWidth(83);
            celda4.setBorder(Border.NO_BORDER);
            tabla.addCell(celda4);

            Cell celda5 = new Cell();
            celda5.add(new Paragraph(decimalFormat.format(lActual.getPorcentajeIva()) + "%"));
            celda5.setWidth(83);
            celda5.setBorder(Border.NO_BORDER);
            tabla.addCell(celda5);

            Cell celda6 = new Cell();
            celda6.add(new Paragraph(decimalFormat.format(lActual.getImporteIva())));
            celda6.setWidth(85);
            celda6.setBorder(Border.NO_BORDER);
            tabla.addCell(celda6);

            tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tabla.setFontSize(12);
            tabla.setTextAlignment(TextAlignment.CENTER);
            tabla.setFontSize(8);
            
            tablas.add(tabla);
        }       
        return tablas;
    }
    
    private List<Table> agregarTablaLineasReciboConDescuento(List<LineasReciboModelo> lineasRecibo) {
        List<Table> tablas = new ArrayList<>();
        for(LineasReciboModelo lActual : lineasRecibo) {

            Table tabla = new Table(7);
            // Se da tamaño a la tabla
            tabla.setWidth(500);
            // Se quita el borde a la tabla
            tabla.setBorder(Border.NO_BORDER);

            Cell celda1 = new Cell();
            celda1.add(new Paragraph(lActual.getConcepto()));
            celda1.setWidth(71);
            celda1.setBorder(Border.NO_BORDER);
            tabla.addCell(celda1);

            Cell celda2 = new Cell();
            celda2.add(new Paragraph(lActual.getSubconcepto()));
            celda2.setWidth(71);
            celda2.setBorder(Border.NO_BORDER);
            tabla.addCell(celda2);

            Cell celda3 = new Cell();
            celda3.add(new Paragraph(decimalFormat.format(lActual.getM3incluidos())));
            celda3.setWidth(71);
            celda3.setBorder(Border.NO_BORDER);
            tabla.addCell(celda3);

            Cell celda4 = new Cell();
            celda4.add(new Paragraph(decimalFormat.format(lActual.getBaseImponible())));
            celda4.setWidth(71);
            celda4.setBorder(Border.NO_BORDER);
            tabla.addCell(celda4);

            Cell celda5 = new Cell();
            celda5.add(new Paragraph(decimalFormat.format(lActual.getPorcentajeIva()) + "%"));
            celda5.setWidth(71);
            celda5.setBorder(Border.NO_BORDER);
            tabla.addCell(celda5);
            
            Cell celda6 = new Cell();
            celda6.add(new Paragraph(decimalFormat.format(lActual.getImporteIva())));
            celda6.setWidth(71);
            celda6.setBorder(Border.NO_BORDER);
            tabla.addCell(celda6);
            
            Cell celda7 = new Cell();
            celda7.add(new Paragraph(decimalFormat.format(lActual.getBonificacion())));
            celda7.setWidth(74);
            celda7.setBorder(Border.NO_BORDER);
            tabla.addCell(celda7);

            tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tabla.setTextAlignment(TextAlignment.CENTER);
            tabla.setFontSize(8);

            tablas.add(tabla);
        }       
        return tablas;
    }
    
    private Table agregarTotales(double totalBaseImponible, double totalIva) {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(3);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("TOTALES"));
        celda1.setWidth(249);
        celda1.setBorder(Border.NO_BORDER);
        celda1.setTextAlignment(TextAlignment.CENTER);
        tabla.addCell(celda1);
        
        Cell celda2 = new Cell();
        celda2.add(new Paragraph(decimalFormat.format(totalBaseImponible)));
        celda2.setWidth(83);
        celda2.setBorder(Border.NO_BORDER);
        celda2.setTextAlignment(TextAlignment.RIGHT);
        tabla.addCell(celda2);
        
        Cell celda3 = new Cell();
        celda3.add(new Paragraph(decimalFormat.format(totalIva)));
        celda3.setWidth(168);
        celda3.setBorder(Border.NO_BORDER);
        celda3.setTextAlignment(TextAlignment.RIGHT);
        tabla.addCell(celda3);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        tabla.setBorder(Border.NO_BORDER);
        tabla.setFontSize(8);
        
        return tabla;
    }
    
    private Table agregarTotalBaseImponible(double totalBaseImponible) {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(2);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("TOTAL BASE IMPONIBLE.........................."));
        celda1.setWidth(250);
        celda1.setBorder(Border.NO_BORDER);
        celda1.setTextAlignment(TextAlignment.LEFT);
        tabla.addCell(celda1);
        
        Cell celda2 = new Cell();
        celda2.add(new Paragraph(decimalFormat.format(totalBaseImponible)));
        celda2.setWidth(250);
        celda2.setBorder(Border.NO_BORDER);
        celda2.setTextAlignment(TextAlignment.RIGHT);
        tabla.addCell(celda2);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        tabla.setBorder(Border.NO_BORDER);
        tabla.setFontSize(8);
        
        return tabla;
    }
    
    private Table agregarTotalIva(double totalIva) {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(2);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("TOTAL IVA....................................."));
        celda1.setWidth(250);
        celda1.setBorder(Border.NO_BORDER);
        celda1.setTextAlignment(TextAlignment.LEFT);
        tabla.addCell(celda1);
        
        Cell celda2 = new Cell();
        celda2.add(new Paragraph(decimalFormat.format(totalIva)));
        celda2.setWidth(250);
        celda2.setBorder(Border.NO_BORDER);
        celda2.setTextAlignment(TextAlignment.RIGHT);
        tabla.addCell(celda2);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        tabla.setBorder(Border.NO_BORDER);
        tabla.setFontSize(8);
        
        return tabla;
    }
    
    private Table agregarTotalRecibo(double totalRecibo) {
        // Se crea la tabla con un unico espacio
        Table tabla = new Table(2);
        // Se da tamaño a la tabla
        tabla.setWidth(500);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda1 = new Cell();
        celda1.add(new Paragraph("TOTAL RECIBO.................................."));
        celda1.setWidth(250);
        celda1.setBorder(Border.NO_BORDER);
        celda1.setTextAlignment(TextAlignment.LEFT);
        tabla.addCell(celda1);
        
        
        
        Cell celda2 = new Cell();
        celda2.add(new Paragraph(decimalFormat.format(totalRecibo)));
        celda2.setWidth(250);
        celda2.setBorder(Border.NO_BORDER);
        celda2.setTextAlignment(TextAlignment.RIGHT);
        tabla.addCell(celda2);
        
        tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
        tabla.setBorder(Border.NO_BORDER);
        tabla.setFontSize(8);
        
        return tabla;
    }
    
    private LineSeparator agregarLinea() {
        SolidLine line = new SolidLine(2);
        line.setColor(ColorConstants.BLACK);
        LineSeparator ls = new LineSeparator(line);
        ls.setMarginTop(5);
        ls.setMarginBottom(5);
        ls.setWidth(500);
        ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return ls;
    }
    
    private Table agregarEspacioEnBlanco(int largo) {
        Table tabla = new Table(1);
        // Se da tamaño a la tabla de ancho
        tabla.setWidth(500);
        // Se da tamaño a la tabla de largo
        tabla.setHeight(largo);
        // Se quita el borde a la tabla
        tabla.setBorder(Border.NO_BORDER);
        
        Cell celda = new Cell();
        celda.add(new Paragraph(""));
        celda.setWidth(500);
        celda.setBorder(Border.NO_BORDER);
        tabla.addCell(celda);
        return tabla;
    }
}
