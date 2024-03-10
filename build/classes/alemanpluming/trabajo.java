/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

/**
 *
 * @author dsoto
 */
public class trabajo {
    public String detalles;
    public String trabajo;
    public double cargo_viaje;
    public double total;

    public trabajo() {
    }

    public trabajo(String detalles, String trabajo, double cargo_viaje, double total) {
        this.detalles = detalles;
        this.trabajo = trabajo;
        this.cargo_viaje = cargo_viaje;
        this.total = total;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public double getCargo_viaje() {
        return cargo_viaje;
    }

    public void setCargo_viaje(double cargo_viaje) {
        this.cargo_viaje = cargo_viaje;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
