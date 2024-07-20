package aed;

import java.util.ArrayList;

public class SistemaSIU {

// INVARIANTE DE REPRESENTACION: 
//
// No hay dos claves repetidas, tanto en el Trie de carreras como en todos los Tries de Materias.
//
// Dos materias con distinto nombre y pertenecientes a distinta carrera, si pertenecen al mismo
// Infomateria, son consideradas la misma materia. Es decir, tienen el mismo Plantel,
// cupo e inscriptos.  
// 
// Los tipos de cargos docentes son cuatro: Profesor, Jefe de TP, Ayudante de primera y Ayudante
// de segunda y dichos valores son enteros mayores o iguales a 0.
//
// A los estudiantes se los distingue por su número de libreta y este identificador no se repite.
// Su valor se encuentra acotado.
// 
// Todas las claves definidas tienen un valor, dependiendo si es Carrera, Materia o Alumno este
// valor tiene un tipo de dato distinto.
//
// Si un estudiante está inscripto en una materia, el estudiante pertenece al Trie de estudiantes.
//
// 

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    //ATRIBUTOS PRIVADOS:

    private Tries<Tries<Omateria>> _carreras;
    private Tries<Integer> _estudiantes;

    //////////////////////// EJERCICIO 1 ////////////////////////

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){

        Tries<Tries<Omateria>> carreras = new Tries<>();                                // O(1)
            _carreras = carreras;                                                       // O(1)
        Tries<Integer> estudiantes = new Tries<>();                                     // O(1)
            _estudiantes = estudiantes;                                                 // O(1)
        Omateria pivote = new Omateria();                                               // O(1)

        for (int i = 0; i < infoMaterias.length; i++){                                  // M 

            //CADA i ES UN INFOMATERIA

            //EN ListadoMateriasAsociadas IREMOS GUARDANDO TODAS LAS MATERIAS PERTENECIENTES
            //AL MISMO INFOMATERIA i, YA QUE ESTE LISTADO LO GUARDAREMOS COMO ATRIBUTO
            //DEL OBJETO MATERIA.

            conjuntoMateria[] ListadoMateriasAsociadas = 
            new conjuntoMateria[infoMaterias[i].getParesCarreraMateria().length];       // O(1)

           
            for (int j = 0; j < infoMaterias[i].getParesCarreraMateria().length; j++){  // N_m
            
            // CADA j ES UN PAR CARRERA-MATERIA DE LA INFOMATERIA i.

            String carrera = infoMaterias[i].getParesCarreraMateria()[j].getCarrera();  // O(1)
            String materia = infoMaterias[i].getParesCarreraMateria()[j].getNombreMateria(); // O(1)

            //SI LA CARRERA NO EXISTE EN EL TRIE DE CARRERAS, LA AGREGAMOS
                if  (!carreras.pertenece(carrera)){ 
                        Tries<Omateria> materias = new Tries<>(); 
                        carreras.insertar(carrera, materias); 
                }                                                                   // tot: O(c)
                
            //SI ES EL PRIMER ELEMENTO DEL INFOMATERIA i, USAMOS EL PUNTERO "PIVOTE" PARA QUE 
            //LAS MATERIAS DE ESTE INFOMATERIA i, APUNTEN AL MISMO OBJETO-MATERIA Y COMPARTAN
            //INSCRIPTOS, PLANTEL Y CUPO

            if (j == 0){    
                
                if  (!carreras.recorrer(carrera).pertenece(materia)){               // O(c + m)
                        Omateria nuevaMateria = new Omateria();                     // O(1)
                        pivote = nuevaMateria;                                      // O(1)
                        carreras.recorrer((carrera)).insertar(materia,pivote);      // O(c + m)
                    }

                } else {

                    if  (!carreras.recorrer(carrera).pertenece(materia)){           // O(c + m)
                            carreras.recorrer(carrera).insertar(materia,pivote);    // O(c + m)
                    }                                                           // --------------
                }                                                              // tot: O(c + m) 
         

            //ACA VAMOS COMPLETANDO LOS CONJUNTOMATERIA DE EL OBJETO-MATERIA PARA TENER LA REFE
            //AL MOMENTO DE BORRAR. LA REFERENCIA ES EL LLAMADO AL TRIE DE MATERIAS DE "carrera"
            //PARA NO RECORRER EL STRING CARRERA EL BORRAR.

                Tries<Omateria> referencia = carreras.recorrer(carrera);                // O (c), queda absorbido por O(c+m)
                                                                                       
                ListadoMateriasAsociadas[j] = 
                new conjuntoMateria(carrera, materia, referencia);                      // O(1)
                                                                                        
                if (j == infoMaterias[i].getParesCarreraMateria().length - 1){          // O(1)
                    pivote.vincularMaterias(ListadoMateriasAsociadas);                  // O(1)
                }

            }

        } 

    //CREAMOS Y COMPLETAMOS EL TRIE DE ESTUDIANTES.
    for (int k = 0; k < libretasUniversitarias.length; k++){                           // E veces
        if (!estudiantes.pertenece(libretasUniversitarias[k])){                        // O(1)
            estudiantes.insertar(libretasUniversitarias[k],0);                   // O(1)
        }                                                                              //----------
    }                                                                                 // tot: O(1)*E
}
/*
Complejidad: 
Sumando las complejidades de cada linea de la implementacion, tenemos (O(c+m+1)*N_m)*M + E,
que por regla de complejidad queda en O ((c+m)*N_m)*M + E), 
que aplicando distribucion y entendiendo que "N_m * M = Mt" es el total de materias de  la facultad,
 queda en O(c*Mt + m*Mt + E) donde Mt es un entero natural que hace de multiplicador, 
 y dado que O (c*Mt) = O(c), entonces
la complejidad queda en 
                        O ( Σ|c| de la cantidad total de carreras +
                            Σ|m|de la cantidad total de materias + 
                            E)
 */

    //////////////////////// EJERCICIO 2 ////////////////////////

    public void inscribir(String estudiante, String carrera, String materia){

        //CARRERAZA ES EL TRIE DE MATERIAS DE LA CARRERA INGRESADA POR PARAMETRO
        Tries<Omateria> carreraza =  this._carreras.recorrer(carrera);  // O(|c|)
        //RES ES EL OBJETO MATERIA DE LA MATERIA INGRESADA POR PARAMETRO
        Omateria res = carreraza.recorrer(materia);   // O(|m|)

        res.inscribirAlumno(estudiante);     // O(1)

        //ACA INSERTAMOS/ACTUALIZAMOS ESTUDIANTE CON LA CANT. INSCRIPCIONES INCREMENTADA
        int incrementarAlumno = 0;   // O(1)
        if (_estudiantes.pertenece(estudiante)){  //O(1)
            incrementarAlumno = _estudiantes.recorrer(estudiante);   //O(1)
        } 
        incrementarAlumno++;     // O(1)
        _estudiantes.borrar(estudiante); // O(1)
        _estudiantes.insertar(estudiante, incrementarAlumno);   //O(1)
}
/*
 COMPLEJIDAD:
 O(|c| + |m|) pues para recorrer el trie carreras nos toma tiempo |c| que es el tamaño del string de la carrera en cuestión, idem para recorrer el trie para la materia
 en concreto nos toma |m| que es el tamaño del string de la materia. Como sabemos que los estudiantes son identificados con su LU de longitud acotada. Entonces las 
 operaciones que hacemos con el trie _estudiantes las consideramos como O(1).
 */

    //////////////////////// EJERCICIO 4 ////////////////////////

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){

        //CARRERAZA ES EL TRIE DE MATERIAS DE LA CARRERA INGRESADA POR PARAMETRO
        Tries<Omateria> carreraza =  this._carreras.recorrer(carrera);   //O(|c|) 

        //RES ES EL OBJETO MATERIA DE LA MATERIA INGRESADA POR PARAMETRO
        Omateria res = carreraza.recorrer(materia);  // O(|m|)

        res.inscribirProfesor(res,cargo);  // O(1)
    }
