package Model;

public class Producto {

    private Integer id;
    private Double saldo;
    private ProductoTipo productoTipo;
    private Tercero tercero;

    public Producto() {
        this.id = 0;
        this.saldo = 0.0;
        this.productoTipo = new ProductoTipo();
        this.tercero = new Tercero();
    }

    public Producto(Integer id, Double saldo, ProductoTipo productoTipo, Tercero tercero) {
        this.id = id;
        this.saldo = saldo;
        this.productoTipo = productoTipo;
        this.tercero = tercero;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void setProductoTipo(ProductoTipo productoTipo) {
        this.productoTipo = productoTipo;
    }

    // Metdos set
    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    // Metodos get

    public Integer getId() {
        return id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public ProductoTipo getProductoTipo() {
        return productoTipo;
    }

    public Tercero getTercero() {
        return tercero;
    }

    
     @Override
    public String toString() {
        return "id: "+Integer.toString(this.getId())+", saldo: "+Double.toString(this.getSaldo())+", productoTipo: ["+this.getProductoTipo().toString()+"], tercero: ["+this.getTercero().toString()+"]";
    }
    
}
