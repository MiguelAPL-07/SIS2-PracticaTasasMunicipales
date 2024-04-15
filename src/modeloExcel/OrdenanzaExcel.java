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
    private String idOrdenanza;
    private String concepto;
    private String subconcepto;
    private String descripcion;
    private String acumulable;
    private String precioFijo;
    private String m3incluidos;
    private String preciom3;
    private String porcentajeSobreOtroConcepto;
    private String sobreQueConcepto;
    private String iva;

    public OrdenanzaExcel() {
    }

    public OrdenanzaExcel(String pueblo, String tipoCalculo, String idOrdenanza, String concepto, String subconcepto, String descripcion, String acumulable, String precioFijo, String m3incluidos, String preciom3, String porcentajeSobreOtroConcepto, String sobreQueConcepto, String iva) {
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

    public String getIdOrdenanza() {
        return idOrdenanza;
    }

    public void setIdOrdenanza(String idOrdenanza) {
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

    public String getPrecioFijo() {
        return precioFijo;
    }

    public void setPrecioFijo(String precioFijo) {
        this.precioFijo = precioFijo;
    }

    public String getM3incluidos() {
        return m3incluidos;
    }

    public void setM3incluidos(String m3incluidos) {
        this.m3incluidos = m3incluidos;
    }

    public String getPreciom3() {
        return preciom3;
    }

    public void setPreciom3(String preciom3) {
        this.preciom3 = preciom3;
    }

    public String getPorcentajeSobreOtroConcepto() {
        return porcentajeSobreOtroConcepto;
    }

    public void setPorcentajeSobreOtroConcepto(String porcentajeSobreOtroConcepto) {
        this.porcentajeSobreOtroConcepto = porcentajeSobreOtroConcepto;
    }

    public String getSobreQueConcepto() {
        return sobreQueConcepto;
    }

    public void setSobreQueConcepto(String sobreQueConcepto) {
        this.sobreQueConcepto = sobreQueConcepto;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    @Override
    public String toString() {
        return "OrdenanzaExcel{" + "pueblo=" + pueblo + ", tipoCalculo=" + tipoCalculo + ", idOrdenanza=" + idOrdenanza + ", concepto=" + concepto + ", subconcepto=" + subconcepto + ", descripcion=" + descripcion + ", acumulable=" + acumulable + ", precioFijo=" + precioFijo + ", m3incluidos=" + m3incluidos + ", preciom3=" + preciom3 + ", porcentajeSobreOtroConcepto=" + porcentajeSobreOtroConcepto + ", sobreQueConcepto=" + sobreQueConcepto + ", iva=" + iva + '}';
    }
    
    
}
