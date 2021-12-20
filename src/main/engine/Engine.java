package main.engine;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.drawables.GameObject;
import main.drawables.ui.SelectBox;
import main.engine.handler.input.InputHandler;
import main.engine.handler.input.MouseHandler;
import main.engine.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private Canvas cnv;
    private GraphicsContext ctx;

    private int frameRate;
    private int frameCount;

    private Timeline gameLoop;

    private Renderer renderer;

    private List<InputHandler> handlers = new ArrayList<>();

    private List<GameObject> gameObjects = new ArrayList<>();

    public Engine(Stage stage) {
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        cnv = new Canvas(stage.getWidth(), stage.getHeight());
        ctx = cnv.getGraphicsContext2D();

        this.renderer = new Renderer(cnv, ctx);

        SelectBox selectBox = new SelectBox(25,25,25,25);
        gameObjects.add(selectBox);

        handlers.add(new MouseHandler(stage, selectBox));



        instantiateCanvas(stage, stage.getWidth(), stage.getHeight());
        instantiateLoop();
        start();
    }

    private void run(Event e) {
        frameCount++;
        this.update();
    }

    public void start() {
        gameLoop.playFromStart();
    }
    public void stop() {
        gameLoop.stop();
    }
    public void resume() { gameLoop.play();}

    private void instantiateCanvas(Stage stage, double x, double y) {
        stage.setResizable(false);
        stage.setTitle("Game Thing");
        Group root = new Group();
        root.getChildren().add(cnv);

        Scene scene = new Scene(root, x, y);

        scene.setFill(Color.WHITE);

        for(InputHandler handler : handlers) {
            if(handler instanceof MouseHandler) {
                scene.addEventFilter(MouseEvent.ANY, handler);
            }
        }

        stage.setScene(scene);
        stage.show();
    }

    private void instantiateLoop() {
        frameRate = 60;
        gameLoop = createLoop();
    }

    private Timeline createLoop() {
        final Duration d = Duration.millis(1000/frameRate);
        final KeyFrame oneFrame = new KeyFrame(d, this::run);
        Timeline t = new Timeline(frameRate,oneFrame);
        t.setCycleCount(Animation.INDEFINITE);
        return t;
    }

    private void update() {

        renderer.update(gameObjects);
    }
}
