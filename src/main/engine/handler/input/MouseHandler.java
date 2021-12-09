package main.engine.handler.input;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.view.MainJFX;

public abstract class MouseHandler implements EventHandler<MouseEvent> {

    protected MainJFX jfx;
    public MouseHandler(MainJFX jfx){
        this.jfx = jfx;
    }

    public void handle(MouseEvent event){}
}
