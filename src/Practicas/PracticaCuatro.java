/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;

import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.Fecha;
import funcionesAuxiliares.FuncionDNI;
import funcionesAuxiliares.FuncionesRecibo;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import manager.ExcelManager;
import manager.PdfManager;
import modelo.LineasReciboModelo;
import modeloExcel.ContribuyenteExcel;

/**
 *
 * @author Miguel Ángel
 */
public class PracticaCuatro {
    
    private ExcelManager excelManager;
    
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    
    public PracticaCuatro() {
        excelManager = new ExcelManager(new File(Constantes.RUTA_ARCHIVO_ESCRIBIR));
        
        contribuyentes = excelManager.obtenerContribuyentes();
    }
    
    
    public void ejecuccion(String fechaPadron) {
        
        FuncionDNI fDNI = new FuncionDNI();
        FuncionesRecibo funcionesRecibo = new FuncionesRecibo();
        
        double totalBaseImponibleRecibos = 0;
        double totalIvaRecibos = 0;
        double totalRecibos = 0;
        
        int idRecibo = 1257;
        
        for(int i = 1; i <= excelManager.getUltimaFilaContribuyentes(); i++) {
            // Comprobar documento
            if(contribuyentes.containsKey(String.valueOf(i))) {
    
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                

                if(fDNI.validadorNIF_NIE(c.getNifnie()) == 0) {
                    Fecha f = new Fecha();
                    f = f.transformarFechaExcel(c.getFechaAlta());
                    // Verificamos si la fecha alta es anterior a la actual
                    if(!f.comprobarFechaPosteriorAFechaActual(f)) {
                        
                        // Verifa si la fecha solicitada es posterior a la fecha alta
                        if(f.compruebaFechaPosteriorFechaAlta(fechaPadron)) {
                            List<LineasReciboModelo> lineasRecibo = funcionesRecibo.generarLineasRecibo(c);

                            double totalBaseImponible = funcionesRecibo.calcularTotalBaseImponible(lineasRecibo);
                            double totalIva = funcionesRecibo.calcularTotalImporteIva(lineasRecibo);
                            double totalRecibo = totalBaseImponible + totalIva;

                            int consumo = (int) funcionesRecibo.calcularTotalConsumo(lineasRecibo);
                            int lecturaActual = c.getLecturaActual();
                            int lecturaAnterior = c.getLecturaAnterior();

                            // Comprobamos si el contribuyente esta exento de pagar el recibo
                            if(!c.getExencion().isEmpty()) {
                                if(c.getExencion().equalsIgnoreCase("S")) {
                                    totalBaseImponible = 0;
                                    totalIva = 0;
                                    totalRecibo = 0;
                                }
                            } 


                            // Crer PDF
                            PdfManager pdf = new PdfManager();

                            pdf.crearPdf(c.getNifnie(), c.getNombre(), c.getApellido1(), c.getApellido2());
                            pdf.generarPDF(c, lineasRecibo, consumo, "Astorga", "Hogar", "Primer trimestre de 2023", totalBaseImponible, totalIva, totalRecibo);

                            totalBaseImponibleRecibos += totalBaseImponible;
                            totalIvaRecibos += totalIva;
                            totalRecibos += totalRecibo;

                            // Guardar bd
                            
                            
                            
                        }
                    }
                }
            }
        }
        
        // Crear pdf resumen
        PdfManager pdf = new PdfManager();
        pdf.crearPdfResumen();
        pdf.generarPdfResumen(totalBaseImponibleRecibos, totalIvaRecibos, totalRecibos);
    }
}
