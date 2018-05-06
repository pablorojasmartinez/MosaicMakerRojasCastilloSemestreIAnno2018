package gui;

import data.DataFile;
import domain.CutOutImage;
import domain.ImageData;
import domain.Board;
import business.BusinessFile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.SepiaTone;
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

/**
 *
 * @author Pablo Castillo
 */
public class MainWindow extends Application {

    //Atributos
    private final int WIDTH = 1400;
    private final int HEIGHT = 700;
    private Scene scene;
    private Pane pane;
    private Canvas canvas, canvas2;
    private MenuBar bar;
    private Menu menu;
    private MenuItem item;
    private MenuItem loadImage;
    private TextField text, textCanvas, txtName;
    private Label label, labelCan;
    private Button button, button2, btnFlip, btnRotate, btnSavedIamge, btnSave, btnUpload;
    int PixelNumber;
    Image image;
    GraphicsContext gc;
    Group root;
    private WritableImage writable; 
    private PixelReader pixel;
    Stage stage;
    CutOutImage[][] matrix;
    CutOutImage[][] boardMatrix;
    Board mat[][];
    CutOutImage cut;

    int xPressed;
    int yPressed;

    private ImageView imageView; 
    private SnapshotParameters snapshot;

    String path = "";
    String path2 = "";

