package domain;

import java.io.Serializable;

/**
 *
 * @author Pablo Castillo
 */
public class ImageData implements Serializable {

    //Atributos
    private String pathOriginalImage;
    private String pathNewImage;
    private int pixel;
    private int size;

    public ImageData() {
        this.pathOriginalImage = "";
        this.pathNewImage = "";
        this.pixel = 0;
        this.size = 0;
    }//constructor

    public ImageData(String pathImageOriginal, String pathNewImage, int pixel, int size) {
        this.pathOriginalImage = pathImageOriginal;
        this.pathNewImage = pathNewImage;
        this.pixel = pixel;
        this.size = size;
    }//constructor sobrecargado

    //Sets && gets
    public String getPathImageOriginal() {
        return pathOriginalImage;
    }

    public void setPathImageOriginal(String pathImageOriginal) {
        this.pathOriginalImage = pathImageOriginal;
    }

    public String getPathNewImage() {
        return pathNewImage;
    }

    public void setPathNewImage(String pathNewImage) {
        this.pathNewImage = pathNewImage;
    }

    public int getPixel() {
        return pixel;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //toString
    @Override
    public String toString() {
        return "ImageData{" + "pathImageOriginal=" + pathOriginalImage + ", pathNewImage=" + pathNewImage + ", pixel=" + pixel + ", size=" + size + '}';
    }

}
