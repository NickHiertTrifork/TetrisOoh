package main.engine;

import main.Direction;
import main.model.Cell;
import main.model.block.Block;
import main.model.block.BlockState;
import main.model.block.blockGroups.BlockGroup;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler {

    public static boolean isColliding(BlockGroup group, Direction direction) {
        boolean flag = false;

        for(Block block : group.getBlocks()) {
            switch(direction) {
                case SOUTH:
                    if(group.getBlocks().contains(block.getCurrentCell().getSouth().getOccupied())
                            || block.getCurrentCell().getSouth().getOccupied() == null) {
                        flag = false;
                    } else {

                        return true;
                    }
                    break;
                case EAST:
                    if(group.getBlocks().contains(block.getCurrentCell().getEast().getOccupied())
                        || block.getCurrentCell().getEast().getOccupied() == null) {
                        flag = false;
                    } else {
                        return true;
                    }
                    break;
                case NORTH:
                    if(group.getBlocks().contains(block.getCurrentCell().getNorth().getOccupied())
                            || block.getCurrentCell().getNorth().getOccupied() == null) {
                        flag = false;
                    } else {
                        return true;
                    }
                    break;
                case WEST:
                    if(group.getBlocks().contains(block.getCurrentCell().getWest().getOccupied())
                            || block.getCurrentCell().getWest().getOccupied() == null) {
                        flag = false;
                    } else {
                        return true;
                    }
                    break;
            }
        }

        return flag;
    }

    public static List<Cell> fullRowsOnBoard(List<Cell> cells) {

        List<Cell> fullRows = new ArrayList<>();

        boolean rowDone = false;


        Cell currentRow = cells.get(0);
        Cell currentCell = currentRow;

        while(currentRow != null) {
            currentCell = currentRow;
            rowDone = false;
            List<Cell> newRow = new ArrayList<>();


            while (!rowDone) {
                if(currentCell.getOccupied() == null) {
                    rowDone = true;
                    newRow.clear();
                    break;
                } else{
                    newRow.add(currentCell);
                    if(currentCell.getEast() != null) {
                        currentCell = currentCell.getEast();
                    } else {
                        rowDone = true;
                    }
                }
            }

            if(!newRow.isEmpty()) {
                for(Cell c : newRow) {
                    fullRows.add(c);
                }
            }
            currentRow = currentRow.getSouth();
        }

        return fullRows;
    }

    public static void remove(List<Cell> cellsRemoving) {
        for(Cell c : cellsRemoving) {
            c.getOccupied().setState(BlockState.INACTIVE);
            c.setOccupied(null);
            drop(c);
        }
    }

    private static void drop(Cell cellRemoving) {
        Cell currentCell = cellRemoving;
        while(currentCell.getNorth() != null) {
            if(currentCell.getOccupied() == null) {
                currentCell.setOccupied(currentCell.getNorth().getOccupied());
                if(currentCell.getOccupied() != null) {
                    currentCell.getOccupied().setCurrentCell(currentCell);
                }
                currentCell.getNorth().setOccupied(null);
            }

            currentCell = currentCell.getNorth();
        }
    }

    public static void drop(int linesCleared ,List<Cell> cells) {
        int amountToMoveDown = linesCleared;
        Cell topLeft = cells.get(0);
        Cell bottom = topLeft;
        while(bottom.getSouth() != null) {
            bottom = bottom.getSouth();
        }
        Cell currentCol = bottom;


        for(int i = 0; i < amountToMoveDown; i++) {
            while(currentCol != null) {
                Cell currentCell = currentCol;
                while(currentCell.getNorth() != null) {
                    if(currentCell.getOccupied() == null) {
                        currentCell.setOccupied(currentCell.getNorth().getOccupied());
                        if (currentCell.getOccupied() != null) {
                            currentCell.getOccupied().setCurrentCell(currentCell);
                        }
                        currentCell.getNorth().setOccupied(null);
                    }
                    currentCell = currentCell.getNorth();
                }
                currentCol = currentCol.getEast();
            }
            currentCol = bottom;
        }
    }
}
