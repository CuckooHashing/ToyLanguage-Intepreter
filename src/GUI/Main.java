package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        StartWindow contr = loader.getController();
        contr.setStartWindow(primaryStage);
        Scene primaryScene = new Scene(root, 800, 600);
        primaryScene.getStylesheets().add(getClass().getResource("Background.css").toExternalForm());
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
