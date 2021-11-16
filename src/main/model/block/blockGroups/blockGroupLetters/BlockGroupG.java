package main.model.block.blockGroups.blockGroupLetters;

import javafx.scene.paint.Color;
import main.Direction;
import main.Main;
import main.model.Cell;
import main.model.block.Block;
import main.model.block.blockGroups.BlockGroup;

import java.util.List;

public class BlockGroupG extends BlockGroup {

    public BlockGroupG(List<Block> newActiveBlocks, Cell centerTopCell, int index, Main main) {
        direction = Direction.SOUTH;
        super.index = index;

        blocks = newActiveBlocks;

        int canPlace = this.tryPlaceBlocksInShape(direction, newActiveBlocks, centerTopCell);

        for(Block b : blocks) {
            b.setColor(Color.RED);
            //b.setVisible(true);
        }
    }

    public int tryPlaceBlocksInShape(Direction direction, List<Block> activeBlocks, Cell center) {

        int counter = 0;

        for(Block b : activeBlocks) {
            if(counter < 3) {
                if(center != null) {
                    b.setCurrentCell(center);
                    center = center.getWest();
                }
            } else if(counter < 7) {
                if(center != null) {
                    b.setCurrentCell(center);
                    center = center.getSouth();
                }
            } else if(counter < 10) {
                if(center != null) {
                    b.setCurrentCell(center);
                    center = center.getEast();
                }
            } else if(counter < 13) {
                if(center != null) {
                    b.setCurrentCell(center);
                    center = center.getNorth();
                }
            }
            else {
                b.setCurrentCell(center.getSouth().getWest());
            }
            b.setText(Integer.toString(counter));
            counter++;
        }

        return 1;
    }
}
