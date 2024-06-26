/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import java.math.BigInteger;

/**
 * Esta clase calcula el IBAN, comprueba si es correcto y si el formato es 
 * válido
 * @author Miguel Ángel
 */
public class FuncionIBAN {
    
    // Acceso a las funciones genrales
    FuncionesGenerales fg;
    
    /**
     * Constructor para incializar las variables necesarias
     */
    public FuncionIBAN() {
        fg = new FuncionesGenerales();
    }
    
    /**
     * Método que calcula el IBAN correspondiente a un pais y un ccc,
     * verificando si el CCC es correcto
     * @param pais Variable pais del que se quiere generar el IBAN
     * @param ccc Variable ccc del que se quiere generar el IBAN
     * @return Devuelve una cadena con el IBAN generado y una cadena vacía si 
     * los datos eran incorrectos
     */
    public String calculoIBAN(String pais, String ccc) {
        String iban = "";
        if(ccc.length() == 20 && pais.length() == 2) {
            FuncionCCC fCCC = new FuncionCCC();
            // Verificamos si el CCC es correcto, si no no se hace nada
            if(fCCC.verificarCCC(ccc) && fg.comprobarCadenaSoloLetras(pais)) {
                // Primer paso. Se traslada las cuatro primeras posiciones al final
                iban = ccc + pais + "00";

                // Segundo paso. Transformar las letras de pais por numeros de la tabla
                String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

                // Se obtienen los valores asociados a cada letra de la tabla
                String valor1 = String.valueOf(letras.indexOf(String.valueOf(pais.charAt(0))) + 10);
                String valor2 = String.valueOf(letras.indexOf(String.valueOf(pais.charAt(1))) + 10);
                iban = ccc + valor1 + valor2 + "00";

                // Tercer paso. Aplicar modelo 97-10
                BigInteger ibanBI = new BigInteger(iban);
                // Se halla el siguiente calculo = 98-resto. Resto = dividir entre 97 y coger el resto
                // En BigInteger, la funcion remainder halla el resto de dos numeros
                int calculo = 98 - ibanBI.remainder(BigInteger.valueOf(97)).intValue();
                // Se comprueba si solo tiene un digito o dos para ajustarlo
                String digitosControl = "";
                if(calculo < 10) {
                    digitosControl = "0" + String.valueOf(calculo);
                } else {
                    digitosControl = String.valueOf(calculo);
                }
                iban = pais + digitosControl + ccc;
                if(!verificarIBAN(iban)) {
                    iban = "";
                }
            }
        }
        return iban;
    }
    
    /**
     * Método que verifica si un IBAN es correcto
     * @param iban Variable a comprobar
     * @return Devuelve true si es correcto, false si no es correcto
     */
    public boolean verificarIBAN(String iban) {
        boolean r = false;
        if(comrpobarFormatoIBAN(iban)) {
            // Sacar formato verificar IBAN
            String ccc = iban.substring(4, 24);
            String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            // Se obtienen los valores asociados a cada letra de la tabla
            String valor1 = String.valueOf(letras.indexOf(String.valueOf(iban.charAt(0))) + 10);
            String valor2 = String.valueOf(letras.indexOf(String.valueOf(iban.charAt(1))) + 10);
            String digitoControl = iban.substring(2, 4);

            iban = ccc + valor1 + valor2 + digitoControl;

            BigInteger bi = new BigInteger(iban);
            int calculo = bi.remainder(BigInteger.valueOf(97)).intValue();
            if(calculo == 1) {
                r = true;
            }
        }
        return r;
    }
    
    /**
     * Método que comprueba si el formato del IBAN es correcto
     * @param iban Variable a comprobar
     * @return Devuelve true si el formato es correcto, false si no lo es
     */
    public boolean comrpobarFormatoIBAN(String iban) {
        boolean r = false;
        if(iban.length() == 24) {
            if(fg.comprobarCadenaSoloLetras(iban.substring(0, 2)) && 
                    fg.comprobarCadenaSoloNumeros(iban.substring(2, 24))) {
                r = true;
            }
        }
        return r;
    }
}
