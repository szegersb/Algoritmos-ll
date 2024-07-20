package aed;

class ArregloRedimensionableDeRecordatorios implements SecuenciaDeRecordatorios {
    private Recordatorio [] recordatorios; 
    private int tamaño;


    public ArregloRedimensionableDeRecordatorios() {
        this.tamaño = 0;
        this.recordatorios = new Recordatorio[0];
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        this.tamaño = vector.tamaño;
        Recordatorio [] nuevoRecordatorios = new Recordatorio[this.tamaño];
        for (int i = 0; i < this.tamaño; i++) {
            nuevoRecordatorios[i] = vector.recordatorios[i];
        }
        this.recordatorios = nuevoRecordatorios;
    }

    public int longitud() {
        return this.tamaño;
    }

    public void agregarAtras(Recordatorio i) {
        this.tamaño++;
        Recordatorio [] nuevoRecordatorios = new Recordatorio[this.tamaño];
        for (int j = 0; j < this.tamaño - 1; j++) {
            nuevoRecordatorios[j] = recordatorios[j];
        }
        nuevoRecordatorios[this.tamaño - 1] = i;
        this.recordatorios = nuevoRecordatorios;
    }

    public Recordatorio obtener(int i) {
        return this.recordatorios[i];
    }

    public void quitarAtras() {
        this.tamaño--;
        Recordatorio [] nuevoRecordatorios = new Recordatorio[this.tamaño];
        for (int i = 0; i < this.tamaño; i++) {
            nuevoRecordatorios[i] = recordatorios[i];
        }
        this.recordatorios = nuevoRecordatorios;
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        this.recordatorios[indice] = valor;
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        Recordatorio [] nuevoRecordatorios = new Recordatorio[this.tamaño];
        for (int i = 0; i < this.tamaño; i++) {
            nuevoRecordatorios[i] = this.recordatorios[i];
        }
        
        ArregloRedimensionableDeRecordatorios nuevo = new ArregloRedimensionableDeRecordatorios();
        nuevo.tamaño = this.tamaño;
        nuevo.recordatorios = nuevoRecordatorios;
        return nuevo;
    }

}
