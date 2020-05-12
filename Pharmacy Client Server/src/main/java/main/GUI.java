package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GUI extends Application //implements EventHandler<ActionEvent>
{
    private Stage window;


    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClassLoader classLoader = GUI.class.getClassLoader();
        Parent root = FXMLLoader.load(classLoader.getResource("login.fxml"));
        window=primaryStage;

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add("Style.css");
        window.setScene(mainScene);
        window.setTitle("Pharmacy App");
        window.show();
    }


    public static Scene changeScene(String fxml) throws IOException{
        ClassLoader classLoader = GUI.class.getClassLoader();
        Parent pane = FXMLLoader.load(classLoader.getResource(fxml));

        Stage stage = new Stage();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add("Style.css");
        stage.setScene(newScene);
        stage.show();

        return newScene;
    }
}