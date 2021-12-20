package main.engine;

import javafx.geometry.Point2D;
import main.engine.handler.input.MouseHandler;

public class Camera {

    private Point2D location;
    private static Camera instance = null;
    private MouseHandler.MouseOutsideDirection action = MouseHandler.MouseOutsideDirection.INSIDE;
    private double scrollSpeed = 4;

    public synchronized static Camera getInstance() {
        if(instance == null) {
            instance = new Camera();
            instance.setLocation(new Point2D(1000 - (1920/2),500 - (1080/2)));
        }
        return instance;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public void setAction(MouseHandler.MouseOutsideDirection outsideDirection) {
        this.action = outsideDirection;
    }

    public MouseHandler.MouseOutsideDirection getAction() {
        return action;
    }

    public void move() {

        location = switch (action) {
            case TOP -> location.subtract(0,scrollSpeed);
            case RIGHT -> location.add(scrollSpeed,0);
            case BOTTOM -> location.add(0,scrollSpeed);
            case LEFT -> location.subtract(scrollSpeed,0);
            case TOP_LEFT -> location.subtract(scrollSpeed,scrollSpeed);
            case TOP_RIGHT -> location.add(scrollSpeed,-scrollSpeed);
            case BOTTOM_LEFT -> location.add(-scrollSpeed,scrollSpeed);
            case BOTTOM_RIGHT -> location.add(scrollSpeed,scrollSpeed);
            case INSIDE -> location;
        };
        if(location.getX() < 0) location = new Point2D(0, location.getY());
        if(location.getY() < 0) location = new Point2D(location.getX(), 0);

        System.out.println(location);
    }
}
