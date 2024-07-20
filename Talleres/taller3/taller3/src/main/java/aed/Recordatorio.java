package aed;

public class Recordatorio {
    private String mensaje;
    private Fecha fecha;
    private Horario horario;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        this.mensaje = mensaje;
        this.fecha = new Fecha(fecha.dia(), fecha.mes());
        this.horario = horario;
    }

    public Horario horario() {
        return this.horario;
    }

    public Fecha fecha() {
        return new Fecha(fecha.dia(), fecha.mes());
    }

    public String mensaje() {
        return this.mensaje;
    }

    @Override
    public String toString() {
        return mensaje + " @ " + fecha.toString() + " " + horario.toString();
    }

    @Override
    public boolean equals(Object otro) {
        boolean oen = (otro == null);
        boolean cd = (otro.getClass() != this.getClass());
        if (oen || cd){
            return false;
        }
        Recordatorio otroRecordatorio = (Recordatorio) otro;
        return (this.mensaje == otroRecordatorio.mensaje() && this.fecha.equals(otroRecordatorio.fecha())
         && this.horario.equals(otroRecordatorio.horario()));

    }

}
