package MemberShip;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MemberShipController implements Initializable {

    Connection con;

    @FXML
    private AnchorPane anchorpaneLayout;

    @FXML
    private Button cancel_button;

    @FXML
    private Button create_btn;

    @FXML
    private TextField dur_tf;

    @FXML
    private Label error_msg;

    @FXML
    private Label msg_lb;

    @FXML
    private TextField sd_tf;

    @FXML
    private TextField u_id_tf;

    @FXML
    void Cancel(ActionEvent event) {
        try {
            con.close();
            System.exit(0);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CreateMember(ActionEvent event) {
        int u_id_val = Integer.parseInt(u_id_tf.getText());
        String sd_val = sd_tf.getText();
        int dur_val = Integer.parseInt(dur_tf.getText()); 
        PreparedStatement ps;
        int row;
        try {
            ps = con.prepareStatement(" insert into member (u_id,mem_start_date,duration) values (?,to_date(?,'DD-MM-YYYY'),?)");
            ps.setInt(1,u_id_val);
            ps.setString(2,sd_val);
            ps.setInt(3,dur_val);
            row = ps.executeUpdate();
            if(row == 0) {
                msg_lb.setVisible(true);
                msg_lb.setText("Invalid!");
                msg_lb.setStyle("-fx-text-fill: red;");
            }
            else {
                msg_lb.setVisible(true);
                msg_lb.setText("Successfully Created");
                msg_lb.setStyle("-fx-text-fill: green;");
            }
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
