package main.engine.handler.input;

import javafx.scene.input.MouseEvent;
import main.view.MainJFX;

public class MouseReleasedHandler extends MouseHandler{

    public MouseReleasedHandler(MainJFX jfx) {
        super(jfx);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(jfx.getDragging() != null && jfx.isGameOver()) {
            jfx.setDragging(null);
            jfx.repopulateGameObjects();
            jfx.draw(jfx.getCtx());
        }
    }
}
