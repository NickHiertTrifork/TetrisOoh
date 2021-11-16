package main.engine;

import main.MainJFX;
import main.model.Cell;
import main.model.block.Block;
import main.model.block.blockGroups.BlockGroup;
import main.model.block.blockGroups.BlockGroupShapeCross;
import main.model.block.blockGroups.BlockGroupShapeL;
import main.model.block.blockGroups.BlockGroupShapeLRev;

import java.util.List;
import java.util.Random;

public class Generator {

    public static List<Cell> generateNewCellList(List<Cell> list, int current,
                                                  int cellWidth,int cellHeight, int cols, int rows) {
        int x = cellWidth * current;
        if(x > cellWidth * cols - 1) {
            x = cellWidth * (current - (cols * (current / cols)));
        }

        int y = current / cols * cellHeight;
        if(y > cellHeight * cols) {
        }

        Cell cell = new Cell(x,y,cellWidth,cellHeight,Integer.toString(current));

        list.add(cell);

        if(current < cols*rows -1) {
            return generateNewCellList(list, current + 1,cellWidth,cellHeight,cols,rows);
        }
        return list;
    }


    public static void mapCells(List<Cell> cells, int cellWidth, int cellHeight) {
        for(Cell c : cells) {
            c.setWest(cells.stream()
                    .filter(tCell -> tCell.findCellByXY(c.getX()-cellWidth,c.getY()))
                    .findFirst().orElse(null));

            c.setEast(cells.stream()
                    .filter(tCell -> tCell.findCellByXY(c.getX() + cellWidth,c.getY()))
                    .findFirst().orElse(null));

            c.setSouth(cells.stream()
                    .filter(tCell -> tCell.findCellByXY(c.getX(), c.getY() + cellHeight))
                    .findFirst().orElse(null));

            c.setNorth(cells.stream()
                    .filter(tCell -> tCell.findCellByXY(c.getX(), c.getY() - cellHeight))
                    .findFirst().orElse(null));
        }
    }

    public static List<Block> generateNewBlocks(List<Block> list, int current, int cols, int rows) {
        Block block = new Block();
        list.add(block);

        //add(block);

        if(current < cols*rows - 1) {
            return generateNewBlocks(list,current+1, cols,rows);
        }
        return list;
    }

    public static BlockGroup generateNewBlockGroup(List<Block> newActiveBlocks, Cell centerTopCell, int index, MainJFX main) {
        int totalBlockTypes = 3;
        Random randomGen = new Random();
        int random = randomGen.nextInt(totalBlockTypes);
        BlockGroup group = null;
        switch (random) {
            case 0:
                group = new BlockGroupShapeL(newActiveBlocks,centerTopCell,index,main);
                break;
            case 1:
                group = new BlockGroupShapeLRev(newActiveBlocks,centerTopCell,index,main);
                break;
            case 2:
                group = new BlockGroupShapeCross(newActiveBlocks,centerTopCell,index,main);
                break;
        }
        return group;
    }
}
