package com.easytasks.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@NamedQueries({
    @NamedQuery(name = "buscarProyecto",
            query = "select p from Proyecto p where p.nombre = :nombreP and p.responsable = :responsable"
    ),
    @NamedQuery(name = "buscarUsuariosDeProyecto",
            query = "select u from Proyecto p join p.usuarios u where p.nombre = :nombreP and p.responsable = :responsable"
    )
})
@Entity
@Table(name = "Proyectos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre", "responsable"})
})
public class Proyecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    @NotNull
    //TODO: Chequear qué cascade hay que poner
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsable")
    private Usuario responsable;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Contexto contexto;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "proyecto")
    private List<Tarea> tareas;

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

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Proyecto() {
        this.usuarios = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }

    public Proyecto(String nombre, Usuario responsable, Date fechaInicio, Date fechaFin, Contexto contexto) {
        this.nombre = nombre;
        this.responsable = responsable;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.contexto = contexto;
        this.usuarios = new ArrayList<>();
        this.tareas = new ArrayList<>();
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
