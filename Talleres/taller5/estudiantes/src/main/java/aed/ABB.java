package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo raiz;
    private int cardinal;
    
    private class Nodo {
        T valor;
        Nodo izq;
        Nodo der;
        Nodo arriba;

        Nodo(T v) {
            valor = v;
            izq = null;
            der = null;
            arriba = null;
        }
   }

    public ABB() {
        raiz = null;
        cardinal = 0;
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo(){
        if (raiz == null){
            return null;
        }
        Nodo actual = raiz;
        while (actual.izq != null) {
            actual = actual.izq;
        }
        return actual.valor;
    }

    public T maximo(){
        if (raiz == null) {
            return null;
        }
        Nodo actual = raiz;
        while (actual.der != null) {
            actual = actual.der;
        }
        return actual.valor;
    }

    public void insertar(T elem){
        if (raiz == null){
            this.raiz =  new Nodo(elem);
            cardinal ++;
        }
        else {
            Nodo actual = raiz;
            Nodo padre = null;

            while (actual != null) {
                padre = actual;
                if (elem.equals(actual.valor)) {
                    return;
                }
                else if (elem.compareTo(actual.valor) > 0){
                    actual = actual.der;
                }
                else {
                    actual = actual.izq;
                }
            }
            Nodo nuevo = new Nodo(elem);
            nuevo.arriba = padre;
            if (elem.compareTo(padre.valor) > 0) {
                padre.der = nuevo;
            }
            else {
                padre.izq = nuevo;
            }
            cardinal ++;
            }
        }

    public boolean pertenece(T elem){ 
        Nodo actual = raiz;

        while (actual!= null) {
            if (actual.valor.equals(elem)){
                return true;
            }
            else if (elem.compareTo(actual.valor) > 0) {
                actual = actual.der;
            }
            else {
                actual = actual.izq;
            }
        }

        return false;
    }

    public void eliminar(T elem){
        Nodo actual = raiz;
        Nodo padre = null;

        while (actual != null && !actual.valor.equals(elem)) { //para llegar al valor actual que queremos eliminar y tener el padre
            padre = actual;
            if (elem.compareTo(actual.valor) > 0) {
                actual = actual.der;
            }
            else {
                actual = actual.izq;
            }
        }
        if (actual == null) {
            return;
        }
        
        //separo en 4 casos coomo dice las laminas de la clase
        if (actual.izq == null && actual.der == null) { //caso no tiene hijos
            if (actual == raiz) {
                raiz = null;
            }
            else if (actual == padre.izq) {
                padre.izq = null;
            }
            else {
                padre.der = null;
            }
            cardinal --;
        }
        else if (actual.der == null) {//caso hijo izq
            if (actual == raiz) {
                raiz = actual.izq;
                raiz.arriba = null;
            } else if (actual == padre.izq) {
                padre.izq = actual.izq;
                actual.izq.arriba = padre;
            } else {
                padre.der = actual.izq;
                actual.izq.arriba = padre;
            }
            cardinal--;
        }
        else if (actual.izq == null) { //caso solo hijo derecha
            if (actual == raiz) {
                raiz = actual.der;
                raiz.arriba = null;
            } else if (actual == padre.izq) {
                padre.izq = actual.der;
                actual.der.arriba = padre;
            } else {
                padre.der = actual.der;
                actual.der.arriba = padre;
            }
            cardinal--;
        }
        else { // caso tiene 2 hijos
            Nodo minimo = actual.der;
            Nodo padreMinimo = actual;
            while (minimo.izq != null) {
                padreMinimo = minimo;
                minimo = minimo.izq;
            }

            actual.valor = minimo.valor;

            if (padreMinimo.izq == minimo) {
                padreMinimo.izq = minimo.der;
            } else {
                padreMinimo.der = minimo.der;
            }
    
            if (minimo.der != null) {
                minimo.der.arriba = padreMinimo;
            }
            cardinal --;
        }
    }

 
    public String toString(){
        String str = "{";
        Iterador<T> iterador = this.iterador();
        while (iterador.haySiguiente()){
            str = str + iterador.siguiente().toString();
            if (iterador.haySiguiente()) {
                str = str + ",";
            }
        }
        str = str + "}";

        return str;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo actual;

        public ABB_Iterador() {
            actual = raiz;
            while (actual.izq != null && actual.izq != null) {
                actual = actual.izq;
            }
        }

        public boolean haySiguiente() {
            return actual != null;
        }

        public T siguiente() {
            T res = actual.valor;

            if (actual.der != null){
                actual = actual.der;
                while (actual.izq != null) { //para llegar al mas chico de la derecha
                    actual = actual.izq;
                }
            }
            else {
                while(actual.arriba != null && actual == actual.arriba.der) { //para llegar al nodo padre y seguir el camino
                    actual = actual.arriba;
                }
                actual = actual.arriba;
            }
            return res;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }
}
