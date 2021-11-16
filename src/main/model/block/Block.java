package main.model.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.CellDirectionHelper;
import main.Direction;
import main.model.Cell;
import main.model.GameObject;


public class Block extends GameObject {

    int x = -50;
    int y = -50;
    int width = 0;
    int height = 0;
    private Color color;
    String text = "";

    private Cell currentCell;
    private BlockState state = BlockState.INACTIVE;

    public Block() {
    }

    public Cell getCurrentCell() {
        return this.currentCell;
    }

    public void setCurrentCell(Cell cell) {

        state=BlockState.ACTIVE;

        if(currentCell != null && currentCell.getOccupied() == this) {
            currentCell.setOccupied(null);
        }
        this.currentCell = cell;

        this.currentCell.setOccupied(this);
        this.x = this.currentCell.getX() +5;
        this.y = this.currentCell.getY() +5;
        this.width = this.currentCell.getWidth() -10;
        this.height = this.currentCell.getHeight() - 10;
    }

    public void setState(BlockState state) {
        this.state = state;
        if(state == BlockState.INACTIVE) {
            x = -50;
            y = -50;
            if(currentCell != null) {
                if(currentCell.getOccupied() != null) {
                    currentCell.setOccupied(null);
                }
            }
            currentCell = null;
        }
    }

    public BlockState getState() {
        return this.state;
    }

    public void move(Direction direction) {
        if(currentCell.getOccupied() == this) {
            currentCell.setOccupied(null);
        }
        this.currentCell = CellDirectionHelper.getCellAtDirection(currentCell, direction);
        currentCell.setOccupied(this);
        this.x=currentCell.getX() + 5;
        this.y=currentCell.getY() + 5;
    }

    public void setColor(Color color) {
        this.color=color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setText(String text) { this.text=text; }

    public String getText() { return this.text; }


    @Override
    public void draw(GraphicsContext g) {
        g.setFill(this.color);
        g.fillRect(x,y,width,height);
        g.setStroke(Color.WHITE);

        //g.strokeText(text, x +5,y +10);
    }

    @Override
    public String toString() {
        return "Block{" +
                "currentCell=" + currentCell +
                ", state=" + state +
                ", text='" + text + '\'' +
                '}';
    }
}
