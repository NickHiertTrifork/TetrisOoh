package main.drawables;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class GameObject {

    protected Point2D location;
    protected Dimension2D dimension;

    public GameObject(Point2D location, Dimension2D dimension) {
        this.location = location;
        this.dimension = dimension;
    }

    public void draw(GraphicsContext g, Point2D offset) {}
    public void setColor(Color c) {}
    public int getZIndex() { return 0; }

    public Point2D getLocation() { return location; }

    public Dimension2D getDimension() { return dimension; }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public void setDimension(Dimension2D dimension) {
        this.dimension = dimension;
    }
}