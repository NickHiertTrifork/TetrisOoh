package main;

public enum Direction {
    NORTH (0),
    EAST (1),
    SOUTH(2),
    WEST(3);

    private int value;

    private Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Direction fromInteger(int n) {
        for(Direction type : values()) {
            if(type.getValue() == n) {
                return type;
            }
        }
        return null;
    }
};
