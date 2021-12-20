package main.drawables.entity;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import main.drawables.GameObject;
import main.drawables.entity.Targetable;

public abstract class Entity extends GameObject implements Targetable {

    private double health;
    private double armor;

    public Entity(Point2D location, Dimension2D dimension) {
        super(location,dimension);
    }

    @Override
    public void damage(double damage) {
        health -= (damage / armor);
    }
}
