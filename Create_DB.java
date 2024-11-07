import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class Create_DB{
    static final String DB_URL = "jdbc:mysql://localhost:3306/recipe_note";
    static final String USER = "root";
    static final String PASS = "Sri_GH@17062004";
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
            String sql = "CREATE DATABASE RECIPE_NOTE";
            stmt.executeUpdate(sql);
            System.out.println("Database created"); 
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}