/*
 COMPLEJIDAD:
 O(|c| + |m|) pues para recorrer el trie carreras nos toma tiempo |c| que es el tamaño del string de la carrera en cuestión, idem para recorrer el trie para la materia
 en concreto nos toma |m| que es el tamaño del string de la materia. InscribirProfesor() son operaciones constantes (es solo una asignacion y una suma) entonces la tomamos
 como O(1).
 */

    //////////////////////// EJERCICIO 3 ////////////////////////

    public int inscriptos(String materia, String carrera){

        //CARRERAZA ES EL TRIE DE MATERIAS DE LA CARRERA INGRESADA POR PARAMETRO
        Tries<Omateria> carreraza =  this._carreras.recorrer(carrera);  // O(|c|)

        //RES ES EL OBJETO MATERIA DE LA MATERIA INGRESADA POR PARAMETRO       
        Omateria res = carreraza.recorrer(materia);   // O(|m|)

        return res.Inscriptos();	// O(1)
    }
/*
 COMPLEJIDAD:
 O(|c| + |m|) pues para recorrer el trie carreras nos toma tiempo |c| que es el tamaño del string de la carrera en cuestión, idem para recorrer el trie para la materia
 en concreto nos toma |m| que es el tamaño del string de la materia. El método Inscriptos() de Omateria es O(1) pues es un return de un atributo privado de la clase.
 */

     //////////////////////// EJERCICIO 5 ////////////////////////

    public int[] plantelDocente(String materia, String carrera){

        //CARRERAZA ES EL TRIE DE MATERIAS DE LA CARRERA INGRESADA POR PARAMETRO
        Tries<Omateria> carreraza =  this._carreras.recorrer(carrera);  // O(|c|) 

        //RES ES EL OBJETO MATERIA DE LA MATERIA INGRESADA POR PARAMETRO       
        Omateria materiaza = carreraza.recorrer(materia);   // O(|m|)

        //ACA MANIPULAMOS EL ARRAY PARA QUE COINCIDA CON EL ORDEN DADO EN EL TEST YA QUE 
        //EL ORDEN DE ENUM ESTA INVERTIDO CON RESPECTO A LO ESPERADO POR EL TEST.
        int[] res = new int[4];  // O(1)
        res[0] = materiaza.Plantel()[3];  // O(1)
        res[1] = materiaza.Plantel()[2];  // O(1)
        res[2] = materiaza.Plantel()[1];  // O(1)
        res[3] = materiaza.Plantel()[0];  // O(1)


        return res;	  // O(1)
    }
