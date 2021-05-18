/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerDAO;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import Model.Usuario;
import Service.Conexion;

/**
 *
 * @author Guillermo
 */
public class UsuarioJDBC {
   
    
    private static UsuarioJDBC UsuarioJDBC;
    
    public UsuarioJDBC(){
        
    }
    
    public static UsuarioJDBC instancia(){
        if(UsuarioJDBC == null){
            UsuarioJDBC = new UsuarioJDBC();
        }
        return UsuarioJDBC;
    }
     
    private final String SQL_SELECT_REC = "SELECT usuario, clave FROM usuarios WHERE usuario=?";
    public Usuario consultarUsuario( String usuario, String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        Usuario usuarioAux = null;
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_REC);
            stm.setString(1, usuario);
            rs = stm.executeQuery();
            while(rs.next()){
                usuarioAux = new Usuario(rs.getString(1), rs.getString(2));
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return usuarioAux;
    }   
    
}
