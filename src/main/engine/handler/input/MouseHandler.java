package main.engine.handler.input;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.drawables.ui.SelectBox;
import main.engine.Camera;

import static main.engine.handler.input.MouseHandler.MouseOutsideDirection.*;

public class MouseHandler extends InputHandler<MouseEvent> {

    private double x;
    private double y;

    private double xClicked;
    private double yClicked;

    private Stage stage;

    private SelectBox selectBox;

    private MouseOutsideDirection outsideDirection = INSIDE;

    private CurrentMouseButton currentMouseButton;


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

        Camera camera = Camera.getInstance();

        if(e.getX() <= stage.getX()+2 && e.getY() <= stage.getY()) {
            //todo: top left
            if(!camera.getAction().equals(TOP_LEFT)) {
                camera.setAction(TOP_LEFT);
            }
        } else if(e.getX() <= stage.getX()+2 && e.getY() > stage.getY()+2 && e.getY() < stage.getHeight()-2) {
            //todo: left side of stage
            if(!camera.getAction().equals(LEFT)) {
                camera.setAction(LEFT);
            }
        } else if(e.getX() <= stage.getX()+2 && e.getY() >= stage.getHeight()-2) {
            //todo: bottom left
            if(!camera.getAction().equals(BOTTOM_LEFT)) {
                camera.setAction(BOTTOM_LEFT);
            }
        } else if(e.getX() >= stage.getWidth()-2 && e.getY() <= stage.getY()+2) {
            //todo: top right
            if(!camera.getAction().equals(TOP_RIGHT)) {
                camera.setAction(TOP_RIGHT);
            }
        } else if(e.getX() >= stage.getWidth()-2 && e.getY() > stage.getY()+2 && e.getY() < stage.getHeight()-2) {
            //todo: right side of stage
            if(!camera.getAction().equals(RIGHT)) {
                camera.setAction(RIGHT);
            }
        } else if(e.getX() >= stage.getWidth()-2 && e.getY() >= stage.getHeight()-2) {
            //todo: bottom right
            if(!camera.getAction().equals(BOTTOM_RIGHT)) {
                camera.setAction(BOTTOM_RIGHT);
            }
        } else if(e.getX() > stage.getX()+2 && e.getX() < stage.getWidth()-2 && e.getY() <= stage.getY()+2) {
            if(!camera.getAction().equals(TOP)) {
                camera.setAction(TOP);
            }
        } else if(e.getX() > stage.getX()+2 && e.getX() < stage.getWidth()-2 && e.getY() >= stage.getHeight()-2) {
            if(!camera.getAction().equals(BOTTOM)) {
                camera.setAction(BOTTOM);
            }
        } else {
            if(!camera.getAction().equals(INSIDE)) {
                camera.setAction(INSIDE);
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
        currentMouseButton = CurrentMouseButton.PRIMARY;
        System.out.println("Left clicked at x: " + x + ", y: "+ y );
    }

    private void handleRightPressed(MouseEvent e) {
        currentMouseButton = CurrentMouseButton.SECONDARY;
        System.out.println("Right clicked at x: " + x + ", y: "+ y );
    }

    private void handleMiddlePressed(MouseEvent e) {
        currentMouseButton = CurrentMouseButton.MIDDLE;
        System.out.println("Middle clicked at x: " + x + ", y: "+ y );
    }

    private void handleDragged(MouseEvent e) {

        if(currentMouseButton.equals(CurrentMouseButton.PRIMARY))
            handleLeftDragged(e);
    }

    private void handleLeftDragged(MouseEvent e) {
        setCoordinates(e);

        updateSelectBox();

        System.out.println("Dragging");
    }

    private void updateSelectBox() {

        double tempX;
        double tempWidth;
        double tempY;
        double tempHeight;

        if(x - xClicked > 0) {
            tempX = xClicked;
            tempWidth = x - xClicked;
        } else {
            tempX = x;
            tempWidth = xClicked - x;
        }

        if(y - yClicked > 0) {
            tempY = yClicked;
            tempHeight = y - yClicked;
        } else {
            tempY = y;
            tempHeight = yClicked - y;
        }
        selectBox.setLocation(new Point2D(tempX,tempY));
        selectBox.setDimension(new Dimension2D(tempWidth,tempHeight));
    }

    private void handleReleased(MouseEvent e) {
        System.out.println("Released");

        if(currentMouseButton.equals(CurrentMouseButton.PRIMARY))
            handleLeftReleased(e);
    }

    private void handleLeftReleased(MouseEvent e) {
        clearSelectBox();
    }

    private void clearSelectBox() {
        selectBox.setLocation(new Point2D(0,0));
        selectBox.setDimension(new Dimension2D(0,0));
    }

    public enum MouseOutsideDirection{
        TOP_LEFT,LEFT,BOTTOM_LEFT,TOP_RIGHT,RIGHT,BOTTOM_RIGHT,TOP,BOTTOM, INSIDE
    }

    public enum CurrentMouseButton{
        PRIMARY, SECONDARY, MIDDLE
    }
}
