package main;

import main.engine.CollisionHandler;
import main.model.Cell;
import main.model.block.Block;
import main.model.block.BlockState;
import main.model.block.blockGroups.BlockGroup;
import main.model.block.blockGroups.BlockGroupShapeL;
import main.model.block.blockGroups.blockGroupLetters.BlockGroupA;
import main.model.block.blockGroups.blockGroupLetters.BlockGroupG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameWindow {

    private int cellWidth;
    private int cellHeight;

    private int cols;
    private int rows;

    private List<Cell> cells;

    private Cell headCell;
    private Cell centerTopCell;

    private List<Block> allBlocks = new ArrayList<>();
    private BlockGroup currentBlockGroup;

    private int tick = 0;

    private int moveSpeed = 600;

    private int gameOverStepCounter = 3;
    private int gameOverNextLetter = 0;

    private Main main;

    public GameWindow(int width,int height, int cols, int rows, Main main) {
        super();
        //this.setSize(new Dimension(width,height));
        this.cols = cols;
        this.rows = rows;
        this.cellWidth = width/cols;
        this.cellHeight = height/rows;
        //this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.main=main;

        //setVisible(true);

        cells = generateNewCellList(new ArrayList<>(),0);
        allBlocks = generateNewBlocks(new ArrayList<>(), 0);
        mapCells();
        //setUpBoard(headCell);

        centerTopCell = headCell;
        for(int i = 0; i < cols / 2 - 1; i++) {
            centerTopCell = centerTopCell.getEast();
        }

        currentBlockGroup = new BlockGroupShapeL(getInactiveBlocks(allBlocks,4),centerTopCell,0,new MainJFX());

        //this.setBackground(new Color(25,25,100,60));
    }

    private List<Block> getInactiveBlocks(List<Block> blocks, int amount) {
        List<Block> result;

        result = blocks.stream()
                .filter(block -> block.getState() == BlockState.INACTIVE)
                .limit(amount)
                .collect(Collectors.toList());

        result.stream().forEach(block -> block.setState(BlockState.ACTIVE));
        return result;
    }

    private List<Block> generateNewBlocks(List<Block> list, int current) {
        Block block = new Block();
        list.add(block);

        //add(block);

        if(current < cols*rows - 1) {
            return generateNewBlocks(list,current+1);
        }
        return list;
    }

    private List<Cell> generateNewCellList(List<Cell> list, int current) {
        int x = cellWidth * current;
        if(x > cellWidth * cols - 1) {
            x = cellWidth * (current - (cols * (current / cols)));

        }

        int y = current / cols * cellHeight;
        if(y > cellHeight * cols) {
        }

        Cell cell = new Cell(x,y,cellWidth,cellHeight,Integer.toString(current));

        list.add(cell);

        //add(cell);

        if(current < cols*rows -1) {
            return generateNewCellList(list, current + 1);
        }
        return list;
    }

    public void drawGameOver() {
        Cell centerRight = headCell;
        for(int i = 0; i< cols - 1; i++) {
            centerRight = centerRight.getEast();
        }
        for(int i = 0; i < rows / 2 - 4; i++ ) {
            centerRight = centerRight.getSouth();
        }
        if(gameOverNextLetter == 0) {
            currentBlockGroup.getBlocks().addAll(new BlockGroupG(getInactiveBlocks(allBlocks, 14), centerRight, 0, main).getBlocks());

        }else if(gameOverNextLetter == 1) {
            currentBlockGroup.getBlocks().addAll(new BlockGroupA(getInactiveBlocks(allBlocks,10), centerRight,0,main).getBlocks());
        }
        gameOverNextLetter++;
    }

    private void mapCells() {
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
            if(c.findCellByXY(0,0)) {
                headCell = c;
            }
        }
    }

    private void setUpBoard(Cell root) {
        boolean done = false;
        String dir = "east";
        int counter= 0;
        Cell tCell = root;
        while(!done) {
            tCell.setNumber(Integer.toString(counter));
            counter++;

            switch(dir) {
                case "east":
                    if(tCell.getEast() != null) {
                        tCell = tCell.getEast();
                    } else {
                        if(tCell.getSouth() != null) {
                            tCell = tCell.getSouth();
                            dir = "west";
                        } else {
                            done = true;
                        }
                    }
                    break;
                case "west":
                    if(tCell.getWest() != null) {
                        tCell = tCell.getWest();
                    } else {
                        if(tCell.getSouth() != null) {
                            tCell = tCell.getSouth();
                            dir = "east";
                        } else {
                            done = true;
                        }
                    }
                    break;
            }
        }
    }

    public List<Block> getAllBlocks() {
        return allBlocks;
    }

    public BlockGroup getCurrentBlockGroup() {
        return this.currentBlockGroup;
    }

    public void setCurrentBlockGroup(BlockGroup currentBlockGroup) {
        this.currentBlockGroup = currentBlockGroup;
    }

    public void move(Direction direction, boolean skipCheck) {
        boolean canMoveAll = true;

        canMoveAll = CellDirectionHelper.canMoveInDirection(currentBlockGroup,direction);

        if(canMoveAll || skipCheck) {
            if(skipCheck) {
                currentBlockGroup.move(direction);
                //this.repaint();
            }
             else if(!CollisionHandler.isColliding(currentBlockGroup,direction)) {
                currentBlockGroup.move(direction);
                //this.repaint();

            } else if(direction == Direction.SOUTH) {
                currentBlockGroup.getBlocks().forEach(block -> block.setState(BlockState.ROOTED));

                List<Cell> fullRows = CollisionHandler.fullRowsOnBoard(cells);

                if(!fullRows.isEmpty()) {
                    CollisionHandler.remove(fullRows);
                    CollisionHandler.drop(fullRows.size() / cols, cells);
                }

                currentBlockGroup = new BlockGroupShapeL(getInactiveBlocks(allBlocks,4),centerTopCell,1,new MainJFX());
            }
        } else if(direction == Direction.SOUTH) {
            currentBlockGroup.getBlocks().forEach(block -> block.setState(BlockState.ROOTED));

            List<Cell> fullRows = CollisionHandler.fullRowsOnBoard(cells);

            if(!fullRows.isEmpty()) {
                CollisionHandler.remove(fullRows);
                CollisionHandler.drop(fullRows.size() / cols, cells);
            }

            currentBlockGroup = new BlockGroupShapeL(getInactiveBlocks(allBlocks,4),centerTopCell,1,new MainJFX());

            //this.repaint();
        }
    }

    public void rotate(boolean clockwise) {
        boolean canRotate = true;

        canRotate = CellDirectionHelper.canRotateInDirection(currentBlockGroup,clockwise);

    }

    public void updateGame(long deltaMilli, boolean gameOver) {
        tick+= deltaMilli;

        if(!gameOver) {
            if (tick >= moveSpeed) {
                tick = 0;

                this.move(Direction.SOUTH,false);
            }
        } else {
            if(tick > 1000) {
                tick = 0;
                gameOverStepCounter++;
                this.move(Direction.WEST,true);

                if(gameOverStepCounter / 5 == 1) {
                    gameOverStepCounter = 0;
                    drawGameOver();
                }
            }
        }
    }

    /*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Component c : this.getComponents()) {
            c.paint(g);
        }
        repaint();
    }

     */


}
