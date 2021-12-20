package main.drawables;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell extends GameObject{

    private static Dimension2D CELL_SIZE = new Dimension2D(32,32);

    public Cell(Point2D location, Dimension2D dimension) {
        super(location, CELL_SIZE);
    }

    @Override
    public void draw(GraphicsContext g, Point2D offset) {
        g.rect(location.getX(),location.getY(),dimension.getWidth(), dimension.getHeight());
    }

    @Override
    public void setColor(Color c) {

    }

    @Override
    public int getZIndex() {
        return 0;
    }

    public static Dimension2D getCellSize() {
        return CELL_SIZE;
    }
}
