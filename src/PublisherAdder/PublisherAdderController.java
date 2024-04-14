package PublisherAdder;

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

public class PublisherAdderController implements Initializable {

    Connection con;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField p_id_tf;

    @FXML
    private TextField p_name_tf;

    @FXML
    private Label response_lb;

    @FXML
    private Button save_btn;

    @FXML
    void CancelAction(ActionEvent event) throws SQLException {
        con.close();
        System.exit(0);
    }

    @FXML
    void SavePublisher(ActionEvent event) {
        try {
            String p_name_val = p_name_tf.getText();
            int p_id_val = Integer.parseInt(p_id_tf.getText());
            PreparedStatement ps = con.prepareStatement("insert into publisher (p_id, p_name) " +
                                                            "values (?,?)");
            ps.setInt(1,p_id_val);
            ps.setString(2,p_name_val);
            ps.executeUpdate();
            response_lb.setVisible(true);
            response_lb.setText("Publisher Added Successfully!");
            response_lb.setStyle("-fx-text-fill: green;");
            System.out.println("Update Successful");
        }
        catch(SQLException e) {
            response_lb.setVisible(true);
            response_lb.setText("Publisher Already Exists!");
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
