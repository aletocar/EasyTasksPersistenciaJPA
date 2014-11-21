/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.easytasks.persistencia.persistencia;

import com.easytasks.persistencia.entidades.Actividad;
import com.easytasks.persistencia.entidades.Contexto;
import com.easytasks.persistencia.entidades.Etiqueta;
import com.easytasks.persistencia.entidades.Proyecto;
import com.easytasks.persistencia.entidades.Tarea;
import com.easytasks.persistencia.entidades.Usuario;
import javax.ejb.Local;
import javax.persistence.EntityExistsException;

/**
 *
 * @author alejandrotocar
 */
@Local
public interface PersistenciaSBLocal {

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Actividad ">
    void agregarActividad(Actividad a);

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Contexto ">
    void agregarContexto(Contexto c);

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Etiqueta ">
    void agregarEtiqueta(Etiqueta e);

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Proyecto ">
    void agregarProyecto(Proyecto p);

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Tarea ">
    void agregarTarea(Tarea t);

    // <editor-fold defaultstate="collapsed" desc=" Usuario ">
    void agregarUsuario(Usuario u) throws EntityExistsException;

    void borrarActividad(Actividad a);

    void borrarContexto(Contexto c);

    void borrarEtiqueta(Etiqueta e);

    void borrarProyecto(Proyecto p);

    void borrarTarea(Tarea t);

    void borrarUsuario(Usuario u);

    Actividad buscarActividad(Long id);

    Contexto buscarContexto(Long id);

    Etiqueta buscarEtiqueta(Long id);
    // </editor-fold>

    Proyecto buscarProyecto(Long id);

    Tarea buscarTarea(Long id);

    Usuario buscarUsuario(Long id);

    Usuario buscarUsuario(String username);

    void modificarActividad(Actividad a);

    void modificarContexto(Contexto c);

    void modificarEtiqueta(Etiqueta e);

    void modificarProyecto(Proyecto p);

    void modificarTarea(Tarea t);

    void modificarUsuario(Usuario u);
    
}
