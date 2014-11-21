package com.easytasks.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;


@NamedQueries({
    @NamedQuery(name="buscarUsuario",
    query="select u from Usuario u where u.nombreUsuario = :nombreU"
)})
@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String nombreUsuario;

    private String mail;

    private String contraseña;

    @ManyToMany()
    private List<Usuario> contactos;

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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public List<Usuario> getContactos() {
        return contactos;
    }

    public void setContactos(List<Usuario> contactos) {
        this.contactos = contactos;
    }

    public Usuario() {
        this.contactos = new ArrayList<Usuario>();
    }

    public Usuario(String nombre, String nombreUsuario, String mail, String contraseña) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.mail = mail;
        this.contraseña = contraseña;
        this.contactos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombreUsuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) obj;
        return other.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.intValue() : null;
    }

}
