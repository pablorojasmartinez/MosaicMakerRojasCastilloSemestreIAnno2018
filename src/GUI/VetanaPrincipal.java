package GUI;

import Data.Archivo;
import Domain.CutOutImage;
import Domain.ImageData;
import Domain.Tablero;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo Castillo
 */
public class VetanaPrincipal extends Application {

    private final int WIDTH = 1400;
    private final int HEIGHT = 700;
    private Scene scene;
    private Pane pane;
    private Canvas canvas, canvas2;
    private MenuBar bar;
    private Menu menu;
    private MenuItem item;
    private MenuItem loadImage;
    private TextField texto, tetxoCanvas2;
    private Label label, labelCan;
    private Button boton, boton2, btnFlip, btnRotate, btnSavedIamge, btnSave;
    int PixelNumber;
    Image image;
    GraphicsContext gc;
    Group root;
    private WritableImage writable; //convierte pixeles en una imagen
    private PixelReader pixel;
    Stage stage;
    CutOutImage matriz[][];
    CutOutImage matrizTablero[][];
    Tablero mat[][];
    CutOutImage cut;
    int xPressed;
    int yPressed;
    private ImageView imageView; //modifica una imagen
    private SnapshotParameters snapshot;
    String path = "";
    String path2 = "";
    int tamanoMatr;

