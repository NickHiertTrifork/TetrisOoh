package main.drawables.ui;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.drawables.GameObject;

public class SelectBox extends GameObject {

    public SelectBox(Point2D location, Dimension2D dimension) {
        super(location, dimension);
    }

    @Override
    public void draw(GraphicsContext g, Point2D offset) {
        g.setFill(Color.LIGHTGREEN);
        g.setGlobalAlpha(.5);
        g.fillRect(location.getX(), location.getY(), dimension.getWidth(), dimension.getHeight());
        g.stroke();
    }

    @Override
    public int getZIndex() {
        return 1;
    }


}
