/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import java.util.ArrayList;

/**
 *
 * @author Miguel Ángel
 */
public class FuncionEmail {
    
    // Variable donde se van a almacenar los emails generados
    private ArrayList<String> emails;
    
    // Acceso a las funciones generales
    private FuncionesGenerales fg;
    
    /**
     * Constructor para incializar las variables necesarias
     */
    public FuncionEmail() {
        emails = new ArrayList<>();
        fg = new FuncionesGenerales();
    }
    
    /**
     * Método que genera un Email en base al nombre, apellidos y si está ya 
     * generado, apoda un código para que sean únicos
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @return Devuelve el Email generado
     */
    public String generarEmail(String nombre, String apellido1, String apellido2) {
        String email = "";
        String prefijo = "";
        if(!nombre.isEmpty()) {
            prefijo = prefijo + nombre.substring(0, 1).toUpperCase();
            if(!apellido1.isEmpty()) {
                prefijo = prefijo + apellido1.substring(0, 1).toUpperCase();
                if(!apellido2.isEmpty()) {
                    prefijo = prefijo + apellido2.substring(0, 1).toUpperCase();
                } 
            }
        }
        email = prefijo + "00@Agua2024.com";
        if(!compruebaEmailDuplicado(email)) {
            emails.add(email);
        } else {
            int numVeces = obtenerNumVecesEmailDuplicado(email);
            if(numVeces < 10) {
                email = prefijo + "0" + numVeces + "@Agua2024.com";
            } else {
                email = prefijo + numVeces + "@Agua2024.com";
            }
            emails.add(email);
        }
        return email;
    }
    
    /**
     * Método que comprueba si un email ya está en memoria
     * @param email variable a comprobar si ya está guardada
     * @return true si esta duplicado, false si no existe todavia
     */
    public boolean compruebaEmailDuplicado(String email) {
        boolean r = false;
        for (String e : emails) {
            if(obtenerPrefijoEmail(e).equalsIgnoreCase(obtenerPrefijoEmail(email))) {
                r = true;
            }
        }
        return r;
    }
    
    /**
     * Método para obtener el número de veces que un Email está duplicado
     * @param email variable a comprobar número de veces repetida
     * @return Devuelve el número de veces que está duplicado
     */
    public int obtenerNumVecesEmailDuplicado(String email) {
        int num = 0;
        for (String e : emails) {
            if(obtenerPrefijoEmail(e).equalsIgnoreCase(obtenerPrefijoEmail(email))) {
                num = num + 1;
            }
        }
        return num;
    }
    
    /**
     * Método que obtiene el prefijo de un Email en el que tienen todos en común
     * @param email a obtener el prefijo
     * @return Devuelve el prefijo si tiene los dos apellidos o solo uno
     */
    private String obtenerPrefijoEmail(String email) {
        String cadena = "";
        if(fg.comprobarCadenaSoloLetras(email.substring(0, 3))) {
            // Tiene apellido 2
            cadena = email.substring(0, 3);
        } else {
            // No tiene apellido 2
            cadena = email.substring(0, 2);
        }        
        return cadena;
    }
    
}