    @Override
    /*constructor mas o menos*/
    public void start(Stage primaryStage) throws Exception {
        PixelNumber = 0;
        this.stage = primaryStage;
        primaryStage.setTitle("MosaicMaker");
        primaryStage.setFullScreen(true);
        initComponents(primaryStage);
        primaryStage.show();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int valorx = (int) mouseEvent.getX();
                int valory = (int) mouseEvent.getY();
                cut = buscarTrozoImagen(valorx, valory);
            }
        });

        canvas2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int valorx = (int) mouseEvent.getX();
                int valory = (int) mouseEvent.getY();

                Tablero tablero = buscarLugarTablero(valorx, valory);
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (cut.getWritable() != null) {
//                        mat[tablero.getFila()][tablero.getColumna()].setWritable(cut.getWritable());
                        draw2(gc, cut, tablero);

                    }
                } else if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    draw2Deleted(gc, cut, tablero);
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    xPressed = valorx;
                    yPressed = valory;
                }
            }
        });

    }//start

    public CutOutImage buscarTrozoImagen(int x, int y) {
        CutOutImage tab;
        for (int i = 0; i < matriz.length - 1; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].getX() <= x) {
                    if (matriz[i][j].getY() <= y) {
                        if (x <= matriz[i][j].getSize() + matriz[i][j].getX()) {
                            if (y <= matriz[i][j].getSize() + matriz[i][j].getY()) {
                                tab = new CutOutImage(matriz[i][j].getX(), matriz[i][j].getY(), matriz[i][j].getWritable(), matriz[i][j].getSize());
                                return tab;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Tablero buscarLugarTablero(int x, int y) {
        Tablero tab;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j].getX() <= x) {
                    if (mat[i][j].getY() <= y) {
                        if (x <= mat[i][j].getSize() + mat[i][j].getX()) {
                            if (y <= mat[i][j].getSize() + mat[i][j].getY()) {
                                tab = new Tablero(mat[i][j].getX(), mat[i][j].getY(), mat[i][j].getSize(), mat[i][j].getFila(), mat[i][j].getColumna(), null);
                                return tab;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Tablero buscarLugarTableroConImagenes(int x, int y) {
        Tablero tab;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j].getX() <= x) {
                    if (mat[i][j].getY() <= y) {
                        if (x <= mat[i][j].getSize() + mat[i][j].getX()) {
                            if (y <= mat[i][j].getSize() + mat[i][j].getY()) {
                                tab = new Tablero(mat[i][j].getX(), mat[i][j].getY(), mat[i][j].getSize(), mat[i][j].getFila(), mat[i][j].getColumna(), mat[i][j].getWritable());
                                return tab;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private void initComponents(Stage primaryStage) throws FileNotFoundException {
        this.pane = new Pane();
        root = new Group();

        this.scene = new Scene(root, WIDTH, HEIGHT, Color.LIGHTGRAY);
        this.bar = new MenuBar();
        this.menu = new Menu("Menu");
        this.loadImage = new MenuItem("Upload Image");

        this.snapshot = new SnapshotParameters();
        this.menu.getItems().add(0, loadImage);
        this.canvas = new Canvas(2000, 2000);

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(canvas);
        scroll.setPrefSize(500, 500);
        root.getChildren().add(scroll);
        scroll.setLayoutX(60);
        scroll.setLayoutY(80);

        MenuItem exit = new MenuItem("Exit");

        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });

        this.loadImage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showOpenDialog(null);
                    path = file.getAbsolutePath();
                    BufferedImage bufferedImage = ImageIO.read(file);
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                    texto.setVisible(true);
                    boton.setVisible(true);
                    label.setVisible(true);

                    labelCan.setVisible(true);
                    boton2.setVisible(true);
                    tetxoCanvas2.setVisible(true);

                    btnRotate.setVisible(true);
                    btnFlip.setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(VetanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        texto = new TextField();
        texto.setLayoutX(100);
        texto.setLayoutY(30);
        texto.setVisible(false);

        label = new Label("Pixel");
        label.setLayoutX(60);
        label.setLayoutY(30);
        label.setVisible(false);

        boton = new Button("Accept");
        boton.setLayoutX(250);
        boton.setLayoutY(30);
        boton.setVisible(false);

        canvas2 = new Canvas(2000, 2000);

        tetxoCanvas2 = new TextField();
        tetxoCanvas2.setLayoutX(800);
        tetxoCanvas2.setLayoutY(30);
        tetxoCanvas2.setVisible(false);

        labelCan = new Label("Amunt");
        labelCan.setLayoutX(750);
        labelCan.setLayoutY(30);
        this.labelCan.setVisible(false);

        boton2 = new Button("Accept");
        boton2.setLayoutX(950);
        boton2.setLayoutY(30);
        boton2.setVisible(false);

        ScrollPane scroll2 = new ScrollPane();
        scroll2.setContent(canvas2);
        scroll2.setPrefSize(500, 500);
        root.getChildren().add(scroll2);
        scroll2.setLayoutX(700);
        scroll2.setLayoutY(80);
        scroll2.setContent(canvas2);

        btnRotate = new Button("Rotate");
        btnRotate.setLayoutX(800);
        btnRotate.setLayoutY(650);
        btnRotate.setVisible(false);

        btnFlip = new Button("Flip");
        btnFlip.setLayoutY(650);
        btnFlip.setLayoutX(900);
        btnFlip.setVisible(false);

        btnSavedIamge = new Button("Saved Image");
        btnSavedIamge.setLayoutY(650);
        btnSavedIamge.setLayoutX(650);
        btnSave = new Button("Save");
        btnSave.setLayoutY(650);
        btnSave.setLayoutX(1050);
        this.snapshot = new SnapshotParameters();

        gc = canvas2.getGraphicsContext2D();
        gc.setStroke(Color.GRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        this.btnSave.setOnAction(btnSave1);
        this.boton2.setOnAction(buttonAction1);
        this.boton.setOnAction(buttonAction);
        this.btnRotate.setOnAction(buttonActionRotate);
        this.btnFlip.setOnAction(buttonActionFlip);
        this.btnSavedIamge.setOnAction(btnSavedImage);
        menu.getItems().add(exit);
        bar.getMenus().addAll(menu);
        this.root.getChildren().add(this.btnSave);
        this.root.getChildren().add(this.labelCan);
        this.root.getChildren().add(this.canvas);
        this.root.getChildren().add(this.canvas2);
        this.root.getChildren().add(this.texto);
        this.root.getChildren().add(this.label);
        this.root.getChildren().add(this.boton);
        this.root.getChildren().add(this.btnRotate);
        this.root.getChildren().add(this.btnFlip);
        this.root.getChildren().add(this.tetxoCanvas2);
        this.root.getChildren().add(this.boton2);
        this.root.getChildren().add(this.btnSavedIamge);

        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        GraphicsContext gc2 = this.canvas2.getGraphicsContext2D();

        this.root.getChildren().add(bar);
        bar.prefWidthProperty().bind(primaryStage.widthProperty());
        primaryStage.setScene(scene);
    }
    EventHandler<ActionEvent> btnSave1 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Archivo archivo = new Archivo();
                ImageData imageData;
                imageData = new ImageData(path, path2, PixelNumber, tamanoMatr);

                archivo.guardarLibro(imageData);
            } catch (IOException ex) {
                Logger.getLogger(VetanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VetanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };
    EventHandler<ActionEvent> buttonAction1 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int cantidaFilaColum = Integer.parseInt(tetxoCanvas2.getText());
            draw3(gc, cantidaFilaColum);
            tetxoCanvas2.setEditable(false);
            boton2.setDisable(true);
        }
    };

    EventHandler<ActionEvent> btnSavedImage = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                exportImage();
            } catch (IOException ex) {
                Logger.getLogger(VetanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<ActionEvent> buttonActionRotate = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Tablero tablero = buscarLugarTableroConImagenes(xPressed, yPressed);
            dibujarImagenRotada(gc, tablero);
        }
    };

    EventHandler<ActionEvent> buttonActionFlip = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            System.err.println("Esta mierda aún no sirve");
            Tablero tablero = buscarLugarTableroConImagenes(xPressed, yPressed);
            flipImage(gc, tablero);
        }
    };

    EventHandler<ActionEvent> buttonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int numberPixel1 = Integer.parseInt(texto.getText());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gc2 = canvas2.getGraphicsContext2D();
            draw(gc, numberPixel1);
            PixelNumber = numberPixel1;
            //  boton.setDisable(true);
            texto.setEditable(false);
        }
    };

    private void draw(GraphicsContext gc, int numberPixel) {
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        this.pixel = this.image.getPixelReader();

        double largeImage = image.getHeight();
        double widthImage = image.getWidth();

        int x = 0;
        int y = 0;

        int tamnoX = 0;
        int tamanoY = 50;

        int anch = (int) (widthImage / numberPixel);
        int alto = (int) (largeImage / numberPixel);

        matriz = new CutOutImage[alto][anch];
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < anch; j++) {
                if (widthImage > tamnoX && largeImage > tamanoY) {
                    if (largeImage - tamanoY > numberPixel) {
                        this.writable = new WritableImage(this.pixel, tamnoX, tamanoY, numberPixel, numberPixel); //Lee los pixeles (imagen, xInicio, yInicio, xFin, yFin)
                        matriz[i][j] = new CutOutImage(x, y, writable, numberPixel);
                        gc.drawImage(this.writable, x, y);
                        x += numberPixel + 2;
                        tamnoX += numberPixel;
                    }
                }
            }
            if (largeImage >= tamanoY) {
                x = 0;
                tamanoY += numberPixel;
                tamnoX = 0;
                y += numberPixel + 2;
            }
        }
    }

    private void draw2(GraphicsContext gc2, CutOutImage trozo, Tablero tablero) {
        if (trozo.getWritable() != null && tablero != null) {
            gc2.drawImage(trozo.getWritable(), tablero.getX(), tablero.getY());
             mat[tablero.getFila()][tablero.getColumna()] = new Tablero(tablero.getX(), tablero.getY(), tablero.getSize(), tablero.getFila(), tablero.getColumna(), trozo.getWritable());
        }
    }

    private void draw2Deleted(GraphicsContext gc2, CutOutImage trozo, Tablero tablero) {
        if (trozo.getWritable() != null && tablero != null) {
            gc2.setFill(Color.LIGHTGREY);
            gc2.fillRect(tablero.getX(), tablero.getY(), tablero.getSize(), tablero.getSize());
            gc2.strokeRect(tablero.getX(), tablero.getY(), tablero.getSize(), tablero.getSize());
        }
    }

    private void draw3(GraphicsContext gc2, int cantidad) {
        int x = 0;
        int y = 0;
        int numberPixel = PixelNumber;
        tamanoMatr = cantidad;
        mat = new Tablero[cantidad][cantidad];
        matrizTablero = new CutOutImage[cantidad][cantidad];
        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < cantidad; j++) {
                gc2.strokeRect(x, y, numberPixel, numberPixel);
                mat[i][j] = new Tablero(x, y, numberPixel, i, j, null);
                matrizTablero[i][j] = new CutOutImage(x, y, null, numberPixel);
                x += numberPixel + 2;
            }
            y += numberPixel + 2;
            x = 0;
        }
    }

    private void dibujarImagenRotada(GraphicsContext gc, Tablero tablero) {

        WritableImage writable1;

        this.imageView = new ImageView(tablero.getWritable());
        this.snapshot = new SnapshotParameters();

        this.imageView.setRotate(imageView.getRotate() + 90); //rota la imagen 90 grados sentido del reloj
        this.image = imageView.snapshot(snapshot, null);
        this.pixel = this.image.getPixelReader();
        writable1 = new WritableImage(this.pixel, 0, 0, tablero.getSize(), tablero.getSize()); //Lee los pixeles (imagen, xInici
        tablero.setWritable(writable1);

        gc.drawImage(tablero.getWritable(), tablero.getX(), tablero.getY(), tablero.getSize(), tablero.getSize());
        mat[tablero.getFila()][tablero.getColumna()].setWritable(writable1);
    }

    private void flipImage(GraphicsContext gc, Tablero tablero) {

        WritableImage writable1;

        this.imageView = new ImageView(tablero.getWritable());
        imageView.setTranslateZ(imageView.getBoundsInLocal().getWidth() / 2.0);
        imageView.setRotate(180);
        imageView.setRotationAxis(Rotate.Y_AXIS);
        this.snapshot = new SnapshotParameters();
        this.image = imageView.snapshot(snapshot, null);
        this.pixel = this.image.getPixelReader();
        writable1 = new WritableImage(this.pixel, 0, 0, tablero.getSize(), tablero.getSize()); //Lee los pixeles (imagen, xInici
        tablero.setWritable(writable1);
        gc.drawImage(tablero.getWritable(), tablero.getX(), tablero.getY(), tablero.getSize(), tablero.getSize());
        mat[tablero.getFila()][tablero.getColumna()].setWritable(writable1);
    }

    public void exportImage() throws IOException {

        gc.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        repintar();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        WritableImage imagenss = this.canvas2.snapshot(new SnapshotParameters(), null);
        File fileI = new File("imagen");
        ImageIO.write(SwingFXUtils.fromFXImage(imagenss, null), "png", file);
        String ruta = file.getAbsolutePath();
        draw3(gc, tamanoMatr);
        path2 = ruta;
    }

    public void repintar() throws IOException {
//         Tablero tablero = buscarLugarTablero(valorx, valory);
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[j][i].getWritable() != null) {
                    drawNuevoPanel(gc, mat[j][i]);
                }
            }
        }
    }
    
    
       private void drawNuevoPanel(GraphicsContext gc2, Tablero tablero) {
        if (tablero.getWritable() != null && tablero != null) {
            gc2.drawImage(tablero.getWritable(), tablero.getX(), tablero.getY());
//             mat[tablero.getFila()][tablero.getColumna()] = new Tablero(tablero.getX(), tablero.getY(), tablero.getSize(), tablero.getFila(), tablero.getColumna(), tablero.getWritable());
        }
    }
}
