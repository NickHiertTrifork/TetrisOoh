package main.view;

import javafx.application.Application;
import javafx.stage.Stage;
import main.engine.Engine;

public class MainJFX extends Application {

    private Engine engine;

    public MainJFX() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        engine = new Engine(stage);
    }
}
