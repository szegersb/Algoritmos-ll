package aed;

class Funciones {
    int cuadrado(int x) {
        int res = x * x;
        return res;
    }


    double distancia(double x, double y) {
        double res = Math.sqrt(x*x + y*y);
        return res;
    }


    boolean esPar(int n) {
        boolean res = n % 2 == 0;
        return res;
    }


    boolean esBisiesto(int n) {
        boolean res = (n % 4 == 0) && (n % 100 != 0 || n % 400 == 0); 
        return res;
    }


    int factorialIterativo(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            int res = 1;
            for(int i = 1; i <= n; i++) {
                res = res * i;
            }
        return res;
        }
    }


    int factorialRecursivo(int n) {
        if (n == 1 || n == 0) {
            return 1;
        } else {
            return (factorialRecursivo(n-1) * n); 
        }
    }


    boolean esPrimo(int n) {
        int divisores = 0;
        for (int i=1; i<=n; i++) {
            if (n % i == 0) {
                divisores += 1;
            }
        }

        if (divisores == 2) {
            return true;
        } else {
            return false;
        }
    }


    int sumatoria(int[] numeros) {
        int res = 0;

        for (int num : numeros) {
            res += num;
        }
        return res;
    }


    int busqueda(int[] numeros, int buscado) {
        int res = 0;
        for (int i=0; i < numeros.length; i++) {
            if (numeros[i] == buscado) {
                res = i;
            }
        }
        return res;
    }


    boolean tienePrimo(int[] numeros) {
        for (int num : numeros) {
            if(esPrimo(num)) {
                return true;
            }
        }
        return false;
    }


    boolean todosPares(int[] numeros) {
        for (int num : numeros) {
            if(!esPar(num)) {
                return false;
            }
        }
        return true;
    }


    boolean esPrefijo(String s1, String s2) {
        for (int i=0; i < s1.length(); i++) {
            if (s1.length() > s2.length() || s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }


    boolean esSufijo(String s1, String s2) {
        return esPrefijo(revierte(s1), revierte(s2));
    }

    String revierte(String s1) {
        String res = new String();
        for (int i = s1.length() -1; i >= 0; i--) {
            res += s1.charAt(i); 
        }
        
        return res;
    }
}


