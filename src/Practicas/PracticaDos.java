/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;


import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.FuncionCCC;
import funcionesAuxiliares.FuncionDNI;
import funcionesAuxiliares.FuncionEmail;
import funcionesAuxiliares.FuncionIBAN;
import java.io.File;
import java.util.HashMap;
import manager.ExcelManager;
import manager.XmlManager;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;

/**
 *
 * @author Miguel Ángel
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
        primeraEjecucionDNI();
        segundaEjecucionCCC();
    }

    private void primeraEjecucionDNI() {
        FuncionDNI funcionDNI = new FuncionDNI();
        XmlManager xmlManager = new XmlManager();

        for(int i = 1; i <= em.getUltimaFilaContribuyentes(); i++) {
            // Comprobar documento
            if(contribuyentes.containsKey(String.valueOf(i))) {
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                int r = funcionDNI.validadorNIF_NIE(c.getNifnie());
                if(r == 1) {
                    String docActualizado = funcionDNI.corregirDocumento(c.getNifnie());
                    // Actualiza el excel. Subsanable
                    if(em.modificarDniNieHoja(docActualizado, i)) {
                        System.out.println("DNI " + docActualizado + " modificado correctamente");
                    }
                } else if(r == 2) {
                    // Se genera un nodo trabajador
                    xmlManager.agregarNodoDocumentoErroresNifNie(i, c.getNifnie(), c.getNombre(), c.getApellido1(), c.getApellido2());
                } else if(r == 3) {
                    // Actualiza el excel. Subsanable pero duplicado
                    String docActualizado = funcionDNI.corregirDocumento(c.getNifnie());
                    if(em.modificarDniNieHoja(docActualizado, i)) {
                        System.out.println("DNI " + docActualizado + " modificado correctamente");
                    }
                    // Se genera un nodo trabajador
                    xmlManager.agregarNodoDocumentoErroresNifNie(i, docActualizado, c.getNombre(), c.getApellido1(), c.getApellido2());
                }
            }
        }
        // Se genera el xml
        xmlManager.generarDocumentoXmlErroresNifNie();
    }
    
    private void segundaEjecucionCCC() {
        // Actualizo la lista de contribuyentes con las actualizaciones
        contribuyentes = em.obtenerContribuyentes();
        FuncionDNI fDNI = new FuncionDNI();
        FuncionEmail fEmail = new FuncionEmail();
        FuncionCCC fCCC = new FuncionCCC();
        FuncionIBAN fIBAN = new FuncionIBAN();
        XmlManager xmlManager = new XmlManager();
        
        for(int i = 1; i <= em.getUltimaFilaContribuyentes(); i++) {
            
            if(contribuyentes.containsKey(String.valueOf(i))) {
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                
                String iban = "";
                // Verifica si el codigo de cuenta es correcto
                if(fCCC.verificarCCC(c.getCcc())) {
                    // Valido DNI. Si es incorrecto no hago nada
                    if(fDNI.validadorNIF_NIE(c.getNifnie()) == 0) {
                        // Calcula IBAN y agrega al Excel
                        iban = fIBAN.calculoIBAN(c.getPaisCCC(), c.getCcc());
                        em.agregarIBANHoja(iban, i);
                        // Genera el email y agrega al Excel
                        String email = fEmail.generarEmail(c.getNombre(), c.getApellido1(), c.getApellido2());
                        em.agregarEmailHoja(email, i);
                    }
                } else {
                    // Corregir el ccc, generar el IBAN, agregar al Excel y enviar al XML
                    String cccCorregido = fCCC.corregirCCC(c.getCcc());
                    iban = fIBAN.calculoIBAN(c.getPaisCCC(), cccCorregido);
                    xmlManager.agregarNodoDocumentoErroresCCC(i, c.getNombre(), 
                            c.getApellido1() + " " + c.getApellido2(), 
                            c.getNifnie(), c.getCcc(), iban);
                    if(fDNI.validadorNIF_NIE(c.getNifnie()) == 0) {
                        em.modificarCCCHoja(cccCorregido, i);
                        em.agregarIBANHoja(iban, i);
                        // Genera el email y agrega al Excel
                        String email = fEmail.generarEmail(c.getNombre(), c.getApellido1(), c.getApellido2());
                        em.agregarEmailHoja(email, i);
                    }
                }   
            }
        }
        
        // Se genera el xml
        xmlManager.generarDocumentoXmlErroresCCC();
    }
}
