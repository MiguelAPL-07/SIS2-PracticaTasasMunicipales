/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicatasasmunicipales;

import Practicas.PracticaCuatro;
import Practicas.PracticaDos;
import Practicas.PracticaTres;
import Practicas.PracticaUno;
import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.FuncionesGenerales;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Miguel Ángel
 */
public class GestorApp {
    
    private String fechaPadron;
    
    public GestorApp() {
        
    }
    
    public void iniciarAplicacion() {
        //ejecucionPracticaUno();
        ejeccucionPracticaDos();
        
        // Solicita por consola
        Scanner scanner = new Scanner(System.in);
        System.out.println("INTRODUCE UNA FECHA PARA GENERAR RECIBOS CON FORMATO: [1-4]T [año con 4 dígitos] Ejemplo: 1T 2024");
        fechaPadron = scanner.nextLine();
        
        // Comprueba que es correcto
        FuncionesGenerales f = new FuncionesGenerales();
        if(f.comprobarFechaPadronEntrada(fechaPadron)) {
            System.out.println("Fecha de padron introducida correctamente");
            File directorios = new File(Constantes.RUTA_GENERICA_RECIBO_PDF + "\\" + fechaPadron);
            if(!directorios.exists()) {
                if(directorios.mkdir()) {
                    System.out.println("Se ha creado la carpeta con fecha de generacion: " + fechaPadron);
                    ejecucionPracticaTres();
                    ejecucionPracticaCuatro();
                } else {
                    System.out.println("Error al crear directorios");
                }
            } else {
                System.out.println("La fecha de generacion de recibos ya ha sido ejecutada");
            }
        } else {
            System.out.println("Fecha de padron introducida incorrectamente");
        }
    }
    
    private void ejecucionPracticaUno() {
        PracticaUno p1 = new PracticaUno();
        p1.ejecucion();
    }
    
    private void ejeccucionPracticaDos() {
        PracticaDos p2 = new PracticaDos();
        p2.ejecucion();
    }
    
    private void ejecucionPracticaTres() {
        PracticaTres p3 = new PracticaTres();
        p3.ejecuccion(fechaPadron);
    }
    
    private void ejecucionPracticaCuatro() {
        PracticaCuatro p4 = new PracticaCuatro();
        p4.ejecuccion(fechaPadron);
    }
}
