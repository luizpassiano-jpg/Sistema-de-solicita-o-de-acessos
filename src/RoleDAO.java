import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDAO {

    public Integer findRoleIdByName(String roleName) {
        String sql = "SELECT id FROM roles WHERE role_name = ?";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void listRoles() {
        String sql = "SELECT id, role_name FROM roles ORDER BY id";

        try (Connection conn = DatabaseJava.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== ROLES ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Role: " + rs.getString("role_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}