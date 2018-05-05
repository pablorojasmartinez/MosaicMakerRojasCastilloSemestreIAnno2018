package Domain;

import javafx.scene.image.WritableImage;

/**
 *
 * @author Pablo Castillo
 */
public class Board extends CutOutImage {

    //Atributos
    int size;
    int row;
    int column;
    private WritableImage writable;

    public Board() {
        this.size = 0;
        this.row = 0;
        this.column = 0;
        this.writable = null;
    }//Constructor

    public Board(int x, int y, int Size, int fila, int columna, WritableImage writable) {
        super(x, y);
        this.size = Size;
        this.row = fila;
        this.column = columna;
        this.writable = writable;
    }//constructor sobrecargado

    //sets && gets 
    
    public WritableImage getWritable() {
        return writable;
    }

    public void setWritable(WritableImage writable) {
        this.writable = writable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int Size) {
        this.size = Size;
    }

    public int getFila() {
        return row;
    }

    public void setFila(int fila) {
        this.row = fila;
    }

    public int getColumna() {
        return column;
    }

    public void setColumna(int columna) {
        this.column = columna;
    }

}
