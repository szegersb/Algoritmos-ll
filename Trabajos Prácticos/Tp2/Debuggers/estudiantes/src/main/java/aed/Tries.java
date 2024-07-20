package aed;

import java.util.ArrayList;


// INV. REP. Clase "Tries": 
// Un Trie contiene una única raíz
// El Trie es una concatenación de Nodos, cada Nodo se define en la clase "NodoTrie"
// Cuando una clave está definida, en el atributo Valor se define su significado, siendo paramétrico T.
// No admite insertar claves duplicadas
// Pertenece = true sii la clave está definida en el Trie
// Borrar solo se admite para claves que pertenecen al trie, pudiendo eliminarse sin tocar otras claves que compartan caracteres ni alterar el resto de las claves.
// El metodo recorrer devuelve el valor de la clave ingresada por parámetro. 

public class Tries<T> {

    private NodoTrie<T> _raiz;
    private int _tamanio; 

    Tries(){
        this._tamanio = 0;
        this._raiz = null;
    }  //  O(1)

private class NodoTrie<T> {
    private T _valor;
    private ArrayList<NodoTrie<T>> _siguiente;
    

    NodoTrie(){
        _siguiente = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            _siguiente.add(null); // Inicializa con "nulls" las pos de arrayList
        }
    }

    public T obtenerValor() {
        return _valor;
    }

    public ArrayList<NodoTrie<T>> obtenerSiguiente() {
        return _siguiente;
    }
}


public int tamanio(){
    return this._tamanio;
}  //  O(1)

public boolean pertenece (String palabra){ 
                                           
    boolean res = false;
    NodoTrie<T> actual_pertenece = new NodoTrie<>();

    if (this._raiz != null) {
        actual_pertenece = this._raiz;
        int posicion = 0;   
        int j =0;

        for (j = 0; j < palabra.length();j++){ 
            //este for recorre la palabra ingresada y corta en caso de que no esté completa en el Trie
            posicion = (int) palabra.charAt(j); 
            if (actual_pertenece._siguiente.get(posicion) != null){
                actual_pertenece = actual_pertenece._siguiente.get(posicion);
            } else {
                break;
            }       
        }  

        if (actual_pertenece._valor != null && j == palabra.length()){
            res = true;
        } //es decir, que está definida la clave con su valor y no es parte de una clave mas grande.
    }
    
    return res;
}  //  O(|k|) k = palabra

public void insertar (String palabra, T valor){
                                        //asumimos que la palabra ingresada no está previamente definida.
                                        //esto se deberá validar con un pertenece() antes de insertar.
    if (this._raiz == null){
        NodoTrie<T> nuevaRaiz = new NodoTrie<>();
        // for (int i = 0; i < 256; i++) {
        //     nuevaRaiz._siguiente.add(null); // Inicializa con "nulls" las pos de arrayList
        // }
        this._raiz = nuevaRaiz;
    }  //inserto una raiz si el Trie es null.

    NodoTrie<T> actual = new NodoTrie<>();
    actual = this._raiz;
    int posicion = 0;

    for (int j = 0; j < palabra.length();j++){
            posicion = (int) palabra.charAt(j);
            // System.out.println("Letra: " + palabra.charAt(j) ); 
            if (actual._siguiente.get(posicion) != null) {
                actual = actual._siguiente.get(posicion); 
            } else {
                NodoTrie<T> nuevo = new NodoTrie<>();
                // for (int i = 0; i < 256; i++) {
                //     nuevo._siguiente.add(null); // Inicializa con "nulls" las pos de arrayList
                // }                         
                // System.out.println("Pos nodo: " + j + "  es: " + posicion ); // Botonea las posiciones de nodosTrie que estoy usando
                actual._siguiente.set(posicion, nuevo);
                actual = actual._siguiente.get(posicion);
            }
        
        } 
        //el for va creando nuevos nodos y los hilvana a menos que ya este creado.
        // por ejemplo, si quiero ingresar "palabra" pero ya tengo definida "pala"
        //voy recorriendo hasta pala, y luego creo nodos para definir "b", "r" y "a", 
        
    actual._valor = valor;
    this._tamanio++;
  
}  //  O(|k|) k = palabra

public void borrar (String palabra){

    NodoTrie<T> actual = new NodoTrie<>();
    actual = this._raiz;
    NodoTrie<T> testigo = new NodoTrie<>();
    testigo = actual;

    int posicion = 0;

    for (int j = 0; j < palabra.length();j++){
        posicion = (int) palabra.charAt(j);
        actual = actual._siguiente.get(posicion);

        if (!esHoja(actual)){
            testigo = actual._siguiente.get(posicion);
        }
    } 
    actual._valor = null;
    if (actual._siguiente == null){
       testigo._siguiente = null; 
    }
    this._tamanio--;
}  //  O(|k|) k = palabra


private boolean esHoja(NodoTrie<T> Nodo){
    int i = 0;
    int count = 0;
   while (i < 256 && count < 2){  
    if (Nodo._siguiente.get(i) != null){
        count++;
    }
    i++;
   }
   return (i == 256); // si i != 256 es porque count llegó a 2, 
                    //por lo que el nodo siguiente tiene mas de una clave definida
}  // O(1)

public T recorrer(String palabra){
        NodoTrie<T> actual = new NodoTrie<>();
        actual = this._raiz;
        int posicion = 0;
        for (int i = 0; i < palabra.length();i++){
            posicion = (int) palabra.charAt(i);
            actual = actual._siguiente.get(posicion);
        }
        return actual._valor;
}  //  O(|k|) k = palabra

public NodoTrie<T> obtenerRaiz() {
    return this._raiz;
}  // O(1)

public String[] obtenerClaves() { 
    String[] claves = new String[tamanio()];
    StringBuffer prefijo = new StringBuffer();
    obtenerClavesRecursivo(_raiz, prefijo, claves, new int[]{0});
    return claves;
    } // O(|n|) donde n es la longitud del String al que se aplica el método.


    private void obtenerClavesRecursivo(NodoTrie<T> nodo, StringBuffer prefijo, String[] claves, int[] index) {
        if (nodo == null) {
            return; // O(1) por ser op. elemental
        }
    
        if (nodo.obtenerValor() != null) {
            claves[index[0]++] = prefijo.toString();  // O(1) por ser op. elementales
        }
    
        for (int i = 0; i < 256; i++) { // O(1) ya que las op. elementales estan acotadas por 256.
            NodoTrie<T> siguiente = nodo.obtenerSiguiente().get(i); // O(1)
            if (siguiente != null) {  // O(1)
                prefijo.append((char) i); // O(1)
                obtenerClavesRecursivo(siguiente, prefijo, claves, index);  // O(1)
                prefijo.deleteCharAt(prefijo.length() - 1); // O(1)
            }
        }
    } //total O(1)

public void obtenerClavesEnOrdenLexicografico(NodoTrie<T> nodo, StringBuffer prefijo, String[] lista, int[] index) {
    if (nodo == null) {
        return;
    } // O(1)

    if (nodo.obtenerValor() != null) {
        lista[index[0]++] = prefijo.toString();
    } // O(1)

    for (int i = 0; i < 256; i++) { // O(1) ya que las op. elementales estan acotadas por 256.
        NodoTrie<T> siguiente = nodo.obtenerSiguiente().get(i);
        if (siguiente != null) {
            prefijo.append((char) i);
            obtenerClavesEnOrdenLexicografico(siguiente, prefijo, lista, index);
            prefijo.deleteCharAt(prefijo.length() - 1);
        }
    }
} //  O(Σ c∈C |c|)
        
 
    
}
