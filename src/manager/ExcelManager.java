/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.FuncionesGenerales;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Miguel Ángel
 */
public class ExcelManager {
    
    // Guardar la ruta del fichero generado
    private File fileOutput;
    
    // Pasarela para poder escribir en el archivo nuevo
    private FileOutputStream fileOutputStream;
    
    // Nos referiremos al archivo
    private XSSFWorkbook workbook;
    
    // Referencia a la hoja 1 del Excel
    private XSSFSheet hojaContribuyentes;
    
    // Referencia a la hoja 2 del Excel
    private XSSFSheet hojaOrdenanzas;
    
    // Ultima fila con datos de contribuyentes
    private int ultimaFilaContribuyentes;
    
    // Ultima fila con datos de ordenanzas
    private int ultimaFilaOrdenanzas;
    
    // Funciones generales
    private FuncionesGenerales fg;
    
    
    /**
     * Constructor que lee y crea un nuevo fichero
     * @param fileName, fichero a leer
     */
    public ExcelManager(File fileName) {
        fg = new FuncionesGenerales();
        // Ruta de salida
        fileOutput = new File(Constantes.RUTA_ARCHIVO_ESCRIBIR);
        
        // Generar nuevo archivo
        try {
            // Ruta del archivo a leer
            FileInputStream fileInput = new FileInputStream(fileName);
            // Guardamos la referencia del archivo
            workbook = new XSSFWorkbook(fileInput);
            // Ruta del archivo a crear
            fileOutputStream = new FileOutputStream(fileOutput);
            // Guardo todo en el archivo nuevo
            workbook.write(fileOutputStream);
            
            fileOutputStream.close();
            fileInput.close();
        } catch (IOException e) {
            System.out.println("No se ha podido leer el Excel completo");
            System.out.println(e.toString());
        }
        
        // Guardamos hoja 1
        hojaContribuyentes = workbook.getSheetAt(Constantes.HOJA_CONTRIBUYENTES);
        // Guardamos hoja 2
        hojaOrdenanzas = workbook.getSheetAt(Constantes.HOJA_ORDENANZAS); 
    }
    
    private boolean agregarCambiosArchivo() {
        boolean result = false;
        // Escribo los cambios realizados en el libro
        try {
            fileOutputStream = new FileOutputStream(fileOutput);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Libro guardado correctamente");
            result = true;
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("No se ha podido actualizar o guardar el libro correctamente");
        } 
        return result;
    }
    
