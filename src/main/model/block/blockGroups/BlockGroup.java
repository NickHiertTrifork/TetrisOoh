package main.model.block.blockGroups;

import main.CellDirectionHelper;
import main.Direction;
import main.DirectionHelper;
import main.model.Cell;
import main.model.block.Block;
import main.model.block.BlockState;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockGroup {

    protected List<Block> blocks = new ArrayList<>();
    public int index;

    protected Direction direction;
    protected Block origin;

    public void move(Direction direction) {
        List<Block> blocksToBeRemoved = new ArrayList<>();
        for(Block b : blocks) {
            if(CellDirectionHelper.getCellAtDirection(b.getCurrentCell(),direction) != null && b.getState() == BlockState.ACTIVE) {
                b.move(direction);
            } else if(b.getState() == BlockState.ACTIVE){
                blocksToBeRemoved.add(b);
                b.setState(BlockState.INACTIVE);
            }
        }
        for(Block b : blocksToBeRemoved) {
            blocks.remove(b);
        }
    }

    public Direction getDirection() {
        return this.direction;
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public void rotate(boolean clockwise) {
        direction = DirectionHelper.getRotatedDirection(direction,clockwise);
        int tryPlace = tryPlaceBlocksInShape(direction, blocks,origin.getCurrentCell());
        if(tryPlace == 0) {
            direction = DirectionHelper.getRotatedDirection(direction, !clockwise);
        }
    }

    public abstract int tryPlaceBlocksInShape(Direction direction, List<Block> activeBlocks, Cell center);

    protected int checkPlacementReturnType(List<Cell> oldCells, List<Cell> newCells, List<Block> activeBlocks) {
        if (oldCells.get(0) == null) {
            for (Cell c : newCells) {
                if (c.getOccupied() != null) {
                    return 2;
                }
            }
        }

        for(int i = 0; i<newCells.size();i++) {
            Cell c = newCells.get(i);
            if(c != null && c.getOccupied() != null) {
                if(!activeBlocks.contains(c.getOccupied())) {
                    newCells.set(i,null);
                }
            }
        }

        //If newCells doesn't contain null, we can place.
        if (!newCells.contains(null)) {
            for (int i = 0; i < activeBlocks.size(); i++) {
                activeBlocks.get(i).setCurrentCell(newCells.get(i));
                activeBlocks.get(i).setText(Integer.toString(i));
            }
            if (this.origin == null) this.origin = activeBlocks.get(0);
            return 1;
        } else {
            return 0;
        }
    }
}
