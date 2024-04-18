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
public class FuncionEmailTest {
    
    private FuncionEmail fEmail;

    
    public FuncionEmailTest() {
        fEmail = new FuncionEmail();
    }
    
    @Test
    public void test_generarEmail() {
        // Email correcto
        assertEquals("ALA00@Agua2024.com", fEmail.generarEmail("Alberto", "Lopez", "Abella"));
        // Email correcto sin apellido 2
        assertEquals("JL00@Agua2024.com", fEmail.generarEmail("Juan", "Lopez", ""));
        // Email cambiando nombres o apellidos
        assertEquals("ALP00@Agua2024.com", fEmail.generarEmail("Alberto", "Lopez", "Perez"));
        assertEquals("JLA00@Agua2024.com", fEmail.generarEmail("Juan", "Lopez", "Abella"));
        assertEquals("AAL00@Agua2024.com", fEmail.generarEmail("Alberto", "Abella", "Lopez"));
        assertEquals("AL00@Agua2024.com", fEmail.generarEmail("Alberto", "Lopez", ""));
        assertEquals("ABB00@Agua2024.com", fEmail.generarEmail("alejandro", "barrio", "barrio"));
        // Email duplicado
        assertEquals("ALA01@Agua2024.com", fEmail.generarEmail("Alba", "Lopez", "Armenteros"));
        assertEquals("JL01@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("ALA02@Agua2024.com", fEmail.generarEmail("Alejandro", "Lopez", "Armenteros"));
        
        // Casos especiales
        assertEquals("JL02@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL03@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL04@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL05@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL06@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL07@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL08@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL09@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL10@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL11@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("PPP00@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP01@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP02@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP03@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP04@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP05@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP06@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP07@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP08@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP09@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP10@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP11@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP12@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP13@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP14@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        
        // CompruebaEmailDuplicado
        // Email sin duplicar
        assertEquals(false, fEmail.compruebaEmailDuplicado("JJP00@Agua2024.com"));
        assertEquals(false, fEmail.compruebaEmailDuplicado("JP00@Agua2024.com"));
        // Email duplicado
        assertEquals(true, fEmail.compruebaEmailDuplicado("JL05@Agua2024.com"));
        assertEquals(true, fEmail.compruebaEmailDuplicado("ALA00@Agua2024.com"));
        assertEquals(true, fEmail.compruebaEmailDuplicado("AL00@Agua2024.com"));
        
        // obtenerNumVecesEmailDuplicado
        // Email sin duplicar
        assertEquals(0, fEmail.obtenerNumVecesEmailDuplicado("MMM00@Agua2024.com"));
        // Email con uno duplicado
        assertEquals(1, fEmail.obtenerNumVecesEmailDuplicado("ABB00@Agua2024.com"));
        // Email con varios duplicado
        assertEquals(3, fEmail.obtenerNumVecesEmailDuplicado("ALA02@Agua2024.com"));
        assertEquals(15, fEmail.obtenerNumVecesEmailDuplicado("PPP14@Agua2024.com"));
        assertEquals(12, fEmail.obtenerNumVecesEmailDuplicado("JL11@Agua2024.com"));
        assertEquals(12, fEmail.obtenerNumVecesEmailDuplicado("JL00@Agua2024.com"));
        // Comprobar despues de validar
        assertEquals("PPP15@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals(16, fEmail.obtenerNumVecesEmailDuplicado("PPP14@Agua2024.com"));
    }
    
    /*
     @Test
    public void test_generarEmailCasosEspeciales() {
        assertEquals("JL02@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL03@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL04@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL05@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL06@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL07@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL08@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL09@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL10@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("JL11@Agua2024.com", fEmail.generarEmail("Jose", "Laurentis", ""));
        assertEquals("PPP00@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP01@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP02@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP03@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP04@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP05@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP06@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP07@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP08@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP09@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP10@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP11@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP12@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP13@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        assertEquals("PPP14@Agua2024.com", fEmail.generarEmail("Pepe", "Paez", "Peral"));
        
        // CompruebaEmailDuplicado
        // Email sin duplicar
        assertEquals(false, fEmail.compruebaEmailDuplicado("JJP00@Agua2024.com"));
        assertEquals(false, fEmail.compruebaEmailDuplicado("JP00@Agua2024.com"));
        // Email duplicado
        assertEquals(true, fEmail.compruebaEmailDuplicado("JL05@Agua2024.com"));
        assertEquals(true, fEmail.compruebaEmailDuplicado("ALA00@Agua2024.com"));
        assertEquals(true, fEmail.compruebaEmailDuplicado("AL00@Agua2024.com"));
        
        // obtenerNumVecesEmailDuplicado
        // Email sin duplicar
        assertEquals(0, fEmail.compruebaEmailDuplicado("MMM00@Agua2024.com"));
        // Email con uno duplicado
        assertEquals(1, fEmail.compruebaEmailDuplicado("ABB00@Agua2024.com"));
        // Email con varios duplicado
        assertEquals(2, fEmail.compruebaEmailDuplicado("ALA02@Agua2024.com"));
        assertEquals(15, fEmail.compruebaEmailDuplicado("PPP14@Agua2024.com"));
        assertEquals(12, fEmail.compruebaEmailDuplicado("JL11@Agua2024.com"));
        assertEquals(12, fEmail.compruebaEmailDuplicado("JL00@Agua2024.com"));
        // Comprobar despues de validar
        fEmail.generarEmail("Pepe", "Paez", "Peral");
        assertEquals(16, fEmail.compruebaEmailDuplicado("PPP14@Agua2024.com"));
    }
    
    @Test
    public void test_compruebaEmailDuplicadoc() {
        // Email sin duplicar
        assertEquals(false, fEmail.compruebaEmailDuplicado("JJP00@Agua2024.com"));
        assertEquals(false, fEmail.compruebaEmailDuplicado("JP00@Agua2024.com"));
        // Email duplicado
        assertEquals(true, fEmail.compruebaEmailDuplicado("JL05@Agua2024.com"));
        assertEquals(true, fEmail.compruebaEmailDuplicado("ALA00@Agua2024.com"));
        assertEquals(true, fEmail.compruebaEmailDuplicado("AL00@Agua2024.com"));
    }
    
    @Test
    public void test_obtenerNumVecesEmailDuplicado() {
        // Email sin duplicar
        assertEquals(0, fEmail.compruebaEmailDuplicado("MMM00@Agua2024.com"));
        // Email con uno duplicado
        assertEquals(1, fEmail.compruebaEmailDuplicado("ABB00@Agua2024.com"));
        // Email con varios duplicado
        assertEquals(2, fEmail.compruebaEmailDuplicado("ALA02@Agua2024.com"));
        assertEquals(15, fEmail.compruebaEmailDuplicado("PPP14@Agua2024.com"));
        assertEquals(12, fEmail.compruebaEmailDuplicado("JL11@Agua2024.com"));
        assertEquals(12, fEmail.compruebaEmailDuplicado("JL00@Agua2024.com"));
        // Comprobar despues de validar
        fEmail.generarEmail("Pepe", "Paez", "Peral");
        assertEquals(16, fEmail.compruebaEmailDuplicado("PPP14@Agua2024.com"));
    }*/
}
