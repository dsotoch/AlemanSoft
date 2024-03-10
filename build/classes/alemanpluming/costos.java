/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

/**
 *
 * @author dsoto
 */
public class costos {
    private double costo_materiales;
    private double impuesto;
    private double tarifa_madera;
    private double cargo_trabajo;
    private  double tarifa_cobros;
    private double tarifa_cobros_idroject;
    private double total;

    public costos() {
    }

    public costos(double costo_materiales, double impuesto, double tarifa_madera, double cargo_trabajo, double tarifa_cobros, double tarifa_cobros_idroject, double total) {
        this.costo_materiales = costo_materiales;
        this.impuesto = impuesto;
        this.tarifa_madera = tarifa_madera;
        this.cargo_trabajo = cargo_trabajo;
        this.tarifa_cobros = tarifa_cobros;
        this.tarifa_cobros_idroject = tarifa_cobros_idroject;
        this.total = total;
    }

    public double getCosto_materiales() {
        return costo_materiales;
    }

    public void setCosto_materiales(double costo_materiales) {
        this.costo_materiales = costo_materiales;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getTarifa_madera() {
        return tarifa_madera;
    }

    public void setTarifa_madera(double tarifa_madera) {
        this.tarifa_madera = tarifa_madera;
    }

    public double getCargo_trabajo() {
        return cargo_trabajo;
    }

    public void setCargo_trabajo(double cargo_trabajo) {
        this.cargo_trabajo = cargo_trabajo;
    }

    public double getTarifa_cobros() {
        return tarifa_cobros;
    }

    public void setTarifa_cobros(double tarifa_cobros) {
        this.tarifa_cobros = tarifa_cobros;
    }

    public double getTarifa_cobros_idroject() {
        return tarifa_cobros_idroject;
    }

    public void setTarifa_cobros_idroject(double tarifa_cobros_idroject) {
        this.tarifa_cobros_idroject = tarifa_cobros_idroject;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
