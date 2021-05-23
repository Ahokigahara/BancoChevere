package Model;

import java.util.Date;

public class Movimiento {
    private int id;
    private Producto productoOrigen;
    private Producto productoDestino;
    private MovimientoTipo movimientoTipo;
    private String fecha;
    private double monto;
    private String concepto;
    private ReferenciaPago referenciaPago;
    
    public Movimiento() {
        this.id=0;
        this.productoOrigen = new Producto();
        this.productoDestino = new Producto();
        this.movimientoTipo = new MovimientoTipo();
        this.fecha = "";
        this.monto = 0.0;
        this.referenciaPago = new ReferenciaPago();
        this.concepto = "";
    }

    
    public Movimiento(int id, Producto productoOrigen, Producto productoDestino, MovimientoTipo movimientoTipo, String fecha, double monto, String concepto, ReferenciaPago referenciaPago) {
        this.id = id;
        this.productoOrigen = productoOrigen;
        this.productoDestino = productoDestino;
        this.movimientoTipo = movimientoTipo;
        this.fecha = fecha;
        this.monto = monto;
        this.concepto = concepto;
        this.referenciaPago = referenciaPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProductoOrigen() {
        return productoOrigen;
    }

    public void setProductoOrigen(Producto productoOrigen) {
        this.productoOrigen = productoOrigen;
    }

    public Producto getProductoDestino() {
        return productoDestino;
    }

    public void setProductoDestino(Producto productoDestino) {
        this.productoDestino = productoDestino;
    }

    public MovimientoTipo getMovimientoTipo() {
        return movimientoTipo;
    }

    public void setMovimientoTipo(MovimientoTipo movimientoTipo) {
        this.movimientoTipo = movimientoTipo;
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

    public ReferenciaPago getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(ReferenciaPago referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @Override
    public String toString() {
        return "id: "+Integer.toString(this.getId())+" fecha:"+this.getFecha()+", monto:"+Double.toString(this.getMonto())+", productoOrigen: ["+this.getProductoDestino().toString()+"], productoDestino: ["+this.getProductoDestino().toString()+"], movimientoTipo: ["+this.getMovimientoTipo().toString()+"], referenciaPago:["+this.getReferenciaPago().toString()+"], concepto:"+this.getConcepto();
    }  
    
}
