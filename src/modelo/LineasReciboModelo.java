/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Miguel Ángel
 */
public class LineasReciboModelo {
    
    private int idConcepto;
    
    private String concepto;
    
    private String subconcepto;
    
    private double m3incluidos;
    
    private double baseImponible;
    
    private double porcentajeIva;
    
    private double importeIva;

    public LineasReciboModelo() {
    }

    public LineasReciboModelo(int idConcepto, String concepto, String subconcepto, double m3incluidos, double baseImponible, double porcentajeIva, double importeIva) {
        this.idConcepto = idConcepto;
        this.concepto = concepto;
        this.subconcepto = subconcepto;
        this.m3incluidos = m3incluidos;
        this.baseImponible = baseImponible;
        this.porcentajeIva = porcentajeIva;
        this.importeIva = importeIva;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }
    
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getSubconcepto() {
        return subconcepto;
    }

    public void setSubconcepto(String subconcepto) {
        this.subconcepto = subconcepto;
    }

    public double getM3incluidos() {
        return m3incluidos;
    }

    public void setM3incluidos(double m3incluidos) {
        this.m3incluidos = m3incluidos;
    }

    public double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public double getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(double porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public double getImporteIva() {
        return importeIva;
    }

    public void setImporteIva(double importeIva) {
        this.importeIva = importeIva;
    }

    @Override
    public String toString() {
        return "LineasReciboModelo{" + "idConcepto=" + idConcepto + ", concepto=" + concepto + ", subconcepto=" + subconcepto + ", m3incluidos=" + m3incluidos + ", baseImponible=" + baseImponible + ", porcentajeIva=" + porcentajeIva + ", importeIva=" + importeIva + '}';
    }
    
    
}
