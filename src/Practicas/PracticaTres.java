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
import manager.XmlManager;
import modelo.LineasReciboModelo;
import modeloExcel.ContribuyenteExcel;

/**
 *
 * @author Miguel Ángel
 */
public class PracticaTres {
    
    private ExcelManager excelManager;
    
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    
    public PracticaTres() {
        excelManager = new ExcelManager(new File(Constantes.RUTA_ARCHIVO_ESCRIBIR));
        
        contribuyentes = excelManager.obtenerContribuyentes();
    }
    
    public void ejecuccion(String fechaPadron) {
        
        FuncionDNI fDNI = new FuncionDNI();
        FuncionesRecibo funcionesRecibo = new FuncionesRecibo();
        
        XmlManager xmlManager = new XmlManager();
        xmlManager.iniciarlizarDocumentoRecibos();
        
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

                            double baseImponible = funcionesRecibo.calcularTotalBaseImponible(lineasRecibo);
                            double ivaRecibo = funcionesRecibo.calcularTotalImporteIva(lineasRecibo);
                            double totalRecibo = baseImponible + ivaRecibo;

                            int consumo = (int) funcionesRecibo.calcularTotalConsumo(lineasRecibo);
                            int lecturaActual = c.getLecturaActual();
                            int lecturaAnterior = c.getLecturaAnterior();

                            // Comprobamos si el contribuyente esta exento de pagar el recibo
                            if(!c.getExencion().isEmpty()) {
                                if(c.getExencion().equalsIgnoreCase("S")) {
                                    baseImponible = 0;
                                    ivaRecibo = 0;
                                    totalRecibo = 0;
                                }
                            }   

                            xmlManager.agregarNodoDocumentoRecibos(String.valueOf(idRecibo), c.getExencion(), 
                                    String.valueOf(i+1), c.getNombre(), c.getApellido1(), 
                                    c.getApellido2(), c.getNifnie(), c.getIban(), 
                                    String.valueOf(lecturaActual), String.valueOf(lecturaAnterior), String.valueOf(consumo), 
                                    String.valueOf(baseImponible), String.valueOf(ivaRecibo), 
                                    String.valueOf(totalRecibo));

                            // Actualizamos los totales
                            totalBaseImponibleRecibos += baseImponible;
                            totalIvaRecibos += ivaRecibo;
                            idRecibo ++;
                        }
                    }
                }
            }
        }
        // Actualizamos el total
        totalRecibos = totalBaseImponibleRecibos + totalIvaRecibos;
        
        // Actualizamos los atributos totales del xml
        xmlManager.actualizarAtributosTotalesRecibos("1T de 2024", 
                String.format("%.2f", totalBaseImponibleRecibos), 
                String.format("%.2f", totalIvaRecibos), String.format("%.2f", totalRecibos));
        
        // Se genera el documento xml
        xmlManager.generarDocumentoXmlRecibos();
    }
}
