package main.engine.handler.input;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import main.view.MainJFX;

public class MouseDraggedHandler extends MouseHandler{

    public  MouseDraggedHandler(MainJFX jfx) {
        super(jfx);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(jfx.getDragging() != null && jfx.isGameOver()) {
            jfx.getDragging().setLocation(new Point2D(mouseEvent.getSceneX() + jfx.getOffsetX(), mouseEvent.getSceneY() + jfx.getOffsetY()));
            jfx.repopulateGameObjects();
            jfx.draw(jfx.getCtx());
        }
    }
}
