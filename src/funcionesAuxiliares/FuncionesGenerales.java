/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import java.math.BigInteger;

/**
 *
 * @author Miguel Ángel
 */
public class FuncionesGenerales {
    
    
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
    
    public boolean comprobarCadenaSoloLetras(String cadena) {
        boolean result = true;
        for(int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i))) {
                result = false;
            }
        }
        return result;
    }
    
}
