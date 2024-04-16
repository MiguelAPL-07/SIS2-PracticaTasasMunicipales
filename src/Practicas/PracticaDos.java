/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;


import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.FuncionDNI;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.transform.TransformerException;
import manager.ExcelManager;
import manager.XmlManager;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;

/**
 *
 * @author PC
 */
public class PracticaDos {
    
    private File ficheroLeer;
    
    private ExcelManager em; 
    
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    
    private HashMap<String, OrdenanzaExcel> ordenanzas;
    
    
    public PracticaDos() {
        ficheroLeer = new File(Constantes.RUTA_ARCHIVO_LEER);
        
        em = new ExcelManager(ficheroLeer);
        
        contribuyentes = em.obtenerContribuyentes();
        ordenanzas = em.obtenerOrdenanzas();
        
    }
    
    public void ejecucion() {
        em.modificarDniNieHoja("prueba", 2);
        FuncionDNI funcionDNI = new FuncionDNI();
        
        XmlManager xmlManager = new XmlManager();

        for(int i = 1; i <= contribuyentes.size(); i++) {
            // Comprobar documento
            if(contribuyentes.containsKey(String.valueOf(i))) {
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                int r = funcionDNI.validadorNIF_NIE(c.getNifnie());
                if(r == 1) {
                    String docActualizado = funcionDNI.corregirDocumento(c.getNifnie());
                    if(!funcionDNI.comprobarDocumentoDuplicado(docActualizado)) {
                        // Actualiza el excel. Subsanable
                        if(em.modificarDniNieHoja(docActualizado, i)) {
                            System.out.println("DNI " + docActualizado + " modificado correctamente");
                        }
                    } else {
                        // Se genera un nodo contribuyente. Subsanable pero duplicado
                        xmlManager.agregarNodoDocumentoErrores(i, c.getNifnie(), c.getNombre(), c.getApellido1(), c.getApellido2());
                        // Actualiza el excel. Subsanable
                        if(em.modificarDniNieHoja(docActualizado, i)) {
                            System.out.println("DNI " + docActualizado + " modificado correctamente");
                        }
                    }
                } else if(r == 2) {
                    // Se genera un nodo trabajador
                    xmlManager.agregarNodoDocumentoErrores(i, c.getNifnie(), c.getNombre(), c.getApellido1(), c.getApellido2());
                }
            }
        }
        // Se genera el xml
        xmlManager.generarDocumentoXmlErrores(); 
    }

    
    
}