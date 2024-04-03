package BookAdder;
import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;  
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BookAdderController implements Initializable
{
    Connection con;

    @FXML
    private TextField author_name_btn;

    @FXML
    private TextField book_id_btn;

    @FXML
    private TextField book_title_btn;

    @FXML
    private Button cancel_button;

    @FXML
    private TextField pub_name_btn;

    @FXML
    private Label response_lb;

    @FXML
    private Button save_button;

    @FXML
    void CancelAction(ActionEvent event) 
    {
        System.exit(0);
    }

    @FXML
    void SaveBook(ActionEvent event) 
    {
        try
        {  
            int book_id = Integer.parseInt(book_id_btn.getText());
            String book_title = book_title_btn.getText();
            String author_name = author_name_btn.getText();
            String pub_name = pub_name_btn.getText();
            PreparedStatement ps = con.prepareStatement("insert into book values (?,?,?,?)");
            ps.setInt(1,book_id);
            ps.setString(2,book_title);
            ps.setString(3,author_name);
            ps.setString(4, pub_name);
            ps.executeUpdate();
            response_lb.setVisible(true);
            response_lb.setText("Book Added Successfully!");
            response_lb.setStyle("-fx-text-fill: green;");
            System.out.println("Update Successful");
        }
        catch(SQLException e)
        {
            response_lb.setVisible(true);
            response_lb.setText("Book Already Exists!");
            response_lb.setStyle("-fx-text-fill: red;");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        try 
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040"); 
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
