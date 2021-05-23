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

    private final String SQL_INSERT = "INSERT INTO MOVIMIENTOS(productoOrigenId,productoDestinoId,movimientoTipoId,fecha,monto,referenciaId,concepto) VALUES (?,?,?,SYSDATE(),?,?,?)";

    public String insertarMovimientoPago(String tipoMovimiento, int productoOrigenId, int productoDestinoId, String referencia, String concepto, String mensaje) {
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
        Producto p = ProductoJDBC.instancia().consultarProducto(productoOrigenId, mensaje);
        if (p.getSaldo() >= ref.getMonto()) {
            try {
                conn = Conexion.getConnection();
                stm = conn.prepareStatement(SQL_INSERT);
                stm.setInt(1, p.getId());
                stm.setInt(2, productoDestinoId);
                stm.setInt(3, tipoMov);
                stm.setDouble(4, ref.getMonto());
                stm.setInt(5, ref.getId());
                stm.setString(6, "Id:"+Integer.toString(ref.getId())+", Monto: "+Double.toString(ref.getMonto())+", Tercero:"+ref.getTercero().getNombreCompleto());
                stm.executeUpdate();
                ProductoJDBC.instancia().actualizarSaldo(p.getId(), p.getSaldo()-ref.getMonto()); 
                respuesta = "{\"RESULTADO\":true,\"MENSAJE\":\" Pago realizado correctamente, nuevo saldo: "+Double.toString(p.getSaldo()-ref.getMonto())+"\"}";
                
            } catch (SQLException e) {
                mensaje = e.getMessage();
                respuesta = "{\"RESULTADO\":false,\"MENSAJE\":\" Pago no realizado correctamente, "+e.getMessage()+"\"}";
            } finally {
                Conexion.closed(conn);
                Conexion.closed(stm);
                Conexion.closed(rs);
            }
        }else{
            respuesta = "{\"RESULTADO\":false,\"MENSAJE\":\"Sin saldo\"}";
        }

        return respuesta;
    }

    public String insertarMovimientoTransferencia(String tipoMovimiento, int productoOrigenId, int productoDestinoId, double valor, String concepto, String mensaje) {
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
                respuesta = operacionesTransferencia(origen, destino, valor, mensaje);
            } catch (SQLException e) {
                respuesta = "{\"RESULTADO\":false,\"MENSAJE\":\" Transferecnia no realizadoa correctamente, "+e.getMessage()+"\"}";
            } finally {
                Conexion.closed(conn);
                Conexion.closed(stm);
                Conexion.closed(rs);
            }
        }

        return respuesta;
    }

    private final String SQL_SELECT_LIST_MOV_PRO = "SELECT M.id, M.productoOrigenId, M.productoDestinoId, M.movimientoTipoId, M.fecha, M.monto, M.concepto, M.referenciaId FROM movimientos m INNER JOIN productos P ON M.productoOrigenId = P.id INNER JOIN terceros T on P.terceroId=T.id WHERE M.productoOrigenId =? or M.productoDestinoId =?";

    public List<Movimiento> consultarMovimientosPorProducto(int productoId, String mensaje) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ProductoJDBC productoJDBC = new ProductoJDBC();
        MovimientoTipoJDBC movimientoTipoJDBC = new MovimientoTipoJDBC();
        ReferenciaPagoJDBC referenciaPagoJDBC = new ReferenciaPagoJDBC();

        List<Movimiento> movimientos = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(SQL_SELECT_LIST_MOV_PRO);
            stm.setInt(1, productoId);
            stm.setInt(2, productoId);
            rs = stm.executeQuery();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            while (rs.next()) {
                Producto productoOrigen = productoJDBC.consultarProducto(rs.getInt(2));
                Producto productoDestino = productoJDBC.consultarProducto(rs.getInt(3));
                MovimientoTipo movimientoTipo = movimientoTipoJDBC.consultarMovimientoTipo(rs.getInt(4), mensaje);

                ReferenciaPago referenciaPago = new ReferenciaPago();
                if (movimientoTipo.getId() == 1) {
                    referenciaPago = referenciaPagoJDBC.consultarReferenciaPagoId(rs.getInt(8), mensaje);
                }

                Movimiento mov = new Movimiento(rs.getInt(1), productoOrigen, productoDestino, movimientoTipo, rs.getString(5), rs.getDouble(6), rs.getString(7), referenciaPago);
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

    private final String SQL_SELECT_LIST_TIPO = "SELECT M.id, M.productoOrigenId, M.productoDestinoId, M.movimientoTipoId, M.fecha, M.monto, M.concepto, M.referenciaId FROM movimientos M INNER JOIN productos P ON M.productoOrigenId = P.id INNER JOIN terceros T ON P.terceroId=T.id WHERE P.terceroId=? AND M.movimientoTipoId = ? ";

    public List<Movimiento> consultarMovimientosPorTipo(int usuarioId, int tipoMovimiento, String mensaje) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ProductoJDBC productoJDBC = new ProductoJDBC();
        MovimientoTipoJDBC movimientoTipoJDBC = new MovimientoTipoJDBC();
        ReferenciaPagoJDBC referenciaPagoJDBC = new ReferenciaPagoJDBC();

        List<Movimiento> movimientos = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stm = conn.prepareStatement(SQL_SELECT_LIST_TIPO);
            stm.setInt(1, usuarioId);
            stm.setInt(2, tipoMovimiento);
            rs = stm.executeQuery();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            while (rs.next()) {
                Producto productoOrigen = productoJDBC.consultarProducto(rs.getInt(2));
                Producto productoDestino = productoJDBC.consultarProducto(rs.getInt(3));
                MovimientoTipo movimientoTipo = movimientoTipoJDBC.consultarMovimientoTipo(rs.getInt(4), mensaje);

                ReferenciaPago referenciaPago = new ReferenciaPago();
                if (movimientoTipo.getId() == 1) {
                    referenciaPago = referenciaPagoJDBC.consultarReferenciaPagoId(rs.getInt(8), mensaje);
                }

                Movimiento mov = new Movimiento(rs.getInt(1), productoOrigen, productoDestino, movimientoTipo, rs.getString(5), rs.getDouble(6), rs.getString(7), referenciaPago);
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

    public String operacionesTransferencia(Producto origen, Producto destino, double montoTransferido, String mensaje) {
        String respuesta = "";
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
            
            respuesta = "{\"RESULTADO\":true,\"MENSAJE\":\" Transferencia realizada correctamente.\"}";

        } catch (SQLException e) {
            respuesta = "{\"RESULTADO\":false,\"MENSAJE\":\" Transferecnia no realizadoa correctamente, "+e.getMessage()+"\"}";
        } finally {
            Conexion.closed(conn);
            Conexion.closed(stm);
            Conexion.closed(rs);
        }
        
        return respuesta;
    }
}
