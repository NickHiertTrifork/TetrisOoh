package main.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.drawables.GameObject;
import main.drawables.Line;
import main.drawables.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainJFX extends Application {


    private Spinner<Integer> spinner;


    private List<Line> gameObjectLines = new ArrayList<>();
    private List<Point> gameObjectPoints = new ArrayList<>();

    private boolean showLines = true;
    private boolean showPoints = true;
    private boolean showInterpLines = true;
    private boolean showInterpPoints = true;
    
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
    private boolean gameOver;

    private Point[] points = new Point[6];
    private Line[] lines = new Line[5];
    private List<Point> interPoints = new ArrayList<>();
    private List<Point> interInterPoints = new ArrayList<>();

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
        //required window setup (adds button + canvases
        Button startButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        Button resetButton = new Button("Reset");
        Button clearButton = new Button("Clear");

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

        stage.setResizable(false);
        stage.setTitle("Test?");
        Group root = new Group();
        root.getChildren().add(cnv);
        root.getChildren().add(layercnv);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().add(startButton);
        hBox.getChildren().add(pauseButton);
        hBox.getChildren().add(resetButton);
        hBox.getChildren().add(clearButton);
        hBox.getChildren().add(spinnerLabel);
        hBox.getChildren().add(spinner);
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

        scene.setFill(Color.DARKGRAY);
        stage.setScene(scene);
        stage.show();

        genAllowedColors();

        //add drawable stuff here (Point, line, activePoint)
        points[0] = new Point(100,100);
        points[1] = new Point(150,50);
        points[2] = new Point(160,230);
        points[3] = new Point(230,400);
        points[4] = new Point(240,50);
        points[5] = new Point(300,300);
        this.draw(layerctx);
        this.draw(ctx);

        for(int i = 0; i < points.length; i++) {
            Point p = points[i];
            p.draw(ctx);
            if(i > 0) {
                Line l = new Line(p,points[i-1]);
                l.setColor(colors.get(currentColorIndex));
                lines[i-1] = l;
                l.draw(ctx);
            }
        }
        currentColorIndex++;

        startButton.setOnAction(value -> {
            gameOver = false;
            pause = false;
            this.start();
        });

        pauseButton.setOnAction(value -> {
            if(pause) {
                pause = false;
            } else {
                pause = true;
            }
        });

        resetButton.setOnAction(value -> {
            gameOver = true;
            t = 0;
            clear(ctx);
            clear(layerctx);
            repopulateGameObjects();
            draw(ctx);
        });

        clearButton.setOnAction(value -> {
            gameOver = true;
            t = 0;
            clear(ctx);
            clear(layerctx);
            gameObjectLines = new ArrayList<>();
            gameObjectPoints = new ArrayList<>();
            points = null;
            lines = null;
        });

        checkBoxLines.setOnAction(value -> {
            if(showLines) {
                showLines = false;
            } else {
                showLines = true;
            }
        });

        checkBoxPoints.setOnAction(value -> {
            if(showPoints) {
                showPoints = false;
            } else {
                showPoints = true;
            }
        });

        checkBoxInterpLines.setOnAction(value -> {
            if(showInterpLines) {
                showInterpLines = false;
            } else {
                showInterpLines = true;
            }
        });

        checkBoxInterpPoints.setOnAction(value -> {
            if(showInterpPoints) {
                showInterpPoints = false;
            } else {
                showInterpPoints = true;
            }
        });

        checkBoxBezier.setOnAction(value -> {
            if(layercnv.isVisible()) {
                layercnv.setVisible(false);
            } else {
                layercnv.setVisible(true);
            }
        });

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
        Arrays.stream(points).forEach(p -> gameObjectPoints.add(p));
        Arrays.stream(lines).forEach(l -> gameObjectLines.add(l));
    }

    private void update(boolean gameOver) {
        if (!gameOver && !pause) {
            t += increment;
            currentColorIndex = 1;
            increment = (double) spinner.getValue() / 1000;
            repopulateGameObjects();
            drawInterpolation(points,t);
            draw(ctx);
        }
        if(t >= 1) {
            this.gameOver=true;
        }
    }

    public void drawInterpolation(Point[] points, double t) {
        if(points.length == 1) {
            points[0].draw(layerctx);
        } else {
            currentColorIndex = getNextColor(currentColorIndex);
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

    private int getNextColor(int currentColorIndex) {
        if(currentColorIndex == colors.size() -1) {
            return 0;
        }
        return currentColorIndex + 1;
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

    private void genAllowedColors() {
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.PURPLE);
        colors.add(Color.BEIGE);
    }
}
