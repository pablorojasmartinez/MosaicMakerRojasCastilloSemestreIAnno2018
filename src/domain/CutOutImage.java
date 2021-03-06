package domain;

import javafx.scene.image.WritableImage;

/**
 *
 * @author Pablo Castillo
 */
public class CutOutImage {

    //Atributos
    private int x;
    private int y;
    private WritableImage writable;
    private int Size;

    public CutOutImage() {
        this.x = 0;
        this.y = 0;
        this.writable = null;
        this.Size = 0;
    }//constructor

    public CutOutImage(int x, int y) {
        this.x = x;
        this.y = y;
    }//constructor sobrecargado

    public CutOutImage(int x, int y, WritableImage writable, int Size) {
        this.x = x;
        this.y = y;
        this.writable = writable;
        this.Size = Size;
    }//constructor sobrecargado

    //Sets && Gets 
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    //toString
    @Override
    public String toString() {
        return "CutOutImage{" + "x=" + x + ", y=" + y + ", writable=" + writable + ", Size=" + Size + '}';
    }

}
