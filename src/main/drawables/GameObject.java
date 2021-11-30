package main.drawables;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface GameObject {
    public void draw(GraphicsContext g);
    public void setColor(Color c);
}
