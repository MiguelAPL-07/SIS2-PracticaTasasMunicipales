/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicatasasmunicipales;

import Practicas.PracticaUno;

/**
 *
 * @author PC
 */
public class GestorApp {
    
    public GestorApp() {
        
    }
    
    public void iniciarAplicacion() {
        ejecucionPracticaUno();
    }
    
    private void ejecucionPracticaUno() {
        PracticaUno p1 = new PracticaUno();
        p1.ejecucion();
    }
}
