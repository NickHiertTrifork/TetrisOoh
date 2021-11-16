package main.engine;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.Direction;
import main.MainJFX;


public class KeyEventListener implements EventHandler {

    MainJFX jfx;

    public KeyEventListener(MainJFX jfx) {
        this.jfx = jfx;
    }

    @Override
    public void handle(Event event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            keyPressed((KeyEvent) event);
        }
        event.consume();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        //left
        if(e.getCode() == KeyCode.LEFT) {
            jfx.move(Direction.WEST,false);
        }

        //up
        if(e.getCode() == KeyCode.UP) {
            jfx.move(Direction.NORTH,false);
        }

        //right
        if(e.getCode() == KeyCode.RIGHT) {
            jfx.move(Direction.EAST,false);
        }

        //down
        if(e.getCode() == KeyCode.DOWN) {
            jfx.move(Direction.SOUTH,false);
        }

        //z
        if(e.getCode() == KeyCode.Z) {
            jfx.getCurrentBlockGroup().rotate(false);

        }

        //x
        if(e.getCode() == KeyCode.X) {
            jfx.getCurrentBlockGroup().rotate(true);

        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
