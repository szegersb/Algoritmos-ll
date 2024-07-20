package aed;

import java.util.*;

// INV. REP. Clase "ListaEnlazada": 
// Cuando la lista está vacía (_largo == 0), _primero y _ultimo son null.
// Cuando la lista no está vacía (_largo > 0), _primero y _ultimo no son null.
// Cuando hay un solo nodo en la lista (_largo == 1), _primero y _ultimo apuntan al mismo nodo.
// Para cualquier nodo n en la lista, si n._siguiente no es null, entonces n._siguiente._anterior es n.
// Para cualquier nodo n en la lista, si n._anterior no es null, entonces n._anterior._siguiente es n.
// _largo es el número de nodos en la lista y es no negativo.
// El puntero _anterior del primer nodo es null.
// El puntero _siguiente del último nodo es null.
// Un recorrido desde _primero a través de los punteros _siguiente visita exactamente _largo nodos.
// Un recorrido desde _ultimo a través de los punteros _anterior visita exactamente _largo nodos.

public class ListaEnlazada<T> implements Secuencia<T> {

    private Nodo _primero; // es el primer puntero
    private Nodo _ultimo; // es el ultimo puntero
    private int _largo; // es la cantidad de Nodos
    
    private class Nodo {
        Nodo _anterior; // el nodo anterior vinculado
        Nodo _siguiente; //el nodo siguiente vinculado
        T _valor; // el valor del nodo.

        public Nodo(T valor){ //constructor
               _valor = valor;
        }      

    }

    public ListaEnlazada() {
        _largo = 0;
        _primero = null;
        _ultimo = null;
    }  //  O(1)

    

    public int longitud() {
       return this._largo;
    }  //  O(1)

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo (elem); // genero el nuevo nodo a agregar a la lista
        if (this._largo == 0) {
            this._ultimo = nuevo;
            this._primero = nuevo; // nuevo sera el primero de la lista 
        } else { 
            Nodo actual = this._primero;
            this._primero = nuevo; // nuevo sera el primero de la lista 
            nuevo._siguiente = actual;
            actual._anterior = nuevo;
        }
        this._largo++; 
    }  //  O(1)

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo (elem);
        if (this._largo == 0) {
            this._ultimo = nuevo;
            this._primero = nuevo; // nuevo sera el primero de la lista 
        } else { 
            Nodo actual = this._ultimo;
            this._ultimo = nuevo;
            nuevo._anterior = actual;
            nuevo._anterior._siguiente = nuevo;
    }
        this._largo++;
    }  //  O(1)

    public T obtener(int i) {
        Nodo actual = this._primero;
        Nodo siguiente = this._primero;
       for (int j=0; j < i; j++){ 
            actual = siguiente;
            siguiente = siguiente._siguiente;
       }
       return siguiente._valor;
    }  //  O(n)

    public void eliminar(int i) {
        int j = 0;
        Nodo previo = this._primero;
        Nodo actual = this._primero;
        for (j = 0; j < i; j++){ 
            previo = actual;
            actual = actual._siguiente;
       }
       if (j == 0) {
        this._primero = actual._siguiente;
       } else {
        if (j == this._largo - 1) {
            this._ultimo = actual._anterior;
        } else { 
            actual._anterior._siguiente = actual._siguiente;
            actual._siguiente._anterior = actual._anterior;
        }
       }
       this._largo--;
    }  //  O(n)

    public void modificarPosicion(int indice, T elem) {
        Nodo nuevo = new Nodo (elem);
        int j = 0;
        Nodo previo = this._primero;
        Nodo actual = this._primero;
        for (j = 0; j < indice; j++){ 
            previo = actual;
            actual = actual._siguiente;
       } // actual es el nodo a reemplazar
       nuevo._anterior = actual._anterior;
       nuevo._siguiente = actual._siguiente;
       actual._siguiente._anterior = nuevo;
       if (indice != 0){
       actual._anterior._siguiente = nuevo;
    }
    }  //  O(n)

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<>();
        nuevaLista._largo = this._largo;
        nuevaLista._primero = this._primero;
        nuevaLista._ultimo = this._ultimo;
        Nodo actual = nuevaLista._primero;
        Nodo siguiente = nuevaLista._primero._siguiente;
        for (int j = 1; j < this._largo-1; j++){
            Nodo nuevo = new Nodo (obtener(j));
            nuevo._anterior = actual;
            nuevo._siguiente = siguiente;
            actual = siguiente;
            siguiente = siguiente._siguiente;
        }
        return nuevaLista;
    }  //  O(n)


    
    @Override
    public String toString() {
        StringBuffer sbuffer = new StringBuffer();
        String lista = "";
        for (int j = 0; j < this._largo-1;j++){
        lista = lista+obtener(j)+", ";
        }
       sbuffer.append("["+lista+obtener(this._largo-1)+"]");
       return sbuffer.toString();
    }  //  O(n)
}
