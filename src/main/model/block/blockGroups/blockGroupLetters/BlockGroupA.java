package main.model.block.blockGroups.blockGroupLetters;

import javafx.scene.paint.Color;
import main.Direction;
import main.Main;
import main.model.Cell;
import main.model.block.Block;
import main.model.block.blockGroups.BlockGroup;

import java.util.List;

public class BlockGroupA extends BlockGroup {

    public BlockGroupA(List<Block> newActiveBlocks, Cell centerTopCell, int index, Main main) {
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
        center = center.getSouth();
        for(Block b : activeBlocks) {
            if(counter < 4) {
                if(center != null) {
                    b.setCurrentCell(center);
                    center = center.getSouth();
                }
            } else if(counter < 5) {
                if(center != null) {
                    center = center.getNorth().getNorth().getNorth().getNorth().getNorth().getWest();
                    b.setCurrentCell(center);
                }
            } else if(counter < 6) {
                if(center != null) {
                    b.setCurrentCell(center.getSouth().getSouth());
                    center = center.getWest().getSouth();
                }
            }
            else {
                if(center != null) {
                    b.setCurrentCell(center);
                    center = center.getSouth();
                }
            }
            b.setText(Integer.toString(counter));
            counter++;
        }

        return 1;
    }
}
