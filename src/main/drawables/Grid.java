package main.drawables;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Grid extends GameObject{

    private List<Cell> grid = new ArrayList<>();

    public Grid(Point2D empty, Dimension2D mapSize) {
        super(null,null);
        createGrid(mapSize);
    }

    public List<Cell> getGrid() {
        return this.grid;
    }

    @Override
    public void draw(GraphicsContext g, Point2D offset) {
        for(Cell c: grid) {
            c.draw(g,offset);
        }
    }

    private void createGrid(Dimension2D mapSize) {
        Dimension2D mapSizeInCells = calculateMapSizeInCells(mapSize);

        for(int i = 0; i < mapSizeInCells.getWidth() * mapSizeInCells.getHeight(); i++) {

        }
    }

    private Dimension2D calculateMapSizeInCells(Dimension2D mapSize) {
        double width = mapSize.getWidth() / Cell.getCellSize().getWidth();
        double height = mapSize.getHeight() / Cell.getCellSize().getHeight();

        return new Dimension2D(width,height);
    }
}
