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
    
    @Test
    public void compruebaFechaPosteriorFechaAlta() {
        // Fecha alta
        fecha = new Fecha(01, 04, 2023);

        assertEquals(false, fecha.compruebaFechaPosteriorFechaAlta("1T 2023"));
        
        assertEquals(true, fecha.compruebaFechaPosteriorFechaAlta("2T 2023"));
        
        assertEquals(true, fecha.compruebaFechaPosteriorFechaAlta("3T 2023"));
        
        assertEquals(true, fecha.compruebaFechaPosteriorFechaAlta("4T 2023"));
        
        fecha = new Fecha(10, 02, 2023);
        
        assertEquals(true, fecha.compruebaFechaPosteriorFechaAlta("1T 2023"));
        
        fecha = new Fecha(01, 01, 2024);
        
        assertEquals(true, fecha.compruebaFechaPosteriorFechaAlta("1T 2024"));
    }
    
    @Test
    public void generaFechaGeneracionRecibo() {
        
        assertEquals("Primer trimestre de 2023", fecha.transformaFechaPadronExtendida("1T 2023"));
        
        assertEquals("Segundo trimestre de 2023", fecha.transformaFechaPadronExtendida("2T 2023"));
        
        assertEquals("Tercer trimestre de 2023", fecha.transformaFechaPadronExtendida("3T 2023"));
        
        assertEquals("Cuarto trimestre de 2023", fecha.transformaFechaPadronExtendida("4T 2023"));
        
        assertEquals("Primer trimestre de 2024", fecha.transformaFechaPadronExtendida("1T 2024"));
    }
}
