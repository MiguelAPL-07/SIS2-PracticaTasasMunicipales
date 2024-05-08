/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import manager.ExcelManager;
import modelo.LineasReciboModelo;
import modeloExcel.ContribuyenteExcel;
import modeloExcel.OrdenanzaExcel;

/**
 *
 * @author Miguel Ángel
 */
public class FuncionesRecibo {
    
    private ExcelManager excelManager;
    
    private HashMap<String, OrdenanzaExcel> ordenanzas;
    
    public FuncionesRecibo() {
        excelManager = new ExcelManager(new File(Constantes.RUTA_ARCHIVO_ESCRIBIR));
        
        ordenanzas = excelManager.obtenerOrdenanzas();
    }
    
    public List<LineasReciboModelo> generarLineasRecibo(ContribuyenteExcel c) {
        List<LineasReciboModelo> lineasRecibo = new ArrayList<>();
        
        // Conceptos a cobrar
        String[] conceptos = c.getConceptosACobrar().split(" ");
        // Obtengo el consumo que hay entre lecturas
        int consumo = Integer.valueOf(c.getLecturaActual().replace(".0", "")) - Integer.valueOf(c.getLecturaAnterior().replace(".0", ""));
        
        double bonificacion = 0;
        if(!c.getBonificacion().isEmpty()) {
            bonificacion = Double.parseDouble(c.getBonificacion());
        }

        // Recorro todos los conceptos que tiene el recibo
        for(int i = 0; i < conceptos.length; i++) {
            // Obtengo el concepto a calcular
            String concepto = conceptos[i].replace(" ", "");
            
            // Compruebo si el concepto ya esta calculado
            if(!comprobarTodosConceptosCalculados(concepto, lineasRecibo)) {
                
                // Actualizo la lista con las nuevas lineas generadas
                lineasRecibo = generarLineasReciboConcepto(lineasRecibo, concepto, consumo, bonificacion);

               
                // Actualizo el consumo
                consumo = consumo - calcularTotalConsumo(lineasRecibo);
            }
        }
        return lineasRecibo;
    }
    
