
package Domain;

import java.io.Serializable;

/**
 *
 * @author Pablo Castillo
 */
public class ImageData implements Serializable{
    
    private String pathImageOriginal;
    private String pathNewImage;
    private int pixel;
    private int size;
    
    
    public ImageData(){
    this.pathImageOriginal="";
    this.pathNewImage="";
    this.pixel=0;
    this.size=0;
    
    }

    public ImageData(String pathImageOriginal, String pathNewImage, int pixel, int size) {
        this.pathImageOriginal = pathImageOriginal;
        this.pathNewImage = pathNewImage;
        this.pixel = pixel;
        this.size = size;
    }

    public String getPathImageOriginal() {
        return pathImageOriginal;
    }

    public void setPathImageOriginal(String pathImageOriginal) {
        this.pathImageOriginal = pathImageOriginal;
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

    @Override
    public String toString() {
        return "ImageData{" + "pathImageOriginal=" + pathImageOriginal + ", pathNewImage=" + pathNewImage + ", pixel=" + pixel + ", size=" + size + '}';
    }
    
}
