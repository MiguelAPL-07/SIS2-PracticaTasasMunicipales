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
public class ContribuyenteExcel {
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String nifnie;
    private String direccion;
    private String numero;
    private String paisCCC;
    private String ccc;
    private String iban;
    private String email;
    private String exencion;
    private double bonificacion;
    private int lecturaAnterior;
    private int lecturaActual;
    private String fechaAlta;
    private String fechaBaja;
    private String conceptosACobrar;

    public ContribuyenteExcel() {
    }

    public ContribuyenteExcel(String nombre, String apellido1, String apellido2, String nifnie, String direccion, String numero, String paisCCC, String ccc, String iban, String email, String exencion, double bonificacion, int lecturaAnterior, int lecturaActual, String fechaAlta, String fechaBaja, String conceptosACobrar) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nifnie = nifnie;
        this.direccion = direccion;
        this.numero = numero;
        this.paisCCC = paisCCC;
        this.ccc = ccc;
        this.iban = iban;
        this.email = email;
        this.exencion = exencion;
        this.bonificacion = bonificacion;
        this.lecturaAnterior = lecturaAnterior;
        this.lecturaActual = lecturaActual;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.conceptosACobrar = conceptosACobrar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNifnie() {
        return nifnie;
    }

    public void setNifnie(String nifnie) {
        this.nifnie = nifnie;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPaisCCC() {
        return paisCCC;
    }

    public void setPaisCCC(String paisCCC) {
        this.paisCCC = paisCCC;
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExencion() {
        return exencion;
    }

    public void setExencion(String exencion) {
        this.exencion = exencion;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        this.bonificacion = bonificacion;
    }

    public int getLecturaAnterior() {
        return lecturaAnterior;
    }

    public void setLecturaAnterior(int lecturaAnterior) {
        this.lecturaAnterior = lecturaAnterior;
    }

    public int getLecturaActual() {
        return lecturaActual;
    }

    public void setLecturaActual(int lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getConceptosACobrar() {
        return conceptosACobrar;
    }

    public void setConceptosACobrar(String conceptosACobrar) {
        this.conceptosACobrar = conceptosACobrar;
    }

    
   
}
