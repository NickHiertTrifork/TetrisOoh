package main;

public class DirectionHelper {

    public static Direction getRotatedDirection(Direction dir, boolean clockwise) {
        if(clockwise) {
            if(dir.getValue() == 3) {
                return Direction.fromInteger(0);
            } else {
                return Direction.fromInteger(dir.getValue()+1);
            }
        } else {
            if(dir.getValue() == 0) {
                return Direction.fromInteger(3);
            } else {
                return Direction.fromInteger(dir.getValue()-1);
            }
        }
    }
}
