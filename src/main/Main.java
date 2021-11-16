package main;

import main.engine.KeyEventListener;
import main.model.Cell;
import main.model.block.Block;
import main.model.block.BlockState;
import main.model.block.blockGroups.BlockGroup;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {

    double interpolation = 0;
    static final int TIMER_DELAY = 35;
    static final int TICKS_PER_SECOND=60;
    static final int SKIP_TICKS = 1000/TICKS_PER_SECOND;

    static final int width = 1000;
    static final int height = 1000;

    static final int sizes = 8;

    private int cellWidth = width / sizes;
    private int cellHeight = height / sizes;

    private boolean gameOver;

    GameWindow gw;

    Main(){
        //setFocusable(true);

        //this.setLayout(new GridBagLayout());
        //GridBagConstraints constraints = new GridBagConstraints();

        gw = new GameWindow(600,900,10,15,this);
        //gw.setMinimumSize(new Dimension(800,800));

        //constraints.gridx = 0;
        //constraints.gridy = 0;
        //constraints.fill = GridBagConstraints.BOTH;
        //constraints.weightx=1;
        //constraints.weighty=1;
        //add(gw, constraints);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        //setTitle("TestFrame");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);

        //addKeyListener(new KeyEventListener(gw));

        //this.validate();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver=gameOver;
    }

    public static void main(String[] args) {
        Main m = new Main();

        Duration deltaTime = Duration.ZERO;
        Instant beginTime = Instant.now();
        Instant lastTick = Instant.now();

        m.gameOver=false;

        while(!m.gameOver) {

            if(deltaTime.toMillisPart() > SKIP_TICKS) {
                lastTick = Instant.now();

                m.gw.updateGame(deltaTime.toMillisPart(), m.gameOver);

            }
            deltaTime = Duration.between(lastTick, Instant.now());
        }
        for(Block b : m.gw.getAllBlocks()) {
            if(b.getState() != BlockState.INACTIVE) {
                b.setState(BlockState.INACTIVE);
            }
        }

        //m.removeKeyListener(m.getKeyListeners()[0]);
        m.gw.setCurrentBlockGroup(
                new BlockGroup() {
            @Override
            public int tryPlaceBlocksInShape(Direction direction, List<Block> activeBlocks, Cell center) {
                return 0;
            }
        });

        while(m.gameOver) {

            if(deltaTime.toMillisPart() > SKIP_TICKS) {
                lastTick = Instant.now();

                m.gw.updateGame(deltaTime.toMillisPart(),m.gameOver);

            }
            deltaTime = Duration.between(lastTick, Instant.now());
        }
        //System.exit(0);
    }
}
