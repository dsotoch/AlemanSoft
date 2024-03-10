package alemanpluming;

import java.util.ArrayList;

public class materiales {

    private String nombre;
    private double cantidad;
    private double costo;

    private ArrayList<materiales> arrayMateriales;

    public materiales(String nombre, double cantidad, double costo) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costo = costo;
        this.arrayMateriales = new ArrayList<>();
    }

    public materiales() {
        this.arrayMateriales = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        if (cantidad >= 0) {
            this.cantidad = cantidad;
        } else {
            // Manejo de valor no válido (puedes lanzar una excepción, mostrar un mensaje, etc.)
        }
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        if (costo >= 0) {
            this.costo = costo;
        } else {
            // Manejo de valor no válido
        }
    }

    public void GuardarMaterial(String nombre, double cantidad, double costo) {
        materiales material = new materiales(nombre, cantidad, costo);
        arrayMateriales.add(material);
    }

    public ArrayList<materiales> getMateriales() {
        return arrayMateriales;
    }
}
