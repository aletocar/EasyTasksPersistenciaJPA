package com.easytasks.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@NamedQueries({
    @NamedQuery(name="buscarUsuario",
    query="select u from Usuario u where u.nombreUsuario = :nombreU"
),
@NamedQuery(name="buscarContactos",
    query="select u.contactos from Usuario u where u = :usuario"
)})
@Entity
@Table(name="Usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    @Column(unique = true)
    private String nombreUsuario;

    private String mail;

    private String contraseña;

    @ManyToMany()
    @JoinTable(name="contactos", 
            joinColumns=@JoinColumn(name="idUsuario"),
            inverseJoinColumns = @JoinColumn(name="idContacto")
    )
    private List<Usuario> contactos;
    
    @ManyToMany()
    @JoinTable(name="contactos", 
            joinColumns=@JoinColumn(name="idContacto"),
            inverseJoinColumns = @JoinColumn(name="idUsuario")
    )
    private List<Usuario> soyContactoDe;
    
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
    
    public List<Usuario> getSoyContactoDe() {
        return soyContactoDe;
    }

    public void setSoyContactoDe(List<Usuario> soyContactoDe) {
        this.soyContactoDe = soyContactoDe;
    }

    public Usuario() {
        this.contactos = new ArrayList<Usuario>();
        this.soyContactoDe = new ArrayList<Usuario>();
    }

    public Usuario(String nombre, String nombreUsuario, String mail, String contraseña) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.mail = mail;
        this.contraseña = contraseña;
        this.contactos = new ArrayList<>();
        this.soyContactoDe = new ArrayList<>();
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
