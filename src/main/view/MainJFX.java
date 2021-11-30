package main.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.drawables.GameObject;
import main.drawables.Line;
import main.drawables.Point;
import main.utils.ColorCycler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainJFX extends Application {
    private Spinner<Integer> spinner;
    private Slider slider;

    private List<GameObject> gameObjectLines = new ArrayList<>();
    private List<GameObject> gameObjectPoints = new ArrayList<>();

    private boolean showLines = true;
    private boolean showPoints = true;
    private boolean showInterpLines = true;
    private boolean showInterpPoints = true;

    private Point dragging = null;
    private int offsetX = 0;
    private int offsetY = 0;
    
    private List<Color> colors = new ArrayList<>();
    private int currentColorIndex = 0;

    private Canvas cnv =  new Canvas(800,600);
    private Canvas layercnv = new Canvas(800,600);
    private GraphicsContext ctx = cnv.getGraphicsContext2D();
    private GraphicsContext layerctx = layercnv.getGraphicsContext2D();

    double t = 0;
    double increment = 0.001;

    int frameRate;
    int frameCount = 0;
    private final Timeline gameLoop;

    private boolean pause;
    private boolean gameOver = true;

    private List<Point> points = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    public MainJFX() {
        frameRate=60;
        gameLoop = createLoop();
    }

    private Timeline createLoop() {
        final Duration d = Duration.millis(1000/frameRate);
        final KeyFrame oneFrame = new KeyFrame(d, this::run);
        Timeline t = new Timeline(frameRate,oneFrame);
        t.setCycleCount(Animation.INDEFINITE);
        return t;
    }

    private void run(Event e) {
        frameCount++;
        this.update(gameOver);
        this.render(ctx);
    }

    public void start() {
        gameLoop.playFromStart();
    }
    public void stop() {
        gameLoop.stop();
    }

    public Canvas getCnv() {
        return this.cnv;
    }

    public GraphicsContext getCtx() {
        return this.ctx;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //required window setup (adds button + canvases)

        CheckBox checkBoxLines = new CheckBox("show lines");
        checkBoxLines.setSelected(true);
        CheckBox checkBoxInterpLines = new CheckBox("show interpolated lines");
        checkBoxInterpLines.setSelected(true);
        CheckBox checkBoxPoints = new CheckBox("show points");
        checkBoxPoints.setSelected(true);
        CheckBox checkBoxInterpPoints = new CheckBox("show interpolated points");
        checkBoxInterpPoints.setSelected(true);
        CheckBox checkBoxBezier = new CheckBox("Show Bezier curve");
        checkBoxBezier.setSelected(true);

        spinner = new Spinner();
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinner.setValueFactory(valueFactory);

        spinner.setEditable(false);
        spinner.setPrefSize(60,10);
        Label spinnerLabel = new Label();
        spinnerLabel.setText("Speed : ");
        spinnerLabel.setLabelFor(spinner);

        slider = new Slider();
        slider.setBlockIncrement(increment);
        slider.setMin(0);
        slider.setMax(1);

        Label sliderLabel = new Label();
        sliderLabel.setText("Time : ");
        sliderLabel.setLabelFor(slider);

        stage.setResizable(false);
        stage.setTitle("Test?");
        Group root = new Group();
        root.getChildren().add(cnv);
        root.getChildren().add(layercnv);

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        setUpButtonsInHBox(hBox);

        hBox.getChildren().add(spinnerLabel);
        hBox.getChildren().add(spinner);
        hBox.getChildren().add(sliderLabel);
        hBox.getChildren().add(slider);
        hBox.setLayoutX(0);
        hBox.setLayoutY(800-100);

        HBox hBox2 = new HBox();
        hBox2.getChildren().add(checkBoxLines);
        hBox2.getChildren().add(checkBoxPoints);
        hBox2.getChildren().add(checkBoxBezier);
        hBox2.setLayoutX(0);
        hBox2.setLayoutY(800-50);

        HBox hBox3 = new HBox();
        hBox3.getChildren().add(checkBoxInterpLines);
        hBox3.getChildren().add(checkBoxInterpPoints);
        hBox3.setLayoutX(0);
        hBox3.setLayoutY(800 - 25);

        root.getChildren().add(hBox);
        root.getChildren().add(hBox2);
        root.getChildren().add(hBox3);
        Scene scene = new Scene(root, 800, 800);

        scene.setFill(Color.WHITE);

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(gameOver) {
                if(mouseEvent.getSceneX() < cnv.getWidth() && mouseEvent.getSceneY() < cnv.getHeight()) {
                    for(Point p : points) {
                        if(p.isWithinBorders((int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY())) {
                            this.dragging = p;
                            this.offsetX =(int) (dragging.getLocation().getX() - mouseEvent.getSceneX());
                            this.offsetY =(int) (dragging.getLocation().getY() - mouseEvent.getSceneY());
                            break;
                        }
                    }
                    if(dragging == null) {
                        points.add(new Point((int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY()));
                        gameObjectPoints.add(points.get(points.size() - 1));
                        this.dragging = points.get(points.size()-1);
                        addLine();
                        draw(ctx);
                    }
                }
            }
        });

        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent ->  {
            if(dragging != null && gameOver) {
                dragging.setLocation(new Point2D(mouseEvent.getSceneX() + offsetX, mouseEvent.getSceneY() + offsetY));
                repopulateGameObjects();
                draw(ctx);
            }
        });

        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if(dragging != null && gameOver) {
                dragging = null;
                repopulateGameObjects();
                draw(ctx);
            }
        });

        stage.setScene(scene);
        stage.show();

        colors = ColorCycler.generateListOfColors();


        currentColorIndex++;

        checkBoxLines.setOnAction(value -> showLines = !showLines);

        checkBoxPoints.setOnAction(value -> showPoints = !showPoints);

        checkBoxInterpLines.setOnAction(value -> showInterpLines = !showInterpLines);

        checkBoxInterpPoints.setOnAction(value -> showInterpPoints = !showInterpPoints);

        checkBoxBezier.setOnAction(value -> layercnv.setVisible(!layercnv.isVisible()));

        slider.valueProperty().addListener((observableValue, number, t1) -> {
            if(points.size() > 0) {
                t = slider.getValue();
                repopulateGameObjects();
                currentColorIndex = 1;
                drawInterpolation(points.toArray(new Point[points.size()]), t);
                draw(ctx);
            }
        });
    }

    private void setUpButtonsInHBox(HBox hBox) {
        setUpButton(new Button("Play"),
                hBox,
                event -> {
                    gameOver = false;
                    pause = false;
                    if(points.size() > 0) {
                        this.start();
                    }
                });

        setUpButton(new Button("Pause"),
                    hBox,
                    event -> pause = !pause);

        setUpButton(new Button("Reset"),
                    hBox,
                    event -> resetCanvas());

        setUpButton(new Button("Clear"),
                    hBox,
                    event -> clearCanvas());
    }

    private void resetCanvas() {
        resetCanvasToStartingPosition();
        repopulateGameObjects();
        draw(ctx);
    }

    private void resetCanvasToStartingPosition() {
        gameOver = true;
        t = 0;
        slider.setValue(t);
        clear(ctx);
        clear(layerctx);
    }

    private void clearCanvas() {
        resetCanvasToStartingPosition();
        gameObjectLines = new ArrayList<>();
        gameObjectPoints = new ArrayList<>();
        points = new ArrayList<>();
        lines = new ArrayList<>();
    }

    private void setUpButton(Button b, HBox hBox, EventHandler handler) {
        hBox.getChildren().add(b);
        b.setOnAction(handler);
    }

    private void addLine() {
        if(points.size() > 1) {
            lines.add(new Line(points.get(points.size()-1), points.get(points.size()-2)));
            gameObjectLines.add(lines.get(lines.size()-1));
        }
    }

    private void draw(GraphicsContext ctx) {
        ctx.clearRect(0,0,ctx.getCanvas().getWidth(),ctx.getCanvas().getHeight());
        if(showPoints) {
            for (GameObject g : gameObjectPoints) {
                g.draw(ctx);
            }
        }
        if(showLines) {
            for (GameObject g : gameObjectLines) {
                g.draw(ctx);
            }
        }
    }

    private void clear(GraphicsContext ctx) {
        ctx.clearRect(0,0,ctx.getCanvas().getWidth(),ctx.getCanvas().getHeight());
    }

    private void repopulateGameObjects() {
        gameObjectPoints = new ArrayList<>();
        gameObjectLines = new ArrayList<>();
        points.forEach(p -> gameObjectPoints.add(p));
        lines.forEach(l -> gameObjectLines.add(l));
    }

    private void update(boolean gameOver) {
        if (!gameOver && !pause) {
            t += increment;
            currentColorIndex = 1;
            increment = (double) spinner.getValue() / 1000;
            slider.setBlockIncrement(increment);
            slider.increment();
            if(slider.getValue() != t) {
                t = slider.getValue();
            }
            repopulateGameObjects();
            currentColorIndex = 1;
            drawInterpolation(points.toArray(new Point[points.size()]),t);
            draw(ctx);
        }
        if(t >= 1) {
            this.gameOver=true;
        }
    }

    public void drawInterpolation(Point[] points, double t) {
        if(points.length == 1) {
            points[0].setSize(2);
            points[0].setColor(Color.DARKGRAY);
            points[0].draw(layerctx);
        } else {
            currentColorIndex = ColorCycler.getNextColorIndex(currentColorIndex, colors);
            Point[] newPoints = new Point[points.length-1];
            for(int i = 0; i < newPoints.length; i++) {
                newPoints[i] = new Point(interpolatePoint(points[i],points[i+1],t));
                if(showInterpPoints) {
                    gameObjectPoints.add(newPoints[i]);
                }
                if(i > 0) {
                    Line l = new Line(newPoints[i],newPoints[i-1]);
                    l.setColor(colors.get(currentColorIndex));
                    if(showInterpLines) {
                        gameObjectLines.add(l);
                    }
                }
            }
            drawInterpolation(newPoints,t);
        }
    }

    public Point2D interpolatePoint(Point a, Point b, double t) {
        return interpolatePoint(a.getLocation(),b.getLocation(),t);
    }

    public Point2D interpolatePoint(Point2D a, Point2D b, double t) {
        Point2D c = b.subtract(a).multiply(t).add(a);
        return c;
    }

    public void render(GraphicsContext ctx) {
        this.draw(ctx);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
