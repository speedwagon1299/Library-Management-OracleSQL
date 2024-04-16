package Fiction;
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

public class FictionController implements Initializable {

    Connection con;
    Parent root;
    Stage stage;
    Scene scene;
    int ind = -1;
    ObservableList<FictionBook> list = FXCollections.observableArrayList();
    ObservableList<String> cl = FXCollections.observableArrayList("Fiction","Non Fiction","Education","Children");

    @FXML
    private ComboBox<String> attrib_cb;

    @FXML
    private TableColumn<FictionBook, Integer> b_id_col;

    @FXML
    private TableColumn<FictionBook, String> b_title_col;

    @FXML
    private TableView<FictionBook> fiction_table;

    @FXML
    private TableColumn<FictionBook, String> subgenre_col;

    @FXML
    private TableColumn<FictionBook, String> target_age_group_col;

    

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
        if(ch.equals("Fiction")) {
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
        else if(ch.equals("Education")) {
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
        else {
            try {
                con.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("..//Children//ChildrenFXML.fxml"));
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
        subgenre_col.setCellValueFactory(new PropertyValueFactory<>("subgenre"));
        target_age_group_col.setCellValueFactory(new PropertyValueFactory<>("target_age_grp"));
    }

    private void loadData() {        
        try {
            PreparedStatement ps = con.prepareStatement("select b.b_title, b.b_id, f.subgenre, f.target_age_grp " +
                                                        "from book b, fictionbook f " +
                                                        "where b.b_id = f.b_id " );
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
                    list.add(new FictionBook(book_title, book_id, subgenre, target_age_group));
                }
                while(rs.next());
            }
            fiction_table.getItems().setAll(list);
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

    public static class FictionBook {
        private final SimpleStringProperty book_title;
        private final SimpleIntegerProperty book_id;
        private final SimpleStringProperty subgenre;
        private final SimpleStringProperty target_age_group;

        FictionBook(String book_title, int book_id, String subgenre, String target_age_group) {
            this.book_title = new SimpleStringProperty(book_title);
            this.book_id = new SimpleIntegerProperty(book_id);
            this.subgenre = new SimpleStringProperty(subgenre);
            this.target_age_group = new SimpleStringProperty(target_age_group);
        }

        public String getB_title() {
            return book_title.get();
        }

        public int getB_id() {
            return book_id.get();
        }

        public String getSubgenre() {
            return subgenre.get();
        }

        public String getTarget_age_grp() {
            return target_age_group.get();
        }
    }
}


