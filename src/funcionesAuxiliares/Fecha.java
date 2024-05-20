
package funcionesAuxiliares;

import java.time.LocalDate;

/**
 * Esta clase sirve para trabajar con distintos formatos de fecha
 * dentro de la aplicación y tratar las fechas de toda la aplicación y los
 * formatos de Excel
 * @author Miguel Ángel
 */
public class Fecha {
    
    private int dia;
    private int mes;
    private int ano;
    
    public Fecha() {
        dia = 0;
        mes = 0;
        ano = 0;
    }
    
    public Fecha(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
    
    public boolean compruebaFechaPosteriorFechaAlta(String fechaPadron) {
        boolean r = false;
        // La primera parte corresponde con el trimestre y la segunda con el año
        String[] fechaPadron_dividida = fechaPadron.split(" ");
        
        if(ano < Integer.parseInt(fechaPadron_dividida[1])) {
            r = true;
        } else {
            if(ano == Integer.parseInt(fechaPadron_dividida[1])) {
                switch(fechaPadron_dividida[0]) {
                    case "1T":
                        if(mes < 4) {
                            r = true;
                        }
                        break;
                    case "2T":
                        if(mes < 7) {
                            r = true;
                        }
                        break;
                    case "3T":
                        if(mes < 10) {
                            r = true;
                        }
                        break;
                    case "4T":
                        r = true;
                        break;
                }
            }
        }
        
        return r;
    }
    
    public String transformaFechaPadronExtendida(String fechaPadron) {
        String r = "";
        
        // La primera parte corresponde con el trimestre y la segunda con el año
        String[] fechaPadron_dividida = fechaPadron.split(" ");
        
        switch(fechaPadron_dividida[0]) {
            case "1T":
                r = "Primer trimestre de " + fechaPadron_dividida[1];
                break;
            case "2T":
                r = "Segundo trimestre de " + fechaPadron_dividida[1];
                break;
            case "3T":
                r = "Tercer trimestre de " + fechaPadron_dividida[1];
                break;
            case "4T":
                r = "Cuarto trimestre de " + fechaPadron_dividida[1];
                break;
        }
        return r;
    }
    
    // dd-mm-aaaa
    public Fecha transformarFechaPadronTeclado(String fechaPadron) {
        Fecha f = new Fecha();
        
        // La primera parte corresponde con el trimestre y la segunda con el año
        String[] fechaPadron_dividida = fechaPadron.split(" ");
        
        switch(fechaPadron_dividida[0]) {
            case "1T":
                f.setDia(31);
                f.setMes(3);
                f.setAno(Integer.valueOf(fechaPadron_dividida[1]));
                break;
            case "2T":
                f.setDia(30);
                f.setMes(6);
                f.setAno(Integer.valueOf(fechaPadron_dividida[1]));
                break;
            case "3T":
                f.setDia(30);
                f.setMes(9);
                f.setAno(Integer.valueOf(fechaPadron_dividida[1]));
                break;
            case "4T":
                f.setDia(31);
                f.setMes(12);
                f.setAno(Integer.valueOf(fechaPadron_dividida[1]));
                break;
        }
        return f;
    }
    
    public Fecha obtenerFechaHoy() {
        LocalDate fechaActual = LocalDate.now();
        Fecha f = new Fecha();
        f.setAno(fechaActual.getYear());
        f.setMes(fechaActual.getMonthValue());
        f.setDia(fechaActual.getDayOfMonth());
        return f;
    }
    
    /**
     * Método que transforma la fecha de Excel en el formato dd-mm-aaaa
     * @param fecha Fecha de entrada en el formato dd-mes-aaaa
     * @return Devuelve un objeto fecha en el que su formato es dd-mm-aaaa
     */
    public Fecha transformarFechaExcel(String fecha) {
        Fecha f = new Fecha();
        String[] partes = fecha.split("-");
        f.setDia(Integer.parseInt(partes[0]));
        switch(partes[1]) {
            case "ene":
                f.setMes(1);
                break;
            case "feb":
                f.setMes(2);
                break;
            case "mar":
                f.setMes(3);
                break;
            case "abr":
                f.setMes(4);
                break;
            case "may":
                f.setMes(5);
                break;
            case "jun":
                f.setMes(6);
                break;
            case "jul":
                f.setMes(7);
                break;
            case "ago":
                f.setMes(8);
                break;
            case "sep":
                f.setMes(9);
                break;
            case "oct":
                f.setMes(10);
                break;
            case "nov":
                f.setMes(11);
                break;
            case "dic":
                f.setMes(12);
                break;
        }
        f.setAno(Integer.parseInt(partes[2]));
        return f;
    }
    
    /**
     * Método que devuelve el nombre del mes que se solicita
     * @param m Es el mes en número a obtener
     * @return Devuelve el nombre del mes completo en una cadena
     */
    public String obtenerMes(int m) {
        String f = "";
        switch(m) {
            case 1:
                f = "Enero";
                break;
            case 2:
                f = "Febrero";
                break;
            case 3:
                f = "Marzo";
                break;
            case 4:
                f = "Abril";
                break;
            case 5:
                f = "Mayo";
                break;
            case 6:
                f = "Junio";
                break;
            case 7:
                f = "Julio";
                break;
            case 8:
                f = "Agosto";
                break;
            case 9:
                f = "Septiembre";
                break;
            case 10:
                f = "Octubre";
                break;
            case 11:
                f = "Noviembre";
                break;
            case 12:
                f = "Diciembre";;
                break;
        }
        return f;
    }
    
    // mm/aaaa
    /**
     * Método que transforma una fecha de entrada en un objeto Fecha
     * @param fecha Formato de fecha de entrada mm/aaaa
     * @return Devuelve un objeto fecha que sigue el formato dd-mm-aaaa
     */
    public Fecha transformarFechaEntradaTeclado(String fecha) {
        Fecha f = new Fecha();
        String[] partes = fecha.split("/");
        f.setMes(Integer.parseInt(partes[0]));
        f.setAno(Integer.parseInt(partes[1]));
        return f;
    }
    
    // Devuelve el numero de dias que tiene el mes solicitado
    /**
     * Método que devuelve el número de días que tiene el me solicitado
     * @param mes Mes solicitado
     * @return Devuelve el número de días del mes
     */
    public int calcularNumDiasMes(int mes) {
        int num = 0;
        switch(mes) {
            case 1:
                num = 31;
                break;
            case 2:
                num = 28;
                break;
            case 3:
                num = 31;
                break;
            case 4:
                num = 30;
                break;
            case 5:
                num = 31;
                break;
            case 6:
                num = 30;
                break;
            case 7:
                num = 31;
                break;
            case 8:
                num = 31;
                break;
            case 9:
                num = 30;
                break;
            case 10:
                num = 31;
                break;
            case 11:
                num = 30;
                break;
            case 12:
                num = 31;
                break;
        }
        return num;
    }
    
    // Comprueba si la fecha introducida es posterior a la fecha actual del sistema
    /**
     * Método que comprueba si la fecha introducida es posterior a la 
     * fecha actual del sistema
     * @param f, Es la fecha a comprobar
     * @return Devuelve true si es posterior a la fecha actual, false si no lo es
     */
    public boolean comprobarFechaPosteriorAFechaActual(Fecha f) {
        boolean result = false;
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaEntrada = LocalDate.of(f.getAno(), f.getMes(), f.getDia());
        if(fechaEntrada.isAfter(fechaActual)) {
            result = true;
        }
        return result;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return dia + "/" + mes + "/" + ano;
    }  
}
