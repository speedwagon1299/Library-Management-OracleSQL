package AuthorAdder;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthorAdderController implements Initializable {

    Connection con;
    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    private TextField a_dob_tf;

    @FXML
    private TextField a_email_tf;

    @FXML
    private TextField a_id_tf;

    @FXML
    private TextField a_name_tf;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label response_lb;

    @FXML
    private Button save_btn;

    @FXML
    void CancelAction(ActionEvent event) throws Exception {
        con.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("..//MainPage//MainPageFXML.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SaveAuthor(ActionEvent event) {
        try {     
            int a_id_val = Integer.parseInt(a_id_tf.getText());
            String a_name_val = a_name_tf.getText();
            String a_dob_val = a_dob_tf.getText();
            String a_email_val = a_email_tf.getText();
            PreparedStatement ps = con.prepareStatement("insert into author (a_id, a_name, a_dob,a_email) values (?, ?," + 
                                                            "to_date(?,'DD-MM-YYYY'),?)");
            ps.setInt(1,a_id_val);
            ps.setString(2,a_name_val);
            if(a_dob_val.equals("")) {
                ps.setNull(3,java.sql.Types.NULL);
            }
            else {
                ps.setString(3, a_dob_val);
            }
            if(a_email_val.equals("")) {
                ps.setNull(4,java.sql.Types.NULL);
            }
            else {
                ps.setString(4,a_email_val);
            }
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
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
