/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

/**
 * Esta clase sirve para realizar todo lo que tenga que ver con un ccc, como 
 * verificar si es correcto, corregirlo si no lo es y verificar los 
 * dígitos de control
 * @author Miguel Ángel
 */
public class FuncionCCC {
    
    // Acceso a las funciones generales
    FuncionesGenerales fg;
    
    /**
     * Constructor para iniciar las variables necesarias
     */
    public FuncionCCC() {
        fg = new FuncionesGenerales();
    }
    
    /**
     * Verifica si un CCC es correcto realizando el algoritmo
     * @param ccc a comprobar si es correcto
     * @return Devuelve true si es correcto y false si no lo es
     */
    public boolean verificarCCC(String ccc) {
        boolean correcto = false;
        if(ccc.length() == 20 && fg.comprobarCadenaSoloNumeros(ccc)) {
            String codigoEntidad = ccc.substring(0, 4);
            String codigoOficina = ccc.substring(4, 8);
            String primerDigitoControl = ccc.substring(8, 9);
            String segundoDigitoControl = ccc.substring(9, 10);
            String codigoCuenta = ccc.substring(10, 20);
            
            int digitoUno = calculoDigitoControlCCC("00" + codigoEntidad + codigoOficina);
            int digitoDos = calculoDigitoControlCCC(codigoCuenta);
            
            boolean primerDigitoCorrecto = false;
            boolean segundoDigitoCorrecto = false;
            
            // Comprueba si el primer digito de control es correcto
            if(primerDigitoControl.equalsIgnoreCase(String.valueOf(digitoUno))) {
                primerDigitoCorrecto = true;
            }
            // Comprueba si el segundo digito de control es correcto
            if(segundoDigitoControl.equalsIgnoreCase(String.valueOf(digitoDos))) {
                segundoDigitoCorrecto = true;
            }
            if(primerDigitoCorrecto && segundoDigitoCorrecto) {
                correcto = true;
            }
        }
        return correcto;
    }
    
    /**
     * Método que corrige los digitos de control de un CCC incorrecto
     * @param ccc a corregir
     * @return Devuelve el ccc corregido
     */
    public String corregirCCC(String ccc) {
        String cccCorrecto = "";
        if(verificarCCC(ccc)) {
            cccCorrecto = ccc;
        } else {
            if(ccc.length() == 20 && fg.comprobarCadenaSoloNumeros(ccc)) {
                String codigoEntidad = ccc.substring(0, 4);
                String codigoOficina = ccc.substring(4, 8);
                String codigoCuenta = ccc.substring(10, 20);
                int digitoUno = calculoDigitoControlCCC("00" + codigoEntidad + codigoOficina);
                int digitoDos = calculoDigitoControlCCC(codigoCuenta);
                cccCorrecto = codigoEntidad + codigoOficina + String.valueOf(digitoUno) + String.valueOf(digitoDos) + codigoCuenta;
            }
        }
        return cccCorrecto;
    }
    
    /**
     * Calcula el digito de control de los documentos a traves del
     * resto de la division
     * @param resto de la division
     * @return devuelve la letra que le pertenece de la tabla
     */
    
    /**
     * Método que calcula el dígito de control de un ccc a través
     * del algoritmo
     * @param digitos para hallar el dígito de control
     * @return Devuelve el dígito de control hallado
     */
    private int calculoDigitoControlCCC(String digitos) {
        int suma = 0;
        // 2^n mod 11 para 0 <= n <= 10
        // Se suman los diez productos
        for(int i = 0; i < 10; i++) {
            int factor = (int) Math.pow(Double.valueOf(2), Double.valueOf(i));
            suma = suma + factor*Integer.valueOf(digitos.substring(i, i+1));
        }
        // Se divide por 11 y se guarda el resto y se resta 11º
        int digitoControl = 11 - suma % 11;
        if(digitoControl == 11) {
            digitoControl = 0;
        } else if(digitoControl == 10) {
            digitoControl = 1;
        }
        return digitoControl;
    }
}
