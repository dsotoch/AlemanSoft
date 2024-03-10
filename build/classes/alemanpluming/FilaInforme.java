/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

/**
 *
 * @author dsoto
 */
public class FilaInforme {

    private String cantidad;
    private String material;
    private String costo;
    private String total;

    // Constructor, getters y setters según sea necesario...
    // Por ejemplo:
    public FilaInforme(String cantidad, String material, String costo, String total) {
        this.cantidad = cantidad;
        this.material = material;
        this.costo = costo;
        this.total = total;
    }

    public FilaInforme() {
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
}
