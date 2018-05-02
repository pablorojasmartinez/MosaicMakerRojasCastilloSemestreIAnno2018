/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Pablo Castillo
 */
public class WindowNumberPixel implements Initializable{
    /*constructor mas o menos*/
     private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private  Group root;
      private Stage stagePrincipal;
    public void start(Stage primaryStage) throws Exception {
       primaryStage.setTitle("Tutor de Programacion");
        primaryStage.setFullScreen(true);
        initComponents(primaryStage);
        primaryStage.show();
    
    }
 private void initComponents(Stage primaryStage) throws FileNotFoundException {
  this.pane = new Pane();
        root = new Group();
        ;
        this.scene = new Scene(root, 400, 400, Color.AQUAMARINE);
        this.canvas = new Canvas(400, 400);
//        canvas.setLayoutX(60);
 this.root.getChildren().add(this.canvas);
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
//        root.getChildren().add(bar);
//        bar.prefWidthProperty().bind(primaryStage.widthProperty());
        primaryStage.setScene(scene);
        
        
 }
   public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
