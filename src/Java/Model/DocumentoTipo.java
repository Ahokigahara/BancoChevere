package Model;

public class DocumentoTipo {

    private String id;
    private String nombre;

    public DocumentoTipo() {
        this.id = "";
        this.nombre = "";
    }

    public DocumentoTipo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Metdos set

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // Metodos get

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
     @Override
    public String toString() {
        return "id: "+this.getId()+", nombre: "+this.getNombre();
    }
    
}
