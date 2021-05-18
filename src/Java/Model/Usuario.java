package Model;

public class Usuario extends Tercero  {
    private String usuario;
    private String clave;

    //Constructor
    public Usuario() {
       super();
       this.usuario = "";
       this.clave = "";
    }
    public Usuario(String usuario, String clave, int id, String documento, DocumentoTipo documentoTipo, String documentoExpedicion, String nombres, String apellidos, String direccion, String telefono, String email) {
        super(id, documento, documentoTipo, documentoExpedicion, nombres, apellidos, direccion, telefono, email);
        this.usuario = usuario;
        this.clave = clave;        
    }


    // Metdoos set
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
   

    // Metodos get
    public String getUsuario() {
        return usuario;
    }
    public String getClave() {
        return clave;
    }


    @Override
    public String toString() {
        return "Usuario: "+this.getUsuario()+", clave: "+this.getClave()+", id: "+Integer.toString(this.getId())+", documento: "+this.getDocumento()+", documentoTipo: ["+this.getDocumentoTipo().toString()+"], documentoExpedicion: "+this.getDocumentoExpedicion()+", nombres: "+this.getNombres()+", apellidos: "+this.getApellidos()+", direccion: "+this.getDireccion()+", telefono: "+this.getTelefono() +", email: "+this.getEmail();
    }
    
}
