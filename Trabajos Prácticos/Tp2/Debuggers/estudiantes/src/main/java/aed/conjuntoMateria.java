package aed;
 
// INV.REP "CONJUNTOMATERIA"
// Es similar a InfoMateria pero no se usó esa clase porque no sabemos si se podia, ademas de que necesitamos hacer modificaciones sobre los mismos
// Cada conjuntoMateria es un struct de carrera - materia y una referencia al Trie de materias que
// pertenece a la "carrera".
// No existen 2 pares de ConjuntoMateria iguales
// materia pertence al Trie de la referencia

public class conjuntoMateria <T> {

    private String _carrera;
    private String _materia;
    private Tries<Omateria> _referencia;

    public conjuntoMateria(String c, String m, Tries<Omateria> referencia){
        this._carrera = c;
        this._materia = m;
        this._referencia = referencia;
    }

    public void modificarReferencia (Tries<Omateria> valor){
        this._referencia = valor;
    }  //  O(1)

    public String Carrera(){
        return this._carrera;
    }  //  O(1)

    public String Materia(){
        return this._materia;
    }  //  O(1)

    public Tries<Omateria> Referencia(){
        return this._referencia;
    }
}  //  O(1)
