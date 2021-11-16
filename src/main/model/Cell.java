package main.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.model.block.Block;


public class Cell extends GameObject{
    private Cell north = null;
    private Cell south = null;
    private Cell east = null;
    private Cell west = null;

    private int x;
    private int y;
    private int width;
    private int height;
    private String name;

    private Block occupied;

    public Cell(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public Cell getNorth() {
        return north;
    }

    public void setNorth(Cell north) {
        this.north = north;
    }

    public Cell getSouth() {
        return south;
    }

    public void setSouth(Cell south) {
        this.south = south;
    }

    public Cell getEast() {
        return east;
    }

    public void setEast(Cell east) {
        this.east = east;
    }

    public Cell getWest() {
        return west;
    }

    public void setWest(Cell west) {
        this.west = west;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getNumber() {
        return name;
    }

    public Block getOccupied() {
        return this.occupied;
    }

    public void setOccupied(Block block) {
        this.occupied=block;
    }

    public void setNumber(String name) {
        this.name = name;
    }

    public boolean findCellByXY(int x, int y) {
        if(this.x == x && this.y == y) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cell{" +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", name=" + name +
                '}';
    }

    @Override
    public void draw(GraphicsContext g) {
        g.setFill(new Color(0.4,0.4,0.7,0.5));
        g.fillRect(x,y,width,height);
        g.setStroke(Color.WHITE);
        g.strokeRect(x,y,width,height);

        if(occupied != null) {
        }
    }


}
