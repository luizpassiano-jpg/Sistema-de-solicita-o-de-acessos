import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseJava {

    private static final String URL = "jdbc:postgresql://localhost:5432/projetoitau";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}