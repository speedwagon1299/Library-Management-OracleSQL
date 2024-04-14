package AuthorAdder;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuthorAdderController implements Initializable {

    Connection con;

    @FXML
    private TextField a_dob_tf;

    @FXML
    private TextField a_email_tf;

    @FXML
    private TextField a_id_tf;

    @FXML
    private TextField a_name_id;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label response_lb;

    @FXML
    private Button save_btn;

    @FXML
    void CancelAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void SaveAuthor(ActionEvent event) {
        try {    
            int a_id_val = Integer.parseInt(a_id_tf.getText());
            String a_name_val = a_name_id.getText();
            String a_dob_val = a_dob_tf.getText();
            String a_email_val = a_email_tf.getText();
            PreparedStatement ps = con.prepareStatement("insert into author (a_id, a_name, a_dob,a_email) values (?, ?," + 
                                                            "to_date(?,'DD-MM-YYYY'),?)");
            ps.setInt(1,a_id_val);
            ps.setString(2,a_name_val);
            ps.setString(3, a_dob_val);
            ps.setString(4,a_email_val);
            ps.executeUpdate();
            response_lb.setVisible(true);
            response_lb.setText("Author Added Successfully!");
            response_lb.setStyle("-fx-text-fill: green;");
            System.out.println("Update Successful");
        }
        catch(SQLException e) {
            response_lb.setVisible(true);
            response_lb.setText("Author Already Exists!");
            response_lb.setStyle("-fx-text-fill: red;");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try 
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040"); 
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }

}