/*
 COMPLEJIDAD
 O(|c| + |m|) pues para recorrer el trie carreras nos toma tiempo |c| que es el tamaño del string de la carrera en cuestión, idem para recorrer el trie para la materia
 en concreto nos toma |m| que es el tamaño del string de la materia. Las demás operaciones son 4 asignaciones que las tomamos como tiempo constante O(1).
 */

     //////////////////////// EJERCICIO 6 ////////////////////////
    
    public boolean excedeCupo(String materia, String carrera){

        //CARRERAZA ES EL TRIE DE MATERIAS DE LA CARRERA INGRESADA POR PARAMETRO        
        Tries<Omateria> carreraza =  this._carreras.recorrer(carrera);  // O(|c|)

        //RES ES EL OBJETO MATERIA DE LA MATERIA INGRESADA POR PARAMETRO       
        Omateria res = carreraza.recorrer(materia);  // O(|m|)
        
        return res.Inscriptos() > res.Cupo();  // O(1)
    }
/*
 COMPLEJIDAD
 O(|c| + |m|) pues para recorrer el trie carreras nos toma tiempo |c| que es el tamaño del string de la carrera en cuestión, idem para recorrer el trie para la materia
 en concreto nos toma |m| que es el tamaño del string de la materia. Los métodos .Inscriptos() y .Cupo() son simplemente returns de atributos privados de la clase
 Omateria que son O(1).
 */

      //////////////////////// EJERCICIO 7 ////////////////////////

    public String[] carreras() {
        String[] listaCarreras = new String[_carreras.tamanio()]; //completar este String es O(Σ c∈C (1)) ya que se realiza una operación O(1) por cada carrera.
        _carreras.obtenerClavesEnOrdenLexicografico(_carreras.obtenerRaiz(), new StringBuffer(), listaCarreras, new int[]{0});  // O(Σ c∈C |c|) 
        //
        //O(1) ya que como se muestra en la implementacion del metodo en Tries.java, las operaciones elementales 
        // estan acotadas por la longitud fija del ArrayList del Nodo, que es 256. 
        return listaCarreras; // O(1)
    }
 
