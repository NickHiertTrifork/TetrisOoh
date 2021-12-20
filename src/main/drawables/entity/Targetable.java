package main.drawables.entity;

import javafx.geometry.Point2D;

public interface Targetable {

    public Point2D getLocation();
    public void damage(double damage);
}
