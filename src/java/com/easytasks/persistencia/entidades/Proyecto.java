package com.easytasks.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
public class Proyecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    //TODO: Chequear qué cascade hay que poner
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario responsable;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;

    @ManyToOne(fetch = FetchType.EAGER)
    private Contexto contexto;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Contexto getContexto() {
        return contexto;
    }

    public void setContexto(Contexto contexto) {
        this.contexto = contexto;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Proyecto() {
        this.usuarios = new ArrayList<>();
    }

    public Proyecto(String nombre, Usuario responsable, Date fechaInicio, Date fechaFin, Contexto contexto) {
        this.nombre = nombre;
        this.responsable = responsable;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.contexto = contexto;
        this.usuarios = new ArrayList<Usuario>();
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) obj;
        return other.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.intValue() : null;
    }
}
