package Service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private static String[][] configuraciones
            = {
                {
                    "com.mysql.cj.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/banco_v2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
                    "root",
                    "1234"
                },
                {
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/banco_v3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "banco",
                    "banco"
                }
            };
    private static int configuracion = 1;
    private static String JDBC_DRIVER = configuraciones[configuracion][0];
    private static String JDBC_URL = configuraciones[configuracion][1];
    private static String JDBC_USER = configuraciones[configuracion][2];
    private static String JDBC_PASS = configuraciones[configuracion][3];
    private static Driver driver = null;

    public static synchronized Connection getConnection() throws SQLException {
        if (driver == null) {
            try {
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException e) {
                System.out.println("Fallo en cargar el Driver");
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    public static void closed(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {

        }
    }

    public static void closed(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {

        }
    }

    public static void closed(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {

        }
    }

}
