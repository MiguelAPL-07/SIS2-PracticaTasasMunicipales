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
 * @author PC
 */
public class XmlManager {
    
    private Document documentoErrores;
    
    public XmlManager() {
        iniciarlizarDocumentoErrores();
    }
    
    // cambiar boolean
    private void iniciarlizarDocumentoErrores() {
        try {
            // Creo una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Creo un documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Creo un DOMImplementation
            DOMImplementation implementation = builder.getDOMImplementation();

            // Creo un documento con un elemento raiz
            documentoErrores = implementation.createDocument(null, "Contribuyentes", null);
            documentoErrores.setXmlVersion("1.0");
        } catch (ParserConfigurationException e) {
            System.out.println("Error al iniciar el documento errores.xml");
            System.out.println(e.toString());
        }
    }
    
    public void agregarNodoDocumentoErrores(int id, String nifnie, String nombre,
            String apellido1, String apellido2) {
        // Creo el elemento principal
        Element contribuyente = documentoErrores.createElement("Contribuyente");
        contribuyente.setAttribute("id", String.valueOf(id+1));
        
        // Creo los subelementos
        Element nif_nie = documentoErrores.createElement("NIF_NIE");
        Text textoNifnie = documentoErrores.createTextNode(nifnie);
        nif_nie.appendChild(textoNifnie);
        
        Element elementNombre = documentoErrores.createElement("Nombre");
        Text textoNombre = documentoErrores.createTextNode(nombre);
        elementNombre.appendChild(textoNombre);
        
        Element elementApellido1 = documentoErrores.createElement("PrimerApellido");
        Text textoApellido1 = documentoErrores.createTextNode(apellido1);
        elementApellido1.appendChild(textoApellido1);
        
        Element elementApellido2 = documentoErrores.createElement("SegundoApellido");
        Text textoApellido2 = documentoErrores.createTextNode(apellido2);
        elementApellido2.appendChild(textoApellido2);
        
        // Añado los subelementos
        contribuyente.appendChild(nif_nie);
        contribuyente.appendChild(elementNombre);
        contribuyente.appendChild(elementApellido1);
        contribuyente.appendChild(elementApellido2);
        
        // Añado al root el elemento contribuyentes
        documentoErrores.getDocumentElement().appendChild(contribuyente);
    }
    
    //Agregar boolean
    public void generarDocumentoXmlErrores() {
        try {
            // Asocio el source con el Document
            Source source = new DOMSource(documentoErrores);

            File f = new File(Constantes.RUTA_ARCHIVO_ESCRIBIR_XML);
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
