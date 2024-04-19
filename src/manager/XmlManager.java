/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import funcionesAuxiliares.Constantes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Miguel Ángel
 */
public class XmlManager {
    
    // Documento para ErroresNifNie.xml
    private Document documentoErroresNifNie;
    
    // Documento para ErroresCCC.xml
    private Document documentoErroresCCC;
    
    public XmlManager() {
        iniciarlizarDocumentoErroresNifNie();
        iniciarlizarDocumentoErroresCCC();
    }
    
    // cambiar boolean
    private void iniciarlizarDocumentoErroresNifNie() {
        try {
            // Creo una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Creo un documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Creo un DOMImplementation
            DOMImplementation implementation = builder.getDOMImplementation();

            // Creo un documento con un elemento raiz
            documentoErroresNifNie = implementation.createDocument(null, "Contribuyentes", null);
            documentoErroresNifNie.setXmlVersion("1.0");
        } catch (ParserConfigurationException e) {
            System.out.println("Error al iniciar el documento errores.xml");
            System.out.println(e.toString());
        }
    }
    
    public void agregarNodoDocumentoErroresNifNie(int id, String nifnie, String nombre,
            String apellido1, String apellido2) {
        // Creo el elemento principal
        Element contribuyente = documentoErroresNifNie.createElement("Contribuyente");
        contribuyente.setAttribute("id", String.valueOf(id+1));
        
        // Creo los subelementos
        Element nif_nie = documentoErroresNifNie.createElement("NIF_NIE");
        Text textoNifnie = documentoErroresNifNie.createTextNode(nifnie);
        nif_nie.appendChild(textoNifnie);
        
        Element elementNombre = documentoErroresNifNie.createElement("Nombre");
        Text textoNombre = documentoErroresNifNie.createTextNode(nombre);
        elementNombre.appendChild(textoNombre);
        
        Element elementApellido1 = documentoErroresNifNie.createElement("PrimerApellido");
        Text textoApellido1 = documentoErroresNifNie.createTextNode(apellido1);
        elementApellido1.appendChild(textoApellido1);
        
        Element elementApellido2 = documentoErroresNifNie.createElement("SegundoApellido");
        Text textoApellido2 = documentoErroresNifNie.createTextNode(apellido2);
        elementApellido2.appendChild(textoApellido2);
        
        // Añado los subelementos
        contribuyente.appendChild(nif_nie);
        contribuyente.appendChild(elementNombre);
        contribuyente.appendChild(elementApellido1);
        contribuyente.appendChild(elementApellido2);
        
        // Añado al root el elemento contribuyentes
        documentoErroresNifNie.getDocumentElement().appendChild(contribuyente);
    }
    
    //Agregar boolean
    public void generarDocumentoXmlErroresNifNie() {
        try {
            // Asocio el source con el Document
            Source source = new DOMSource(documentoErroresNifNie);

            File f = new File(Constantes.RUTA_ARCHIVO_ESCRIBIR_XML_NIF_NIE);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);

            // Creo el Result, indicado que fichero se va a crear
            Result result = new StreamResult(pw);

            // Creo un transformer, se crea el fichero XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            System.out.println(e.toString());
        }
        
    }
    
    // cambiar boolean
    private void iniciarlizarDocumentoErroresCCC() {
        try {
            // Creo una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Creo un documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Creo un DOMImplementation
            DOMImplementation implementation = builder.getDOMImplementation();

            // Creo un documento con un elemento raiz
            documentoErroresCCC = implementation.createDocument(null, "Cuentas", null);
            documentoErroresCCC.setXmlVersion("1.0");
        } catch (ParserConfigurationException e) {
            System.out.println("Error al iniciar el documento errores.xml");
            System.out.println(e.toString());
        }
    }
    
    public void agregarNodoDocumentoErroresCCC(int id, String nombre, String apellidos,
            String nifnie, String cccErroneo, String ibanCorrecto) {
        
// Creo el elemento principal
        Element contribuyente = documentoErroresCCC.createElement("Cuenta");
        contribuyente.setAttribute("id", String.valueOf(id+1));
        
        // Creo los subelementos
        Element elementNombre = documentoErroresCCC.createElement("Nombre");
        Text textoNombre = documentoErroresCCC.createTextNode(nombre);
        elementNombre.appendChild(textoNombre);
        
        Element elementApellidos = documentoErroresCCC.createElement("Apellidos");
        Text textoApellidos = documentoErroresCCC.createTextNode(apellidos);
        elementApellidos.appendChild(textoApellidos);
        
        Element nif_nie = documentoErroresCCC.createElement("NIFNIE");
        Text textoNifnie = documentoErroresCCC.createTextNode(nifnie);
        nif_nie.appendChild(textoNifnie);
        
        
        
        Element elementCCCErroneo = documentoErroresCCC.createElement("CCCErroneo");
        Text textoCCCErroneo = documentoErroresCCC.createTextNode(cccErroneo);
        elementCCCErroneo.appendChild(textoCCCErroneo);
        
        Element elementIBANCorrecto = documentoErroresCCC.createElement("IBANCorrecto");
        Text textoIBANCorrecto = documentoErroresCCC.createTextNode(ibanCorrecto);
        elementIBANCorrecto.appendChild(textoIBANCorrecto);
        
        // Añado los subelementos
        contribuyente.appendChild(elementNombre);
        contribuyente.appendChild(elementApellidos);
        contribuyente.appendChild(nif_nie);
        contribuyente.appendChild(elementCCCErroneo);
        contribuyente.appendChild(elementIBANCorrecto);
        
        // Añado al root el elemento contribuyentes
        documentoErroresCCC.getDocumentElement().appendChild(contribuyente);
    }
    
    //Agregar boolean
    public void generarDocumentoXmlErroresCCC() {
        try {
            // Asocio el source con el Document
            Source source = new DOMSource(documentoErroresCCC);

            File f = new File(Constantes.RUTA_ARCHIVO_ESCRIBIR_XML_CCC);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);

            // Creo el Result, indicado que fichero se va a crear
            Result result = new StreamResult(pw);

            // Creo un transformer, se crea el fichero XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            System.out.println(e.toString());
        }
        
    }
}
