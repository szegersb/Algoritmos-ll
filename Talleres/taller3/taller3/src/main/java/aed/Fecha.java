package aed;

public class Fecha {
    private int dia;
    private int mes;

    public Fecha(int dia, int mes) {
        this.dia = dia;
        this.mes = mes;
    }

    public Integer dia() {
        return this.dia;
    }

    public Integer mes() {
        return this.mes;
    }

    @Override
    public String toString() {
        String diaStr = String.valueOf(dia);
        String mesStr = String.valueOf(mes);
        return diaStr + "/" + mesStr;
    }

    @Override
    public boolean equals(Object otra) {
        // otro es null, oen True
        boolean oen = (otra == null);
        // clase es distinta
        boolean cd = otra.getClass() != this.getClass();
        if (oen || cd) {
            return false;
 } 
     Fecha otraFecha = (Fecha) otra;
     return (otraFecha.dia() == this.dia 
         && otraFecha.mes() == this.mes);

    }

    public void incrementarDia() {
        if (dia < diasEnMes(mes)) {
            dia = dia + 1;
        }
        else if (mes == 12) {
            dia = 1;
            mes = 1;
        }
        else if (dia == diasEnMes(mes)) {
            dia = 1;
            mes = mes + 1;
        }
    }

    private int diasEnMes(int mes) {
        int dias[] = {
                // ene, feb, mar, abr, may, jun
                31, 28, 31, 30, 31, 30,
                // jul, ago, sep, oct, nov, dic
                31, 31, 30, 31, 30, 31
        };
        return dias[mes - 1];
    }

}
