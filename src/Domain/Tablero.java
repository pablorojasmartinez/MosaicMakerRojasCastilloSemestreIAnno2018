package Domain;

import javafx.scene.image.WritableImage;

/**
 *
 * @author Pablo Castillo
 */
public class Tablero extends CutOutImage {

    int Size;
    int fila;
    int columna;
    private WritableImage writable;

    public Tablero() {
        this.Size = 0;
        this.fila = 0;
        this.columna = 0;
        this.writable = null;
    }

    public Tablero(int x, int y, int Size, int fila, int columna, WritableImage writable) {
        super(x, y);
        this.Size = Size;
        this.fila = fila;
        this.columna = columna;
        this.writable = writable;
    }

    public WritableImage getWritable() {
        return writable;
    }

    public void setWritable(WritableImage writable) {
        this.writable = writable;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
