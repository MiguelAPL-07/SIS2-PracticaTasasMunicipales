/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;

import POJOS.Contribuyente;
import POJOS.Recibos;
import java.util.List;
import java.util.Scanner;
import modelo.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Miguel Ángel
 */
public class PracticaUno {
    
    SessionFactory sf = null;
    Session session = null;
    
    List<Contribuyente> contribuyentes;
    List<Recibos> recibos;
    
    public PracticaUno() {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        
        // Cargar toda la base de datos
        contribuyentes = obtenerContribuyentesBD();
        recibos = obtenerRecibosBD();
    }
    
    public void ejecucion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("INTRODUCE UN NIF");
        String dni = scanner.nextLine();
        
        // Apartado 1
        Contribuyente c = buscarContribuyentePorDNI(dni);
        System.out.println("****** DATOS CONTRIBUYENTE ******");
        System.out.println("     Nombre: " + c.getNombre());
        System.out.println("  Apellidos: " + c.getApellido1() + " " + c.getApellido2());
        System.out.println("        NIF: " + c.getNifnie());
        System.out.println("  Direccion: " + c.getDireccion());
        System.out.println("*********************************");
        // Apartado 2
        // Apartado 3
        
        
        sf.close();
        session.close();
    }

    
    public Contribuyente buscarContribuyentePorDNI(String dni) {
        Contribuyente contribuyente = new Contribuyente();
        for(Contribuyente cActual : contribuyentes) {
            if(cActual.getNifnie().equalsIgnoreCase(dni.trim())) {
                contribuyente = cActual;
            }
        }
        return contribuyente;
    }

    
    public List<Contribuyente> obtenerContribuyentesBD() {
        List<Contribuyente> resultado = null;
        String consulta = "FROM Contribuyente c";
        try {
            Query query = session.createQuery(consulta);
            resultado = query.list();
        } catch (Exception e) {
            System.out.println("No se ha podido cargar la tabla contribuyentes");
        }
        return resultado;
    }
    
    public List<Recibos> obtenerRecibosBD() {
        List<Recibos> resultado = null;
        String consulta = "FROM Recibos c";
        try {
            Query query = session.createQuery(consulta);
            resultado = query.list();
        } catch (Exception e) {
            System.out.println("No se ha podido cargar la tabla recibos");
        }
        return resultado;
    }
}
