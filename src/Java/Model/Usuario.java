/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Guillermo
 */
public class Usuario extends Persona  {
    private String usuario;
    private String clave;

    //Constructor
    public Usuario() {
        super(0, "");
        this.setUsuario("");
        this.setClave("");       
    }
    public Usuario(String usuario, String nombre, String clave) {
        this.setUsuario(usuario);
        this.setNombre(nombre);
        this.setClave(clave);
    }
    public Usuario(String usuario, String clave) {
        this.setUsuario(usuario);
        this.setClave(clave);
    }
    public Usuario(String usuario, String clave, int id, String nombre) {
        this.setUsuario(usuario);
        this.setClave(clave);
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
        return this.getId()+" "+this.getNombre()+""+this.getUsuario()+" "+this.getClave();
    }
    
}
