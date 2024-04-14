package DisplayBooks;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DisplayBooksController implements Initializable {

    Connection con;
    ObservableList<Book> list = FXCollections.observableArrayList();
    ObservableList<String> cl = FXCollections.observableArrayList("Book Name","Book ID","Author ID","Author Name","Publisher Name","Price Range","Copies Range");
    String ls[] = {"b_title","b_id","a_id","a_name","p_name","price","copies"};    
    int ind;

    @FXML
    private ComboBox<String> attrib_cb;

    @FXML
    private TableColumn<Book,String> a_name_col;

    @FXML
    private TableColumn<Book,Integer> b_id_col;

    @FXML
    private TableView<Book> book_table;

    @FXML
    private TableColumn<Book,String> b_title_col;

    @FXML
    private TableColumn<Book,String> p_name_col;

    @FXML
    private TableColumn<Book,Integer> price_col;

    @FXML
    private TableColumn<Book,Integer> copies_col;

    @FXML
    private TextField attrib_tf;    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040");
        }
        catch(Exception e){

        }
        initCol();
        // attrib_cb.setItems(cl);
        loadData();
    }

    @FXML
    void selectAttribute(ActionEvent event) {
        // String ch = attrib_cb.getValue();
        // if(ch.equals("Book Name"))  ind = 0;
        // else if(ch.equals("Book ID"))  ind = 1;
        // else if(ch.equals("Author ID")) ind = 2;
        // else if(ch.equals("Author Name")) ind = 3;
        // else if(ch.equals("Publisher Name")) ind = 4;
        // else if(ch.equals("Price Range"))   ind = 5;
        // else    ind = 6;
        // attrib_tf.setEditable(true);
    }

    private void initCol() {
        b_title_col.setCellValueFactory(new PropertyValueFactory<>("b_title"));
        b_id_col.setCellValueFactory(new PropertyValueFactory<>("b_id"));
        a_name_col.setCellValueFactory(new PropertyValueFactory<>("a_name"));
        p_name_col.setCellValueFactory(new PropertyValueFactory<>("p_name"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        copies_col.setCellValueFactory(new PropertyValueFactory<>("copies"));
    }

    private void loadData() {        
        try {
            PreparedStatement ps = con.prepareStatement("select b.b_title, w.b_id, a.a_name, p.p_name, b.price, b.copies " +
                                                        "from book b, wrote w, author a, publisher p " +
                                                        "where b.b_id = w.b_id and a.a_id = w.a_id and b.p_id = p.p_id");
            System.out.println("Hi");
            ResultSet rs = ps.executeQuery();
            System.out.println("Hi");
            if(!rs.next())  System.out.println("No data");
            else {
                do {
                    String book_title = rs.getString(1);
                    int book_id = rs.getInt(2);
                    String author_name = rs.getString(3);
                    String publisher = rs.getString(4);
                    double price = rs.getDouble(5);
                    int copies = rs.getInt(6);
                    list.add(new Book(book_title, book_id, author_name, publisher,price,copies));
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

    public static class Book {
        private final SimpleStringProperty book_title;
        private final SimpleIntegerProperty book_id;
        private final SimpleStringProperty author_name;
        private final SimpleStringProperty publisher_name;
        private final SimpleDoubleProperty price;
        private final SimpleIntegerProperty copies;
        
        Book(String book_title, int book_id, String author_name, String publisher_name, double price, int copies) {
        // Book(String book_title, int book_id, String publisher_name, double price, int copies) {
            this.book_title = new SimpleStringProperty(book_title);
            this.book_id = new SimpleIntegerProperty(book_id);
            this.author_name = new SimpleStringProperty(author_name);
            this.publisher_name = new SimpleStringProperty(publisher_name);
            this.price = new SimpleDoubleProperty(price);
            this.copies = new SimpleIntegerProperty(copies);
        }

        public String getB_title() {
            return book_title.get();
        }

        public int getB_id() {
            return book_id.get();
        }

        public String getA_name() {
            return author_name.get();
        }

        public String getP_name() {
            return publisher_name.get();
        }

        public double getPrice() {
            return price.get();
        }

        public int getCopies() {
            return copies.get();
        }
        
    }
}
