/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

/**
 *
 * @author Miguel Ángel
 */
public class FuncionCCC {
    
    public boolean verificarCCC(String ccc) {
        boolean correcto = false;
        if(ccc.length() == 20) {
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
    
    public String corregirCCC(String ccc) {
        String cccCorrecto = "";
        if(verificarCCC(ccc)) {
            cccCorrecto = ccc;
        } else {
            if(ccc.length() == 20) {
                String codigoEntidad = ccc.substring(0, 4);
                String codigoOficina = ccc.substring(4, 8);
                String codigoCuenta = ccc.substring(10, 20);
                int digitoUno = calculoDigitoControlCCC("00" + codigoEntidad + codigoOficina);
                int digitoDos = calculoDigitoControlCCC(codigoCuenta);
                System.out.println("Digot uno: " + digitoUno + " " + digitoDos);
                cccCorrecto = codigoEntidad + codigoOficina + String.valueOf(digitoUno) + String.valueOf(digitoDos) + codigoCuenta;
            }
        }
        return cccCorrecto;
    }
    
    private int calculoDigitoControlCCC(String digitos) {
        int suma = 0;
        for(int i = 0; i < 10; i++) {
            int factor = (int) Math.pow(Double.valueOf(2), Double.valueOf(i));
            suma = suma + factor*Integer.valueOf(digitos.substring(i, i+1));
        }
        int digitoControl = 11 - suma % 11;
        if(digitoControl == 11) {
            digitoControl = 0;
        } else if(digitoControl == 10) {
            digitoControl = 1;
        }
        return digitoControl;
    }
    
}
