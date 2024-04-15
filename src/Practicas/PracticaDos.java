/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;


import funcionesAuxiliares.Constantes;
import java.io.File;
import java.util.HashMap;
import manager.ExcelManager;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;

/**
 *
 * @author PC
 */
public class PracticaDos {
    
    private File ficheroLeer;
    
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    
    private HashMap<String, OrdenanzaExcel> ordenanzas;
    
    public PracticaDos() {
        ficheroLeer = new File(Constantes.RUTA_ARCHIVO_LEER);
        
    }
    
    public void ejecucion() {
        
        ExcelManager em = new ExcelManager(ficheroLeer);
        
        
    }
}
