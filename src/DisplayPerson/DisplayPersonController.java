package DisplayPerson;
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

public class DisplayPersonController implements Initializable {

    Connection con;
    Parent root;
    Stage stage;
    Scene scene;
    int ind = -1;
    ObservableList<Member> list = FXCollections.observableArrayList();


    @FXML
    private TableColumn<Member, Integer> u_id_col;

    @FXML
    private TableColumn<Member, String> u_name_col;

    @FXML
    private TableView<Member> member_table;

    @FXML
    private TableColumn<Member, String> mem_start_date_col;

    @FXML
    private TableColumn<Member, String> dur_col;

    

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
    

    private void initCol() {
        u_name_col.setCellValueFactory(new PropertyValueFactory<>("u_name"));
        u_id_col.setCellValueFactory(new PropertyValueFactory<>("u_id"));
        mem_start_date_col.setCellValueFactory(new PropertyValueFactory<>("mem_start_date"));
        dur_col.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void loadData() {        
        try {
            PreparedStatement ps = con.prepareStatement("select p.u_id, p.u_name, m.mem_start_date, m.duration " +
                                                        "from person p, member m " +
                                                        "where p.u_id = m.u_id " );
            System.out.println("Hi");
            ResultSet rs = ps.executeQuery();
            System.out.println("Hi");
            if(!rs.next())  System.out.println("No data");
            else {
                do {
                    String book_title = rs.getString(2);
                    int book_id = rs.getInt(1);
                    String subgenre = rs.getString(3);
                    String target_age_group= rs.getString(4);
                    list.add(new Member(book_title, book_id, subgenre, target_age_group));
                }
                while(rs.next());
            }
            member_table.getItems().setAll(list);
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
        loadData();
    }

    public static class Member {
        private final SimpleStringProperty book_title;
        private final SimpleIntegerProperty book_id;
        private final SimpleStringProperty subgenre;
        private final SimpleStringProperty target_age_group;

        Member(String book_title, int book_id, String subgenre, String target_age_group) {
            this.book_title = new SimpleStringProperty(book_title);
            this.book_id = new SimpleIntegerProperty(book_id);
            this.subgenre = new SimpleStringProperty(subgenre);
            this.target_age_group = new SimpleStringProperty(target_age_group);
        }

        public String getU_name() {
            return book_title.get();
        }

        public int getU_id() {
            return book_id.get();
        }

        public String getMem_start_date() {
            return subgenre.get();
        }

        public String getDuration() {
            return target_age_group.get();
        }
    }
}


