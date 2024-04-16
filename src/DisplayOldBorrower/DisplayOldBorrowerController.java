package DisplayOldBorrower;
import java.awt.Button;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DisplayOldBorrowerController implements Initializable {

    Connection con;
    ObservableList<Borrow> list = FXCollections.observableArrayList();
    ObservableList<String> cl = FXCollections.observableArrayList("Book Name","Book ID","Person ID","Person Name","None");
    String ls[] = {"b_title","b_id","u_id","u_name",""};    
    int ind = -1;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> attrib_cb;

    @FXML
    private TextField attrib_tf;

    @FXML
    private TableColumn<Borrow, String> b_cond_col;

    @FXML
    private TableColumn<Borrow, String> b_date_col;

    @FXML
    private TableColumn<Borrow, Integer> b_dur_col;

    @FXML
    private TableColumn<Borrow, Integer> b_id_col;

    @FXML
    private TableColumn<Borrow, String> b_title_col;

    @FXML
    private TableView<Borrow> borrow_table;

    @FXML
    private TableColumn<Borrow, Integer> u_id_col;

    @FXML
    private TableColumn<Borrow, String> u_name_col; 

    @FXML
    private TableColumn<Borrow, String> ret_cond_col;

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
        if(ch.equals("Book Name"))  ind = 0;
        else if(ch.equals("Book ID"))  ind = 1;
        else if(ch.equals("Person ID")) ind = 2;
        else if(ch.equals("Person Name")) ind = 3;
        else    ind = 4;
        list.clear();
        loadData();
    }

    private void initCol() {
        b_title_col.setCellValueFactory(new PropertyValueFactory<>("b_title"));
        b_id_col.setCellValueFactory(new PropertyValueFactory<>("b_id"));
        u_id_col.setCellValueFactory(new PropertyValueFactory<>("u_id"));
        u_name_col.setCellValueFactory(new PropertyValueFactory<>("u_name"));
        b_date_col.setCellValueFactory(new PropertyValueFactory<>("bor_date"));
        b_dur_col.setCellValueFactory(new PropertyValueFactory<>("bor_dur"));
        b_cond_col.setCellValueFactory(new PropertyValueFactory<>("bor_cond"));
        ret_cond_col.setCellValueFactory(new PropertyValueFactory<>("ret_cond"));
    }

    private void loadData() {        
        try {
            String extra = "";
            if(ind == 0) {
                extra = " and b.b_title = '" + attrib_tf.getText() + "'";
            }
            else if(ind == 1) {
                extra = " and b.b_id = " + attrib_tf.getText();
            }
            else if(ind == 2) {
                extra = " and u.u_id = " + attrib_tf.getText();
            }
            else if(ind == 3) {
                extra = " and u.u_name = '" + attrib_tf.getText() + "'";
            }
            else {
                extra = "";
            }
            PreparedStatement ps = con.prepareStatement("select b.b_title, b.b_id, u.u_name, u.u_id, bor.bor_date, bor.bor_dur, bor.bor_cond, bor.ret_cond " +
                                                        "from book b, borrowed_history bor, person u " +
                                                        "where b.b_id = bor.b_id and u.u_id = bor.u_id" + extra);
            System.out.println("Hi");
            ResultSet rs = ps.executeQuery();
            System.out.println("Hi");
            if(!rs.next())  System.out.println("No data");
            else {
                do {
                    String book_title = rs.getString(1);
                    int book_id = rs.getInt(2);
                    String author_name = rs.getString(3);
                    int u_id_val = rs.getInt(4);
                    String publisher = rs.getString(5);
                    int copies = rs.getInt(6);
                    String publisher2 = rs.getString(7);
                    String publisher3 = rs.getString(8);
                    list.add(new Borrow(book_title, book_id, author_name, u_id_val, publisher, copies, publisher2, publisher3));
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

        borrow_table.getItems().setAll(list);
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

    public static class Borrow {
        private final SimpleStringProperty book_title;
        private final SimpleIntegerProperty book_id;
        private final SimpleStringProperty author_name;
        private final SimpleIntegerProperty publisher_name;
        private final SimpleStringProperty price;
        private final SimpleIntegerProperty copies;
        private final SimpleStringProperty price2;
        private final SimpleStringProperty price3;
        
        Borrow(String book_title, int book_id, String author_name, int publisher_name, String price, int copies, String price2, String price3) {
            this.book_title = new SimpleStringProperty(book_title);
            this.book_id = new SimpleIntegerProperty(book_id);
            this.author_name = new SimpleStringProperty(author_name);
            this.publisher_name = new SimpleIntegerProperty(publisher_name);
            this.price = new SimpleStringProperty(price);
            this.copies = new SimpleIntegerProperty(copies);
            this.price2 = new SimpleStringProperty(price2);
            this.price3 = new SimpleStringProperty(price3);
        }

        public String getB_title() {
            return book_title.get();
        }

        public int getB_id() {
            return book_id.get();
        }

        public String getU_name() {
            return author_name.get();
        }

        public int getU_id() {
            return publisher_name.get();
        }

        public String getBor_date() {
            return price.get();
        }

        public int getBor_dur() {
            return copies.get();
        }
        
        public String getBor_cond() {
            return price2.get();
        }

        public String getRet_cond() {
            return price3.get();
        }
    }
}