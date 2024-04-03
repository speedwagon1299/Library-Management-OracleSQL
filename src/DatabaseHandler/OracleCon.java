package DatabaseHandler;
import java.sql.*;  
public class OracleCon 
{
    public static void main(String[] args) 
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","C040");  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from emp");  
            while(rs.next())  
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)); 
            con.close();  
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
