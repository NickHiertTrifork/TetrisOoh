package main.drawables;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point implements GameObject {
    private Point2D location;
    private int size = 8;
    private Color color = Color.RED;

    public Point(int x, int y) {
        location = new Point2D(x,y);
    }

    public Point(Point2D location) {
        this.location = location;
    }

    public Point(Point point) {
        this.location = point.getLocation();
    }

    public Point(int x, int y, int size) {
        this.location = new Point2D(x,y);
        this.size = size;
    }

    public Point(Point2D location, int size) {
        this.location = location;
        this.size = size;
    }

    public Point2D getLocation() {
        return this.location;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setLocation(Point2D location) {
        this.location=location;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public boolean isWithinBorders(int x, int y) {
        if(x > location.getX()-size/2 && x < location.getX()+size/2 &&
           y > location.getY()-size/2 && y < location.getY()+size/2) {
            System.out.println("inside");
            return true;
        }
        System.out.println("outside");
        return false;
    }

    public void draw(GraphicsContext g) {
        g.setFill(color);
        g.fillOval(location.getX()-size/2,location.getY()-size/2,size,size);
    }
}
