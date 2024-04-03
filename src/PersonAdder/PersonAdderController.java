package PersonAdder;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PersonAdderController implements Initializable {

    Connection con;

    @FXML
    private TextField address_tf;

    @FXML
    private TextField area_code_tf;

    @FXML
    private ComboBox<String> gender_cb;

    @FXML
    private ComboBox<String> membership_cb;

    @FXML
    private TextField name_tf;

    @FXML
    private TextField person_id_tf;

    @FXML
    private TextField phone_num_tf;

    @FXML
    void AddPerson(ActionEvent event) {

    }

    @FXML
    void CancelAction(ActionEvent event) {

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
