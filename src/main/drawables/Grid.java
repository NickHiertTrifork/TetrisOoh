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

        generateNewCellList(grid,0,Cell.getCellSize().getWidth(),Cell.getCellSize().getHeight(),mapSizeInCells.getWidth(), mapSizeInCells.getHeight());
    }

    private Dimension2D calculateMapSizeInCells(Dimension2D mapSize) {
        double width = mapSize.getWidth() / Cell.getCellSize().getWidth();
        double height = mapSize.getHeight() / Cell.getCellSize().getHeight();

        return new Dimension2D(width,height);
    }

    public static List<Cell> generateNewCellList(List<Cell> list, int current,
                                                 double cellWidth,double cellHeight, double cols, double rows) {
        double x = cellWidth * current;
        if(x > cellWidth * cols - 1) {
            x = cellWidth * (current - (cols * (current / cols)));
        }

        double y = current / cols * cellHeight;
        if(y > cellHeight * cols) {
        }

        Cell cell = new Cell(new Point2D(x,y),new Dimension2D(cellWidth,cellHeight));

        list.add(cell);

        if(current < cols*rows -1) {
            return generateNewCellList(list, current + 1,cellWidth,cellHeight,cols,rows);
        }
        return list;
    }
}
