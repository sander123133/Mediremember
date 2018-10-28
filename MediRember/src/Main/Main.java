package Main;

import ScreenBuilders.inlogschermBuilder;
import Utility.DbConnector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    DbConnector dbConnector;
    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane inlogscherm = new GridPane();
        dbConnector = new DbConnector();
        Scene scene = new Scene(inlogscherm,300,275);
        new inlogschermBuilder(scene, inlogscherm, dbConnector);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
