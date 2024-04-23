/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import java.util.ArrayList;

/**
 * Esta clase se encarga de gestionar los DNIs y NIEs de la aplicación
 * Validar si son correctos, y en caso de que no, corregirlos si es posible
 * Además de todas las comprobaciones y algoritmos necesarios para
 * seguir los patrones adecuados y la obtención de los dígitos de control
 * @author Miguel Ángel
 */
public class FuncionDNI {
    
    // Se almacenan los documentos validados para encontrar duplicados
    private ArrayList<String> documentos;
    
    /**
     * Constructor para iniciar los documentos donde se van a almacenar los
     * DNIs y NIEs validados
     */
    public FuncionDNI() {
        documentos = new ArrayList<>();
    }
    
    /**
     * Algoritmo que valida un DNI o NIE 
     * @param documento a validar
     * @return 
     * Devuelve 0 si el documento es correcto
     * Devuelve 1 si se puede arreglar y corregir en el excel
     * Devuelve 2 si hay que meterlo en el xml de errores
     * Devuelve 3 si es subsanable pero esta duplicado. Se modifica el Excel y 
     * se agrega al xml de errores
     */
    public int validadorNIF_NIE(String documento) {
        int resto = -1;
        String caracteres = "";
        int resultado = -1;
        
        // Comprobar la longitud == 9
        if(documento.replace(" ", "").length() == 9 || documento.replace(" ", "").length() == 8) {
            boolean descartado = false;
            try {
                // NIF = 8 numeros y una letra al final
                int digito1 = Integer.parseInt(String.valueOf(documento.charAt(0)));
                caracteres = documento.substring(0, 8);
            } catch(NumberFormatException e) {
                caracteres = cambiarFormatoNIE(documento);
                if(caracteres.equals("")) {
                    descartado = true;
                }
            }
            if(!descartado) {
                try {
                    resto = Integer.parseInt(caracteres) % 23;
                    if(resto != -1) {
                        if(documento.replace(" ", "").length() == 9) {
                            // Se comprueba si la letra es correcta
                            if(calculoDigitoControl(resto) == documento.toUpperCase().charAt(8)) {
                                // Se comprueba si esta duplicado
                                if(!comprobarDocumentoDuplicado(documento)){
                                    // Documento correcto
                                    resultado = 0;
                                    documentos.add(documento);
                                } else {
                                    // Esta duplicado, se escribe en el xml
                                    resultado = 2;
                                }
                            } else {
                                 // Es subsanable 
                                 String dniCorregido = corregirDocumento(documento);
                                 // Si corregido esta duplicado no se añade
                                 if(!comprobarDocumentoDuplicado(dniCorregido)){
                                     documentos.add(dniCorregido);
                                     resultado = 1;
                                 } else {
                                     resultado = 3;
                                 }
                            }
                        } else {
                            // Es subsanable 
                            String dniCorregido = corregirDocumento(documento);
                            // Si corregido esta duplicado no se añade
                            if(!comprobarDocumentoDuplicado(dniCorregido)){
                                documentos.add(dniCorregido);
                                resultado = 1;
                            } else {
                                resultado = 3;
                            }
                        } 
                    }
                } catch (NumberFormatException e2) {
                    resultado = 2;
                    System.out.println("No se cumple con los digitos necesarios. Tiene que ser solo numeros");
                }
            } else {
                resultado = 2;
            }
        } else {
           resultado = 2;
        }
        return resultado;
    }
    
    /**
     * Calcula el digito de control de los documentos a traves del
     * resto de la division
     * @param resto de la division
     * @return devuelve la letra que le pertenece de la tabla
     */
    private char calculoDigitoControl(int resto) {
        String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
        return letrasValidas.charAt(resto);
    }
    
    /**
     * Comprueba si el doucmento esta duplicado en los documentos almacenados
     * @param documento a validar la duplicidad
     * @return true si esta duplicado, false si no esta duplicado
     */
    public boolean comprobarDocumentoDuplicado(String documento) {
        boolean rep = false;
        for(String docActual : documentos) {
            if(docActual.equalsIgnoreCase(documento)) {
                rep = true;
            }
        }
        return rep;
    }

    /**
     * Corrige o añade la letra del DNI que le corresponde
     * @param documento a corregir
     * @return devuelve el DNI corregido
     */
    public String corregirDocumento(String documento) {
        boolean tipoNie = false;
        // Verifica si es NIE o NIF
        try {
            // NIF = 8 numeros y una letra al final
            int digito1 = Integer.parseInt(String.valueOf(documento.charAt(0)));
        } catch(NumberFormatException e) {
            documento = cambiarFormatoNIE(documento);
            tipoNie = true;
        }
        String doc = "";
        int resto = Integer.parseInt(documento.substring(0, 8)) % 23;
        if(documento.replace(" ", "").length() == 9) {
            // Modificar letra
            doc = documento.substring(0, 8).concat(String.valueOf(calculoDigitoControl(resto)));
        } else {
            // Añadir la letra
            doc = documento.concat(String.valueOf(calculoDigitoControl(Integer.parseInt(documento) % 23)));
        }
        if(tipoNie) {
            doc = deshacerFormatoNumericoNie(doc);
        }
        return doc;
    }
    
    /**
     * Cambia la letra inicial por el numero correspondiente para que quede
     * con el mismo formato que el DNI
     * @param documento a cambiar de formato
     * @return devuelve el documento con formato DNI
     */
    private String cambiarFormatoNIE(String documento) {
        String caracteres = "";
        // NIE = una letra 7 numeros y una letra al final
        switch (documento.toUpperCase().charAt(0)) {
            case 'X':
                caracteres = "0".concat(documento.substring(1, 8));
                break;
            case 'Y':
                caracteres = "1".concat(documento.substring(1, 8));
                break;
            case 'Z':
                caracteres = "2".concat(documento.substring(1, 8));
                break;
            default:
                caracteres = "";
                break;
        }
        return caracteres;
    }
    
    /**
     * Cambia el numero inicial por la letra correspondiente para que
     * tenga el formato de un NIE
     * @param documento a cambiar de formato
     * @return devuelve el documento con formato NIE
     */
    private String deshacerFormatoNumericoNie(String documento) {
        String caracteres = "";
        // NIE = una letra 7 numeros y una letra al final
        switch (documento.toUpperCase().charAt(0)) {
            case '0':
                caracteres = "X".concat(documento.substring(1, 9));
                break;
            case '1':
                caracteres = "Y".concat(documento.substring(1, 9));
                break;
            case '2':
                caracteres = "Z".concat(documento.substring(1, 9));
                break;
            default:
                caracteres = "";
                break;
        }
        return caracteres;
    }
}