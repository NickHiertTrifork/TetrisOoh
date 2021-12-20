package main.engine;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.drawables.GameObject;
import main.drawables.Grid;
import main.drawables.entity.unit.Unit;
import main.drawables.ui.SelectBox;
import main.engine.handler.input.InputHandler;
import main.engine.handler.input.MouseHandler;
import main.engine.handler.input.ScrollHandler;
import main.engine.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private Canvas cnv;
    private GraphicsContext ctx;

    private Canvas topLayerCnv;
    private GraphicsContext topLayerCtx;

    private int frameRate;
    private int frameCount;

    private Timeline gameLoop;

    private Renderer renderer;

    private MouseHandler.MouseOutsideDirection mouseOutsideDirection = MouseHandler.MouseOutsideDirection.INSIDE;

    private List<InputHandler> handlers = new ArrayList<>();

    private List<GameObject> gameObjects = new ArrayList<>();

    public Engine(Stage stage) {
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        cnv = new Canvas(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        ctx = cnv.getGraphicsContext2D();

        topLayerCnv = new Canvas(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        topLayerCtx = topLayerCnv.getGraphicsContext2D();

        this.renderer = new Renderer(cnv, ctx, topLayerCnv, topLayerCtx);

        SelectBox selectBox = new SelectBox(new Point2D(0,0), new Dimension2D(0,0));
        gameObjects.add(selectBox);
        gameObjects.add(new Unit(new Point2D(800,800), new Dimension2D(32,32)));
        gameObjects.add(new Unit(new Point2D(400,400), new Dimension2D(32,32)));

        handlers.add(new MouseHandler(stage, selectBox));
        handlers.add(new ScrollHandler());

        Camera.getInstance();

        gameObjects.add(new Grid(null,new Dimension2D(1000,1000)));



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
        root.getChildren().add(topLayerCnv);

        Scene scene = new Scene(root, x, y);

        scene.setFill(Color.WHITE);

        for(InputHandler handler : handlers) {
            if(handler instanceof MouseHandler) {
                scene.addEventFilter(MouseEvent.ANY, handler);
            } else if(handler instanceof ScrollHandler) {
                scene.addEventFilter(ScrollEvent.ANY, handler);
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

        if(!Camera.getInstance().getAction().equals(MouseHandler.MouseOutsideDirection.INSIDE)) {
            Camera.getInstance().move();
        }
        renderer.update(gameObjects);
    }
}
