package FictionDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FictionDetailsController implements Initializable {
   
    Connection con;
    Parent root;
    Stage stage;
    Scene scene;


 {

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
    void FictionDetails(ActionEvent event) {
        try{
            String subgenre_val = subgenre_tf.getText();
            String target_age_group_val = target_age_group_tf.getText();

            PreparedStatement ps = con.prepareStatement(
                "update fictionbook set subgenre = ?, target_age_grp = ? where b_id = ?");

        ps.setString(1, subgenre_val);
        ps.setString(2, target_age_group_val);
        ps.setInt(3, b_id_val ); 
        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            responseLb.setVisible(true);
            responseLb.setText("Fiction Book Details Updated Successfully!");
            responseLb.setStyle("-fx-text-fill: green;");
            System.out.println("Fiction Book Details Updated Successfully!");
        } else {
            responseLb.setVisible(true);
            responseLb.setText("No book found with ID: " + book_id);
            responseLb.setStyle("-fx-text-fill: red;");
            System.out.println("No book found with ID: " + book_id);
        }
    } catch (SQLException e) {
        responseLb.setVisible(true);
        responseLb.setText("Error updating book details!");
        responseLb.setStyle("-fx-text-fill: red;");
        e.printStackTrace();
    }
}
            ;
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

