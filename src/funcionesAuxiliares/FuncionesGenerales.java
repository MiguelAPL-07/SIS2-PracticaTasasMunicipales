/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import java.math.BigInteger;

/**
 * Clase que se utiliza para crear funciones generales para usar en toda 
 * la aplicación
 * @author Miguel Ángel
 */
public class FuncionesGenerales {
    
    /**
     * Método que comprueba si en una cadena solo hay números
     * @param cadena es la variable a determinar su contenido
     * @return 
     * Devuelve true si solo esta compuesta de números 
     * Devuelve false si hay letras en la cadena
     */
    public boolean comprobarCadenaSoloNumeros(String cadena) {
        boolean correcto = true;
        // Comprobamos si solo hay numeros
        try {
            BigInteger bi = new BigInteger(cadena);
        } catch(NumberFormatException e) {
            correcto = false;
        }
        return correcto;
    }
    
    /**
     * Método que comprueba si en una cadena solo hay letras
     * @param cadena es la variable a determinar su contenido
     * @return 
     * Devuelve true si solo esta compuesta de letrasa
     * Devuelve false si hay numeros en la cadena
     */
    public boolean comprobarCadenaSoloLetras(String cadena) {
        boolean result = true;
        for(int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i))) {
                result = false;
            }
        }
        return result;
    }
    
    /**
     * Método que comprueba una cadena y determina si solo hay espacios o 
     * hay contenido diferente a espacios en blanco
     * @param cadena
     * @return 
     * Devuelve true si solo contiene espacios en blanco
     * Devuelve false si no solo son espacios en blanco
     */
    public boolean comprobarCadenaSoloEspacios(String cadena) {
        boolean soloEspaciosBlanco = false;
        int contEspacios = 0;
        for(int i = 0; i < cadena.length(); i++) {
            if(cadena.charAt(i) == ' ') {
                contEspacios++;
            }
        }
        if(contEspacios == cadena.length()) {
            soloEspaciosBlanco = true;
        }
        return soloEspaciosBlanco;
    }
    
}
