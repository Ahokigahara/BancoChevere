/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.Dto;

/**
 *
 * @author Megan
 */
public class ProductoDTO {
    public int id;
    public double saldo;
    public TerceroDTO id_Tercero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public TerceroDTO getId_Tercero() {
        return id_Tercero;
    }

    public void setId_Tercero(TerceroDTO id_Tercero) {
        this.id_Tercero = id_Tercero;
    }
    
    
}
