package main.drawables.entity.building;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.drawables.entity.Entity;
import main.drawables.entity.Targetable;

public abstract class Building extends Entity implements Targetable {

    public Building(Point2D location, Dimension2D dimension) {
        super(location,dimension);
    }

    @Override
    public void draw(GraphicsContext g, Point2D offset) {

    }

    @Override
    public void setColor(Color c) {

    }
}
