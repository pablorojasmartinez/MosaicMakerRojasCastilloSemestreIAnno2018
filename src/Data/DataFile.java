/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;
  
import Domain.ImageData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo Castillo
 */
public class DataFile {

    //Atributos
    private String path;

    public DataFile() {
    }//constructor

    //Este método recibe por parametros un atributo de tipo ImageData y un atributoString que pertenece a una dirección
    //Sirve para crear y guardar en un archivo los atributos arriba mencionados.
    public void saveFile(ImageData imageData, String path) throws IOException, ClassNotFoundException {
        this.path = path;
        File myFile = new File(path);
        List<ImageData> imageList = new ArrayList<ImageData>();

        if (myFile.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(myFile));
            Object aux = objectInputStream.readObject();
            imageList = (List<ImageData>) aux;
            objectInputStream.close();
        }//if(myFile.exists())

        imageList.add(imageData);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(myFile));
        output.writeUnshared(imageList);
        output.close();
    }
    
    //Este método guarda en  un arrayList de tipo ImageData.
    public List<ImageData> arrays(String path) throws IOException, ClassNotFoundException {
        File myFile = new File(path);

        List<ImageData> imageList = new ArrayList<ImageData>();
        if (myFile.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(myFile));
            Object aux = objectInputStream.readObject();
            imageList = (List<ImageData>) aux;
            objectInputStream.close();
        }//if(myFile.exists())

        return imageList;
    }
}
