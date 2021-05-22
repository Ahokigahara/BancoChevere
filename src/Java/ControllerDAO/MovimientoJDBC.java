/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerDAO;

import Model.*;
import Service.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author megan
 */
public class MovimientoJDBC {

    private static MovimientoJDBC MovimientoJDBC;
    private static final int MOVIMIENTO_PAGO = 1;
    private static final int MOVIMIENTO_TRANSFERENCIA = 2;

    public MovimientoJDBC() {

    }

    public static MovimientoJDBC instancia() {
        if (MovimientoJDBC == null) {
            MovimientoJDBC = new MovimientoJDBC();
        }
        return MovimientoJDBC;
    }

    private final String SQL_INSERT = "INSERT INTO MOVIMIENTOS(productoOrigenId,productoDestinoId,movimientoTipoId,fecha,monto,referencia,concepto) VALUES (?,?,?,SYSDATE(),?,?,?)";

    public String insertarMovimientoPago(String tipoMovimiento, int productoOrigenId, int productoDestinoId, String referencia, String concepto) {
        String respuesta = "";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int tipoMov = 0;
        if (tipoMovimiento.equals("PAGO")) {
            tipoMov = MOVIMIENTO_PAGO;

        } else if (tipoMovimiento.equals("TRANSFERENCIA")) {
            tipoMov = MOVIMIENTO_TRANSFERENCIA;
        }
        ReferenciaPago ref = ReferenciaPagoJDBC.instancia().consultarReferenciaPago(productoDestinoId, referencia, "");
        Producto p = ProductoJDBC.instancia().consultarProducto(productoOrigenId, "");
        if (p.getSaldo() >= ref.getMonto()) {

            try {
                conn = Conexion.getConnection();
                stm = conn.prepareStatement(SQL_INSERT);
                stm.setInt(1, p.getId());
                stm.setInt(2, productoDestinoId);
                stm.setInt(3, tipoMov);
                stm.setDouble(4, ref.getMonto());
                stm.setString(5, referencia);
                stm.setString(6, concepto);
                stm.executeUpdate();
                respuesta = "Exitoso," + (p.getSaldo() - ref.getMonto());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                Conexion.closed(conn);
                Conexion.closed(stm);
                Conexion.closed(rs);
            }
        }

        return respuesta;
    }

    public String insertarMovimientoTransferencia(String tipoMovimiento, int productoOrigenId, int productoDestinoId, double valor, String concepto) {
        String SQL_INSERT = "INSERT INTO MOVIMIENTOS(productoOrigenId,productoDestinoId,movimientoTipoId,fecha,monto,concepto) VALUES (?,?,?,SYSDATE(),?,?)";
        String respuesta = "";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int tipoMov = 0;
        if (tipoMovimiento.equals("PAGO")) {
            tipoMov = MOVIMIENTO_PAGO;

        } else if (tipoMovimiento.equals("TRANSFERENCIA")) {
            tipoMov = MOVIMIENTO_TRANSFERENCIA;
        }
        Producto origen = ProductoJDBC.instancia().consultarProducto(productoOrigenId, "");
        Producto destino = ProductoJDBC.instancia().consultarProducto(productoDestinoId, "");
        if (origen.getSaldo() >= valor) {

            try {
                conn = Conexion.getConnection();
                stm = conn.prepareStatement(SQL_INSERT);
                stm.setInt(1, origen.getId());
                stm.setInt(2, destino.getId());
                stm.setInt(3, tipoMov);
                stm.setDouble(4, valor);
                stm.setString(5, concepto);
                stm.executeUpdate();
                operacionesTransferencia(origen, destino, valor);
                respuesta = "Exitoso";
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                Conexion.closed(conn);
                Conexion.closed(stm);
                Conexion.closed(rs);
            }
        }

        return respuesta;
    }

