package aed;

public class Agenda {
    private Fecha fechaHoy;
    private Recordatorio[] recordatorios;
    private int tamaño;

    public Agenda(Fecha fechaActual) {
        Fecha nuevaFecha = new Fecha(fechaActual.dia(), fechaActual.mes());
        this.fechaHoy = nuevaFecha;
        this.recordatorios = new Recordatorio[0];
        this.tamaño = 0;
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        this.tamaño++;
        Recordatorio[] nuevoArreglo = new Recordatorio[this.tamaño];
        for (int i = 0; i < this.tamaño - 1; i++) {
            nuevoArreglo[i] = this.recordatorios[i];
        }
        nuevoArreglo[this.tamaño - 1] = recordatorio;
        this.recordatorios = nuevoArreglo;
    }

    @Override
    public String toString() {
        String nuevoStr = "";
        for (int i = 0; i < this.tamaño; i++) {
            if (this.recordatorios[i].fecha().equals(this.fechaHoy)){
                nuevoStr = nuevoStr + this.recordatorios[i] + "\n";
            }
        }
        nuevoStr = this.fechaHoy.toString() + "\n" + "=====\n" + nuevoStr;
        return nuevoStr;

    }

    public void incrementarDia() {
        int dia = this.fechaHoy.dia();
        int mes = this.fechaHoy.mes();
        if(this.fechaHoy.dia() == diasEnMes(this.fechaHoy.mes())){
            mes ++;
            dia = 1;
        }else if(mes == 12 && dia == 31) {
            mes = 1;
            dia = 1;
        } else {
            dia ++;
        }
        this.fechaHoy = new Fecha(dia, mes);
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
    
    public Fecha fechaActual() {
        return this.fechaHoy;
    }

}
