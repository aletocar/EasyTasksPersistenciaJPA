/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytasks.persistencia.persistencia;

import com.easytasks.persistencia.entidades.Contexto;
import com.easytasks.persistencia.entidades.Etiqueta;
import com.easytasks.persistencia.entidades.Proyecto;
import com.easytasks.persistencia.entidades.Tarea;
import com.easytasks.persistencia.entidades.Token;
import com.easytasks.persistencia.entidades.Usuario;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author alejandrotocar
 */
@Local
public interface PersistenciaSBLocal {

    //void agregarContexto(Contexto c);
    void agregarEtiqueta(Etiqueta e);

    void agregarProyecto(Proyecto p);

    void agregarTarea(Tarea t);

    void agregarUsuario(Usuario u) throws EntityExistsException;

    void borrarEtiqueta(Etiqueta e);

    void borrarProyecto(Proyecto p);

    void borrarTarea(Tarea t);

    void borrarUsuario(Usuario u) throws EntityNotFoundException;

    Contexto buscarContexto(String nombre);

    Etiqueta buscarEtiqueta(Long id);

    Etiqueta buscarEtiqueta(String nombre);

    Proyecto buscarProyecto(Long id);

    Proyecto buscarProyecto(String nombre, Usuario u);

    Tarea buscarTarea(Long id);

    Tarea buscarTarea(String nombre, Proyecto proyecto) throws EJBException;

    List<Tarea> buscarSubtareasDeTarea(String nombre, Proyecto p) throws EJBException;

    List<Tarea> buscarTareasCompletadasPorUsuario(Usuario u) throws EJBException;

    List<Tarea> buscarTareasCompletadasResponsable(Usuario u) throws EJBException;

    List<Usuario> buscarResponsablesDeTarea(String nombre, Proyecto p) throws EJBException;

    Usuario buscarUsuario(Long id) throws EJBException;

    Usuario buscarUsuario(String username);

    List<Usuario> buscarContactos(Usuario u);

    List<Usuario> buscarUsuariosDeProyecto(String nombre, Usuario responsable) throws EJBException;

    void modificarEtiqueta(Etiqueta e);

    void modificarProyecto(Proyecto p);

    void modificarTarea(Tarea t);

    List<Tarea> buscarTareasDeProyecto(Proyecto p) throws EJBException;

    void modificarUsuario(Usuario u);

    void agregarToken(Token t);

    void borrarToken(Token t);

    Token buscarToken(String t) throws EJBException;

}
