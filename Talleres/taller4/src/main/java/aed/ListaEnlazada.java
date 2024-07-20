package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private int tamaño;
    private Nodo primero;
    private Nodo ultimo;
    
    //creo la clase nodo privada para que solo la pueda usar aca
    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;
        Nodo(T v) { // asi se define un constructor nodo
            valor = v;
            sig = null;
            ant = null;
        }
    }

    public ListaEnlazada() {
        tamaño = 0;
        primero = null;
        ultimo = null;
    }

    public int longitud() {
        return tamaño;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevoNodo = new Nodo(elem);
        if (tamaño == 0) {
            tamaño = 1;
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        }
        else {
            tamaño ++;
            nuevoNodo.sig = primero;
            primero.ant = nuevoNodo;
            primero = nuevoNodo;
        }
    }

    public void agregarAtras(T elem) {
        Nodo nuevoNodo = new Nodo(elem);
        if (tamaño == 0) {
            tamaño = 1;
            primero = nuevoNodo;
            ultimo = nuevoNodo;
        }
        else {
            tamaño ++;
            ultimo.sig = nuevoNodo;
            nuevoNodo.ant = ultimo;
            ultimo = nuevoNodo;
        }

    }

    public T obtener(int i) {
        Nodo actual = primero;
        for (int j = 0; j < i; j++) {
            actual = actual.sig;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        Nodo actual = primero;
        for (int j = 0; j < i; j++) {
            actual = actual.sig;
            }
        if (i == 0) { //caso elimina el primero
            if(actual.sig != null){ // si tiene mas de un elem
                primero = actual.sig;
                primero.ant = null;
            }
            else { //si solo tiene un elem
                primero = null;
                ultimo = null;
            }
        }

        else if (i == tamaño - 1) { //caso elimina el ultimo
            if (actual.ant != null) { // si tiene mas de un elem
                ultimo = actual.ant;
                ultimo.sig = null;    
            }
            else { // si solo tiene un elem
                primero = null;
                ultimo = null;
            }
        }
        else { //cualquier otro caso
            actual.ant.sig = actual.sig;
            actual.sig.ant = actual.ant;
        }
        tamaño --;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        for (int j = 0; j < indice; j++) {
            actual = actual.sig;
        }
        actual.valor = elem; 
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> copia = new ListaEnlazada<T>();
        Nodo actual = primero;
        for (int i=0; i < tamaño; i++){
            copia.agregarAtras(actual.valor);
            actual = actual.sig;
        }
        return copia;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) { //sobrecarga "constructor que nos es vacio"
        ListaEnlazada<T> copia = lista.copiar();
        this.tamaño = copia.tamaño;
        this.primero = copia.primero;
        this.ultimo = copia.ultimo;   
    }
    
    @Override
    public String toString() {
        String str = "[";
        Nodo actual = primero;
        for (int i = 0; i < tamaño; i++) {
            if (i == tamaño - 1){
                str = str + actual.valor.toString() + "]";
            }
            else {
                str = str + actual.valor.toString() + ", ";
            }
            actual = actual.sig;
        }
        return str;
    }

    private class ListaIterador implements Iterador<T> {
        private int dedito;
        
        public boolean haySiguiente() {
            return tamaño > dedito;
        }
        
        public boolean hayAnterior() {
            return dedito != 0 && tamaño >= dedito;
        }

        public T siguiente() {
            Nodo actual = primero;
            for (int i = 0; i < dedito; i++) {
                actual = actual.sig;
            }
            dedito ++;
            return actual.valor;
        }
        

        public T anterior() {
            dedito--;
            Nodo actual = primero;
            for (int i = 0; i < dedito; i++) {
                actual = actual.sig;
            }
            return actual.valor;

        }
    }

    public Iterador<T> iterador() {
        ListaIterador nuevo = new ListaIterador();
        nuevo.dedito = 0;
        return nuevo;
    }
}
