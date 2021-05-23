package ControllerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.ReferenciaPago;
import Model.Tercero;
import Service.Conexion;

public class ReferenciaPagoJDBC {

    private static ReferenciaPagoJDBC ReferenciaPagoJDBC;

    public ReferenciaPagoJDBC() {

    }

    public static ReferenciaPagoJDBC instancia() {
        if (ReferenciaPagoJDBC == null) {
            ReferenciaPagoJDBC = new ReferenciaPagoJDBC();
        }
        return ReferenciaPagoJDBC;
    }

    private final String SQL_SELECT_ONE = "SELECT A.id, A.referencia, A.monto, A.terceroId FROM referenciaspago AS A WHERE A.terceroId=? AND A.referencia=?";
    public ReferenciaPago consultarReferenciaPago(Integer entidad, String referencia, String mensaje) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ReferenciaPago referenciaPago = null;
        Tercero tercero = new Tercero();
        TerceroJDBC tercerosJDBC = new TerceroJDBC();
        mensaje = "Sin error";
        
        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(SQL_SELECT_ONE);
            stm.setInt(1, entidad);
            stm.setString(2, referencia);
            rs = stm.executeQuery();
            while (rs.next()) {
                tercero = tercerosJDBC.consultarTercero(rs.getInt(4), mensaje);
                referenciaPago = new ReferenciaPago(rs.getInt(1), rs.getString(2), rs.getDouble(3), tercero);
            }
        } catch (SQLException e) {
            mensaje = "Error: " + e.getMessage();
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return referenciaPago;
    }
    
    private final String SQL_SELECT_ONE_ID = "SELECT A.id, A.referencia, A.monto, A.terceroId FROM referenciaspago AS A WHERE A.id=?";
    public ReferenciaPago consultarReferenciaPagoId(Integer id, String mensaje) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ReferenciaPago referenciaPago = null;
        Tercero tercero = new Tercero();
        TerceroJDBC tercerosJDBC = new TerceroJDBC();
        mensaje = "Sin error";
        
        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(SQL_SELECT_ONE_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                tercero = tercerosJDBC.consultarTercero(rs.getInt(4), mensaje);
                referenciaPago = new ReferenciaPago(rs.getInt(1), rs.getString(2), rs.getDouble(3), tercero);
            }
        } catch (SQLException e) {
            mensaje = "Error: " + e.getMessage();
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return referenciaPago;
    }
}
