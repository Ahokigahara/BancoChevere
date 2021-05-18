package Model;

public class MovimientoTipo {

    private Integer id;
    private String nombre;

    public MovimientoTipo() {
        this.id = 0;
        this.nombre = "";
    }

    public MovimientoTipo(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Metdos set

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // Metodos get

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
     @Override
    public String toString() {
        return "id: "+Integer.toString(this.getId())+", nombre: "+this.getNombre();
    }
    
}
