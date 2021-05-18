package ControllerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.MovimientoTipo;
import Service.Conexion;

public class MovimientoTipoJDBC {
   
    
    private static MovimientoTipoJDBC MovimientoTipoJDBC;
    
    public MovimientoTipoJDBC(){
        
    }
    
    public static MovimientoTipoJDBC instancia(){
        if(MovimientoTipoJDBC == null){
            MovimientoTipoJDBC = new MovimientoTipoJDBC();
        }
        return MovimientoTipoJDBC;
    }
     
    private final String SQL_SELECT_ONE = "SELECT A.id, A.nombre FROM movimientostipo as A WHERE A.id=?";
    public MovimientoTipo consultarMovimientoTipo(Integer id, String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        MovimientoTipo movimientoTipo = null;
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while(rs.next()){
                movimientoTipo = new MovimientoTipo(rs.getInt(1), rs.getString(2));
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return movimientoTipo;
    }   
    
}
