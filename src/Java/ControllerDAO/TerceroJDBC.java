package ControllerDAO;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Tercero;
import Service.Conexion;
import java.util.ArrayList;
import java.util.List;

public class TerceroJDBC {

    private static TerceroJDBC TerceroJDBC;

    public TerceroJDBC() {

    }

    public static TerceroJDBC instancia() {
        if (TerceroJDBC == null) {
            TerceroJDBC = new TerceroJDBC();
        }
        return TerceroJDBC;
    }

    private final String SQL_SELECT_ONE = "SELECT A.id, A.documento, A.documentoTipoId, A.documentoExpedicion, A.nombres, A.apellidos, A.direccion, A.telefono, A.email FROM terceros as A WHERE A.id=?";
    public Tercero consultarTercero(Integer id, String mensaje) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Tercero tercero = null;
        DocumentoTipo documentoTipo = new DocumentoTipo();
        DocumentoTipoJDBC documentoTipoJDBC = new DocumentoTipoJDBC();
        mensaje = "Sin error";
        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                documentoTipo = documentoTipoJDBC.consultarDocumentoTipo(rs.getString(3), mensaje);
                tercero = new Tercero(rs.getInt(1), rs.getString(2), documentoTipo, rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
            }
        } catch (SQLException e) {
            mensaje = "Error: " + e.getMessage();
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return tercero;
    }
    
    private final String SQL_SELECT_ENT = "SELECT A.terceroId FROM referenciaspago as A GROUP BY A.terceroId";
    public List<Tercero> consultarEntidades(String mensaje){
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        
        DocumentoTipoJDBC documentoTipoJDBC = new DocumentoTipoJDBC();
        List<Tercero> terceros = new ArrayList();
        
        mensaje = "Sin error";
        try{
            conn = Conexion.getConnection() ;
            stm = conn.prepareStatement(SQL_SELECT_ENT);
            rs = stm.executeQuery();
            while(rs.next()){
                Tercero tercero = new Tercero();
                tercero = consultarTercero(rs.getInt(1), mensaje);
                terceros.add(tercero);
            }
        }catch(SQLException e){
            mensaje = "Error: "+e.getMessage();
        }finally{
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return terceros;
    }        

}
