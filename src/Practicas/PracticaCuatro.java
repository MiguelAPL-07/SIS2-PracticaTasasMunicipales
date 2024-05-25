/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practicas;

import POJOS.Contribuyente;
import POJOS.Lecturas;
import POJOS.Lineasrecibo;
import POJOS.Ordenanza;
import POJOS.Recibos;
import POJOS.RelContribuyenteOrdenanza;
import funcionesAuxiliares.Constantes;
import funcionesAuxiliares.Fecha;
import funcionesAuxiliares.FuncionDNI;
import funcionesAuxiliares.FuncionesRecibo;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import manager.ExcelManager;
import manager.PdfManager;
import modelo.HibernateUtil;
import modelo.LineasReciboModelo;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Miguel Ángel
 */
public class PracticaCuatro {
    
    // Conexion con la base de datos
    SessionFactory sf = null;
    Session session = null;
    
    private ExcelManager excelManager;
    
    private HashMap<String, ContribuyenteExcel> contribuyentes;
    
    private HashMap<String, OrdenanzaExcel> ordenanzas;
    
    public PracticaCuatro() {
        
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        
        excelManager = new ExcelManager(new File(Constantes.RUTA_ARCHIVO_ESCRIBIR));
        
        contribuyentes = excelManager.obtenerContribuyentes();
        
        ordenanzas = excelManager.obtenerOrdenanzas();
    }
    
    
    public void ejecuccion(String fechaPadron) {
        // Se guardan todas las ordenanzas en la base de datos
        insertarOrdenanzasBD();
        
        FuncionDNI fDNI = new FuncionDNI();
        FuncionesRecibo funcionesRecibo = new FuncionesRecibo();
        
        double totalBaseImponibleRecibos = 0;
        double totalIvaRecibos = 0;
        double totalRecibos = 0;
        
        for(int i = 1; i <= excelManager.getUltimaFilaContribuyentes(); i++) {
            // Comprobar documento
            if(contribuyentes.containsKey(String.valueOf(i))) {
    
                ContribuyenteExcel c = (ContribuyenteExcel) contribuyentes.get(String.valueOf(i));
                
                if(fDNI.validadorNIF_NIE(c.getNifnie()) == 0) {
                    
                    // Se agrega el contribuyente a la base de datos
                    if(insertarContribuyenteBD(c)) {
                        Contribuyente contribuyente = obtenerContribuyenteBD(c.getNifnie());
                    
                        Fecha f = new Fecha();
                        f = f.transformarFechaExcel(c.getFechaAlta());
                        // Verificamos si la fecha alta es anterior a la actual
                        if(!f.comprobarFechaPosteriorAFechaActual(f)) {

                            // Verifa si la fecha solicitada es posterior a la fecha alta
                            if(f.compruebaFechaPosteriorFechaAlta(fechaPadron)) {
                                List<LineasReciboModelo> lineasRecibo = funcionesRecibo.generarLineasRecibo(c);

                                double totalBaseImponible = funcionesRecibo.calcularTotalBaseImponible(lineasRecibo);
                                double totalIva = funcionesRecibo.calcularTotalImporteIva(lineasRecibo);
                                double totalRecibo = totalBaseImponible + totalIva;

                                int consumo = (int) funcionesRecibo.calcularTotalConsumo(lineasRecibo);
                                int lecturaActual = c.getLecturaActual();
                                int lecturaAnterior = c.getLecturaAnterior();

                                // Comprobamos si el contribuyente esta exento de pagar el recibo
                                if(!c.getExencion().isEmpty()) {
                                    if(c.getExencion().equalsIgnoreCase("S")) {
                                        totalBaseImponible = 0;
                                        totalIva = 0;
                                        totalRecibo = 0;
                                    }
                                } 
                                
                                // Se obtiene una ordenanza cuqluiera de las lineas recibo para tener el pueblo y tipoCalculo, todas poseen lo mismo para cada contribuyente
                                Ordenanza o = obtenerOrdenanzaBD(lineasRecibo.get(0).getIdConcepto(), lineasRecibo.get(0).getSubconcepto());
                                
                                // Crer PDF
                                PdfManager pdf = new PdfManager();

                                pdf.crearPdf(fechaPadron, c.getNifnie(), c.getNombre(), c.getApellido1(), c.getApellido2());
                                pdf.generarPDF(c, lineasRecibo, consumo, o.getPueblo(), o.getTipoCalculo(), f.transformaFechaPadronExtendida(fechaPadron), totalBaseImponible, totalIva, totalRecibo);

                                totalBaseImponibleRecibos += totalBaseImponible;
                                totalIvaRecibos += totalIva;
                                totalRecibos += totalRecibo;

                                // Guardar bd
                                String[] fechaPadron_dividido = fechaPadron.split(" ");
                                
                                // Se comprueba si el recibo para la fecha de padron ya ha sido generado
                                f = f.transformarFechaPadronTeclado(fechaPadron);
                                Date fPadron = new Date(f.getAno()-1900, f.getMes()-1, f.getDia());
                                if(!comprobarSiExisteReciboBD(fPadron, contribuyente.getNifnie())) {
                                    // Se guarda el recibo del contribuynte con su id
                                    insertarReciboBD(lecturaAnterior, lecturaActual, consumo, fechaPadron, totalBaseImponible, totalIva, totalRecibo, contribuyente);

                                    // Se guarda la lectura del contribuyente con su id
                                    insertarLecturaBD(fechaPadron_dividido[1], fechaPadron_dividido[0], lecturaAnterior, lecturaActual, contribuyente);

                                    // Se obtiene el id del recibo
                                    Recibos recibo = obtenerReciboBD(contribuyente.getNifnie());

                                    // Se guarda lineas recibo
                                    for(LineasReciboModelo lActual : lineasRecibo) {
                                        // Se inserta la linea actual del recibo
                                        if(insertarLineaRecibo(lActual, recibo)) {
                                            // Se obtiene la ordenanza de cada linea recibo
                                            Ordenanza ordenanza = obtenerOrdenanzaBD(lActual.getIdConcepto(), lActual.getSubconcepto());
                                            // Se guarda en rel_contribuyente_ordenanza el contribuyente y la ordenanza
                                            insertarRelContribuyenteOrdenanzaBD(contribuyente, ordenanza);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // Se cierra la conexion con la base de datos
        sf.close();
        session.close();
        
        Fecha f = new Fecha();
        
        // Crear pdf resumen
        PdfManager pdf = new PdfManager();
        pdf.crearPdfResumen(fechaPadron);
        pdf.generarPdfResumen(f.transformaFechaPadronExtendida(fechaPadron), totalBaseImponibleRecibos, totalIvaRecibos, totalRecibos);
    }
    
    public boolean insertarRelContribuyenteOrdenanzaBD(Contribuyente c, Ordenanza o) {
        boolean result = false;
        RelContribuyenteOrdenanza relOC = new RelContribuyenteOrdenanza();
        relOC.setContribuyente(c);
        relOC.setOrdenanza(o);
        try {
            // Se limpia la sesion para que no colapse con objetos diferentes                
            session.clear();
            // Inserta en base de datos
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(relOC);
            tx.commit();
            result = true;
        } catch (Exception e) {
            System.out.println("No se ha podido insertar la relacion entre contribuyente y ordenanza: " + c.getNifnie());
        }
        return result;
    }
    
    public boolean insertarLineaRecibo(LineasReciboModelo lineaRecibo, Recibos r) {
        boolean result = false;
        Lineasrecibo lr = new Lineasrecibo();
        lr.setConcepto(lineaRecibo.getConcepto());
        lr.setSubconcepto(lineaRecibo.getSubconcepto());
        lr.setBaseImponible(lineaRecibo.getBaseImponible());
        lr.setPorcentajeIva(lineaRecibo.getPorcentajeIva());
        lr.setImporteiva(lineaRecibo.getImporteIva());
        lr.setM3incluidos(lineaRecibo.getM3incluidos());
        lr.setBonificacion(lineaRecibo.getBonificacion());
        if(lineaRecibo.getImporteBonificacion() > 0) {
            lr.setImporteBonificacion(lineaRecibo.getImporteBonificacion());
        }
        lr.setRecibos(r);
        try {
            // Se limpia la sesion para que no colapse con objetos diferentes                
            session.clear();
            // Inserta en base de datos
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(lr);
            tx.commit();
            result = true;
        } catch (Exception e) {
            System.out.println("No se ha podido insertar la linea del recibo: " + lineaRecibo.getIdConcepto());
        }
        return result;
    }
    
    public boolean insertarLecturaBD(String ejercicio, String periodo, int lecturaAnterior, int lecturaActual, Contribuyente c) {
        boolean result = false;
        Lecturas l = new Lecturas();
        l.setEjercicio(ejercicio);
        l.setPeriodo(periodo);
        l.setLecturaAnterior(lecturaAnterior);
        l.setLecturaActual(lecturaActual);
        l.setContribuyente(c);
        try {
            // Se limpia la sesion para que no colapse con objetos diferentes                
            session.clear();
            // Inserta en base de datos
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(l);
            tx.commit();
            result = true;
        } catch (Exception e) {
            System.out.println("No se ha podido insertar la lectura del contribuyente con DNI: " + c.getNifnie());
        }
        return result;
    }
    
    public boolean insertarReciboBD(int lecturaAnterior, int lecturaActual, int consumom3, String fPadron,
            double totalBaseImponible, double totalIVA, double totalRecibo, Contribuyente c) {
        boolean result = false;
        Fecha f = new Fecha();
        Recibos r = new Recibos();
        r.setNifContribuyente(c.getNifnie());
        r.setDireccionCompleta(c.getDireccion() + c.getNumero());
        r.setNombre(c.getNombre());
        r.setApellidos(c.getApellido1() + c.getApellido2());
        
        f = f.obtenerFechaHoy();
        Date fechaRecibo = new Date(f.getAno()-1900, f.getMes()-1, f.getDia());
        r.setFechaRecibo(fechaRecibo);
        
        r.setLecturaAnterior(lecturaAnterior);
        r.setLecturaActual(lecturaActual);
        r.setConsumom3(consumom3);
        
        f = f.transformarFechaPadronTeclado(fPadron);
        Date fechaPadron = new Date(f.getAno()-1900, f.getMes()-1, f.getDia());
        r.setFechaPadron(fechaPadron);
        
        r.setTotalBaseImponible(totalBaseImponible);
        r.setTotalIva(totalIVA);
        r.setTotalRecibo(totalRecibo);
        if(!c.getIban().isEmpty()) {
            r.setIban(c.getIban());
        }
        if(!c.getEemail().isEmpty()) {
            r.setEmail(c.getEemail());
        }
        r.setExencion(String.valueOf(c.getExencion()));
        r.setContribuyente(c);
        try {
            // Se limpia la sesion para que no colapse con objetos diferentes
            session.clear();
            // Inserta en base de datos
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(r);
            tx.commit();
            result = true;
        } catch (Exception e) {
            System.out.println("No se ha podido insertar el recibo del contribuyente con DNI: " + c.getNifnie());
        }
        return result;
    }
    
    // Devuelve true si lo inserta en la base de datos o si ya esta metido en bd
    public boolean insertarContribuyenteBD(ContribuyenteExcel contribuyeteExcel) {
        boolean result = false;
        // Se comprueba si el contribuyente esta en base de datos
        if(!comprobarSiExisteContribuyenteBD(contribuyeteExcel.getNifnie())) {
            Fecha f = new Fecha();

            Contribuyente c = new Contribuyente();

            c.setNombre(contribuyeteExcel.getNombre());
            c.setApellido1(contribuyeteExcel.getApellido1());
            if(!contribuyeteExcel.getApellido2().isEmpty()) {
                c.setApellido2(contribuyeteExcel.getApellido2());
            }
            c.setNifnie(contribuyeteExcel.getNifnie());
            c.setDireccion(contribuyeteExcel.getDireccion());
            c.setNumero(contribuyeteExcel.getNumero());
            c.setPaisCcc(contribuyeteExcel.getPaisCCC());
            c.setCcc(contribuyeteExcel.getCcc());
            if(!contribuyeteExcel.getIban().isEmpty()) {
                c.setIban(contribuyeteExcel.getIban());
            }
            if(!contribuyeteExcel.getEmail().isEmpty()) {
                c.setEemail(contribuyeteExcel.getEmail());
            }
            c.setExencion(contribuyeteExcel.getExencion().charAt(0));
            c.setBonificacion(contribuyeteExcel.getBonificacion());
            f = f.transformarFechaExcel(contribuyeteExcel.getFechaAlta());
            Date fechaAlta = new Date(f.getAno()-1900, f.getMes()-1, f.getDia());
            c.setFechaAlta(fechaAlta);
            if(!contribuyeteExcel.getFechaBaja().isEmpty()) {
                f = f.transformarFechaExcel(contribuyeteExcel.getFechaBaja());
                Date fechaBaja = new Date(f.getAno()-1900, f.getMes()-1, f.getDia());
                c.setFechaBaja(fechaBaja);
            }
            try {
                // Se limpia la sesion para que no colapse con objetos diferentes
                session.clear();
                // Inserta en base de datos
                Transaction tx = session.beginTransaction();
                session.saveOrUpdate(c);
                tx.commit();
                result = true;
            } catch (Exception e) {
                System.out.println("No se ha podido insertar el contribuyente con DNI: " + c.getNifnie());
            }
        } else {
            result = true;
        }
        return result;
    }
    
    public void insertarOrdenanzasBD() {
        for(int i = 1; i <= excelManager.getUltimaFilaOrdenanzas(); i++) {
            // Comprobar documento
            if(ordenanzas.containsKey(String.valueOf(i))) {
                // Obtengo la ordenza que se corresponde con la fila actual
                OrdenanzaExcel oExcel = (OrdenanzaExcel) ordenanzas.get(String.valueOf(i));
                
                // Se comprueba si la ordenanza esta en base de datos
                if(!comprobarSiExisteOrdenanzaBD(oExcel.getIdOrdenanza(), oExcel.getSubconcepto())) {
                    // Creo objeto ordenanza correspondiente a la base de datos y seteo todos los campos
                    Ordenanza o = new Ordenanza();
                    o.setIdOrdenanza(oExcel.getIdOrdenanza());
                    o.setConcepto(oExcel.getConcepto());
                    o.setSubconcepto(oExcel.getSubconcepto());
                    o.setDescripcion(oExcel.getDescripcion());
                    if(!oExcel.getAcumulable().isEmpty()) {
                        o.setAcumulable(oExcel.getAcumulable());
                    }
                    o.setPrecioFijo(oExcel.getPrecioFijo());
                    o.setM3incluidos(oExcel.getM3incluidos());
                    if(oExcel.getPreciom3()> 0) {
                        o.setPreciom3(oExcel.getPreciom3());
                    }
                    if(oExcel.getPorcentajeSobreOtroConcepto() > 0) {
                        o.setPorcentaje(oExcel.getPorcentajeSobreOtroConcepto());
                    }
                    o.setConceptoRelacionado(oExcel.getSobreQueConcepto());
                    if(oExcel.getIva() > 0) {
                        o.setIva(oExcel.getIva());
                    }
                    if(!oExcel.getPueblo().isEmpty()) {
                        o.setPueblo(oExcel.getPueblo());
                    }
                    if(!oExcel.getTipoCalculo().isEmpty()) {
                        o.setTipoCalculo(oExcel.getTipoCalculo());
                    }
                    // Se limpia la sesion para que no colapse con objetos diferentes
                    session.clear();
                    // Inserta en base de datos
                    Transaction tx = session.beginTransaction();
                    session.saveOrUpdate(o);
                    tx.commit();
                }
            }
        }
    }
    
    public Contribuyente obtenerContribuyenteBD(String dni) {
        Contribuyente c = null;
        // Consulta a ejecutar
        String consulta = "SELECT c FROM Contribuyente c WHERE c.nifnie=:param1";
        try {
            // Modificamos los parametros
            Query query = session.createQuery(consulta).setParameter("param1", dni);
            // Obtenemos el resultado
            if(query.list() != null) {
                c = (Contribuyente) query.list().get(0); 
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el contribuyente con DNI: " + dni);
        }
        return c;
    }
    
    public Recibos obtenerReciboBD(String dni) {
        Recibos r = null;
        // Consulta a ejecutar
        String consulta = "SELECT r FROM Recibos r WHERE r.nifContribuyente=:param1";
        try {
            // Modificamos los parametros
            Query query = session.createQuery(consulta).setParameter("param1", dni);
            // Obtenemos el resultado      
            if(query.list() != null) {
                r = (Recibos) query.list().get(0); 
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el recibo con DNI: " + dni);
        }
        return r;
    }
    
    public Ordenanza obtenerOrdenanzaBD(int idOrdenanza, String subconcepto) {
        Ordenanza o = null;
        // Consulta a ejecutar
        String consulta = "SELECT o FROM Ordenanza o WHERE o.idOrdenanza=:param1 AND o.subconcepto=:param2";
        try {
            // Modificamos los parametros
            Query query = session.createQuery(consulta).setParameter("param1", idOrdenanza).setParameter("param2", subconcepto);
            // Obtenemos el resultado     
            if(query.list() != null) {
                o = (Ordenanza) query.list().get(0); 
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la ordenanza con IdOrdenanza: " + idOrdenanza + " y subconcepto: " + subconcepto);
        }

        return o;
    }
    
    public boolean comprobarSiExisteContribuyenteBD(String dni) {
        boolean result = false;
        // Consulta a ejecutar
        String consulta = "SELECT c FROM Contribuyente c WHERE c.nifnie=:param1";
        try {
            // Modificamos los parametros
            Query query = session.createQuery(consulta).setParameter("param1", dni);
            if(query.list() != null) {
                Contribuyente c = (Contribuyente) query.list().get(0); 
            }
            result = true;
        } catch (Exception e) {
            System.out.println("No existe el contribuyente con DNI: " + dni + ". Se agrega a base de datos");
        }
        return result;
    }
    
    public boolean comprobarSiExisteOrdenanzaBD(int idOrdenanza, String subconcepto) {
        boolean result = false;
        // Consulta a ejecutar
        String consulta = "SELECT o FROM Ordenanza o WHERE o.idOrdenanza=:param1 AND o.subconcepto=:param2";
        try {
            // Modificamos los parametros
            Query query = session.createQuery(consulta).setParameter("param1", idOrdenanza).setParameter("param2", subconcepto);
            if(query.list() != null) {
                Ordenanza o = (Ordenanza) query.list().get(0); 
            }
            result = true;
        } catch (Exception e) {
            System.out.println("No existe la ordenanza con IdOrdenanza: " + idOrdenanza + " y subconcepto: " + subconcepto + ". Se agrega a base de datos");
        }
        return result;
    }
    
    public boolean comprobarSiExisteReciboBD(Date fechaPadron, String nif) {
        boolean result = false;        
        // Consulta a ejecutar
        String consulta = "SELECT r FROM Recibos r WHERE r.fechaPadron=:param1 AND r.nifContribuyente=:param2";
        try {
            // Modificamos los parametros
            Query query = session.createQuery(consulta).setParameter("param1", fechaPadron).setParameter("param2", nif);
            // Obtenemos el resultado      
            Recibos r = null;
            if(query.list() != null) {
                r = (Recibos) query.list().get(0); 
                result = true;
            }
        } catch (Exception e) {
            System.out.println("No existe el recibo con Fecha de padron: " + fechaPadron.toString() + ". Se agrega a la base de datos");
        }
        return result;
    }
}