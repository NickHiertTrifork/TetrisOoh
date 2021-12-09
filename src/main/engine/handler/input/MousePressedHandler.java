package main.engine.handler.input;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import main.drawables.Point;
import main.view.MainJFX;

public class MousePressedHandler extends MouseHandler{

    public MousePressedHandler(MainJFX jfx) {
        super(jfx);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(jfx.isGameOver()) {
            if(mouseEvent.getSceneX() < jfx.getCnv().getWidth() && mouseEvent.getSceneY() < jfx.getCnv().getHeight()) {
                for(Point p : jfx.getPoints()) {
                    if(p.isWithinBorders((int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY())) {
                        jfx.setDragging(p);
                        jfx.setOffsetX((int) (jfx.getDragging().getLocation().getX() - mouseEvent.getSceneX()));
                        jfx.setOffsetY((int) (jfx.getDragging().getLocation().getY() - mouseEvent.getSceneY()));
                        break;
                    }
                }
                if(jfx.getDragging() == null) {
                    jfx.getPoints().add(new Point((int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY()));
                    jfx.getGameObjectPoints().add(jfx.getPoints().get(jfx.getPoints().size() - 1));
                    jfx.setDragging(jfx.getPoints().get(jfx.getPoints().size()-1));
                    jfx.addLine();
                    jfx.draw(jfx.getCtx());
                }
            }
        }
    }
}
