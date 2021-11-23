package main.drawables;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point implements GameObject {
    private Point2D location;

    public Point(int x, int y) {
        location = new Point2D(x,y);
    }

    public Point(Point2D location) {
        this.location = location;
    }

    public Point(Point point) {
        this.location = point.getLocation();
    }

    public Point2D getLocation() {
        return this.location;
    }

    public void setLocation(Point2D location) {
        this.location=location;
    }

    public void draw(GraphicsContext g) {
        g.setFill(Color.RED);
        g.fillOval(location.getX()-4,location.getY()-4,8,8);
    }
}
