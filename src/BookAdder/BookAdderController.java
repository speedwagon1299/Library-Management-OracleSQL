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
    private TextField b_id_tf;

    @FXML
    private TextField b_title_tf;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField copies_tf;

    @FXML
    private TextField p_id_tf;

    @FXML
    private TextField price_tf;

    @FXML
    private Label response_lb;

    @FXML
    private Button save_btn;

    @FXML
    private TextField year_tf;
    
    @FXML
    void CancelAction(ActionEvent event) throws SQLException 
    {
        con.close();
        System.exit(0);
    }

    @FXML
    void SaveBook(ActionEvent event) 
    {
        try
        {  
            int b_id_val = Integer.parseInt(b_id_tf.getText());
            String b_title_val = b_title_tf.getText();
            int p_id_val;
            if(p_id_tf.getText().equals("")) {
                p_id_val = -1;
            }    
            else {
                p_id_val = Integer.parseInt(p_id_tf.getText());
            }
            int year_val;
            if(year_tf.getText().equals("")) {
                year_val = -1;
            }    
            else {
                year_val = Integer.parseInt(year_tf.getText());
            }
            double price_val;
            if(price_tf.getText().equals("")) {
                price_val = -1;
            }    
            else {
                price_val = Double.parseDouble(price_tf.getText());
            }
            int copies_val;
            if(copies_tf.getText().equals("")) {
                copies_val = -1;
            }    
            else {
                copies_val = Integer.parseInt(copies_tf.getText());
            }
            PreparedStatement ps = con.prepareStatement("insert into book (b_id, b_title, p_id, year, price, copies)" +
                                                        " values (?,?,?,?,?,?)");
            ps.setInt(1,b_id_val);
            ps.setString(2,b_title_val);
            if(p_id_val == -1) {
                ps.setNull(3, java.sql.Types.NULL);
            } 
            else {
                ps.setInt(3, p_id_val);
            }
            if(year_val == -1) {
                ps.setNull(4, java.sql.Types.NULL);
            } 
            else {
                ps.setInt(4, year_val);
            }
            if(price_val == -1) {
                ps.setNull(5, java.sql.Types.NULL);
            } 
            else {
                ps.setDouble(5, price_val);
            }
            if(copies_val == -1) {
                ps.setInt(6, 1);
            }
            else {
                ps.setInt(6,copies_val);
            }
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
            e.printStackTrace();
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
