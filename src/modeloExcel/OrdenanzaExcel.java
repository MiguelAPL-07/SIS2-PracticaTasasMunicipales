/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloExcel;

/**
 *
 * @author Miguel Ángel
 */
public class OrdenanzaExcel {
    
    private String pueblo;
    private String tipoCalculo;
    private int idOrdenanza;
    private String concepto;
    private String subconcepto;
    private String descripcion;
    private String acumulable;
    private int precioFijo;
    private int m3incluidos;
    private double preciom3;
    private double porcentajeSobreOtroConcepto;
    private int sobreQueConcepto;
    private double iva;

    public OrdenanzaExcel() {
    }

    public OrdenanzaExcel(String pueblo, String tipoCalculo, int idOrdenanza, String concepto, String subconcepto, String descripcion, String acumulable, int precioFijo, int m3incluidos, double preciom3, double porcentajeSobreOtroConcepto, int sobreQueConcepto, double iva) {
        this.pueblo = pueblo;
        this.tipoCalculo = tipoCalculo;
        this.idOrdenanza = idOrdenanza;
        this.concepto = concepto;
        this.subconcepto = subconcepto;
        this.descripcion = descripcion;
        this.acumulable = acumulable;
        this.precioFijo = precioFijo;
        this.m3incluidos = m3incluidos;
        this.preciom3 = preciom3;
        this.porcentajeSobreOtroConcepto = porcentajeSobreOtroConcepto;
        this.sobreQueConcepto = sobreQueConcepto;
        this.iva = iva;
    }

    public String getPueblo() {
        return pueblo;
    }

    public void setPueblo(String pueblo) {
        this.pueblo = pueblo;
    }

    public String getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(String tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public int getIdOrdenanza() {
        return idOrdenanza;
    }

    public void setIdOrdenanza(int idOrdenanza) {
        this.idOrdenanza = idOrdenanza;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAcumulable() {
        return acumulable;
    }

    public void setAcumulable(String acumulable) {
        this.acumulable = acumulable;
    }

    public int getPrecioFijo() {
        return precioFijo;
    }

    public void setPrecioFijo(int precioFijo) {
        this.precioFijo = precioFijo;
    }

    public int getM3incluidos() {
        return m3incluidos;
    }

    public void setM3incluidos(int m3incluidos) {
        this.m3incluidos = m3incluidos;
    }

    public double getPreciom3() {
        return preciom3;
    }

    public void setPreciom3(double preciom3) {
        this.preciom3 = preciom3;
    }

    public double getPorcentajeSobreOtroConcepto() {
        return porcentajeSobreOtroConcepto;
    }

    public void setPorcentajeSobreOtroConcepto(double porcentajeSobreOtroConcepto) {
        this.porcentajeSobreOtroConcepto = porcentajeSobreOtroConcepto;
    }

    public int getSobreQueConcepto() {
        return sobreQueConcepto;
    }

    public void setSobreQueConcepto(int sobreQueConcepto) {
        this.sobreQueConcepto = sobreQueConcepto;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    @Override
    public String toString() {
        return "OrdenanzaExcel{" + "pueblo=" + pueblo + ", tipoCalculo=" + tipoCalculo + ", idOrdenanza=" + idOrdenanza + ", concepto=" + concepto + ", subconcepto=" + subconcepto + ", descripcion=" + descripcion + ", acumulable=" + acumulable + ", precioFijo=" + precioFijo + ", m3incluidos=" + m3incluidos + ", preciom3=" + preciom3 + ", porcentajeSobreOtroConcepto=" + porcentajeSobreOtroConcepto + ", sobreQueConcepto=" + sobreQueConcepto + ", iva=" + iva + '}';
    }

      
}
