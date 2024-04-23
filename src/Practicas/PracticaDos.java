/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;


import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.Fecha;
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
 * Esta clase responde a los apartados de la Practica Dos
 * Comprueba los DNIs y NIEs, corrige el Excel y genera el ErroresNifNie.xml
 * Comprueba el CCC, corrige el Excel y genera el ErroresCCC.xml
 * Además, genera un IBAN correcto y un Email si todo lo anterior es correcto
 * y lo guarda en el Excel
 * @author Miguel Ángel
 */
public class PracticaDos {
    
    // Variable para guardar el fichero que se lee
    private File ficheroLeer;
    
    // Acceso a todo el funcionamiento con el Excel
    private ExcelManager em; 
    
    // variables para guardar los datos obtenidos de la base de datos
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    private HashMap<String, OrdenanzaExcel> ordenanzas;
    
    /**
     * Constructor para inicializar las variables necesarias
     */
    public PracticaDos() {
        ficheroLeer = new File(Constantes.RUTA_ARCHIVO_LEER);
        
        em = new ExcelManager(ficheroLeer);
        
        contribuyentes = em.obtenerContribuyentes();
        ordenanzas = em.obtenerOrdenanzas();
    }
    
    /**
     * Método público que realiza la ejecucción completa de la clase
     */
    public void ejecucion() {
        primeraEjecucionDNI();
        segundaEjecucionCCC();
    }

    /**
     * En este método se lleva a cabo la comprobación de los DNIs y NIEs,
     * su corrección en el Excel y la generación del ErroresNifNie.xml
     */
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
    
    /**
     * En este método se lleva a cabo la comprobación del CCC, su corrección 
     * en el Excel y la generación de ErroresCCC.xml
     * También, genera el IBAN y el Email para los casos correctos y lo 
     * guarda en el Excel
     */
    private void segundaEjecucionCCC() {
        // Actualizo la lista de contribuyentes con las actualizaciones
        contribuyentes = em.obtenerContribuyentes();
        FuncionEmail fEmail = new FuncionEmail();
        FuncionCCC fCCC = new FuncionCCC();
        FuncionIBAN fIBAN = new FuncionIBAN();
        XmlManager xmlManager = new XmlManager();
        
        for(int i = 1; i <= em.getUltimaFilaContribuyentes(); i++) {
            FuncionDNI fDNI = new FuncionDNI();
            if(contribuyentes.containsKey(String.valueOf(i))) {
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                Fecha f = new Fecha();
                f = f.transformarFechaExcel(c.getFechaAlta());
                // Verificamos si la fecha alta es anterior a la actual
                if(!f.comprobarFechaPosteriorAFechaActual(f)) {
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
        }
        // Se genera el xml
        xmlManager.generarDocumentoXmlErroresCCC();
    }
}
