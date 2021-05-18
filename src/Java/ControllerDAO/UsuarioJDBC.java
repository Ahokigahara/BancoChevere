package ControllerDAO;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Usuario;
import Service.Conexion;

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
     
    private final String SQL_SELECT_ONE = "SELECT A.usuario, A.clave,   B.id, B.documento, B.documentoTipoId, B.documentoExpedicion, B.nombres, B.apellidos, B.direccion, B.telefono, B.email FROM usuarios as A LEFT JOIN terceros as B on A.terceroId = B.id WHERE A.usuario=?";
    public Usuario consultarUsuario( String user, String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        Usuario usuario = null;
        DocumentoTipo documentoTipo = new DocumentoTipo();
        DocumentoTipoJDBC documentoTipoJDBC = new DocumentoTipoJDBC();
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setString(1, user);
            rs = stm.executeQuery();
            while(rs.next()){
                documentoTipo = documentoTipoJDBC.consultarDocumentoTipo(rs.getString(5), mensaje);
                usuario = new Usuario(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), documentoTipo, rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return usuario;
    }   
    
}
