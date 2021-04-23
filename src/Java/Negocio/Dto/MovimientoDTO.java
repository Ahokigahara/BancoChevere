/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.Dto;

import java.sql.Date;

/**
 *
 * @author Megan
 */
public class MovimientoDTO {
    public int id;
    public ProductoDTO producto_Origen_Id;
    public ProductoDTO producto_Destino_Id;
    public Date fecha_movimiento;
    public double monto;
    public String referencia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductoDTO getProducto_Origen_Id() {
        return producto_Origen_Id;
    }

    public void setProducto_Origen_Id(ProductoDTO producto_Origen_Id) {
        this.producto_Origen_Id = producto_Origen_Id;
    }

    public ProductoDTO getProducto_Destino_Id() {
        return producto_Destino_Id;
    }

    public void setProducto_Destino_Id(ProductoDTO producto_Destino_Id) {
        this.producto_Destino_Id = producto_Destino_Id;
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
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
    
    
}
