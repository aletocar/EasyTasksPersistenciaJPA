package com.easytasks.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@Entity
public class Realizable implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    private int prioridad;

    private boolean completado;
    
    @OneToMany
    private List<Usuario> listaResponsables;

    @OneToMany
    private List<Etiqueta> listaEtiquetas;
   
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
    
    public Realizable() {
        listaEtiquetas = new ArrayList<Etiqueta>();
        listaResponsables = new ArrayList<Usuario>();
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Realizable)) {
            return false;
        }
        Realizable other = (Realizable) obj;
        return other.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.intValue() : null;
    }
}
