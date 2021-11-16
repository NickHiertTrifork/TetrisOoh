package main;

import main.model.Cell;
import main.model.block.Block;
import main.model.block.blockGroups.BlockGroup;

public class CellDirectionHelper {

    public static boolean canMoveInDirection(BlockGroup group, Direction direction) {
        boolean flag = true;
        for(Block origin : group.getBlocks())
        switch(direction) {
            case SOUTH:
                if(flag == false) {
                    return false;
                }

                if(origin.getCurrentCell().getSouth() != null) {
                    flag = true;
                } else {
                    flag = false;
                }
                break;
            case NORTH:
                if(flag == false) {
                    return false;
                }

                if(origin.getCurrentCell().getNorth() != null) {
                    flag = true;
                } else {
                    flag = false;
                }
                break;
            case EAST:
                if(flag == false) {
                    return false;
                }

                if(origin.getCurrentCell().getEast() != null) {
                    flag = true;
                } else {
                    flag = false;
                }
                break;
            case WEST:
                if(flag == false) {
                    return false;
                }

                if(origin.getCurrentCell() != null && origin.getCurrentCell().getWest() != null) {
                    flag = true;
                } else {
                    flag = false;
                }
                break;
        }
        return flag;
    }

    public static boolean canRotateInDirection(BlockGroup group, boolean clockwise) {
        boolean flag = true;
        for(Block origin : group.getBlocks()) {

        }

        return true;
    }

    public static Cell getCellAtDirection(Cell origin, Direction direction) {
        Cell destination = null;
        if (origin == null) {
            return origin;
        }
        switch(direction) {
            case SOUTH:
                destination = origin.getSouth();
                break;
            case NORTH:
                destination = origin.getNorth();
                break;
            case EAST:
                destination = origin.getEast();
                break;
            case WEST:
                destination = origin.getWest();
                break;

        }
        return destination;
    }
}
