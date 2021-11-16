package main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.engine.CollisionHandler;
import main.engine.Generator;
import main.engine.KeyEventListener;
import main.engine.Renderer;
import main.model.Cell;
import main.model.GameObject;
import main.model.block.Block;
import main.model.block.BlockState;
import main.model.block.blockGroups.BlockGroup;
import main.model.block.blockGroups.BlockGroupShapeL;
import main.model.block.blockGroups.BlockGroupShapeLRev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainJFX extends Application {

    private List<GameObject> gameObjects = new ArrayList<>();
    private List<Cell> cells = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();

    private Canvas cnv =  new Canvas(600,800);
    private GraphicsContext ctx = cnv.getGraphicsContext2D();

    int frameRate;
    int frameCount = 0;
    private final Timeline gameLoop;

    private int moveSpeed = 30;

    private int gameOverStepCounter = 3;
    private int gameOverNextLetter = 0;

    private int cols = 10;
    private int rows = 15;

    private Cell centerTopCell;

    private BlockGroup currentBlockGroup;

    private boolean gameOver;


    public MainJFX() {
        frameRate=60;
        gameLoop = createLoop();
    }

    private Timeline createLoop() {
        final Duration d = Duration.millis(1000/frameRate);
        final KeyFrame oneFrame = new KeyFrame(d, this::run);
        Timeline t = new Timeline(frameRate,oneFrame);
        t.setCycleCount(Animation.INDEFINITE);
        return t;
    }

    private void run(Event e) {
        frameCount++;
        this.update(gameOver);
        this.render(ctx);
    }

    public void start() {
        gameLoop.playFromStart();
    }
    public void stop() {
        gameLoop.stop();
    }

    public Canvas getCnv() {
        return this.cnv;
    }

    public GraphicsContext getCtx() {
        return this.ctx;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Test?");

        Group root = new Group();
        root.getChildren().add(cnv);
        Scene scene = new Scene(root,800,800);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new KeyEventListener(this));
        scene.setFill(Color.DARKGRAY);
        stage.setScene(scene);
        stage.show();

        cols = 10;
        rows = 15;
        int cellWidth = (int) cnv.getWidth()/cols;
        int cellHeight = (int) cnv.getHeight()/rows;

        cells = Generator.generateNewCellList(new ArrayList<Cell>(),0,cellWidth,cellHeight,cols,rows);
        Generator.mapCells(cells,cellWidth,cellHeight);

        centerTopCell = cells.get(0).getEast().getEast().getEast().getEast();

        blocks = Generator.generateNewBlocks(new ArrayList<Block>(),0,cols,rows);

        cells.stream().forEach(gameObjects::add);
        blocks.stream().forEach(gameObjects::add);

        currentBlockGroup = Generator.generateNewBlockGroup(getInactiveBlocks(blocks,4),centerTopCell,0,this);

        this.draw(ctx);

        this.start();
    }

    private void draw(GraphicsContext ctx) {
        ctx.clearRect(0,0,ctx.getCanvas().getWidth(),ctx.getCanvas().getHeight());
        for(GameObject g : gameObjects) {
            g.draw(ctx);
        }
    }

    public BlockGroup getCurrentBlockGroup() {
        return this.currentBlockGroup;
    }

    private void update(boolean gameOver) {
        if (!gameOver) {
            if(frameCount > moveSpeed) {
                frameCount = 0;
                this.move(Direction.SOUTH, false);
            }
        } else {
            gameOverStepCounter++;
            this.move(Direction.WEST, true);
            if (gameOverStepCounter / 5 == 1) {
                gameOverStepCounter = 0;
            }
        }
    }

    public void move(Direction direction, boolean skipCheck) {
        boolean canMoveAll;

        canMoveAll = CellDirectionHelper.canMoveInDirection(currentBlockGroup,direction);

        if(canMoveAll || skipCheck) {
            if(skipCheck) {
                currentBlockGroup.move(direction);
            }
            else if(!CollisionHandler.isColliding(currentBlockGroup,direction)) {
                currentBlockGroup.move(direction);

            } else if(direction == Direction.SOUTH) {
                currentBlockGroup.getBlocks().forEach(block -> block.setState(BlockState.ROOTED));

                List<Cell> fullRows = CollisionHandler.fullRowsOnBoard(cells);

                if(!fullRows.isEmpty()) {
                    CollisionHandler.remove(fullRows);
                }
                currentBlockGroup = Generator.generateNewBlockGroup(getInactiveBlocks(blocks,4),centerTopCell,0,this);
            }
        } else if(direction == Direction.SOUTH) {
            currentBlockGroup.getBlocks().forEach(block -> block.setState(BlockState.ROOTED));

            List<Cell> fullRows = CollisionHandler.fullRowsOnBoard(cells);

            if(!fullRows.isEmpty()) {
                CollisionHandler.remove(fullRows);
            }
            currentBlockGroup = Generator.generateNewBlockGroup(getInactiveBlocks(blocks,4),centerTopCell,0,this);
        }
    }

    public void render(GraphicsContext ctx) {
        this.draw(ctx);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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
}
