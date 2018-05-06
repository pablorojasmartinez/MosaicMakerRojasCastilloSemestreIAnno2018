/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.DataFile;
import domain.ImageData;
import java.io.IOException;

/**
 *
 * @author Pablo Rojas Martínez
 */
public class BusinessFile {

    //Atrubuto
    DataFile dataFile;

    public BusinessFile() {
        dataFile = new DataFile();
    }//constructor

    //Método que llama el método saveFile de la clase DataFile
    public void saveFileBusiness(ImageData imageData, String path) throws IOException, ClassNotFoundException {
        this.dataFile.saveFile(imageData, path);
    }

    //Método que llama el método arrays de la clase DataFile
    public void arrayBusiness(String path) throws IOException, ClassNotFoundException {
        this.dataFile.arrays(path);
    }

}
