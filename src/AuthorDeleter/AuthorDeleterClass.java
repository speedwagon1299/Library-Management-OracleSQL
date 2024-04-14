package AuthorDeleter;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuthorDeleterClass extends Application
{
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            Parent root = FXMLLoader.load(getClass().getResource("AuthorDeleterFXML.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Library Management System");
            primaryStage.show();
        } 
        catch (IOException e) {}
    }
 
    public static void main(String[] args)     
    {
        launch(args);
    }
}
