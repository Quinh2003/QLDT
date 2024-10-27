package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {
    private static final Map<String, String> SERVER_URLS = new HashMap<>();

    static {
        SERVER_URLS.put("central", "jdbc:sqlserver://DESKTOP-G9C3FTA\\MSSQLSERVER_1:1433;databaseName=QLDT;encrypt=false;trustServerCertificate=true");
        SERVER_URLS.put("Chi_Nhanh_1", "jdbc:sqlserver://DESKTOP-G9C3FTA\\MSSQLSERVER_2:1436;databaseName=QLDT;encrypt=false;trustServerCertificate=true");
        SERVER_URLS.put("Chi_Nhanh_2", "jdbc:sqlserver://DESKTOP-G9C3FTA\\MSSQLSERVER_3:1435;databaseName=QLDT;encrypt=false;trustServerCertificate=true");
    }

    public static Connection getConnection(String serverKey, String user, String password) throws SQLException {
        String url = SERVER_URLS.get(serverKey);
        if (url == null) {
            throw new IllegalArgumentException("Invalid server key: " + serverKey);
        }
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        String user = "sa";
        String password = "1234567890";

        try (Connection centralConnection = getConnection("central", user, password)) {
            System.out.println("Connected to Central Server successfully!");
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
