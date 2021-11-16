package main.engine;

import main.MainJFX;
import main.model.GameObject;

import java.util.List;

public class Engine {
    private KeyEventListener listener;
    private Renderer renderer;

    public Engine(MainJFX jfx, List<GameObject> gameObjects) {
        this.listener = new KeyEventListener(jfx);
        this.renderer = new Renderer(jfx.getCtx(),gameObjects);
    }
}
