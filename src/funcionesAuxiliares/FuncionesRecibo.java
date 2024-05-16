/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcionesAuxiliares;

import java.io.File;
import java.util.ArrayList;
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
        int consumo = c.getLecturaActual() - c.getLecturaAnterior();
        
        double bonificacion = c.getBonificacion();

        // Recorro todos los conceptos que tiene el recibo
        for(int i = 0; i < conceptos.length; i++) {
            // Obtengo el concepto a calcular
            int concepto = Integer.valueOf(conceptos[i].replace(" ", ""));
            
            // Compruebo si el concepto ya esta calculado
            if(!comprobarConceptoCalculado(concepto, lineasRecibo)) {
                
                // Actualizo la lista con las nuevas lineas generadas
                lineasRecibo = generarLineasReciboConcepto(lineasRecibo, concepto, consumo, bonificacion);

               
                // Actualizo el consumo
                consumo = consumo - calcularTotalConsumo(lineasRecibo);
            }
        }
        return lineasRecibo;
    }
    
    // Actualiza la lista ingresada por parametro las lineas del recibo en base al concepto
    private List<LineasReciboModelo> generarLineasReciboConcepto(List<LineasReciboModelo> lineasRecibo, int concepto, int consumo, double bonificacion) {
        // Recorro todos los conceptos que hay en el Excel     
        for(int j = 1; j <= excelManager.getUltimaFilaOrdenanzas(); j++) {
                
            // Obtengo la ordenanza actual
            OrdenanzaExcel o = (OrdenanzaExcel) ordenanzas.get(String.valueOf(j));

            int idOrdenanza = o.getIdOrdenanza();

            // Si el concepto coincide con el id de la ordenanza, se añade al recibo
            if(concepto == idOrdenanza) {

                // Preparar datos a usar
                int precioFijo = o.getPrecioFijo();

                int m3incluidos = o.getM3incluidos();

                double preciom3 = o.getPreciom3();

                double porcentajeSobreOtrosConceptos = o.getPorcentajeSobreOtroConcepto();

                double iva = o.getIva();

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
                    
                    // Comprobar si hay mas tramos acumulables para la misma ordenanza
                    if(comprobarUltimoTramoAcumulable(concepto)) {
                        if(precioFijo > 0) {
                            baseImponible = precioFijo;
                        }
                        if(consumo <= m3incluidos) {
                            consumoTramo = consumo;
                        } else {
                            consumoTramo = consumo;
                            baseImponible = baseImponible + consumoTramo * preciom3;
                        }
                    } else {
                        if(precioFijo > 0) {
                            baseImponible = precioFijo;
                        }
                        // Los tramos acumulables, acumulan los m3 que incluye cada tramo 
                        // pero se cobra todo el consumo en el tramo en el que sumado a los anteriores sea suficiente para combatir el consumo
                        // Se calcula los m3 que incluyen todos los tramos anteriores mas el actual para saber si es suficiente para el consumo
                        if(consumo <= calculaM3IncluidosAnterioresTramos(concepto, o.getSubconcepto())) {
                            consumoTramo = consumo;
                            baseImponible = baseImponible + consumoTramo * preciom3;
                        }
                    }
                }

                // Se actualiza el consumo calculado
                consumo = consumo - consumoTramo;
                
                double importeBonificacion = 0;
                // Se realiza las bonificaciones
                if(bonificacion > 0) {
                    if(baseImponible > 0) {
                        importeBonificacion = baseImponible*bonificacion/100;
                        baseImponible = baseImponible - importeBonificacion;
                    }
                }

                // Se comprueba si tiene otro porcentaje sobre otro concepto
                if(porcentajeSobreOtrosConceptos > 0) {
                    int sobreQueConcepto = o.getSobreQueConcepto();
                    
                    // Comprueba si el otro concepto ya se ha calculado
                    boolean calculado = comprobarConceptoCalculado(sobreQueConcepto, lineasRecibo);
                   
                    if(!calculado) {
                        // Se actualiza las lineas del recibo
                        lineasRecibo = generarLineasReciboConcepto(lineasRecibo, sobreQueConcepto, consumo, bonificacion);
                    }
                    
                    // Calcula Base Imponible sobre el otro concepto
                    double t = 0;
                    for(LineasReciboModelo lActual : lineasRecibo) {
                        if(lActual.getIdConcepto() == sobreQueConcepto) {
                            t = t + lActual.getBaseImponible();
                        }
                    }
                    baseImponible = t*porcentajeSobreOtrosConceptos/100;
                }

                

                // Realizar calculo del iva: base imponible x iva %
                importeIva = baseImponible * iva / 100;

                // Agregar linea recibo
                lineasRecibo.add(new LineasReciboModelo(concepto, 
                        o.getConcepto(), o.getSubconcepto(), consumoTramo, 
                        baseImponible, o.getIva(), importeIva, bonificacion, importeBonificacion));
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
    
    public boolean comprobarUltimoTramoAcumulable(int concepto) {
        boolean r = false;
        int numVeces = 0;
        for(int j = 1; j <= excelManager.getUltimaFilaOrdenanzas(); j++) {
            // Obtengo la ordenanza actual
            OrdenanzaExcel o = (OrdenanzaExcel) ordenanzas.get(String.valueOf(j));
            if(o.getIdOrdenanza() == concepto) {
                numVeces ++;
            }
        }
        // Si solo hay una ordenanza con ese concepto quiere decir que es el ultimo
        if(numVeces == 1) {
            r = true;
        }
        return r;
    }
    
    // Calcula la suma de los m3 incluidos por cada tramo acumulable de los tramos anteriores
    public int calculaM3IncluidosAnterioresTramos(int concepto, String subconcepto) {
        int m3incluidos = 0;
        boolean encontrado = false;
        for(int j = 1; j <= excelManager.getUltimaFilaOrdenanzas(); j++) {
            // Obtengo la ordenanza actual
            OrdenanzaExcel o = (OrdenanzaExcel) ordenanzas.get(String.valueOf(j));
            // Mientras no encuentre el subconcepto
            if(!encontrado) {
                // Compruebo si es el mismo concepto
                if(o.getIdOrdenanza() == concepto) {
                    // Agrego los m3 incluidos
                    m3incluidos += o.getM3incluidos();
                    if(o.getSubconcepto().equalsIgnoreCase(subconcepto)) {
                        encontrado = true;
                    }
                }
            }
        }
        return m3incluidos;
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
    
    private boolean comprobarConceptoCalculado(int concepto, List<LineasReciboModelo> lineasReciboGeneradas) {
        boolean r = false;
        for(LineasReciboModelo lActual : lineasReciboGeneradas) {
            if(lActual.getIdConcepto() == concepto) {
                r = true;
            }
        }
        return r;
    }
}
