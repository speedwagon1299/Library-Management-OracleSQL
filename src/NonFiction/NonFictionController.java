package ViewBookDetails;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NonFictionController {

    @FXML
    private TableView<?> NonFiction_table;

    @FXML
    private ComboBox<?> attrib_cb;

    @FXML
    private TextField attrib_tf;

    @FXML
    private TableColumn<Book, Integer> b_id_col;

    @FXML
    private TableColumn<Book, String> b_title_col;

    @FXML
    private TableColumn<Book, String> subgenre;

    @FXML
    private TableColumn<Book, String> topic;

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
    void selectAttribute(ActionEvent event) {

    }

    private void initCol() {
        b_title_col.setCellValueFactory(new PropertyValueFactory<>("b_title"));
        b_id_col.setCellValueFactory(new PropertyValueFactory<>("b_id"));
        a_name_col.setCellValueFactory(new PropertyValueFactory<>("subgenre"));
        p_name_col.setCellValueFactory(new PropertyValueFactory<>("topic"));
    }

    private void loadData() {        
        try {
            
            }
            PreparedStatement ps = con.prepareStatement("select b.b_title, w.b_id, n.subgenre, n.topic " +
                                                        "from book b, wrote w, nonfiction n " +
                                                        "where b.b_id = w.b_id " );
            System.out.println("Hi");
            ResultSet rs = ps.executeQuery();
            System.out.println("Hi");
            if(!rs.next())  System.out.println("No data");
            else {
                do {
                    String book_title = rs.getString(1);
                    int book_id = rs.getInt(2);
                    String subgenre = rs.getString(3);
                    String target_age_group= rs.getString(4);
                    
                }
                while(rs.next());
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        book_table.getItems().setAll(list);
    }

}

    @Override
public void initialize(URL location, ResourceBundle resources) {
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040");
    }
    catch(Exception e){

    }
    initCol();
    attrib_cb.setItems(cl);
    loadData();
}

}
