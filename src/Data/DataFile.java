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

    private String path;

    public DataFile() {
//        this.path="ArchivoLibros";
    }

    public void saveFile(ImageData libro, String path) throws IOException, ClassNotFoundException {
        this.path = path;
        File myFile = new File(path);
        List<ImageData> imageList = new ArrayList<ImageData>();

        if (myFile.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(myFile));
            Object aux = objectInputStream.readObject();
            imageList = (List<ImageData>) aux;
            objectInputStream.close();
        }//if(myFile.exists())

        imageList.add(libro);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(myFile));
        output.writeUnshared(imageList);
        output.close();
    }

    public List<ImageData> arrays() throws IOException, ClassNotFoundException {
        File myFile = new File(this.path);

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
