/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicatasasmunicipales;

import POJOS.Contribuyente;
import java.util.List;
import modelo.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author PC
 */
public class PracticaUno {
    SessionFactory sf = null;
    Session session = null;
    
    List<Contribuyente> contribuyentes;
    
    public PracticaUno() {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        
        contribuyentes = traerTodosLosContribuyentes();
        
        Contribuyente contribuyente = new Contribuyente();
        for(Contribuyente cActual : contribuyentes) {
            System.out.println(cActual.getNombre());
        }
    }
    
    public List<Contribuyente> traerTodosLosContribuyentes() {
        List<Contribuyente> resultado = null;
        String consulta = "FROM Contribuyente c";
        try {
            Query query = session.createQuery(consulta);
            resultado = query.list();
        } catch (Exception e) {
            System.out.println("No se ha podido cargar los contribuyentes");
        }
        return resultado;
    }
    
}