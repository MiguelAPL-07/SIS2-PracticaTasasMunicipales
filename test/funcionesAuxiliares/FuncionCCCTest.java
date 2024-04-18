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
public class FuncionCCCTest {
    
    public FuncionCCCTest() {
        
    }
    
    
    @Test
    public void test_verificarCCC_casosSimples() {
        FuncionCCC fCCC = new FuncionCCC();
        
        // Mas digitos caso limite
        assertEquals(false, fCCC.verificarCCC("123456789123456789123"));
        // Menos digitos caso limite
        assertEquals(false, fCCC.verificarCCC("1234567891234567891"));
        // Mas y menos digitos
        assertEquals(false, fCCC.verificarCCC("12345678912345678912334532"));
        assertEquals(false, fCCC.verificarCCC("12345432"));
        // Contiene letras
        assertEquals(false, fCCC.verificarCCC("AA123435432323"));
        // Longitud correcta pero contiene letras
        assertEquals(false, fCCC.verificarCCC("1234567891234567891A"));
        // CCC correcto
        assertEquals(true, fCCC.verificarCCC("11112223504444444444"));
        assertEquals(true, fCCC.verificarCCC("14904008027388395614"));
        
    }
    
    @Test
    public void test_corregirCCC_casosSimples() {
        FuncionCCC fCCC = new FuncionCCC();
        
        // Primer digito incorrecto
        assertEquals("11112223504444444444", fCCC.corregirCCC("11112223704444444444"));
        // Segundo digito incorrecto
        assertEquals("11112223504444444444", fCCC.corregirCCC("11112223514444444444"));
        // Ambos digitos incorrectos
        assertEquals("11112223504444444444", fCCC.corregirCCC("11112223774444444444"));
        // Corregit CCC correcto
        assertEquals("11112223504444444444", fCCC.corregirCCC("11112223504444444444"));
        // Corregir CCC con letras
        assertEquals("", fCCC.corregirCCC("L11122235044444444CD"));
        // Corregir CCC con mas digitos
        assertEquals("", fCCC.corregirCCC("11112223504444444444124"));
        // Corregir CCC con menos digitos
        assertEquals("", fCCC.corregirCCC("111122235044"));

    }
    
}
