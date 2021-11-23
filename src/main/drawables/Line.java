package main.drawables;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line implements GameObject {

    private Point startPoint;
    private Point endPoint;

    private Color color = Color.BLACK;

    public Line(Point startPoint, Point endPoint) {
        this.startPoint=startPoint;
        this.endPoint=endPoint;
    }

    public void setColor(Color color) {
        this.color=color;
    }

    public void draw(GraphicsContext g) {
        g.setStroke(color);
        g.beginPath();
        g.moveTo(startPoint.getLocation().getX(), startPoint.getLocation().getY());
        g.lineTo(endPoint.getLocation().getX(), endPoint.getLocation().getY());
        g.stroke();
    }

    @Override
    public String toString() {
        return startPoint.getLocation().toString() + " " + endPoint.getLocation().toString();
    }
}
