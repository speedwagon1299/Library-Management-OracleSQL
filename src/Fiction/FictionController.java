package Fiction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FictionController {
    

public class DisplayBooksController {

    @FXML
    private ComboBox<?> attrib_cb;

    @FXML
    private TextField attrib_tf;

    @FXML
    private TableColumn<Book, Integer> b_id_col;

    @FXML
    private TableColumn<Book, String> b_title_col;

    @FXML
    private TableView<?> fiction_table;

    @FXML
    private TableColumn<Book, String> subgenre;

    @FXML
    private TableColumn<Book, String> target_age_group;

    

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
        else if(ch.equals("Author ID")) ind = 2;
        else if(ch.equals("Author Name")) ind = 3;
        else if(ch.equals("Publisher Name")) ind = 4;
        else if(ch.equals("Price Range"))   ind = 5;
        else if(ch.equals("Copies Range")) ind = 6;
        else    ind = 7;
        list.clear();
        loadData();
    }

    private void initCol() {
        b_title_col.setCellValueFactory(new PropertyValueFactory<>("b_title"));
        b_id_col.setCellValueFactory(new PropertyValueFactory<>("b_id"));
        a_name_col.setCellValueFactory(new PropertyValueFactory<>("subgenre"));
        p_name_col.setCellValueFactory(new PropertyValueFactory<>("target_age_group"));
    }

    private void loadData() {        
        try {
            
            }
            PreparedStatement ps = con.prepareStatement("select b.b_title, w.b_id, f.subgenre, f.target_age_group " +
                                                        "from book b, wrote w, fiction f " +
                                                        "where b.b_id = w.b_id " + extra);
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

}

}
