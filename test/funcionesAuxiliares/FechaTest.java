/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PC
 */
public class FechaTest {
    
    Fecha fecha;
    
    public FechaTest() {
        fecha = new Fecha();
    }
    
    @Test
    public void test_comprobarFechaPosteriorAFechaActual() {
        Fecha f = new Fecha(21, 4, 2024);
        assertEquals(false, fecha.comprobarFechaPosteriorAFechaActual(f));
        
        f = new Fecha(23, 4, 2030);
        assertEquals(true, f.comprobarFechaPosteriorAFechaActual(f));
        
        f = new Fecha(21, 4, 2022);
        assertEquals(false, f.comprobarFechaPosteriorAFechaActual(f));
    }
}
