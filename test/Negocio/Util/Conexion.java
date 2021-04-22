package Negocio.Util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;          
import java.sql.DriverManager;      
import java.sql.PreparedStatement;   
import java.sql.SQLException;    
/**
 *
 * @author Megan
 */
public class Conexion {
    private final String url = "jdbc:mysql://localhost:3306/banco_v1";        
    PreparedStatement psPrepararSentencia;         
    private static Connection con = null;   
    
     public Conexion() {
     try{   
         
         Class.forName("com.mysql.jdbc.Driver");    
         con = DriverManager.getConnection(url,"root","1234");   
      }
         catch(SQLException e)       
         {
         System.out.println(e);          
         }
         catch(ClassNotFoundException e)       
         {
          System.out.println(e);              
         }
    }
     
    

   public static void cerrar() throws SQLException {
      if (con != null) {
         con.close();
      }
   }

}
