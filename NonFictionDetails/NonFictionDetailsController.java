package NonFictionDetails;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NonFictionDetailsController implements Initializable {

    @FXML
    private TextField a_dob_tf;

    @FXML
    private TextField a_id_tf;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label response_lb;

    @FXML
    private Button save_btn;

    @FXML
    void CancelAction(ActionEvent event) {
        con.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("..//MainPage//MainPageFXML.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void SaveNonFiction(ActionEvent event) {
            try {     
            
                String subgenre_val = subgenre_tf.getText();
                String topic_val = topic_tf.getText();

                PreparedStatement ps = con.prepareStatement(
                "update nonfictionbook set subgenre = ?, topic = ? where b_id = b_id_val");

    
                if(subgenre_tf.equals("")) {
                    ps.setNull(3,java.sql.Types.NULL);
                }
                else {
                    ps.setString(3, subgenre_val);
                }
                if(topic_val.equals("")) {
                    ps.setNull(4,java.sql.Types.NULL);
                }
                else {
                    ps.setString(4,topic_val);
                }
                ps.executeUpdate();
                response_lb.setVisible(true);
                response_lb.setText(" Added Successfully!");
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

}