/*
 COMPLEJIDAD
 O(Σ c∈C (1)) + O(Σ c∈C |c|)  que es lo mismo que  O(Σ c∈C |c|).
 
 */

     //////////////////////// EJERCICIO 8 ////////////////////////

    public String[] materias(String carrera){
        Tries<Omateria> carreraza = this._carreras.recorrer(carrera); // O(|c|)
        String[] listaMaterias = carreraza.obtenerClaves(); ////completar este String es O(Σ mc∈Mc |mc|)
        return listaMaterias;  // O(1)
    }
        
/*
 COMPLEJIDAD es O(|c|) para acceder al Trie de materias de la carrera c, luego O(Σ mc∈Mc |mc|) que es la longitud de todas las materias de la
 carrera c, esto da O (|c| + Σ mc∈Mc |mc|)

 */

     //////////////////////// EJERCICIO 9 ////////////////////////

    public int materiasInscriptas(String estudiante){
        return this._estudiantes.recorrer(estudiante);  //  O(1)
    }
/* 
 COMPLEJIDAD
 O(1) pues como ya dijimos, estudiantes es un trie donde cada clave es una cadena (que representa el LU) y el valor es un entero que representa el numero de materias inscriptas.
 Entonces el recorrer busca el número de materias inscriptas de dicho estudiante que se representa con un LU, y como el LU es acotado entonces se considera como tiempo
 constante.
*/

     //////////////////////// EJERCICIO 10 ////////////////////////

    public void cerrarMateria(String materia, String carrera){

    
    //PRIMERO, NOS PARAMOS EN LA MATERIA A ELIMINAR, LA MISMA ES EL PUNTERO AL OBJETO-MATERIA "PIVOTE"
    //CREADO EN EL EJ.1. ESTE PIVOTE SIRVE PARA MODIFICAR TODAS LAS MATERIAS ASOCIADAS AL MISMO TIEMPO POR REFERENCIA.

       Omateria objetoMateria =  _carreras.recorrer(carrera).recorrer(materia); // O(c + m)
       ListaEnlazada<String> listado = objetoMateria.Alumnos(); // O(1)

    //SEGUNDO, VAMOS ELIMINANDO 1 INSCRIPCION A CADA LU REGISTRADA EN LA MATERIA PIVOTE
    //EL OBTENER Y ELIMINAR ES O(1) PORQUE SIEMPRE ESTAMOS OBTENIENDO EL PRIMERO DE LA LISTA ENLAZADA

       int longitudInicial = listado.longitud(); //fijo la longitud de la lista de alumnos
       for (int k = 0; k < longitudInicial; k++) { 
           String estudiante = listado.obtener(0);  // O(1) ya que obtiene siempre el primero 
            int inscripciones = _estudiantes.recorrer(estudiante) - 1; // O(1) ya que |LU| es acotado.
           _estudiantes.insertar(estudiante, inscripciones);         // O(1) ya que |LU| es acotado.
           listado.eliminar(0);            //  O(1) ya que elimina siempre el primero 
       }                                                            //  total = E_m*O(1) = O(E_m)                                                  


     //POR ULTIMO, BORRAMOS LAS MATERIAS ASOCIADAS INICIANDO EL BORRADO DESDE LA REFERENCIA GUARDADA,
     // ES DECIR, DESDE DONDE INICIA EL TRIE DE MATERIAS, HABIENDO "AHORRADO" RECORRER CADA CARRERA
       conjuntoMateria[] listadoMateriasAsoc = objetoMateria.ListadoMaterias(); // O(1)

       for (int i = 0; i < listadoMateriasAsoc.length; i++){ // O(Σ nc∈Nm |n|)
        listadoMateriasAsoc[i].Referencia().borrar(listadoMateriasAsoc[i].Materia()); // O (n perteneciente al conjuntomateria)
       }

/* 
 COMPLEJIDAD

 O(|c| + |m|) +  O(1) + O(E_m) + O(1) + O(Σ nc∈Nm |n|) = O(|c| + |m| + E_m + Σ nc ∈ Nm (|n|))
*/   


}

}


