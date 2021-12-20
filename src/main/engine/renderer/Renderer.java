package main.engine.renderer;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.drawables.GameObject;
import main.engine.Camera;

import java.util.List;

public class Renderer {

    private Canvas cnv;
    private GraphicsContext ctx;

    private Canvas topLayerCnv;
    private GraphicsContext topLayerCtx;


    public Renderer(Canvas cnv, GraphicsContext ctx, Canvas topLayerCnv, GraphicsContext topLayerCtx) {
        this.cnv = cnv;
        this.ctx = ctx;

        this.topLayerCnv = topLayerCnv;
        this.topLayerCtx = topLayerCtx;
    }

    public void update(List<GameObject> objects) {
        clear();

        for(GameObject object : objects) {
            draw(object);
        }
    }

    public void draw(GameObject object) {
        if(object.getZIndex() == 0)
            object.draw(ctx, Camera.getInstance().getLocation());
        else if(object.getZIndex() == 1) {
            object.draw(topLayerCtx, new Point2D(0,0));
        }
    }

    public void clear() {
        ctx.clearRect(0,0, cnv.getWidth(), cnv.getHeight());
        topLayerCtx.clearRect(0,0,topLayerCnv.getWidth(),topLayerCnv.getHeight());
    }

}
