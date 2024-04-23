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
import org.hibernate.Transaction;

/**
 * Esta clase responde a los apartados de la Práctica Uno
 * Solicita un DNI, comprueba si esta en base de datos e imprime los datos
 * Actualiza los recibos del contribuyente con el DNI solicitado
 * Elimina los recibos con la base imponible menor de la media
 * @author Miguel Ángel
 */
public class PracticaUno {
    
    // Conexion con la base de datos
    SessionFactory sf = null;
    Session session = null;
    
    // Variables para guardar los datos obtenidos de la base de datos
    private List<Contribuyente> contribuyentes;
    private List<Recibos> recibos;
    
    /**
     * Constructor para inicializar las variables necesarias
     */
    public PracticaUno() {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        
        // Cargar toda la base de datos
        contribuyentes = obtenerContribuyentesBD();
        recibos = obtenerRecibosBD();
    }
    
    /**
     * El método ejecucción se encarga de llevar a cabo paso a paso la 
     * ejecucción completa de la práctica uno
     */
    public void ejecucion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("INTRODUCE UN NIF");
        String dni = scanner.nextLine();
        
        // Apartado 1
        Contribuyente contribuyenteSolicitado = buscarContribuyentePorDNI(dni);
        if(contribuyenteSolicitado.getNifnie() != null) {
            System.out.println("****** DATOS CONTRIBUYENTE ******");
            System.out.println("     Nombre: " + contribuyenteSolicitado.getNombre());
            System.out.println("  Apellidos: " + contribuyenteSolicitado.getApellido1() + " " + contribuyenteSolicitado.getApellido2());
            System.out.println("        NIF: " + contribuyenteSolicitado.getNifnie());
            System.out.println("  Direccion: " + contribuyenteSolicitado.getDireccion());
            System.out.println("*********************************");
            
            // Apartado 2
            for(Recibos rActual : recibos) {
                if(contribuyenteSolicitado.getIdContribuyente() == rActual.getContribuyente().getIdContribuyente()) {
                    System.out.println("Total recibo sin actualizar: " + rActual.getTotalRecibo());

                    // Actualizar atributo total recibo en la base de datos
                    rActual.setTotalRecibo(250);
                    Transaction tx = session.beginTransaction();
                    session.saveOrUpdate(rActual);
                    tx.commit();
                    
                    System.out.println("Total recibo actualizado a 250 euros\n");
                }
            }
            
            // Apartado 3
            double media = calcularMediaBaseImponible();
            System.out.println("La media de la base imponible es: " + media);
            int rEliminados = 0;
            for(Recibos r : recibos) {
                if(r.getTotalBaseImponible() < media) {
                    Transaction tx = session.beginTransaction();
                    String hqlBorrado = "DELETE Recibos r WHERE r.numeroRecibo=:param1";
                    session.createQuery(hqlBorrado).setParameter("param1", r.getNumeroRecibo()).executeUpdate();
                    tx.commit();                
                    rEliminados++;
                }
            }
            System.out.println("Total de recibos eliminados: " + rEliminados);
        } else {
            System.out.println("No se ha encontrado un contribuyente con ese NIF en el sistema");
        }

        sf.close();
        session.close();
    }

    /**
     * Método que busca un contribuyente por su DNI en la lista obtenida
     * @param dni El parámetro DNI indica el contribuyente a buscar por su DNI
     * @return 
     */
    private Contribuyente buscarContribuyentePorDNI(String dni) {
        Contribuyente contribuyente = new Contribuyente();
        for(Contribuyente cActual : contribuyentes) {
            if(cActual.getNifnie().equalsIgnoreCase(dni.trim())) {
                contribuyente = cActual;
            }
        }
        return contribuyente;
    }

    /**
     * Calcula la media de la base imponible de todos los recibos que hay en BD
     * @return La media de la base impobible
     */
    private Double calcularMediaBaseImponible() {
        double media = 0;       
        for(Recibos rActual : recibos) {
            media += rActual.getTotalBaseImponible();
        }
        media = media/recibos.size();
        return media;
    }
    
    /**
     * Método que obtiene todos los contribuyentes que hay en base de datos
     * @return Una lista con los contribuyentes
     */
    private List<Contribuyente> obtenerContribuyentesBD() {
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
    
    /**
     * Método que obtiene todos los recibos que hay en base de datos
     * @return Una lista con los recibos
     */
    private List<Recibos> obtenerRecibosBD() {
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
