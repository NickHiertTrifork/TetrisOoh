package main.engine;

import main.view.MainJFX;
import main.drawables.GameObject;

import java.util.List;

public class Engine {
    private Renderer renderer;

    public Engine(MainJFX jfx, List<GameObject> gameObjects) {
        this.renderer = new Renderer(jfx.getCtx(),gameObjects);
    }
}
