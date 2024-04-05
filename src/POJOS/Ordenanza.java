package POJOS;
// Generated 04-abr-2024 18:04:20 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Ordenanza generated by hbm2java
 */
public class Ordenanza  implements java.io.Serializable {


     private int id;
     private int idOrdenanza;
     private String concepto;
     private String subconcepto;
     private String descripcion;
     private String acumulable;
     private Integer precioFijo;
     private Integer m3incluidos;
     private Double preciom3;
     private Double porcentaje;
     private Integer conceptoRelacionado;
     private Double iva;
     private String pueblo;
     private String tipoCalculo;
     private Set relContribuyenteOrdenanzas = new HashSet(0);

    public Ordenanza() {
    }

	
    public Ordenanza(int id, int idOrdenanza, String concepto, String subconcepto, String descripcion) {
        this.id = id;
        this.idOrdenanza = idOrdenanza;
        this.concepto = concepto;
        this.subconcepto = subconcepto;
        this.descripcion = descripcion;
    }
    public Ordenanza(int id, int idOrdenanza, String concepto, String subconcepto, String descripcion, String acumulable, Integer precioFijo, Integer m3incluidos, Double preciom3, Double porcentaje, Integer conceptoRelacionado, Double iva, String pueblo, String tipoCalculo, Set relContribuyenteOrdenanzas) {
       this.id = id;
       this.idOrdenanza = idOrdenanza;
       this.concepto = concepto;
       this.subconcepto = subconcepto;
       this.descripcion = descripcion;
       this.acumulable = acumulable;
       this.precioFijo = precioFijo;
       this.m3incluidos = m3incluidos;
       this.preciom3 = preciom3;
       this.porcentaje = porcentaje;
       this.conceptoRelacionado = conceptoRelacionado;
       this.iva = iva;
       this.pueblo = pueblo;
       this.tipoCalculo = tipoCalculo;
       this.relContribuyenteOrdenanzas = relContribuyenteOrdenanzas;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public int getIdOrdenanza() {
        return this.idOrdenanza;
    }
    
    public void setIdOrdenanza(int idOrdenanza) {
        this.idOrdenanza = idOrdenanza;
    }
    public String getConcepto() {
        return this.concepto;
    }
    
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    public String getSubconcepto() {
        return this.subconcepto;
    }
    
    public void setSubconcepto(String subconcepto) {
        this.subconcepto = subconcepto;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getAcumulable() {
        return this.acumulable;
    }
    
    public void setAcumulable(String acumulable) {
        this.acumulable = acumulable;
    }
    public Integer getPrecioFijo() {
        return this.precioFijo;
    }
    
    public void setPrecioFijo(Integer precioFijo) {
        this.precioFijo = precioFijo;
    }
    public Integer getM3incluidos() {
        return this.m3incluidos;
    }
    
    public void setM3incluidos(Integer m3incluidos) {
        this.m3incluidos = m3incluidos;
    }
    public Double getPreciom3() {
        return this.preciom3;
    }
    
    public void setPreciom3(Double preciom3) {
        this.preciom3 = preciom3;
    }
    public Double getPorcentaje() {
        return this.porcentaje;
    }
    
    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    public Integer getConceptoRelacionado() {
        return this.conceptoRelacionado;
    }
    
    public void setConceptoRelacionado(Integer conceptoRelacionado) {
        this.conceptoRelacionado = conceptoRelacionado;
    }
    public Double getIva() {
        return this.iva;
    }
    
    public void setIva(Double iva) {
        this.iva = iva;
    }
    public String getPueblo() {
        return this.pueblo;
    }
    
    public void setPueblo(String pueblo) {
        this.pueblo = pueblo;
    }
    public String getTipoCalculo() {
        return this.tipoCalculo;
    }
    
    public void setTipoCalculo(String tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }
    public Set getRelContribuyenteOrdenanzas() {
        return this.relContribuyenteOrdenanzas;
    }
    
    public void setRelContribuyenteOrdenanzas(Set relContribuyenteOrdenanzas) {
        this.relContribuyenteOrdenanzas = relContribuyenteOrdenanzas;
    }




}


