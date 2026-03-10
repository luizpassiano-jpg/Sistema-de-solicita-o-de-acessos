import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccessRequestDAO {

    public void requestAccess(int userId, int roleId) {
        String sql = "INSERT INTO access_requests (user_id, role_id, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, roleId);
            stmt.setString(3, "PENDING");

            stmt.executeUpdate();
            System.out.println("Solicitação criada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveRequest(int requestId) {
        String sql = "UPDATE access_requests SET status = 'APPROVED' WHERE id = ?";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Solicitação aprovada!");
            } else {
                System.out.println("Solicitação não encontrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listRequests() {
        String sql = """
                SELECT a.id, u.username, r.role_name, a.status
                FROM access_requests a
                JOIN users u ON a.user_id = u.id
                JOIN roles r ON a.role_id = r.id
                ORDER BY a.id
                """;

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== SOLICITAÇÕES ===");
            while (rs.next()) {
                System.out.println(
                        "ID Solicitação: " + rs.getInt("id") +
                                " | Usuário: " + rs.getString("username") +
                                " | Role: " + rs.getString("role_name") +
                                " | Status: " + rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}