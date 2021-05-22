package ControllerDAO;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Producto;
import Service.Conexion;
import java.util.ArrayList;
import java.util.List;

public class ProductoJDBC {
   
    
    private static ProductoJDBC ProductoJDBC;
    
    public ProductoJDBC(){
        
    }
    
    public static ProductoJDBC instancia(){
        if(ProductoJDBC == null){
            ProductoJDBC = new ProductoJDBC();
        }
        return ProductoJDBC;
    }
     
    private final String SQL_SELECT_ONE = "SELECT A.id, A.saldo, A.productoTipoId, A.terceroId, A.numero FROM productos as A WHERE A.numero=?";
    public Producto consultarProducto(Integer productoId, String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        Producto producto = null;
        ProductoTipo productoTipo = new ProductoTipo();
        ProductoTipoJDBC productoTipoJDBC = new ProductoTipoJDBC();
        Tercero tercero = new Tercero();
        TerceroJDBC tercerosJDBC = new TerceroJDBC();        
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setInt(1, productoId);
            rs = stm.executeQuery();
            while(rs.next()){
                productoTipo = productoTipoJDBC.consultarProductoTipo(rs.getInt(3), mensaje);
                tercero = tercerosJDBC.consultarTercero(rs.getInt(4), mensaje);
                producto = new Producto(rs.getInt(1), rs.getDouble(2), productoTipo, tercero,rs.getInt(5));
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return producto;
    }  
    
    public Producto consultarProducto(Integer productoId){
        String SQL_SELECT_ONE = "SELECT A.id, A.saldo, A.productoTipoId, A.terceroId, A.numero FROM productos as A WHERE A.id=?";
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        Producto producto = null;
        ProductoTipo productoTipo = new ProductoTipo();
        ProductoTipoJDBC productoTipoJDBC = new ProductoTipoJDBC();
        Tercero tercero = new Tercero();
        TerceroJDBC tercerosJDBC = new TerceroJDBC();        
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setInt(1, productoId);
            rs = stm.executeQuery();
            while(rs.next()){
                productoTipo = productoTipoJDBC.consultarProductoTipo(rs.getInt(3), "");
                tercero = tercerosJDBC.consultarTercero(rs.getInt(4), "");
                producto = new Producto(rs.getInt(1), rs.getDouble(2), productoTipo, tercero,rs.getInt(5));
            }
        }catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return producto;
    }  
    
    private final String SQL_SELECT_LIST = "SELECT A.id, A.saldo, A.productoTipoId, A.terceroId,A.numero FROM productos as A WHERE A.terceroId=?";
    public List<Producto> consultarProductos(Integer terceroId, String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        
        ProductoTipoJDBC productoTipoJDBC = new ProductoTipoJDBC();
        TerceroJDBC tercerosJDBC = new TerceroJDBC(); 
        List<Producto> productos = new ArrayList();
        
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_LIST);
            stm.setInt(1, terceroId);
            rs = stm.executeQuery();
            while(rs.next()){
                ProductoTipo productoTipo = new ProductoTipo();
                productoTipo = productoTipoJDBC.consultarProductoTipo(rs.getInt(3), mensaje);
                
                Tercero tercero = new Tercero();
                tercero = tercerosJDBC.consultarTercero(rs.getInt(4), mensaje);
                
                Producto producto = new Producto(rs.getInt(1), rs.getDouble(2), productoTipo, tercero,rs.getInt(5));
                productos.add(producto);
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return productos;
    }       
    private final String SQL_UPDATE_ONE = "UPDATE productos SET saldo=? where numero=?";
    public void actualizarSaldo(String origen, String respuesta) {
        String[] saldo=respuesta.split(",");
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_UPDATE_ONE);
            stm.setDouble(1, Double.parseDouble(saldo[1]));
            stm.setInt(2, Integer.parseInt(origen));
            stm.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
    }
    
}
