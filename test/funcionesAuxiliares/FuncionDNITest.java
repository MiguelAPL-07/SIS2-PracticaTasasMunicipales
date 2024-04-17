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
public class FuncionDNITest {
    
    public FuncionDNITest() {
    }
    
    
    @Test
    public void test_validarDNI_casosSimples() {
        FuncionDNI fDNI = new FuncionDNI();
        // DNI vario
        assertEquals(2, fDNI.validadorNIF_NIE(""));
        // DNI correcto
        assertEquals(0, fDNI.validadorNIF_NIE("12345678Z"));
        // DNI incorrecto solo letras
        assertEquals(2, fDNI.validadorNIF_NIE("ABCd"));
        // DNI solo numeros, mas digitos
        assertEquals(2, fDNI.validadorNIF_NIE("1234567893"));
        // DNI solo numeros, menos digitos
        assertEquals(2, fDNI.validadorNIF_NIE("12345"));
        // DNI incorrecto sin letra pero subsanable
        assertEquals(1, fDNI.validadorNIF_NIE("33693230"));
         // DNI incorrecto letra incorrecta pero subsanable
        assertEquals(1, fDNI.validadorNIF_NIE("14754857L"));
        // DNI incorrecto pero subsanable y duplicado
        assertEquals(3, fDNI.validadorNIF_NIE("12345678A"));
        // DNI incorrecto pero subsanable y duplicado
        assertEquals(3, fDNI.validadorNIF_NIE("12345678"));
    }
    
    @Test
    public void test_validarNIE_casosSimples() {
        FuncionDNI fDNI = new FuncionDNI();
        // NIE correcto
        assertEquals(0, fDNI.validadorNIF_NIE("X9339365P"));
        assertEquals(0, fDNI.validadorNIF_NIE("Y4417420P"));
        assertEquals(0, fDNI.validadorNIF_NIE("Z1174494G"));
        // NIE incorrecto
        assertEquals(2, fDNI.validadorNIF_NIE("X053450"));
        assertEquals(2, fDNI.validadorNIF_NIE("X0134567AA"));
        // NIE incorrecto pero subsanable, letra incorrecta Y9679641J
        assertEquals(1, fDNI.validadorNIF_NIE("Y9679641M"));
        // NIE incorrecto pero subsanable, sin letra
        assertEquals(1, fDNI.validadorNIF_NIE("Y1234567"));
        // NIE incorrecto pero subsanable y duplicado
        assertEquals(3, fDNI.validadorNIF_NIE("X9339365"));
        // NIE incorrecto pero subsanable y duplicado
        assertEquals(3, fDNI.validadorNIF_NIE("Y4417420L"));
    }
    
    @Test
    public void test_corregitDocumento() {
        FuncionDNI fDNI = new FuncionDNI();
        // Sin letra
        assertEquals("12345678Z", fDNI.corregirDocumento("12345678"));
        // Letra incorrecta
        assertEquals("12345678Z", fDNI.corregirDocumento("12345678B"));
        // Sin letra NIE
        assertEquals("X9339365P", fDNI.corregirDocumento("X9339365"));
        assertEquals("Y4417420P", fDNI.corregirDocumento("Y4417420"));
        assertEquals("Z1174494G", fDNI.corregirDocumento("Z1174494"));
        // Letra incorrecta NIE
        assertEquals("Z6577493G", fDNI.corregirDocumento("Z6577493P"));
        assertEquals("Y2397509A", fDNI.corregirDocumento("Y2397509L"));
        assertEquals("X6516217H", fDNI.corregirDocumento("X6516217B"));
        
    }
    
    @Test
    public void test_comprobarDocumentoDuplicado() {
        FuncionDNI fDNI = new FuncionDNI();
        // Validamos varios y ya se agregan al array
        assertEquals(0, fDNI.validadorNIF_NIE("69822359V"));
        assertEquals(0, fDNI.validadorNIF_NIE("56726023L"));
        assertEquals(0, fDNI.validadorNIF_NIE("50074774W"));        
        // DNI duplicado en el array
        assertEquals(false, fDNI.comprobarDocumentoDuplicado("12345678Z"));  
        assertEquals(true, fDNI.comprobarDocumentoDuplicado("56726023L")); 
        
        // DNI subsanable pero duplicado
        assertEquals(3, fDNI.validadorNIF_NIE("56726023"));
        String docActualizado = fDNI.corregirDocumento("56726023");
        assertEquals("56726023L", docActualizado);
        assertEquals(true, fDNI.comprobarDocumentoDuplicado(docActualizado));
        
        // Comprobar NIE duplicado
        // Validamos varios y ya se agregan al array
        assertEquals(0, fDNI.validadorNIF_NIE("Z6577493G"));
        assertEquals(0, fDNI.validadorNIF_NIE("Y2397509A"));
        assertEquals(0, fDNI.validadorNIF_NIE("X6516217H"));   
        assertEquals(0, fDNI.validadorNIF_NIE("X9339365P"));
        assertEquals(0, fDNI.validadorNIF_NIE("Y4417420P"));
        assertEquals(0, fDNI.validadorNIF_NIE("Z1174494G"));   
        // NIE duplicado en el array
        assertEquals(false, fDNI.comprobarDocumentoDuplicado("Z1958640B"));  
        assertEquals(true, fDNI.comprobarDocumentoDuplicado("X9339365P")); 
        
        // NIE subsanable pero duplicado
        assertEquals(3, fDNI.validadorNIF_NIE("Z6577493"));
        String docActualizado2 = fDNI.corregirDocumento("Z6577493");
        assertEquals("Z6577493G", docActualizado2);
        assertEquals(true, fDNI.comprobarDocumentoDuplicado(docActualizado2));
    }
}
