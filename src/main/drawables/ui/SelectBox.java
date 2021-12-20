package main.drawables.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.drawables.GameObject;

public class SelectBox implements GameObject {
    private double x;
    private double y;
    private double width;
    private double height;

    public SelectBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext g) {
        g.setFill(Color.BLACK);
        //g.setGlobalAlpha(.5);
        g.fillRect(x,y,width,height);
        g.stroke();
    }

    @Override
    public void setColor(Color c) {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }


}