    int matrixSize;

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
                cut = searchCutImage(valorx, valory);
            }
        });

        canvas2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int valueX = (int) mouseEvent.getX();
                int valueY = (int) mouseEvent.getY();

                Board board = boardSearchPlace(valueX, valueY);
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (cut.getWritable() != null) {
//                        mat[tablero.getFila()][tablero.getColumna()].setWritable(cut.getWritable());
                        drawCutImage(gc, cut, board);

                    }
                } else if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    deleteImage(gc, cut, board);
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    xPressed = valueX;
                    yPressed = valueY;
                }
            }
        });

    }//start

    /*busca en  las coordenadas x y y de donde se presionó la imagen original y hace un recorte del tamaño de 
    los pixeles escogidos*/
    public CutOutImage searchCutImage(int x, int y) {

        CutOutImage board;
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getX() <= x) {
                    if (matrix[i][j].getY() <= y) {
                        if (x <= matrix[i][j].getSize() + matrix[i][j].getX()) {
                            if (y <= matrix[i][j].getSize() + matrix[i][j].getY()) {
                                board = new CutOutImage(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWritable(), matrix[i][j].getSize());
                                return board;
                            }
                        }
                    }
                }
            }//
        }///for
        return null;
    }

    /*busca si el lugar seleccionado en el canvas está dentro de la matriz para dibujar la imagen utilizando
    el x y Y de la posicion q se seleccionó*/
    public Board boardSearchPlace(int x, int y) {
        Board board;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j].getX() <= x) {
                    if (mat[i][j].getY() <= y) {
                        if (x <= mat[i][j].getSize() + mat[i][j].getX()) {
                            if (y <= mat[i][j].getSize() + mat[i][j].getY()) {
                                board = new Board(mat[i][j].getX(), mat[i][j].getY(), mat[i][j].getSize(), mat[i][j].getFila(), mat[i][j].getColumna(), null);
                                return board;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /*busca si el lugar seleccionado en el canvas está dentro de la matriz para dibujar la imagen utilizando
    el x y Y de la posición q se seleccionó solamente que en esta ocasión se guarda la imagen dentro de una  matriz para poder usarla en otros métodos*/
    public Board searchPlaceWithImages(int x, int y) {
        Board board;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j].getX() <= x) {
                    if (mat[i][j].getY() <= y) {
                        if (x <= mat[i][j].getSize() + mat[i][j].getX()) {
                            if (y <= mat[i][j].getSize() + mat[i][j].getY()) {
                                board = new Board(mat[i][j].getX(), mat[i][j].getY(), mat[i][j].getSize(), mat[i][j].getFila(), mat[i][j].getColumna(), mat[i][j].getWritable());
                                return board;
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
        root.setEffect(new InnerShadow());

        this.scene = new Scene(root, WIDTH, HEIGHT, Color.YELLOWGREEN);
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
                    text.setVisible(true);
                    button.setVisible(true);
                    label.setVisible(true);

                    labelCan.setVisible(true);
                    button2.setVisible(true);
                    textCanvas.setVisible(true);

                    btnRotate.setVisible(true);
                    btnFlip.setVisible(true);
                    btnSavedIamge.setVisible(true);
                    btnSave.setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        text = new TextField();
        text.setLayoutX(100);
        text.setLayoutY(30);
        text.setVisible(false);

        label = new Label("Pixel");
        label.setLayoutX(60);
        label.setLayoutY(30);
        label.setVisible(false);

        button = new Button("Accept");
        button.setLayoutX(250);
        button.setLayoutY(30);
        button.setVisible(false);

        canvas2 = new Canvas(1000, 1000);

        textCanvas = new TextField();
        textCanvas.setLayoutX(800);
        textCanvas.setLayoutY(30);
        textCanvas.setVisible(false);

        labelCan = new Label("Amount");
        labelCan.setLayoutX(750);
        labelCan.setLayoutY(30);
        this.labelCan.setVisible(false);

        button2 = new Button("Accept");
        button2.setLayoutX(950);
        button2.setLayoutY(30);
        button2.setVisible(false);

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

        btnSavedIamge = new Button("Save Image");
        btnSavedIamge.setLayoutY(650);
        btnSavedIamge.setLayoutX(650);
        btnSavedIamge.setVisible(false);

        btnSave = new Button("Save Project");
        btnSave.setLayoutY(650);
        btnSave.setLayoutX(1000);

        this.snapshot = new SnapshotParameters();
        btnSave.setVisible(false);

        btnUpload = new Button("Upload Project");
        btnUpload.setLayoutY(650);
        btnUpload.setLayoutX(1100);

        gc = canvas2.getGraphicsContext2D();
        gc.setStroke(Color.GRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        this.btnSave.setOnAction(btnSave1);
        this.button2.setOnAction(buttonAction1);
        this.button.setOnAction(buttonAction);
        this.btnRotate.setOnAction(buttonActionRotate);
        this.btnFlip.setOnAction(buttonActionFlip);
        this.btnSavedIamge.setOnAction(btnSavedImage);
        this.btnUpload.setOnAction(btnUnload);

        menu.getItems().add(exit);
        bar.getMenus().addAll(menu);

        this.root.getChildren().add(this.btnUpload);
        this.root.getChildren().add(this.btnSave);
        this.root.getChildren().add(this.labelCan);
        this.root.getChildren().add(this.canvas);
        this.root.getChildren().add(this.canvas2);
        this.root.getChildren().add(this.text);
        this.root.getChildren().add(this.label);
        this.root.getChildren().add(this.button);
        this.root.getChildren().add(this.btnRotate);
        this.root.getChildren().add(this.btnFlip);
        this.root.getChildren().add(this.textCanvas);
        this.root.getChildren().add(this.button2);
        this.root.getChildren().add(this.btnSavedIamge);

        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        GraphicsContext gc2 = this.canvas2.getGraphicsContext2D();

        this.root.getChildren().add(bar);
        bar.prefWidthProperty().bind(primaryStage.widthProperty());
        primaryStage.setScene(scene);
    }

    EventHandler<ActionEvent> btnUnload = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(null);
                path = file.getAbsolutePath();

                List<ImageData> imageList = new ArrayList<ImageData>();
                ImageData imageData;
                DataFile dataFile = new DataFile();
                imageList = dataFile.arrays(path);
                imageData = imageList.get(0);
                /*carga la imagen original*/
                image = new Image(new FileInputStream(imageData.getPathImageOriginal()));
                Image image2 = new Image(new FileInputStream(imageData.getPathNewImage()));
                GraphicsContext gc = canvas.getGraphicsContext2D();
                GraphicsContext gc2 = canvas2.getGraphicsContext2D();
                drawOriginalImage(gc, imageData.getPixel());

                PixelNumber = imageData.getPixel();

                modifiedDrawImage(gc2, image2);
                drawLines(gc2, imageData.getSize());

                btnFlip.setVisible(true);
                btnRotate.setVisible(true);
                btnSave.setVisible(true);
                btnSavedIamge.setVisible(true);

            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };

    EventHandler<ActionEvent> btnSave1 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {

                BusinessFile businessFile = new BusinessFile();
                ImageData imageData;
                imageData = new ImageData(path, path2, PixelNumber, matrixSize);
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showSaveDialog(null);
                path = file.getAbsolutePath();

                businessFile.saveFileBusiness(imageData, path);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };
    EventHandler<ActionEvent> buttonAction1 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int cantidaFilaColum = Integer.parseInt(textCanvas.getText());
            drawLines(gc, cantidaFilaColum);
            textCanvas.setEditable(false);
            button2.setDisable(true);
        }
    };

    EventHandler<ActionEvent> btnSavedImage = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                exportImage();
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<ActionEvent> buttonActionRotate = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Board board = searchPlaceWithImages(xPressed, yPressed);
            drawRotateImage(gc, board);
        }
    };

    EventHandler<ActionEvent> buttonActionFlip = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Board board = searchPlaceWithImages(xPressed, yPressed);
            flipImage(gc, board);
        }
    };

    EventHandler<ActionEvent> buttonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int numberPixel1 = Integer.parseInt(text.getText());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gc2 = canvas2.getGraphicsContext2D();
            drawOriginalImage(gc, numberPixel1);
            PixelNumber = numberPixel1;
            text.setEditable(false);
        }
    };

    /*Aqui se pinta la imagen original y se parte en trozso seleccionados por los pixeles que le quiera dar el usuario
    aparte de llenar una matriz con los datos de los trozos de la imagen para luego ser utilizados si se seleccionaran
     */
    private void drawOriginalImage(GraphicsContext gc, int numberPixel) {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        this.pixel = this.image.getPixelReader();

        double largeImage = image.getHeight();
        double widthImage = image.getWidth();

        int x = 0;
        int y = 0;

        int sizeX = 0;
        int sizeY = 50;

        int width = (int) (widthImage / numberPixel);
        int height = (int) (largeImage / numberPixel);

        matrix = new CutOutImage[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (widthImage > sizeX && largeImage > sizeY) {
                    if (largeImage - sizeY > numberPixel) {
                        this.writable = new WritableImage(this.pixel, sizeX, sizeY, numberPixel, numberPixel); //Lee los pixeles (imagen, xInicio, yInicio, xFin, yFin)
                        matrix[i][j] = new CutOutImage(x, y, writable, numberPixel);
                        gc.drawImage(this.writable, x, y);
                        x += numberPixel + 2;
                        sizeX += numberPixel;
                    }
                }
            }
            if (largeImage >= sizeY) {
                x = 0;
                sizeY += numberPixel;
                sizeX = 0;
                y += numberPixel + 2;
            }
        }
    }

    /*En este métdo  se dibujan los pedazos de imagen que se seleciona con el clicker y se dibujan en el segundo lienzo*/
    private void drawCutImage(GraphicsContext gc2, CutOutImage cutImage, Board board) {
        if (cutImage.getWritable() != null && board != null) {
            gc2.drawImage(cutImage.getWritable(), board.getX(), board.getY());
            mat[board.getFila()][board.getColumna()] = new Board(board.getX(), board.getY(), board.getSize(), board.getFila(), board.getColumna(), cutImage.getWritable());
        }
    }

    /*aqui se pinta un cuadro del mismo color del canvas haciendo la simulación de que se borro la imagen*/
    private void deleteImage(GraphicsContext gc2, CutOutImage trozo, Board board) {
        if (trozo.getWritable() != null && board != null) {
            gc2.setFill(Color.LIGHTGREY);
            gc2.fillRect(board.getX(), board.getY(), board.getSize(), board.getSize());
            gc2.strokeRect(board.getX(), board.getY(), board.getSize(), board.getSize());
        }
    }

    /*Aquí se pintan la rayas de la matriz del canvas2 lo que hace simular una matriz donde se van a pegar los trozos de la imagen*/
    private void drawLines(GraphicsContext gc2, int amount) {
        int x = 0;
        int y = 0;
        int numberPixel = PixelNumber;
        matrixSize = amount;
        mat = new Board[amount][amount];
        boardMatrix = new CutOutImage[amount][amount];
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < amount; j++) {
                gc2.strokeRect(x, y, numberPixel, numberPixel);
                mat[i][j] = new Board(x, y, numberPixel, i, j, null);
                boardMatrix[i][j] = new CutOutImage(x, y, null, numberPixel);
                x += numberPixel + 2;
            }
            y += numberPixel + 2;
            x = 0;
        }
    }

    /*dibuja la imagen rotada */
    private void drawRotateImage(GraphicsContext gc, Board board) {

        WritableImage writable1;

        this.imageView = new ImageView(board.getWritable());
        this.snapshot = new SnapshotParameters();

        this.imageView.setRotate(imageView.getRotate() + 90); //rota la imagen 90 grados sentido del reloj
        this.image = imageView.snapshot(snapshot, null);
        this.pixel = this.image.getPixelReader();
        writable1 = new WritableImage(this.pixel, 0, 0, board.getSize(), board.getSize()); //Lee los pixeles (imagen, xInici
        board.setWritable(writable1);

        gc.drawImage(board.getWritable(), board.getX(), board.getY(), board.getSize(), board.getSize());
        mat[board.getFila()][board.getColumna()].setWritable(writable1);
    }

    /*dibuja la imagen flip*/
    private void flipImage(GraphicsContext gc, Board board) {

        WritableImage writable1;

        this.imageView = new ImageView(board.getWritable());
        imageView.setTranslateZ(imageView.getBoundsInLocal().getWidth() / 2.0);
        imageView.setRotate(180);
        imageView.setRotationAxis(Rotate.Y_AXIS);
        this.snapshot = new SnapshotParameters();
        this.image = imageView.snapshot(snapshot, null);
        this.pixel = this.image.getPixelReader();
        writable1 = new WritableImage(this.pixel, 0, 0, board.getSize(), board.getSize()); //Lee los pixeles (imagen, xInici
        board.setWritable(writable1);
        gc.drawImage(board.getWritable(), board.getX(), board.getY(), board.getSize(), board.getSize());
        mat[board.getFila()][board.getColumna()].setWritable(writable1);
    }

    /*lo que hace el metodo es hacer una captura de los componentes del canvas */
    public void exportImage() throws IOException {

        gc.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        repaint();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        WritableImage images = this.canvas2.snapshot(new SnapshotParameters(), null);
        File fileI = new File("image");
        ImageIO.write(SwingFXUtils.fromFXImage(images, null), "png", file);
        String path = file.getAbsolutePath();
        drawLines(gc, matrixSize);
        path2 = path;
    }

    /*repinta las imagenes solamente*/
    public void repaint() throws IOException {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[j][i].getWritable() != null) {
                    drawNewPanel(gc, mat[j][i]);
                }
            }
        }
    }

    private void drawNewPanel(GraphicsContext gc2, Board board) {
        if (board.getWritable() != null && board != null) {
            gc2.drawImage(board.getWritable(), board.getX(), board.getY());
        }
    }

    /*lo que hace es pintar la imagen cargada de un proyecto anterior*/
    private void modifiedDrawImage(GraphicsContext gc2, Image image2) {
        gc2.drawImage(image2, 0, 0);
    }
}
