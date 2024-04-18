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
    
    private ArrayList<String> emails;
    
    private FuncionesGenerales fg;
    
    public FuncionEmail() {
        emails = new ArrayList<>();
        fg = new FuncionesGenerales();
    }
    
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
     * Metodo que comprueba si un email ya esta en memoria
     * @param email
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
    
    public int obtenerNumVecesEmailDuplicado(String email) {
        int num = 0;
        for (String e : emails) {
            if(obtenerPrefijoEmail(e).equalsIgnoreCase(obtenerPrefijoEmail(email))) {
                num = num + 1;
            }
        }
        return num;
    }
    
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
