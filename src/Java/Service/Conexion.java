
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
    private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String DB = "banco_v2";
    private static String JDBC_URL = "jdbc:mysql://localhost:3306/"+DB+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "1234"; 
    private static Driver driver;
    
    public static synchronized Connection getConnection() throws SQLException{
        //if(driver ==null){
            try{
               Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
               driver = (Driver)jdbcDriverClass.newInstance();
               DriverManager.registerDriver(driver);
            }catch(ClassNotFoundException  |  InstantiationException | 
                    IllegalAccessException e){
                System.out.println("Fallo en cargar el Driver");
            }
        //}
        return DriverManager.getConnection(JDBC_URL,JDBC_USER, JDBC_PASS);
    }
    
    public static void closed(ResultSet rs){
        try{
            if(rs!=null)
                rs.close();
        }catch(SQLException e){
            
        }
    }
    
    public static void closed(PreparedStatement stmt){
        try{
            if(stmt!=null)
                stmt.close();
        }catch(SQLException e){
            
        }
    }
    
    public static void closed(Connection conn){
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException e){
            
        }
    }

    
}