    public HashMap obtenerContribuyentes() {
        HashMap contribuyentes = new HashMap();
        for(int i = 1; i <= hojaContribuyentes.getLastRowNum(); i++) {
            XSSFRow fila = hojaContribuyentes.getRow(i);
            if(fila != null) {
                // No puede no tener nombre
                if(fila.getCell(0) != null && !fg.comprobarCadenaSoloEspacios(fila.getCell(0).toString())) {
                    // Datos por columnas
                    String nombre = (fila.getCell(0) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(0).toString())) ? fila.getCell(0).toString() : "";
                    String apellido1 = (fila.getCell(1) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(1).toString())) ? fila.getCell(1).toString() : "";
                    String apellido2 = (fila.getCell(2) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(2).toString())) ? fila.getCell(2).toString() : "";
                    String nifnie = (fila.getCell(3) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(3).toString())) ? fila.getCell(3).toString() : "";
                    String direccion = (fila.getCell(4) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(4).toString())) ? fila.getCell(4).toString() : "";
                    String numero = (fila.getCell(5) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(5).toString())) ? fila.getCell(5).toString() : "";
                    String paisCCC = (fila.getCell(6) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(6).toString())) ? fila.getCell(6).toString() : "";
                    String ccc = (fila.getCell(7) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(7).toString())) ? fila.getCell(7).toString() : "";
                    String iban = (fila.getCell(8) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(8).toString())) ? fila.getCell(8).toString() : "";
                    String email = (fila.getCell(9) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(9).toString())) ? fila.getCell(9).toString() : "";
                    String exencion = (fila.getCell(10) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(10).toString())) ? fila.getCell(10).toString() : "";
                    double bonificacion = (fila.getCell(11) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(11).toString())) ? Double.parseDouble(fila.getCell(11).toString()) : 0;
                    int lecturaAnterior = (fila.getCell(12) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(12).toString())) ? (int) Double.parseDouble(fila.getCell(12).toString()) : 0;
                    int lecturaActual = (fila.getCell(13) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(13).toString())) ? (int) Double.parseDouble(fila.getCell(13).toString()) : 0;      
                    String fechaAlta = (fila.getCell(14) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(14).toString())) ? fila.getCell(14).toString() : "";
                    String fechaBaja = (fila.getCell(15) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(15).toString())) ? fila.getCell(15).toString() : "";
                    String conceptosACobrar = (fila.getCell(16) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(16).toString())) ? fila.getCell(16).toString() : "";   

                    // Se crea el objeto con todos los datos
                    ContribuyenteExcel contribuyenteActual = new ContribuyenteExcel(nombre, apellido1, apellido2, nifnie, direccion, numero, paisCCC, ccc, iban, email, exencion, bonificacion, lecturaAnterior, lecturaActual, fechaAlta, fechaBaja, conceptosACobrar);
                    // Se agrega al HasMap
                    contribuyentes.put(Integer.toString(i), contribuyenteActual);
                    // Se actualiza la ultima fila
                    setUltimaFilaContribuyentes(i);
                }
            }
        }
        return contribuyentes;
    }
    
    public HashMap obtenerOrdenanzas() {
        HashMap ordenanzas = new HashMap();
        for(int i = 1; i <= hojaOrdenanzas.getLastRowNum(); i++) {
            XSSFRow fila = hojaOrdenanzas.getRow(i);
            if(fila != null) {
                // No puede no tener nombre
                if(fila.getCell(0) != null && !fg.comprobarCadenaSoloEspacios(fila.getCell(0).toString())) {
                    // Datos por columnas
                    String pueblo = (fila.getCell(0) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(0).toString())) ? fila.getCell(0).toString() : "";
                    String tipoCalculo = (fila.getCell(1) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(1).toString())) ? fila.getCell(1).toString() : "";
                    int idOrdenanza = (fila.getCell(2) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(2).toString())) ? (int) Double.parseDouble(fila.getCell(2).toString()) : 0;
                    String concepto = (fila.getCell(3) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(3).toString())) ? fila.getCell(3).toString() : "";
                    String subconcepto = (fila.getCell(4) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(4).toString())) ? fila.getCell(4).toString() : "";
                    String descripcion = (fila.getCell(5) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(5).toString())) ? fila.getCell(5).toString() : "";
                    String acumulable = (fila.getCell(6) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(6).toString())) ? fila.getCell(6).toString() : "";
                    int precioFijo = (fila.getCell(7) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(7).toString())) ? (int) Double.parseDouble(fila.getCell(7).toString()) : 0;
                    int m3incluidos = (fila.getCell(8) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(8).toString())) ? (int) Double.parseDouble(fila.getCell(8).toString()) : 0;
                    double preciom3 = (fila.getCell(9) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(9).toString())) ? Double.parseDouble(fila.getCell(9).toString()) : 0;
                    double porcentajeSobreOtroConcepto = (fila.getCell(10) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(10).toString())) ? Double.parseDouble(fila.getCell(10).toString()) : 0;
                    int sobreQueConcepto = (fila.getCell(11) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(11).toString())) ? (int) Double.parseDouble(fila.getCell(11).toString()) : 0;
                    double iva = (fila.getCell(12) != null && 
                            !fg.comprobarCadenaSoloEspacios(fila.getCell(12).toString())) ? Double.parseDouble(fila.getCell(12).toString()) : 0;

                    // Se crea el objeto con todos los datos
                    OrdenanzaExcel ordenanzaActual = new OrdenanzaExcel(pueblo, tipoCalculo, idOrdenanza, concepto, subconcepto, descripcion, acumulable, precioFijo, m3incluidos, preciom3, porcentajeSobreOtroConcepto, sobreQueConcepto, iva);
                    // Se agrega al HasMap
                    ordenanzas.put(Integer.toString(i), ordenanzaActual);
                    // Se actualiza la ultima fila
                    setUltimaFilaOrdenanzas(i);
                }
            }
        }
        return ordenanzas;
    }
    
    /**
     * Modifica el DNI en la hoja de Excel
     * @param documento, DNI corregido que se actualiza en el Excel
     * @param fila en el que ha de cambiar el DNI
     * @return true si ha sido modificado, false si no se ha podidod modificar
     */
    public boolean modificarDniNieHoja(String documento, int fila) {
        boolean result = false;        
        // Realizo los cambios
        XSSFRow hssFRow = (XSSFRow) hojaContribuyentes.getRow(fila);
        if(hssFRow.getCell(3) != null) {
            if(!fg.comprobarCadenaSoloEspacios(hssFRow.getCell(3).toString())) {
                hssFRow.getCell(3).setCellValue(documento);
            }
        }            
        // Escribo los cambios realizados en el libro
        result = agregarCambiosArchivo();
        return result;
    }
    
    public boolean modificarCCCHoja(String ccc, int fila) {
        boolean result = false;
        // Realizo los cambios
        XSSFRow hssFRow = (XSSFRow) hojaContribuyentes.getRow(fila);
        if(hssFRow.getCell(7) != null) {
            if(!fg.comprobarCadenaSoloEspacios(hssFRow.getCell(7).toString())) {
                hssFRow.getCell(7).setCellValue(ccc);
            }
        }  
        // Escribo los cambios realizados en el libro
        result = agregarCambiosArchivo();
        return result;
    }
    
    public boolean agregarIBANHoja(String iban, int fila) {
        boolean result = false;
        // Realizo los cambios
        XSSFRow hssFRow = (XSSFRow) hojaContribuyentes.getRow(fila);
        if(hssFRow.getCell(8) == null) {
            hssFRow.createCell(8).setCellValue(iban);
        } else {
            hssFRow.getCell(8).setCellValue(iban);
        }
        // Escribo los cambios realizados en el libro
        result = agregarCambiosArchivo();
        return result;
    }

    public boolean agregarEmailHoja(String email, int fila) {
        boolean result = false;
        // Realizo los cambios
        XSSFRow hssFRow = (XSSFRow) hojaContribuyentes.getRow(fila);
        if(hssFRow.getCell(9) == null) {
            hssFRow.createCell(9).setCellValue(email);
        } else {
            // Si solo tiene espacios en blanco se modifica, si ya tiene correo no
            if(fg.comprobarCadenaSoloEspacios(hssFRow.getCell(9).toString())) {
                hssFRow.getCell(9).setCellValue(email);
            }
        }
        // Escribo los cambios realizados en el libro
        result = agregarCambiosArchivo();
        return result;
    }
    
    public int getUltimaFilaContribuyentes() {
        return ultimaFilaContribuyentes;
    }

    public void setUltimaFilaContribuyentes(int ultimaFilaContribuyentes) {
        this.ultimaFilaContribuyentes = ultimaFilaContribuyentes;
    }

    public int getUltimaFilaOrdenanzas() {
        return ultimaFilaOrdenanzas;
    }

    public void setUltimaFilaOrdenanzas(int ultimaFilaOrdenanzas) {
        this.ultimaFilaOrdenanzas = ultimaFilaOrdenanzas;
    }
    
    
}
