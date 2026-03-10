import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean isAdmin(String username) {
        if ("admin".equalsIgnoreCase (username) ){
            return true;
        }
        String sql = """
        SELECT r.role_name
        FROM access_requests a
        JOIN users u ON a.user_id = u.id
        JOIN roles r ON a.role_id = r.id
        WHERE u.username = ? AND r.role_name = 'ADMIN' AND a.status = 'APPROVED'
    """;

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            System.out.println("Usuário criado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao criar usuário.");
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer findUserIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Usuário excluído com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listUsers() {
        String sql = "SELECT id, username FROM users ORDER BY id";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== USUÁRIOS ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Nome: " + rs.getString("username"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}