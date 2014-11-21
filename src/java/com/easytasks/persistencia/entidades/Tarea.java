package com.easytasks.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class Tarea extends Realizable implements Serializable{

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaLimite;

    //TODO: Chequear que este cascade este bien
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Realizable> subtareas;

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public List<Realizable> getSubtareas() {
        return subtareas;
    }

    public void setSubtareas(List<Realizable> Subtareas) {
        this.subtareas = Subtareas;
    }

    public Tarea() {
        subtareas = new ArrayList<Realizable>();
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) obj;
        return other.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().intValue() : null;
    }
}
