package main.engine.handler.input;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.drawables.ui.SelectBox;

import java.nio.channels.Pipe;

import static main.engine.handler.input.MouseHandler.MouseOutsideDirection.*;

public class MouseHandler extends InputHandler<MouseEvent> {

    private double x;
    private double y;

    private double xClicked;
    private double yClicked;

    private Stage stage;

    private SelectBox selectBox;

    private MouseOutsideDirection outsideDirection = INSIDE;


    public MouseHandler(Stage stage, SelectBox selectBox) {
        this.stage = stage;
        this.selectBox = selectBox;
    }

    protected void setCoordinates(MouseEvent event) {
        this.x = event.getX();
        this.y = event.getY();
    }

    protected void setClickedCoordinates(MouseEvent e) {
        this.xClicked = e.getX();
        this.yClicked = e.getY();
    }

    @Override
    public void handle(MouseEvent e) {
        setCoordinates(e);
        if(e.getEventType().equals(MouseEvent.MOUSE_MOVED))
            handleMouseMovement(e);

        if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED))
            handlePressed(e);

        if(e.getEventType().equals(MouseEvent.MOUSE_DRAGGED))
            handleDragged(e);

        if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED))
            handleReleased(e);
    }

    private void handleMouseMovement(MouseEvent e) {
        //basically this is only for boundary checks
        //todo: if mouse is outside of bounds, use Robot to move it back.
        //      If it's moving back, then scroll the camera.
        //      This handler would need to send something back, or know about Robot / window..

        if(e.getX() <= stage.getX()+2 && e.getY() <= stage.getY()+2) {
            //todo: top left
            if(!outsideDirection.equals(TOP_LEFT)) {
                outsideDirection = TOP_LEFT;
                System.out.println("top left");
            }
        } else if(e.getX() <= stage.getX()+2 && e.getY() > stage.getY()+2 && e.getY() < stage.getHeight()-2) {
            //todo: left side of stage
            if(!outsideDirection.equals(LEFT)) {
                outsideDirection = LEFT;
                System.out.println("left side");
            }
        } else if(e.getX() <= stage.getX()+2 && e.getY() >= stage.getHeight()-2) {
            //todo: bottom left
            if(!outsideDirection.equals(BOTTOM_LEFT)) {
                outsideDirection = BOTTOM_LEFT;
                System.out.println("bottom left");
            }
        } else if(e.getX() >= stage.getWidth()-2 && e.getY() <= stage.getY()+2) {
            //todo: top right
            if(!outsideDirection.equals(TOP_RIGHT)) {
                outsideDirection = TOP_RIGHT;
                System.out.println("top right");
            }
        } else if(e.getX() >= stage.getWidth()-2 && e.getY() > stage.getY()+2 && e.getY() < stage.getHeight()-2) {
            //todo: right side of stage
            if(!outsideDirection.equals(RIGHT)) {
                outsideDirection = RIGHT;
                System.out.println("right side");
            }
        } else if(e.getX() >= stage.getWidth()-2 && e.getY() >= stage.getHeight()-2) {
            //todo: bottom right
            if(!outsideDirection.equals(BOTTOM_RIGHT)) {
                outsideDirection = BOTTOM_RIGHT;
                System.out.println("bottom right");
            }
        } else if(e.getX() > stage.getX()+2 && e.getX() < stage.getWidth()-2 && e.getY() <= stage.getY()+2) {
            if(!outsideDirection.equals(TOP)) {
                outsideDirection = TOP;
                System.out.println("top");
            }
        } else if(e.getX() > stage.getX()+2 && e.getX() < stage.getWidth()-2 && e.getY() >= stage.getHeight()-2) {
            if(!outsideDirection.equals(BOTTOM)) {
                outsideDirection = BOTTOM;
                System.out.println("bottom");
            }
        } else {
            if(!outsideDirection.equals(INSIDE)) {
                outsideDirection = INSIDE;
                System.out.println("inside");
            }
        }
    }

    private void handlePressed(MouseEvent e) {
        if(e.isPrimaryButtonDown())
            handleLeftPressed(e);
        else if(e.isSecondaryButtonDown())
            handleRightPressed(e);
        else if(e.isMiddleButtonDown())
            handleMiddlePressed(e);
    }

    private void handleLeftPressed(MouseEvent e) {
        setClickedCoordinates(e);
        System.out.println("Left clicked at x: " + x + ", y: "+ y );
    }

    private void handleRightPressed(MouseEvent e) {
        System.out.println("Right clicked at x: " + x + ", y: "+ y );
    }

    private void handleMiddlePressed(MouseEvent e) {
        System.out.println("Middle clicked at x: " + x + ", y: "+ y );
    }

    private void handleDragged(MouseEvent e) {

        if(e.isPrimaryButtonDown())
            handleLeftDragged(e);
    }

    private void handleLeftDragged(MouseEvent e) {
        setCoordinates(e);

        selectBox.setX(xClicked);
        selectBox.setY(yClicked);
        selectBox.setWidth(x);
        selectBox.setHeight(y);

        System.out.println("Dragging");
    }

    private void handleReleased(MouseEvent e) {
        System.out.println("Released");
    }

    public enum MouseOutsideDirection{
        TOP_LEFT,LEFT,BOTTOM_LEFT,TOP_RIGHT,RIGHT,BOTTOM_RIGHT,TOP,BOTTOM, INSIDE
    }
}
