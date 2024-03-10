/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author dsoto
 */
public class factura {

    private clientr cliente;
    private materiales materiales;
    private trabajo trabajo;
    private otros otros;
    private costos costos;
    private String factura;
    private ArrayList<materiales> materialesList;

    public ArrayList<materiales> getMaterialesList() {
        return materialesList;
    }

    public void setMaterialesList(ArrayList<materiales> materialesList) {
        this.materialesList = materialesList;
    }

    public factura(clientr cliente, trabajo trabajo, otros otros, costos costos, String factura, ArrayList<materiales> materialesList) {
        this.cliente = cliente;
        this.trabajo = trabajo;
        this.otros = otros;
        this.costos = costos;
        this.factura = factura;
        this.materialesList = materialesList;
    }

    public factura(clientr cliente, materiales materiales, trabajo trabajo, otros otros, costos costos, String factura) {
        this.cliente = cliente;
        this.materiales = materiales;
        this.trabajo = trabajo;
        this.otros = otros;
        this.costos = costos;
        this.factura = factura;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public factura() {
    }

    public clientr getCliente() {
        return cliente;
    }

    public void setCliente(clientr cliente) {
        this.cliente = cliente;
    }

    public materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(materiales materiales) {
        this.materiales = materiales;
    }

    public trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public otros getOtros() {
        return otros;
    }

    public void setOtros(otros otros) {
        this.otros = otros;
    }

    public costos getCostos() {
        return costos;
    }

    public void setCostos(costos costos) {
        this.costos = costos;
    }

    public factura guardarFactura() {
        factura factura = new factura(this.cliente, this.materiales, this.trabajo, this.otros, this.costos, this.factura);
        return factura;
    }

}
