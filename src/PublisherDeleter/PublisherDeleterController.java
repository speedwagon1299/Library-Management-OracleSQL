package PublisherDeleter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PublisherDeleterController implements Initializable {

    ObservableList<String> cl = FXCollections.observableArrayList("Publisher ID","Publisher Name");
    String ls[] = {"p_id","p_name"};    
    int ind;
    Connection con;
    
    @FXML
    private Label attrib_lb;

    @FXML
    private TextField attrib_tf;

    @FXML
    private Button cancel_button;

    @FXML
    private ComboBox<String> cb_p_attrib;

    @FXML
    private Button delete_button;

    @FXML
    private Label delete_lb;

    @FXML
    void CancelAction(ActionEvent event) throws SQLException {
        con.close();
        System.exit(0);
    }

    @FXML
    void DeletePublisher(ActionEvent event) {
        try {
            String attrib = attrib_tf.getText();
            if(attrib.equals(""))
            {
                delete_lb.setVisible(true);
                delete_lb.setText("Please Select an Attribute");
                delete_lb.setStyle("-fx-text-fill: red;");
                return;
            }
            PreparedStatement ps;
            int row;
            if(ind == 0) {
                ps = con.prepareStatement("delete from publisher where p_id = ?");
                ps.setInt(1,Integer.parseInt(attrib));
                row = ps.executeUpdate();
            }
            else {
                ps = con.prepareStatement("delete from publisher where p_name = ?");
                ps.setString(1,attrib);
                row = ps.executeUpdate();
            }
            if(row == 0)
            {
                delete_lb.setVisible(true);
                delete_lb.setText("Invalid!");
                delete_lb.setStyle("-fx-text-fill: red;");
            }
            else
            {
                delete_lb.setVisible(true);
                delete_lb.setText("Successfully Deleted " + row + " entries");
                delete_lb.setStyle("-fx-text-fill: green;");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SelectAttribute(ActionEvent event) {
        String ch = cb_p_attrib.getValue();
        attrib_lb.setText("Enter " + ch);
        if(ch.equals("Publisher ID"))
        {
            ind = 0;
        }
        else
        {
            ind = 1;
        }
        attrib_tf.setEditable(true);
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
        cb_p_attrib.setItems(cl);
    }

}
