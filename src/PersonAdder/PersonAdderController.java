package PersonAdder;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonAdderController implements Initializable {

    Connection con;
    ObservableList<String> gen_list = FXCollections.observableArrayList("Male","Female","Other");
    ObservableList<String> mem_list = FXCollections.observableArrayList("Member","Non-Member");
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private ComboBox<String> gender_cb;

    @FXML
    private ComboBox<String> membership_cb;

    @FXML
    private TextField u_address_tf;

    @FXML
    private TextField u_id_tf;

    @FXML
    private TextField u_name_tf;

    @FXML
    private TextField u_phone_tf;

    @FXML
    private Label delete_lb;

    @FXML
    void AddPerson(ActionEvent event) {
        try {
            int u_id_val = Integer.parseInt(u_id_tf.getText());
            String u_name_val = u_name_tf.getText();
            String u_gender_val_int = gender_cb.getValue();
            String u_gender_val;
            if(u_gender_val_int.equals("")) {
                u_gender_val = " ";
            }
            else if(u_gender_val_int.equals("Male")) {
                u_gender_val = "M";
            }
            else if(u_gender_val_int.equals("Female")) {
                u_gender_val = "F";
            }
            else {
                u_gender_val = "O";
            }
            String u_phone_val = u_phone_tf.getText();
            String u_address_val = u_address_tf.getText();
            String u_mem_status_val_int = membership_cb.getValue();
            String u_mem_status_val;
            if(u_mem_status_val_int.equals("Member")) {
                u_mem_status_val = "T";
            }
            else {
                u_mem_status_val = "F";
            }
            PreparedStatement ps;
            ps = con.prepareStatement("insert into person (u_id, u_name, u_gender, u_phone, u_address, mem_status) " +
                                            "values (?,?,?,?,?,?)");
            ps.setInt(1,u_id_val);
            ps.setString(2,u_name_val);
            if(u_gender_val.equals(" ")) {
                ps.setNull(3,java.sql.Types.NULL);
            }
            else {
                ps.setString(3,u_gender_val);
            }
            ps.setString(4,u_phone_val);
            if(u_address_val.equals("")) {
                ps.setNull(5,java.sql.Types.NULL);
            }
            else {
                ps.setString(5,u_address_val);
            }
            ps.setString(6,u_mem_status_val);
            int row = ps.executeUpdate();
            if(row == 0) {
                delete_lb.setVisible(true);
                delete_lb.setText("Invalid!");
                delete_lb.setStyle("-fx-text-fill: red;");
            }
            else {
                delete_lb.setVisible(true);
                delete_lb.setText("Successfully Inserted");
                delete_lb.setStyle("-fx-text-fill: green;");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040"); 
        } 
        catch(Exception e) {
            e.printStackTrace();
        }
        gender_cb.setItems(gen_list);
        membership_cb.setItems(mem_list);
    }

}
