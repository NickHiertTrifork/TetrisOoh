package main.engine.handler.input;

import javafx.scene.input.ScrollEvent;
import main.engine.Camera;

public class ScrollHandler extends InputHandler<ScrollEvent>{

    public ScrollHandler() {

    }

    @Override
    public void handle(ScrollEvent e) {
        double deltaY = e.getDeltaY();
        if(deltaY > 0) {
            Camera.getInstance().increaseZoomAmount();
        } else {
            Camera.getInstance().decreaseZoomAmount();
        }
    }

    public enum ZoomAmount{
        NORMAL, ZOOM25, ZOOM50
    }
}
