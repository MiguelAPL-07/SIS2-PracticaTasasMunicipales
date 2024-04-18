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
    
    public FuncionEmail() {
        emails = new ArrayList<>();
    }
    
    public String generarEmail(String nombre, String apellido1, String apellido2) {
        String email = "";
        if(!nombre.isEmpty()) {
            email = email + nombre.substring(0, 1).toUpperCase();
            if(!apellido1.isEmpty()) {
                email = email + apellido1.substring(0, 1).toUpperCase();
                if(!apellido2.isEmpty()) {
                    email = email + apellido2.substring(0, 1).toUpperCase();
                }
            }
        }
        if(!compruebaEmailDuplicado(email)) {
            emails.add(email);
            email = email + "00@Agua2024.com";
        } else {
            int numVeces = obtenerNumVecesEmailDuplicado(email);
            if(numVeces < 10) {
                email = email + "0" + numVeces + "@Agua2024.com";
            } else {
                email = email + numVeces + "@Agua2024.com";
            }
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
            if(e.equalsIgnoreCase(email)) {
                r = true;
            }
        }
        return r;
    }
    
    public int obtenerNumVecesEmailDuplicado(String email) {
        int num = 0;
        for (String e : emails) {
            if(e.equalsIgnoreCase(email)) {
                num = num + 1;
            }
        }
        return num;
    }
    
}
