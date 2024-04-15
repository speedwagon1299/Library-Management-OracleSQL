package WroteAdder;

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

public class WroteAdderController implements Initializable {

    Connection con;
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Retrieve from BookAdder as previous page
    int b_id_val;
    
    public void setB_id_val(int b_id_val) {
        this.b_id_val = b_id_val;
    }

    @FXML
    private TextField a_id1_tf;

    @FXML
    private TextField a_id2_tf;

    @FXML
    private TextField a_id3_tf;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label response_lb;

    @FXML
    private Button save_btn;

    @FXML
    void CancelAction(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("..//BookAdder//BookAdderFXML.fxml"));
        PreparedStatement ps = con.prepareStatement("delete from book where b_id = ?");
        ps.setInt(1,b_id_val);
        ps.executeUpdate();
        con.close();
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SaveWrote(ActionEvent event) {
        try {    
            int a_id1_val = Integer.parseInt(a_id1_tf.getText());
            int a_id2_val;
            if(a_id2_tf.getText().equals("")) {
                a_id2_val = -1;
            }
            else {
                a_id2_val = Integer.parseInt(a_id2_tf.getText());
            } 
            int a_id3_val;
            if(a_id3_tf.getText().equals("")) {
                a_id3_val = -1;
            }
            else {
                a_id3_val = Integer.parseInt(a_id3_tf.getText());
            } 
            PreparedStatement ps = con.prepareStatement("insert into wrote (b_id, a_id) values (?,?)");
            ps.setInt(1,b_id_val);
            ps.setInt(2,a_id1_val);
            ps.executeUpdate();
            if(a_id2_val != -1) {
                ps.setInt(1,b_id_val);
                ps.setInt(2,a_id2_val);
                ps.executeUpdate();
            }
            if(a_id3_val != -1) {
                ps.setInt(1,b_id_val);
                ps.setInt(2,a_id3_val);
                ps.executeUpdate();
            }
            response_lb.setVisible(true);
            response_lb.setVisible(true);
            response_lb.setText("Added Successfully!");
            response_lb.setStyle("-fx-text-fill: green;");
            System.out.println("Update Successful");
        }
        catch(SQLException e) {
            response_lb.setVisible(true);
            response_lb.setText("Entry Already Exists!");
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
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
