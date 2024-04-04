package LoginPage;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginPageController implements Initializable
{

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
    void LogIn(ActionEvent event) 
    {
        String username = username_tf.getText();
        String password = password_tf.getText();
        if(username.equals("SYSTEM") && password.equals("C040"))
        {
            error_msg.setVisible(false);
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
