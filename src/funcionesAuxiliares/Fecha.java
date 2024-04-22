
package funcionesAuxiliares;

import java.time.LocalDate;

/**
 *
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
    
    // dd-mes-aaaa
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
    public Fecha transformarFechaEntradaTeclado(String fecha) {
        Fecha f = new Fecha();
        String[] partes = fecha.split("/");
        f.setMes(Integer.parseInt(partes[0]));
        f.setAno(Integer.parseInt(partes[1]));
        return f;
    }
    
    // Devuelve el numero de dias que tiene el mes solicitado
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
        return "Fecha{" + "dia=" + dia + ", mes=" + mes + ", ano=" + ano + '}';
    }
    
    
    
}
