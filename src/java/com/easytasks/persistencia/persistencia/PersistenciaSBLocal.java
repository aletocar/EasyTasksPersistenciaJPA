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
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

/**
 *
 * @author alejandrotocar
 */
@Local
public interface PersistenciaSBLocal {

    void agregarContexto(Contexto c);

    void agregarEtiqueta(Etiqueta e);

    void agregarProyecto(Proyecto p);

    void agregarTarea(Tarea t);

    void agregarUsuario(Usuario u) throws EntityExistsException;

    void borrarContexto(Contexto c);

    void borrarEtiqueta(Etiqueta e);

    void borrarProyecto(Proyecto p);

    void borrarTarea(Tarea t);

    void borrarUsuario(Usuario u) throws EntityNotFoundException;

    Contexto buscarContexto(Long id);

    Etiqueta buscarEtiqueta(Long id);

    Proyecto buscarProyecto(Long id);

    Proyecto buscarProyecto(String nombre);

    Tarea buscarTarea(Long id);

    Usuario buscarUsuario(Long id) throws EJBException;

    Usuario buscarUsuario(String username);

    void modificarContexto(Contexto c);

    void modificarEtiqueta(Etiqueta e);

    void modificarProyecto(Proyecto p);

    void modificarTarea(Tarea t);

    void modificarUsuario(Usuario u);

    void agregarToken(Token t);

    void borrarToken(Token t);

    Token buscarToken(String t) throws EJBException;
}
