package main.utils;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorCycler {
    public static List<Color> generateListOfColors() {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.PINK);
        colors.add(Color.PURPLE);
        colors.add(Color.GREEN);
        colors.add(Color.LIME);
        colors.add(Color.CHOCOLATE);

        return colors;
    }

    public static int getNextColorIndex(int index, List<Color> colors) {
            if(index == colors.size() -1) {
                return 0;
            }
            return index + 1;
    }
}
