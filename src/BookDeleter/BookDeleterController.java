package BookDeleter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BookDeleterController implements Initializable
{

    ObservableList<String> cl = FXCollections.observableArrayList("Book Name","Book ID","Author Name","Publisher");
    String ls[] = {"book_title","book_id","author_id_1","publisher_id"};    
    int ind;
    Connection con;

    @FXML
    private Label attrib_lb;

    @FXML
    private TextField attrib_tf;

    @FXML
    private Button cancel_button;

    @FXML
    private ComboBox<String> cb_book_attrib;

    @FXML
    private Button delete_button;

    @FXML
    private Label delete_lb;

    @FXML
    void CancelAction(ActionEvent event) 
    {
        System.exit(0);
    }

    @FXML
    void DeleteBook(ActionEvent event) 
    {
        try
        { 
            String attrib = attrib_tf.getText();
            if(attrib.equals(""))
            {
                delete_lb.setVisible(true);
                delete_lb.setText("Please Select an Attribute");
                delete_lb.setStyle("-fx-text-fill: red;");
                return;
            }
            PreparedStatement ps = con.prepareStatement("delete from book where " + ls[ind] + " = ?");
            ps.setString(1,attrib);
            int row = ps.executeUpdate();
            if(row == 0)
            {
                delete_lb.setVisible(true);
                delete_lb.setText("Record does not Exist!");
                delete_lb.setStyle("-fx-text-fill: red;");
            }
            else
            {
                delete_lb.setVisible(true);
                delete_lb.setText("Successfully Deleted!");
                delete_lb.setStyle("-fx-text-fill: green;");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void SelectAttribute(ActionEvent event) 
    {
        String ch = cb_book_attrib.getValue();
        attrib_lb.setText("Enter " + ch);
        if(ch.equals("Book Name"))
        {
            ind = 0;
        }
        else if(ch.equals("Book ID"))
        {
            ind = 1;
        }
        else if(ch.equals("Author Name"))
        {
            ind = 2;
        }
        else
        {
            ind = 3;
        }
        attrib_tf.setEditable(true);
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
        cb_book_attrib.setItems(cl);
    }
}
