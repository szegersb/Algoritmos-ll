package aed;

public class Horario {
    private int hora;
    private int minutos;

    public Horario(int hora, int minutos) {
        this.hora = hora;
        this.minutos = minutos;
    }

    public int hora() {
        return this.hora;
    }

    public int minutos() {
        return this.minutos;
    }

    @Override
    public String toString() {
        String horaStr = String.valueOf(hora);
        String minutosStr = String.valueOf(minutos);
        if (minutosStr.length() == 1 && minutos!=0){
            minutosStr = "0" + minutosStr;
        }
        
        return horaStr + ":" + minutosStr;
    }

    @Override
    public boolean equals(Object otro) {
        boolean oen = (otro == null);
        boolean cd = otro.getClass() != this.getClass();
        if (oen || cd) {
            return false;
        }

        Horario otroHorario = (Horario) otro;
        return (otroHorario.hora() == this.hora && otroHorario.minutos() == this.minutos);
    }

}
