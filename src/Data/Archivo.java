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
public class Archivo {
      private String path;
    
    public Archivo(){
        this.path="ArchivoLibros";
    }
    public void guardarLibro(ImageData libro) throws IOException, ClassNotFoundException {
        File myFile = new File(this.path);
        List<ImageData> listaLibros = new ArrayList<ImageData>();

        if (myFile.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(myFile));
            Object aux = objectInputStream.readObject();
            listaLibros = (List<ImageData>) aux;
            objectInputStream.close();
        }//if(myFile.exists())

        listaLibros.add(libro);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(myFile));
        output.writeUnshared(listaLibros);
        output.close();
    }//guardarPersona
}
