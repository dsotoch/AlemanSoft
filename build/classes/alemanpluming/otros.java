/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

import java.util.Date;

/**
 *
 * @author dsoto
 */
public class otros {
    private String trabajador;
    private Date fecha;
    private String comentarios;
    private String terminos;

    public otros() {
    }

    public otros(String trabajador, Date fecha, String comentarios, String terminos) {
        this.trabajador = trabajador;
        this.fecha = fecha;
        this.comentarios = comentarios;
        this.terminos = terminos;
    }

    public String getTerminos() {
        return terminos;
    }

    public void setTerminos(String terminos) {
        this.terminos = terminos;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
