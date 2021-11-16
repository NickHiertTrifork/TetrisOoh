package main.model.block.blockGroups;

import javafx.scene.paint.Color;
import main.CellDirectionHelper;
import main.Direction;
import main.DirectionHelper;
import main.MainJFX;
import main.model.Cell;
import main.model.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockGroupShapeLRev extends BlockGroup{

    public BlockGroupShapeLRev(List<Block> newActiveBlocks, Cell centerTopCell, int index, MainJFX main) {
        direction = Direction.SOUTH;
        super.index = index;

        blocks = newActiveBlocks;

        int canPlace = this.tryPlaceBlocksInShape(direction, newActiveBlocks, centerTopCell);
        if (canPlace == 2) {
            main.setGameOver(true);
        }

        for (Block b : blocks) {
            b.setColor(Color.BLUE);
            //b.setVisible(true);
        }
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /*
    0 = cant rotate
    1 = can rotate & can place
    2 = cant place: game over.
     */

    public int tryPlaceBlocksInShape(Direction direction, List<Block> activeBlocks, Cell origin) {
        List<Cell> oldCells = activeBlocks.stream().map(Block::getCurrentCell).collect(Collectors.toList());
        List<Cell> newCells = new ArrayList<>();
        Cell temp = null;
        if (this.origin == null) {
            temp = origin.getSouth();
        } else {
            temp = origin;
        }

        newCells.add(temp);

        newCells.add(CellDirectionHelper.getCellAtDirection(temp, direction));

        temp = CellDirectionHelper.getCellAtDirection(temp,
                DirectionHelper.getRotatedDirection(
                        DirectionHelper.getRotatedDirection(direction, false), false));
        newCells.add(temp);

        temp = CellDirectionHelper.getCellAtDirection(temp, DirectionHelper.getRotatedDirection(direction, true));
        newCells.add(temp);

        return checkPlacementReturnType(oldCells,newCells,activeBlocks);
    }
}
