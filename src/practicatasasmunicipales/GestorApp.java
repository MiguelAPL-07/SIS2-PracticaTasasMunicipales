/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicatasasmunicipales;

import Practicas.PracticaDos;
import Practicas.PracticaUno;

/**
 *
 * @author PC
 */
public class GestorApp {
    
    public GestorApp() {
        
    }
    
    public void iniciarAplicacion() {
        //ejecucionPracticaUno();
        ejeccucionPracticaDos();
    }
    
    private void ejecucionPracticaUno() {
        PracticaUno p1 = new PracticaUno();
        p1.ejecucion();
    }
    
    private void ejeccucionPracticaDos() {
        PracticaDos p2 = new PracticaDos();
        p2.ejecucion();
    }
}
