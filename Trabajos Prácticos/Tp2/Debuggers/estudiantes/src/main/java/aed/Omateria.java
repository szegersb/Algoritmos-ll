package aed;

import aed.SistemaSIU.CargoDocente;

// INV. REP. Clase "Omateria": 
// Omateria es el objeto materia en sí. Las materias tienen a lo sumo 4 tipos de Docentes.
// El cupo de Omateria es un Natural y se calcula en funcion de la cantidad de cada cargo docente.
// La Cantidad de inscriptos de una Omateria es un Natural, es el mismo para todas las pertenecientes al mismo infoMateria.
// El plantel de una Omateria, es el mismo para todas las pertenecientes al mismo infoMateria.
// La Lista de LU de inscriptos no contiene repetidos
// El listado de materias contiene los nombres de el objeto para cada carrera en la que esté representada
//un mismo Omateria, tiene tantos nombres como elementos tenga el atributo ListadoMaterias, ni mas, ni menos.
// Los elementos de la listaMaterias no repiten el nombre de las carreras a las que representa.

public class Omateria {

    private int[] _plantel;
    private int _cupo;
    private int _inscriptos;
    private ListaEnlazada<String> _alumnos;
    private conjuntoMateria[] _ListadoMaterias;

    public Omateria(){
        this._plantel = new int[4];
        this._cupo = 0;
        this._inscriptos = 0;
        this._alumnos = new ListaEnlazada();
        this._ListadoMaterias = new conjuntoMateria[10];
    }  //  O(1)

    public void vincularMaterias (conjuntoMateria[] listado){
        this._ListadoMaterias = listado;
    }  //  O(1)

    public conjuntoMateria[] ListadoMaterias(){
        return this._ListadoMaterias;
    }  //  O(1)

    public int[] Plantel(){
        return this._plantel;
    }  //  O(1)

    public int Cupo(){
        return this._cupo;
    }  //  O(1)

    public int Inscriptos(){
        return this._inscriptos;
    }  //  O(1)

    public ListaEnlazada Alumnos(){
        return this._alumnos;
    }  //  O(1)

    public void calcularCupo(){
        int profesores = this._plantel[CargoDocente.PROF.ordinal()];
        int jtps = this._plantel[CargoDocente.JTP.ordinal()];
        int ay1s = this._plantel[CargoDocente.AY1.ordinal()];
        int ay2s = this._plantel[CargoDocente.AY2.ordinal()];
 
 
        int maxProf = profesores * 250;
        int maxJtp = jtps * 100;
        int maxAy1 = ay1s * 20;
        int maxAy2 = ay2s * 30;
 
 
        this._cupo = Math.min(Math.min(maxProf, maxJtp), Math.min(maxAy1, maxAy2));
}  //  O(1)


    public void inscribirProfesor (Omateria m, CargoDocente docente){ //ojo que esto va a sumar 1 profe
                                                                     // cada ves q se llama al metodo.
        this._plantel[docente.ordinal()] = this._plantel[docente.ordinal()] + 1 ;
        this.calcularCupo();
    }  //  O(1)

    public void inscribirAlumno (String estudiante){
        this._inscriptos++;
        _alumnos.agregarAdelante(estudiante);
    }  //  O(1)

}
