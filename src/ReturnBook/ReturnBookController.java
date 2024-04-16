package ReturnBook;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReturnBookController implements Initializable {

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
    private Label error_msg;

    @FXML
    private TextField ret_con_tf;

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
        // String sd_val = sd_tf.getText();
        // int dur_val = Integer.parseInt(dur_tf.getText());
        // String bor_con_val = bor_con_tf.getText();
        String ret_con_val = ret_con_tf.getText();
        PreparedStatement ps;
        int row;
        try {
            ps = con.prepareStatement("update borrowed set ret_cond = ? where u_id = ? and b_id = ?");
            ps.setString(1,ret_con_val);
            ps.setInt(2,b_id_val);
            ps.setInt(3,u_id_val);
            row = ps.executeUpdate();
            ps = con.prepareStatement("update book set copies = copies + 1 where b_id = ?");
            ps.setInt(1,b_id_val);
            row = ps.executeUpdate();
            System.out.println("Successful 1");
            ps = con.prepareStatement("delete from borrowed where b_id = ? and u_id = ?");
            ps.setInt(1,b_id_val);
            ps.setInt(2,u_id_val);
            row = ps.executeUpdate();
            System.out.println("Successful 2");
        }
        catch (SQLException e) {
            System.out.println("SQL ERROR");
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
