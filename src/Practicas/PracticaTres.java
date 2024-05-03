/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;

import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.FuncionesGenerales;
import java.io.File;
import java.util.HashMap;
import manager.ExcelManager;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;

/**
 *
 * @author Miguel Ángel
 */
public class PracticaTres {
    
    //private PdfManager pdfManager;
    
    private ExcelManager excelManager;
    
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    
    private HashMap<String, OrdenanzaExcel> ordenanzas;
    
    public PracticaTres() {
        excelManager = new ExcelManager(new File(Constantes.RUTA_ARCHIVO_LEER));
        
        contribuyentes = excelManager.obtenerContribuyentes();
        
        ordenanzas = excelManager.obtenerOrdenanzas();
    }
    
    public void ejecuccion() {
        /*for(int i = 1; i <= excelManager.getUltimaFilaContribuyentes(); i++) {
            // Comprobar documento
            if(contribuyentes.containsKey(String.valueOf(i))) {
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                
                /*for(int i = 0; i < ; i++) {
                    
                }
            }
        }*/
        
        FuncionesGenerales fg = new FuncionesGenerales();
        
        
        ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(7));
        // Conceptos a cobrar
        String[] conceptos = c.getConceptosACobrar().split(" ");
        // Obtengo el consumo que hay entre lecturas
        int consumo = Integer.valueOf(c.getLecturaActual().replace(".0", "")) - Integer.valueOf(c.getLecturaAnterior().replace(".0", ""));
        
        
        // Datos finales
        double baseImponibleTotal = 0.0;
        double importeIvaTotal = 0.0;

        // Recorro todos los conceptos que tiene el recibo
        for(int i = 0; i < conceptos.length; i++) {
            // Obtengo el concepto a calcular
            String concepto = conceptos[i].replace(" ", "");
            // Recorro todos los conceptos que hay en el Excel
            
            
            
            
            
            
            
            //LLamar a un metodo por cada concepto que recorra
            
            for(int j = 1; j <= excelManager.getUltimaFilaOrdenanzas(); j++) {
                
                // Obtengo la ordenanza actual
                OrdenanzaExcel o = (OrdenanzaExcel) ordenanzas.get(String.valueOf(j));
                
                int idOrdenanza = 0;
                if(!o.getIdOrdenanza().isEmpty()) {
                    idOrdenanza = Integer.parseInt(o.getIdOrdenanza().replace(".0", ""));
                }
                
                // Si el concepto coincide con el id de la ordenanza, se añade al recibo
                if(Integer.parseInt(concepto) == idOrdenanza) {
                
                    

                    // Preparar datos a usar
                    
                    int precioFijo = 0;
                    if(!o.getPrecioFijo().isEmpty()) {
                        precioFijo = Integer.parseInt(o.getPrecioFijo().replace(".0", ""));
                    }
                    int m3incluidos = 0;
                    if(!o.getM3incluidos().isEmpty()) {
                        m3incluidos = Integer.parseInt(o.getM3incluidos().replace(".0", ""));
                    }
                    double preciom3 = 0;
                    if(!o.getPreciom3().isEmpty()) {
                        preciom3 = Double.parseDouble(o.getPreciom3());
                    }
                    double porcentajeSobreOtrosConceptos = 0;
                    if(!o.getPorcentajeSobreOtroConcepto().isEmpty()) {
                         porcentajeSobreOtrosConceptos = Double.parseDouble(o.getPorcentajeSobreOtroConcepto());
                    }
                    double iva = 0;
                    if(!o.getIva().isEmpty()) {
                         iva = Double.parseDouble(o.getIva());
                    }



                    // Datos por cada concepto
                    double baseImponible = 0;
                    double importeIva = 0;

                
                    System.out.println("consumo " + consumo);
                
                
                    
                    int consumoTramo = 0;
                    // Tiene precio fijo
                    if(precioFijo > 0) {
                        baseImponible = precioFijo;
                        consumoTramo = m3incluidos;
                    } else {
                        // Comprobamos los tramos
                        if(consumo > 0) {
                            
                            // Si el consumo es menor que lo que te inclye, nos quedamos con el consumo, no con todo el tramo
                            if(m3incluidos > consumo) {
                                consumoTramo = consumo;
                            } else {
                                consumoTramo = m3incluidos;
                            }
                            // Se calcula la base imponible
                            baseImponible = consumoTramo * preciom3;
                            
                        }
                    }
                    
                    if(porcentajeSobreOtrosConceptos > 0) {
                        String sobreQueConcepto = o.getSobreQueConcepto().replaceAll(" ", "");
                        
                    }
                    
                    // Se actualiza el consumo calculado
                    consumo = consumo - consumoTramo;
                    
                    // Realizar calculo del iva: base imponible x iva %
                    importeIva = baseImponible * iva / 100;

                    // Actualizamos sumatorios
                    baseImponibleTotal += baseImponible;
                    importeIvaTotal += importeIva;
                    
                    
                    // Agregar fila pdf
                    System.out.println(o.getConcepto() + " " + o.getSubconcepto() + " " + consumoTramo + " " +
                            baseImponible + " " + o.getIva() + " " + importeIva);
                    
                }
                
                
            }
        }
        
        System.out.println("Importe iva : " + importeIvaTotal + " Importe base: " + baseImponibleTotal);
    }
    
    
    public void calculaBaseImponibleConcepto(String concepto) {
        
    }
    
    public boolean aplicarOtroPorcentajeSobreEsteConcepto() {
        return true;
    }
    
    public void buscarIdOrdenanzaSobreElQueSeAplicaOtroConcepto() {
        
    }
}
