package LoginPage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginPageController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane anchorpaneLayout;

    @FXML
    private Button cancel_button;

    @FXML
    private Label error_msg;

    @FXML
    private Button login_button;

    @FXML
    private PasswordField password_tf;

    @FXML
    private TextField username_tf;

    @FXML
    void Cancel(ActionEvent event) 
    {
        System.exit(0);
    }

    @FXML
    public void OpenMain (ActionEvent event) throws IOException
    {
        String username = username_tf.getText();
        String password = password_tf.getText();
        if(username.equals("SYSTEM") && password.equals("C040"))
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("..//MainPage//MainPageFXML.fxml"));
            root = loader.load();	
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            error_msg.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
