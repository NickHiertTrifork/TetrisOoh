package main.engine;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import main.MainJFX;
import main.model.GameObject;

import java.util.List;

public class Renderer extends AnimationTimer {

    private GraphicsContext ctx;
    private List<GameObject> gameObjects;
    public Renderer(GraphicsContext ctx, List<GameObject> gameObjects) {

    }
    @Override
    public void handle(long l) {
       // jfx.update(jfx.getCtx());
    }
}