    public List<Movimiento> consultarMovimientosProducto(int usuarioId, int productoId) {
        String sql = "SELECT m.fecha,p.numero as origen, m.monto,m.concepto,m.productoDestinoId FROM movimientos m INNER JOIN productos p ON m.productoOrigenId = p.id and p.terceroId=? inner join terceros t on p.terceroId=t.id \n"
                + " where m.productoOrigenId =? or m.productoDestinoId =? ";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Movimiento> movimientos = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setInt(1, usuarioId);
            stm.setInt(2, productoId);
            stm.setInt(3, productoId);
            rs = stm.executeQuery();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            while (rs.next()) {

                Movimiento mov = new Movimiento();
                mov.setFecha(formateador.format(rs.getDate(1)));
                mov.setProductoOrigenNumero(rs.getInt(2));
                mov.setMonto(rs.getDouble(3));
                mov.setConcepto(rs.getString(4));

                String subCons = "select t.nombres, t.apellidos,p.numero from terceros t join productos p on t.id = p.terceroId where t.id =?";
                PreparedStatement stm2 = conn.prepareStatement(subCons);
                stm2.setInt(1, rs.getInt("productoDestinoId"));
                ResultSet rs2 = stm2.executeQuery();
                if (rs2.next()) {
                    mov.setEntidadDestino(rs2.getString(1) + " " + rs2.getString(2));
                    mov.setProductoDestinoId(rs.getInt(3));
                }
                movimientos.add(mov);

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return movimientos;
    }  
    
    public List<Movimiento> consultarMovimientos(int usuarioId, int tipoMovimiento) {
        String sql = "SELECT m.fecha,p.numero as origen, m.monto,m.concepto,m.productoDestinoId FROM movimientos m INNER JOIN productos p ON m.productoOrigenId = p.id and p.terceroId=? inner join terceros t on p.terceroId=t.id \n"
                + " where m.movimientoTipoId =?  ";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Movimiento> movimientos = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setInt(1, usuarioId);
            stm.setInt(2, tipoMovimiento);
            rs = stm.executeQuery();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            while (rs.next()) {

                Movimiento mov = new Movimiento();
                mov.setFecha(formateador.format(rs.getDate(1)));
                mov.setProductoOrigenNumero(rs.getInt(2));
                mov.setMonto(rs.getDouble(3));
                mov.setConcepto(rs.getString(4));

                String subCons = "select t.nombres, t.apellidos,p.numero from terceros t join productos p on t.id = p.terceroId where t.id =?";
                PreparedStatement stm2 = conn.prepareStatement(subCons);
                stm2.setInt(1, rs.getInt("productoDestinoId"));
                ResultSet rs2 = stm2.executeQuery();
                if (rs2.next()) {
                    mov.setEntidadDestino(rs2.getString(1) + " " + rs2.getString(2));
                    mov.setProductoDestinoId(rs.getInt(3));
                }
                movimientos.add(mov);

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return movimientos;
    }
    private final String SQL_SELECT_LIST_MOV = "SELECT m.fecha,p.numero as origen, m.referencia,m.monto,m.concepto,m.productoDestinoId FROM movimientos m INNER JOIN productos p ON m.productoOrigenId = p.id and p.terceroId=? inner join terceros t on p.terceroId=t.id "
            + "where m.movimientoTipoId =? ";

    public List<Movimiento> consultarMovimientosPago(int usuarioId, int tipoMovimiento) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Movimiento> movimientos = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(SQL_SELECT_LIST_MOV);
            stm.setInt(1, usuarioId);
            stm.setInt(2, tipoMovimiento);
            rs = stm.executeQuery();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            while (rs.next()) {

                Movimiento mov = new Movimiento();
                mov.setFecha(formateador.format(rs.getDate(1)));
                mov.setProductoOrigenNumero(rs.getInt(2));
                mov.setReferencia(rs.getString(3));
                mov.setMonto(rs.getDouble(4));
                mov.setConcepto(rs.getString(5));

                String subCons = "select t.nombres, t.apellidos from terceros t where t.id =?";
                PreparedStatement stm2 = conn.prepareStatement(subCons);
                stm2.setInt(1, rs.getInt("productoDestinoId"));
                ResultSet rs2 = stm2.executeQuery();
                if (rs2.next()) {
                    mov.setEntidadDestino(rs2.getString(1) + " " + rs2.getString(2));
                }
                movimientos.add(mov);

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        return movimientos;
    }

    public void operacionesTransferencia(Producto origen, Producto destino, double montoTransferido) {
        String sql = "UPDATE productos SET saldo=? where numero=?";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        double nuevoSaldoOrigen = origen.getSaldo() - montoTransferido;
        double nuevoSaldoDestino = destino.getSaldo() + montoTransferido;

        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setDouble(1, nuevoSaldoOrigen);
            stm.setInt(2, origen.getNumero());
            stm.executeUpdate();

            stm = conn.prepareStatement(sql);
            stm.setDouble(1, nuevoSaldoDestino);
            stm.setInt(2, destino.getNumero());
            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
    }
}
