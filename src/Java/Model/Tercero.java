package Model;

public class Tercero {
    private int id;
    private String documento;
    private DocumentoTipo documentoTipo;
    private String documentoExpedicion;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String email;    

    // Constructores
    public Tercero() {
        this.id=0;
        this.documento = "";
        this.documentoTipo = new DocumentoTipo();
        this.documentoExpedicion = "";
        this.nombres = "";
        this.apellidos = "";
        this.direccion = "";
        this.telefono = "";
        this.email = "";
        
    }   

    public Tercero(int id, String documento, DocumentoTipo documentoTipo, String documentoExpedicion, String nombres, String apellidos, String direccion, String telefono, String email) {
        this.id = id;
        this.documento = documento;
        this.documentoTipo = documentoTipo;
        this.documentoExpedicion = documentoExpedicion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    
    //Metodos set
    public void setId(int id) {
        this.id = id;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setDocumentoTipo(DocumentoTipo documentoTipo) {
        this.documentoTipo = documentoTipo;
    }

    public void setDocumentoExpedicion(String documentoExpedicion) {
        this.documentoExpedicion = documentoExpedicion;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    
    // Metodos get

    public int getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public DocumentoTipo getDocumentoTipo() {
        return documentoTipo;
    }

    public String getDocumentoExpedicion() {
        return documentoExpedicion;
    }
    
    public String getNombreCompleto(){
        return nombres+" "+apellidos;
    }
    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    
    @Override
    public String toString(){
        return "id: "+Integer.toString(this.getId())+", documento: "+this.getDocumento()+", documentoTipo: ["+this.getDocumentoTipo().toString()+"], documentoExpedicion: "+this.getDocumentoExpedicion()+", nombres: "+this.getNombres()+", apellidos: "+this.getApellidos()+", direccion: "+this.getDireccion()+", telefono: "+this.getTelefono() +", email: "+this.getEmail();
    }
}
