/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytasks.persistencia.transformadores;

import com.easytasks.dataTransferObjects.*;
import com.easytasks.persistencia.entidades.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author alejandrotocar
 */
@Stateless
@LocalBean
public class TransformadorAEntidadSB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Usuario transformarUsuario(DtoUsuario u) {
        Usuario ret = new Usuario();
        ret.setContraseña(u.getContraseña());
        ret.setMail(u.getMail());
        ret.setNombre(u.getNombre());
        ret.setNombreUsuario(u.getNombreUsuario());
        List<Usuario> listaContactos = new ArrayList<>();
        for (DtoUsuario usuario : u.getContactos()) {
            Usuario us = transformarUsuarioLista(usuario);
            listaContactos.add(us);
        }
        ret.setContactos(listaContactos);
        List<Usuario> listaSoyContactoDe = new ArrayList<>();
        for (DtoUsuario usuario : u.getSoyContactoDe()) {
            Usuario us = transformarUsuarioLista(usuario);
            listaSoyContactoDe.add(us);
        }
        ret.setSoyContactoDe(listaSoyContactoDe);
        return ret;
    }

    private Usuario transformarUsuarioLista(DtoUsuario u) {
        Usuario ret = new Usuario();
        ret.setContraseña(u.getContraseña());
        ret.setMail(u.getMail());
        ret.setNombre(u.getNombre());
        ret.setNombreUsuario(u.getNombreUsuario());
        ret.setContactos(null);
        ret.setSoyContactoDe(null);
        return ret;
    }

    public Etiqueta transformarEtiqueta(DtoEtiqueta e) {
        Etiqueta ret = new Etiqueta();
        ret.setNombre(e.getNombre());
        return ret;
    }

    public Contexto transformarContexto(DtoContexto c) {
        Contexto ret = new Contexto();
        ret.setDescripcion(c.getDescripcion());
        ret.setNombre(c.getNombre());
        return ret;
    }

    public Proyecto transformarProyecto(DtoProyecto p) {
        Proyecto ret = new Proyecto();
        ret.setContexto(transformarContexto(p.getContexto()));
        ret.setFechaFin(p.getFechaFin());
        ret.setFechaInicio(p.getFechaInicio());
        ret.setNombre(p.getNombre());
        ret.setResponsable(transformarUsuarioLista(p.getResponsable()));
        List<Usuario> listaUsuarios = new ArrayList<>();
        for (DtoUsuario u : p.getUsuarios()) {
            Usuario us = transformarUsuarioLista(u);
            listaUsuarios.add(us);
        }
        ret.setUsuarios(listaUsuarios);
        List<Tarea> listaTareas = new ArrayList<>();
        for (DtoTarea t : p.getTareas()) {
            Tarea ta = transformarTareaLista(t);
            listaTareas.add(ta);
        }
        ret.setTareas(listaTareas);
        return ret;
    }

    public Proyecto transformarProyectoLista(DtoProyecto p) {
        Proyecto ret = new Proyecto();
        ret.setContexto(transformarContexto(p.getContexto()));
        ret.setFechaFin(p.getFechaFin());
        ret.setFechaInicio(p.getFechaInicio());
        ret.setNombre(p.getNombre());
        ret.setResponsable(transformarUsuarioLista(p.getResponsable()));
        ret.setUsuarios(null);
        ret.setTareas(null);
        return ret;
    }

    public Tarea transformarTarea(DtoTarea t) {
        Tarea ret = new Tarea();
        ret.setCompletado(t.isCompletado());
        ret.setFechaCreacion(t.getFechaCreacion());
        ret.setFechaLimite(t.getFechaLimite());
        List<Tarea> listaRealizables = new ArrayList<>();
        for (DtoTarea r : t.getSubtareas()) {
            Tarea dto = transformarTareaLista(r);
            listaRealizables.add(dto);
        }
        ret.setSubtareas(listaRealizables);
        ret.setNombre(t.getNombre());
        ret.setPrioridad(t.getPrioridad());
        List<Etiqueta> listaEtiquetas = new ArrayList<>();
        List<Usuario> listaUsuarios = new ArrayList<>();
        for (DtoEtiqueta e : t.getListaEtiquetas()) {
            Etiqueta dto = transformarEtiqueta(e);
            listaEtiquetas.add(dto);
        }
        for (DtoUsuario u : t.getListaResponsables()) {
            Usuario dto = transformarUsuarioLista(u);
            listaUsuarios.add(dto);
        }
        ret.setListaEtiquetas(listaEtiquetas);
        ret.setListaResponsables(listaUsuarios);
        Proyecto p = transformarProyectoLista(t.getProyecto());
        ret.setProyecto(p);
        return ret;
    }

    public Tarea transformarTareaLista(DtoTarea t) {
        Tarea ret = new Tarea();
        ret.setCompletado(t.isCompletado());
        ret.setFechaCreacion(t.getFechaCreacion());
        ret.setFechaLimite(t.getFechaLimite());
        ret.setSubtareas(null);
        ret.setNombre(t.getNombre());
        ret.setPrioridad(t.getPrioridad());
        Proyecto p = transformarProyectoLista(t.getProyecto());
        ret.setProyecto(p);
        ret.setListaEtiquetas(null);
        ret.setListaResponsables(null);
        return ret;
    }
}
