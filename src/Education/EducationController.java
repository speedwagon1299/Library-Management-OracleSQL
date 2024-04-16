package Education;
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

public class EducationController implements Initializable {

    Connection con;
    Parent root;
    Stage stage;
    Scene scene;
    int ind = -1;
    ObservableList<EducationBook> list = FXCollections.observableArrayList();
    ObservableList<String> cl = FXCollections.observableArrayList("Fiction","Non Fiction","Education","Children");

    @FXML
    private ComboBox<String> attrib_cb;

    @FXML
    private TextField attrib_tf;

    @FXML
    private TableColumn<EducationBook, Integer> b_id_col;

    @FXML
    private TableColumn<EducationBook, String> b_title_col;

    @FXML
    private TableView<EducationBook> education_table;

    @FXML
    private TableColumn<EducationBook, String> subject_col;

    @FXML
    private TableColumn<EducationBook, String> subject_area_col;

    

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
        if(ch.equals("Education")) {
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
        subject_col.setCellValueFactory(new PropertyValueFactory<>("subject"));
        subject_area_col.setCellValueFactory(new PropertyValueFactory<>("subject_area"));
    }

    private void loadData() {        
        try {
            PreparedStatement ps = con.prepareStatement("select b.b_title, b.b_id, e.subject, e.subject_area " +
                                                        "from book b, educationbook e " +
                                                        "where b.b_id = e.b_id " );
            System.out.println("Hi");
            ResultSet rs = ps.executeQuery();
            System.out.println("Hi");
            if(!rs.next())  System.out.println("No data");
            else {
                do {
                    String book_title = rs.getString(1);
                    int book_id = rs.getInt(2);
                    String subject = rs.getString(3);
                    String subject_area= rs.getString(4);
                    list.add(new EducationBook(book_title, book_id, subject, subject_area));
                }
                while(rs.next());
            }
            education_table.getItems().setAll(list);
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

    public static class EducationBook {
        private final SimpleStringProperty book_title;
        private final SimpleIntegerProperty book_id;
        private final SimpleStringProperty subject;
        private final SimpleStringProperty subject_area;

        EducationBook(String book_title, int book_id, String subject, String subject_area) {
            this.book_title = new SimpleStringProperty(book_title);
            this.book_id = new SimpleIntegerProperty(book_id);
            this.subject = new SimpleStringProperty(subject);
            this.subject_area = new SimpleStringProperty(subject_area);
        }

        public String getB_title() {
            return book_title.get();
        }

        public int getB_id() {
            return book_id.get();
        }

        public String getSubject() {
            return subject.get();
        }

        public String getSubject_area() {
            return subject_area.get();
        }
    }
}


