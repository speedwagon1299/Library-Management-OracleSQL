package Children;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ChildrenController implements Initializable {

    Connection con;
    Parent root;
    Stage stage;
    Scene scene;
    int ind = -1;
    ObservableList<ChildrenBook> list = FXCollections.observableArrayList();
    ObservableList<String> cl = FXCollections.observableArrayList("Fiction","Non Fiction","Education","Children");

    @FXML
    private ComboBox<String> attrib_cb;

    @FXML
    private TextField attrib_tf;

    @FXML
    private TableColumn<ChildrenBook, Integer> b_id_col;

    @FXML
    private TableColumn<ChildrenBook, String> b_title_col;

    @FXML
    private TableView<ChildrenBook> children_table;

    @FXML
    private TableColumn<ChildrenBook, String> age_range_col;

    @FXML
    private TableColumn<ChildrenBook, String> interactivity_level_col;

    

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
    

    
    @FXML
    void selectAttribute(ActionEvent event) {
        String ch = attrib_cb.getValue();
        if(ch.equals("Children")) {
        }
        else if(ch.equals("Fiction")) {
            try {
                con.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("..//Fiction//FictionFXML.fxml"));
                root = loader.load();	
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show(); 
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(ch.equals("Non Fiction")) {
            try {
                con.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("..//NonFiction//NonFictionFXML.fxml"));
                root = loader.load();	
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show(); 
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                con.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("..//Education//EducationFXML.fxml"));
                root = loader.load();	
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show(); 
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void initCol() {
        b_title_col.setCellValueFactory(new PropertyValueFactory<>("b_title"));
        b_id_col.setCellValueFactory(new PropertyValueFactory<>("b_id"));
        age_range_col.setCellValueFactory(new PropertyValueFactory<>("age_range"));
        interactivity_level_col.setCellValueFactory(new PropertyValueFactory<>("interactivity_level"));
    }

    private void loadData() {        
        try {
            PreparedStatement ps = con.prepareStatement("select b.b_title, b.b_id, c.age_range, c.interactivity_level " +
                                                        "from book b, childrenbook c " +
                                                        "where b.b_id = c.b_id " );
            System.out.println("Hi");
            ResultSet rs = ps.executeQuery();
            System.out.println("Hi");
            if(!rs.next())  System.out.println("No data");
            else {
                do {
                    String book_title = rs.getString(1);
                    int book_id = rs.getInt(2);
                    String age_range = rs.getString(3);
                    String interactivity_level= rs.getString(4);
                    list.add(new ChildrenBook(book_title, book_id, age_range, interactivity_level));
                }
                while(rs.next());
            }
            children_table.getItems().setAll(list);
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
            e.printStackTrace();
        }   
        initCol();
        attrib_cb.setItems(cl);
        loadData();
    }

    public static class ChildrenBook {
        private final SimpleStringProperty book_title;
        private final SimpleIntegerProperty book_id;
        private final SimpleStringProperty age_range;
        private final SimpleStringProperty interactivity_level;

        ChildrenBook(String book_title, int book_id, String age_range, String interactivity_level) {
            this.book_title = new SimpleStringProperty(book_title);
            this.book_id = new SimpleIntegerProperty(book_id);
            this.age_range = new SimpleStringProperty(age_range);
            this.interactivity_level = new SimpleStringProperty(interactivity_level);
        }

        public String getB_title() {
            return book_title.get();
        }

        public int getB_id() {
            return book_id.get();
        }

        public String getAge_range() {
            return age_range.get();
        }

        public String getInteractivity_level() {
            return interactivity_level.get();
        }
    }
}


