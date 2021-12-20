package main.engine.renderer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import main.drawables.GameObject;
import java.util.List;

public class Renderer {

    private Canvas cnv;
    private GraphicsContext ctx;


    public Renderer(Canvas cnv, GraphicsContext ctx) {
        this.cnv = cnv;
        this.ctx = ctx;
    }

    public void update(List<GameObject> objects) {
        clear();
        for(GameObject object : objects) {
            draw(object);
        }
    }

    public void draw(GameObject object) {
        object.draw(ctx);
    }

    public void clear() {
        ctx.clearRect(0,0, cnv.getWidth(), cnv.getHeight());
    }

}
