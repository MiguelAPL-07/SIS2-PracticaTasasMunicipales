/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;

import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.FuncionesRecibo;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import manager.ExcelManager;
import modelo.LineasReciboModelo;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;

/**
 *
 * @author Miguel Ángel
 */
public class PracticaTres {
    
    private ExcelManager excelManager;
    
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    
    private HashMap<String, OrdenanzaExcel> ordenanzas;
    
    public PracticaTres() {
        excelManager = new ExcelManager(new File(Constantes.RUTA_ARCHIVO_LEER));
        
        contribuyentes = excelManager.obtenerContribuyentes();
        
        ordenanzas = excelManager.obtenerOrdenanzas();
    }
    
    public void ejecuccion() {
        
        FuncionesRecibo funcionesRecibo = new FuncionesRecibo();
        
        for(int i = 1; i <= excelManager.getUltimaFilaContribuyentes(); i++) {
            // Comprobar documento
            if(contribuyentes.containsKey(String.valueOf(i))) {
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                
                List<LineasReciboModelo> lineasRecibo = new ArrayList<>();
                lineasRecibo = funcionesRecibo.generarLineasRecibo(c);
        
                System.out.println(funcionesRecibo.calcularTotalBaseImponible(lineasRecibo));
            }
        }
    }
}