    // Actualiza la lista ingresada por parametro las lineas del recibo en base al concepto
    private List<LineasReciboModelo> generarLineasReciboConcepto(List<LineasReciboModelo> lineasRecibo, String concepto, int consumo, double bonificacion) {
        // Recorro todos los conceptos que hay en el Excel     
        for(int j = 1; j <= excelManager.getUltimaFilaOrdenanzas(); j++) {
                
            // Obtengo la ordenanza actual
            OrdenanzaExcel o = (OrdenanzaExcel) ordenanzas.get(String.valueOf(j));

            int idOrdenanza = 0;
            if(!o.getIdOrdenanza().isEmpty()) {
                idOrdenanza = Integer.parseInt(o.getIdOrdenanza().replace(".0", ""));
            }

            // Si el concepto coincide con el id de la ordenanza, se añade al recibo
            if(Integer.parseInt(concepto) == idOrdenanza) {

                // Preparar datos a usar
                int precioFijo = 0;
                if(!o.getPrecioFijo().isEmpty()) {
                    precioFijo = Integer.parseInt(o.getPrecioFijo().replace(".0", ""));
                }
                int m3incluidos = 0;
                if(!o.getM3incluidos().isEmpty()) {
                    m3incluidos = Integer.parseInt(o.getM3incluidos().replace(".0", ""));
                }
                double preciom3 = 0;
                if(!o.getPreciom3().isEmpty()) {
                    preciom3 = Double.parseDouble(o.getPreciom3());
                }
                double porcentajeSobreOtrosConceptos = 0;
                if(!o.getPorcentajeSobreOtroConcepto().isEmpty()) {
                     porcentajeSobreOtrosConceptos = Double.parseDouble(o.getPorcentajeSobreOtroConcepto());
                }
                double iva = 0;
                if(!o.getIva().isEmpty()) {
                     iva = Double.parseDouble(o.getIva());
                }
                String acumulable = "N";
                if(!o.getAcumulable().isEmpty()) {
                    acumulable = o.getAcumulable();
                }

                // Datos por cada concepto
                double baseImponible = 0;
                double importeIva = 0;

                int consumoTramo = 0;

                // Consumo por tramos no acumulables
                if(acumulable.equalsIgnoreCase("N")) {
                    // Tiene precio fijo
                    if(precioFijo > 0) {
                        baseImponible = precioFijo;
                        // Si el consumo es menor que lo que te inclye, nos quedamos con el consumo, no con todo el tramo
                        if(m3incluidos > consumo) {
                            consumoTramo = consumo;
                        } else {
                            consumoTramo = m3incluidos;
                        }
                    } else {
                        // Comprobamos los tramos
                        if(consumo > 0) {
                            // Si el consumo es menor que lo que te inclye, nos quedamos con el consumo, no con todo el tramo
                            if(m3incluidos > consumo) {
                                consumoTramo = consumo;
                            } else {
                                consumoTramo = m3incluidos;
                            }
                            // Se calcula la base imponible
                            baseImponible = consumoTramo * preciom3;
                        }
                    }
                } else {
                    // Consumo por tramos acumulables
                    if(precioFijo > 0) {
                        baseImponible = precioFijo;
                    }
                    if(consumo <= m3incluidos) {
                        baseImponible = baseImponible + consumo * preciom3;
                        consumoTramo = consumo;
                    }
                }

                // Se actualiza el consumo calculado
                consumo = consumo - consumoTramo;

                // Se comprueba si tiene otro porcentaje sobre otro concepto
                if(porcentajeSobreOtrosConceptos > 0) {
                    String sobreQueConcepto = o.getSobreQueConcepto().replaceAll(".0", "");
                    
                    // Comprueba si el otro concepto ya se ha calculado
                    boolean calculado = comprobarTodosConceptosCalculados(sobreQueConcepto, lineasRecibo);
                   
                    if(!calculado) {
                        // Se actualiza las lineas del recibo
                        lineasRecibo = generarLineasReciboConcepto(lineasRecibo, sobreQueConcepto, consumo, bonificacion);
                    }
                    
                    // Calcula Base Imponible sobre el otro concepto
                    double t = 0;
                    for(LineasReciboModelo lActual : lineasRecibo) {
                        if(lActual.getIdConcepto() == Integer.parseInt(sobreQueConcepto)) {
                            t = t + lActual.getBaseImponible();
                        }
                    }
                    baseImponible = t*porcentajeSobreOtrosConceptos/100;
                }

                // Se realiza las bonificaciones
                if(bonificacion > 0) {
                    baseImponible = baseImponible - baseImponible*bonificacion%100;
                }

                // Realizar calculo del iva: base imponible x iva %
                importeIva = baseImponible * iva / 100;

                // Agregar linea recibo
                lineasRecibo.add(new LineasReciboModelo(Integer.parseInt(concepto), 
                        o.getConcepto(), o.getSubconcepto(), consumoTramo, 
                        baseImponible, Double.parseDouble(o.getIva()), importeIva));
            }
        }
        return lineasRecibo;
    }
    
    public double calcularTotalBaseImponible(List<LineasReciboModelo> lineasRecibo) {
        double total = 0;
        for(LineasReciboModelo lineaActual : lineasRecibo) {
            total += lineaActual.getBaseImponible();
        }
        return total;
    }
    
    public double calcularTotalImporteIva(List<LineasReciboModelo> lineasRecibo) {
        double total = 0;
        for(LineasReciboModelo lineaActual : lineasRecibo) {
            total += lineaActual.getImporteIva();
        }
        return total;
    }
    
    public int calcularTotalConsumo(List<LineasReciboModelo> lineasRecibo) {
        int total = 0;
        for(LineasReciboModelo lineaActual : lineasRecibo) {
            total += (int) lineaActual.getM3incluidos();
        }
        return total;
    }
    
    private boolean comprobarTodosConceptosCalculados(String conceptos, List<LineasReciboModelo> lineasReciboGeneradas) {
        
        boolean r = true;

        String[] listaConceptos = conceptos.split(" ");
        // Recorro todos los conceptos que tiene el recibo
        for(int i = 0; i < listaConceptos.length; i++) {
            boolean encontrado = false;
            // Obtengo el concepto a calcular
            String concepto = listaConceptos[i].replace(" ", "");
            for(LineasReciboModelo lActual : lineasReciboGeneradas) {
                if(Integer.parseInt(concepto) == lActual.getIdConcepto()) {
                    encontrado = true;
                }
            }
            if(!encontrado) {
                r = false;
            }
        }
        
        return r;
    }
}
