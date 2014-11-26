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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author alejandrotocar
 */
@NamedQueries({
    @NamedQuery(name = "buscarTareasDeProyecto",
            query = "select t from Tarea t where t.proyecto = :proyecto"
    ),
    @NamedQuery(name = "buscarSubTareasDeTarea",
            query = "select t.subtareas from Tarea t where t.nombre = :nombreT and t.proyecto = :proyecto"
    ),
    @NamedQuery(name = "buscarResponsablesDeTarea",
            query = "select t.listaResponsables from Tarea t where t.nombre = :nombreT and t.proyecto = :proyecto"
    ),
    @NamedQuery(name = "buscarTarea",
            query = "select t from Tarea t where t.proyecto = :proyecto and t.nombre = :nombreT"
    ),
    @NamedQuery(name = "buscarTareasRealizadasDeUsuario",
            query = "select t from Tarea t where t.realizador = :usuario"
    ),
    @NamedQuery(name = "buscarTareasRealizadasResponsable",
            query = "select t from Tarea t where t.completado = true and t.proyecto in (select p from Proyecto p where p.responsable = :usuario)"
    ),
    @NamedQuery(name = "buscarTareasPendientes",
            query = "select t from Tarea t where t.completado = false and t.listaResponsables = :usuario"
    )
})
@Entity
@Table(name="Tareas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre", "proyecto"})
})
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "proyecto")
    private Proyecto proyecto;

    @Column(name = "nombre")
    private String nombre;

    private int prioridad;

    private boolean completado;
    @ManyToOne(optional = true)
    private Usuario realizador;

    @OneToMany
    private List<Usuario> listaResponsables;

    @OneToMany
    private List<Etiqueta> listaEtiquetas;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaLimite;

    //TODO: Chequear que este cascade este bien
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tarea> subtareas;

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

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public List<Usuario> getListaResponsables() {
        return listaResponsables;
    }

    public void setListaResponsables(List<Usuario> listaResponsables) {
        this.listaResponsables = listaResponsables;
    }

    public List<Etiqueta> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<Etiqueta> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }

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

    public List<Tarea> getSubtareas() {
        return subtareas;
    }

    public void setSubtareas(List<Tarea> Subtareas) {
        this.subtareas = Subtareas;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Usuario getRealizador() {
        return realizador;
    }

    public void setRealizador(Usuario realizador) {
        this.realizador = realizador;
    }

    public Tarea() {
        subtareas = new ArrayList<>();
        listaEtiquetas = new ArrayList<>();
        listaResponsables = new ArrayList<>();
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
