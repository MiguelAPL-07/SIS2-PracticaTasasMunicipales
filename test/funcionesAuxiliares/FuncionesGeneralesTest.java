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
 * @author PC
 */
public class FuncionesGeneralesTest {
    
    FuncionesGenerales fg;
    
    public FuncionesGeneralesTest() {
        fg = new FuncionesGenerales();
    }
    
    @Test
    public void test_comprobarCadenaSoloNumeros() {
        assertEquals(true, fg.comprobarCadenaSoloNumeros("846843484648468"));
        assertEquals(true, fg.comprobarCadenaSoloNumeros("846843484648468"));
        assertEquals(false, fg.comprobarCadenaSoloNumeros("asdfdsv"));
        assertEquals(false, fg.comprobarCadenaSoloNumeros("AAAAA"));
        assertEquals(false, fg.comprobarCadenaSoloNumeros("1234rgffdgaDDF"));
    }
    
    @Test
    public void test_comprobarCadenasSoloLetras() {
        assertEquals(true, fg.comprobarCadenaSoloLetras("AA"));
        assertEquals(false, fg.comprobarCadenaSoloLetras("1234"));
        assertEquals(false, fg.comprobarCadenaSoloLetras("AA12"));
        assertEquals(true, fg.comprobarCadenaSoloLetras("A"));
        assertEquals(true, fg.comprobarCadenaSoloLetras("AinfdugbiodcdnA"));
        assertEquals(false, fg.comprobarCadenaSoloLetras("A1"));
    }

    
}
