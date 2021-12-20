package main.drawables.entity.unit;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.drawables.entity.Entity;

public class Unit extends Entity {

    public Unit(Point2D location, Dimension2D dimension) {
        super(location,dimension);
    }

    @Override
    public void draw(GraphicsContext g, Point2D offset) {
        g.setGlobalAlpha(1);
        g.fillRect(location.getX() - offset.getX(),location.getY() - offset.getY(), 50, 50);
    }

    @Override
    public void setColor(Color c) {

    }
}
