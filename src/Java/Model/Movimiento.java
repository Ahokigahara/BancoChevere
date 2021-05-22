/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Guillermo
 */
public class Movimiento {
    private int id;
    private int productoOrigenId;
    private int productoOrigenNumero;
    private int productoDestinoId;
    private String entidadDestino;
    private int movimientoTipoId;
    private String tipoMovimiento;
    private String fecha;
    private double monto;
    private String referencia;
    private String concepto;
    public Movimiento() {
    }

    
    public Movimiento(int id, int productoOrigenId, int productoDestinoId, int movimientoTipoId, String fecha, double monto, String referencia) {
        this.id = id;
        this.productoOrigenId = productoOrigenId;
        this.productoDestinoId = productoDestinoId;
        this.movimientoTipoId = movimientoTipoId;
        this.fecha = fecha;
        this.monto = monto;
        this.referencia = referencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductoOrigenId() {
        return productoOrigenId;
    }

    public void setProductoOrigenId(int productoOrigenId) {
        this.productoOrigenId = productoOrigenId;
    }

    public int getProductoDestinoId() {
        return productoDestinoId;
    }

    public void setProductoDestinoId(int productoDestinoId) {
        this.productoDestinoId = productoDestinoId;
    }

    public int getMovimientoTipoId() {
        return movimientoTipoId;
    }

    public void setMovimientoTipoId(int movimientoTipoId) {
        this.movimientoTipoId = movimientoTipoId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getProductoOrigenNumero() {
        return productoOrigenNumero;
    }

    public void setProductoOrigenNumero(int productoOrigenNumero) {
        this.productoOrigenNumero = productoOrigenNumero;
    }

    public String getEntidadDestino() {
        return entidadDestino;
    }

    public void setEntidadDestino(String entidadDestino) {
        this.entidadDestino = entidadDestino;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    
    
}
