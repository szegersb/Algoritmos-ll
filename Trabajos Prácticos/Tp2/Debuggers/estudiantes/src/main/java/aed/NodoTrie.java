package aed;

import java.util.ArrayList;

// INV. REP. Clase "NodoTrie": 
// El NodoTrie es cada Nodo dentro de un Trie 
// El Valor de un Nodo Trie es vacio si y solo si el Array Siguiente NO es nulo.
// Cada Nodo tiene una longitud fija de 256 espacios, asociado cada indexacion a su correspondiente caracter ASCII

public class NodoTrie<T> {
    private T _valor;
    private ArrayList<NodoTrie<T>> _siguiente;

    NodoTrie(){
        _siguiente = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            _siguiente.add(null); // Inicializa con "nulls" las pos de arrayList
        }
    }
}
