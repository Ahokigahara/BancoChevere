package Model;

public class ReferenciaPago {

    private int id;
    private String referencia;
    private double monto;
    private Tercero tercero;

    public ReferenciaPago() {
        this.id = 0;
        this.referencia = "";
        this.monto = 0.0;
        this.tercero = new Tercero();
    }

    public ReferenciaPago(int id, String referencia, double monto, Tercero tercero) {
        this.id = id;
        this.referencia = referencia;
        this.monto = monto;
        this.tercero = tercero;
    }

    // Metodos set

    public void setId(int id) {
        this.id = id;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }
    
    
    // Metodos get

    public int getId() {
        return id;
    }

    public String getReferencia() {
        return referencia;
    }

    public double getMonto() {
        return monto;
    }

    public Tercero getTercero() {
        return tercero;
    }

    @Override
    public String toString(){
        return "id: "+Integer.toString(this.getId())+", refreencia: "+this.getReferencia()+", monto: "+Double.toString(this.getMonto())+", tercero: ["+this.getTercero().toString()+"]";
    }    
    
}
