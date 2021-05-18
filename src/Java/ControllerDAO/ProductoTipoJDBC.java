package ControllerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.ProductoTipo;
import Service.Conexion;

public class ProductoTipoJDBC {
   
    
    private static ProductoTipoJDBC ProductoTipoJDBC;
    
    public ProductoTipoJDBC(){
        
    }
    
    public static ProductoTipoJDBC instancia(){
        if(ProductoTipoJDBC == null){
            ProductoTipoJDBC = new ProductoTipoJDBC();
        }
        return ProductoTipoJDBC;
    }
     
    private final String SQL_SELECT_ONE = "SELECT A.id, A.nombre FROM productostipo as A WHERE A.id=?";
    public ProductoTipo consultarProductoTipo(Integer id, String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        ProductoTipo productoTipo = null;
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while(rs.next()){
                productoTipo = new ProductoTipo(rs.getInt(1), rs.getString(2));
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return productoTipo;
    }   
    
}
