package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.views.GeneratorViews;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GeneratorViews view = new GeneratorViews(primaryStage);
        Scene scene = view.getStartView();

        primaryStage.setTitle("International Library System");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/books.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}