package main.model;

public class Player{

    private Cell currentCell;

    public Player(Cell startingCell) {
        this.currentCell = startingCell;
        //setName("Player");
    }

    /*
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawOval(currentCell.getX() + currentCell.getWidth()/4,
                   currentCell.getY() + currentCell.getHeight()/3,
                currentCell.getWidth()/2,currentCell.getHeight()/2);
    }

     */

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }
}
