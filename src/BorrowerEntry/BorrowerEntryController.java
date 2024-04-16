package BorrowerEntry;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BorrowerEntryController implements Initializable {

    Connection con;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private AnchorPane anchorpaneLayout;

    @FXML
    private TextField b_id_tf;

    @FXML
    private TextField bor_con_tf;

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
    private TextField ret_con_tf;

    @FXML
    private TextField sd_tf;

    @FXML
    private TextField u_id_tf;

    @FXML
    void CancelAction(ActionEvent event) throws Exception{
        con.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("..//MainPage//MainPageFXML.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void CreateMember(ActionEvent event) {
        int u_id_val = Integer.parseInt(u_id_tf.getText());
        int b_id_val = Integer.parseInt(b_id_tf.getText());
        String sd_val = sd_tf.getText();
        int dur_val = Integer.parseInt(dur_tf.getText());
        String bor_con_val = bor_con_tf.getText();
        // String ret_con_val = ret_con_tf.getText();
        PreparedStatement ps;
        int row;
        try {
            ps = con.prepareStatement("insert into borrowed (b_id, u_id, bor_date, bor_dur, bor_cond, ret_cond) values " +
                                            "(?,?,to_date(?,'DD-MM-YYYY'),?,?,?)");
            ps.setInt(1,b_id_val);
            ps.setInt(2,u_id_val);
            ps.setString(3,sd_val);
            ps.setInt(4,dur_val);
            ps.setString(5,bor_con_val);
            ps.setNull(6,java.sql.Types.NULL);
            row = ps.executeUpdate();
            if(row == 0) {
                //handle error
            }
            ps = con.prepareStatement("update book set copies = copies - 1 where b_id = ?");
            ps.setInt(1,b_id_val);
            row = ps.executeUpdate();
            if(row == 0) {
                //handle error
            }
        }
        catch (Exception e) {
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
