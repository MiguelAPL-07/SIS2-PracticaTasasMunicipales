/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
/**
 *
 * @author Miguel Ángel
 */
public class FuncionIBANTest {
    
    FuncionIBAN fIBAN;
    
    public FuncionIBANTest() {
        fIBAN = new FuncionIBAN();
    }
    
    @Test
    public void test_calculoIBAN() {
        // IBAN con pais y CCC correcto
        assertEquals("ES9211112223504444444444", fIBAN.calculoIBAN("ES", "11112223504444444444"));
        // Pais con letras incorrectas
        assertEquals("", fIBAN.calculoIBAN("ESE", "11112223504444444444"));
        assertEquals("", fIBAN.calculoIBAN("E", "11112223504444444444"));
        assertEquals("", fIBAN.calculoIBAN("", "11112223504444444444"));
        // CCC con menos y mas digitos
        assertEquals("", fIBAN.calculoIBAN("ES", "111122235044444444443452"));
        assertEquals("", fIBAN.calculoIBAN("ES", "11112223443452"));
        // Pais y CCC ambos incorrectos
        assertEquals("", fIBAN.calculoIBAN("ESE", "11112223124444444444"));
        assertEquals("", fIBAN.calculoIBAN("E", "11112223124444444444"));
        // CCC incorrecto
        assertEquals("", fIBAN.calculoIBAN("ES", "11112223404444444444"));
        assertEquals("", fIBAN.calculoIBAN("ES", "11112223564444444444"));
        assertEquals("", fIBAN.calculoIBAN("ES", "11112223124444444444"));
        // Pais con numeros
        assertEquals("", fIBAN.calculoIBAN("12", "11112223504444444444"));
        assertEquals("", fIBAN.calculoIBAN("1212342", "11112223504444444444"));
        assertEquals("", fIBAN.calculoIBAN("1", "11112223504444444444"));
        // CCC correcto, pais correcto, hay que agregar un 0 por dif < 10
        
    }
    
    @Test
    public void test_verificarIBAN() {
        // IBAN correcto
        assertEquals(true, fIBAN.verificarIBAN("ES9211112223504444444444"));
        // IBAN incorrecto
        assertEquals(false, fIBAN.verificarIBAN("ES0011112223504444444444"));
        assertEquals(false, fIBAN.verificarIBAN("ES001111222350444444444"));
    }  

    @Test
    public void test_comprobarFormatoIBAN() {
        // Formato correcto
        assertEquals(true, fIBAN.comrpobarFormatoIBAN("ES9211112223504444444444"));
        assertEquals(true, fIBAN.comrpobarFormatoIBAN("ES9561112223504444444444"));
        // Formato incorrecto solo numeros
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("112223504444444444"));
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("129211112223504444444444"));
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("24356439211112223504444444444"));
        // Formato incorrecto solo letras
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("ESerdhghjdjugtshgjkltgvb"));
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("ESerdhghjdjugtshgjkgvb"));
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("ESerdhghjdjugtshgdvvdjkltgvb"));
        // Formato incorrecto general
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("E39211112223504444444444"));
        assertEquals(false, fIBAN.comrpobarFormatoIBAN("ESE211112223504444444444"));        
    }  
    
}
