package DisplayBooks;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DisplayBooksController implements Initializable {

    Connection con;
    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Book,String> author_name_col;

    @FXML
    private TableColumn<Book,String> book_id_col;

    @FXML
    private TableView<Book> book_table;

    @FXML
    private TableColumn<Book,String> book_title_col;

    @FXML
    private TableColumn<Book,String> publisher_col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040");
        }
        catch(Exception e){

        }
        initCol();
        loadData();
    }

    private void initCol() {
        book_title_col.setCellValueFactory(new PropertyValueFactory<>("book_title"));
        book_id_col.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        author_name_col.setCellValueFactory(new PropertyValueFactory<>("author_name"));
        publisher_col.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }

    private void loadData() {        
        try {
            PreparedStatement ps = con.prepareStatement("select book_title, book_id, author_name, publisher_name " +  
                                                            "from book, author, publisher " + 
                                                            "where book.author_id_1 = author.author_id and book.publisher_id = publisher.publisher_id");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String book_title = rs.getString("book_title");
                int book_id = rs.getInt("book_id");
                String author_name = rs.getString("author_name");
                String publisher = rs.getString("publisher");
                list.add(new Book(book_title, book_id, author_name, publisher));
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
        private final SimpleStringProperty publisher;
        
        Book(String book_title, int book_id, String author_name, String publisher) {
            this.book_title = new SimpleStringProperty(book_title);
            this.book_id = new SimpleIntegerProperty(book_id);
            this.author_name = new SimpleStringProperty(author_name);
            this.publisher = new SimpleStringProperty(publisher);
        }

        public String getBook_title() {
            return book_title.get();
        }

        public int getBook_id() {
            return book_id.get();
        }

        public String getAuthor_name() {
            return author_name.get();
        }

        public String getPublisher() {
            return publisher.get();
        }
        
    }
}
