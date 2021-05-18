package ControllerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.DocumentoTipo;
import Service.Conexion;

public class DocumentoTipoJDBC {
   
    
    private static DocumentoTipoJDBC DocumentoTipoJDBC;
    
    public DocumentoTipoJDBC(){
        
    }
    
    public static DocumentoTipoJDBC instancia(){
        if(DocumentoTipoJDBC == null){
            DocumentoTipoJDBC = new DocumentoTipoJDBC();
        }
        return DocumentoTipoJDBC;
    }
     
    private final String SQL_SELECT_ONE = "SELECT A.id, A.nombre FROM documentostipo as A WHERE A.id=?";
    public DocumentoTipo consultarDocumentoTipo(String id, String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        DocumentoTipo documentoTipo = null;
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while(rs.next()){
                documentoTipo = new DocumentoTipo(rs.getString(1), rs.getString(2));
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return documentoTipo;
    }   
    
}
