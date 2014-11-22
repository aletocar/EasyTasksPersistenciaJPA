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
public class TransformadorADtoSB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public DtoUsuario transformarUsuario(Usuario u) {
        DtoUsuario ret = new DtoUsuario();
        ret.setContraseña(u.getContraseña());
        ret.setId(u.getId());
        ret.setMail(u.getMail());
        ret.setNombre(u.getNombre());
        ret.setNombreUsuario(u.getNombreUsuario());
        List<DtoUsuario> listaContactos = new ArrayList<>();
        for (Usuario usuario : u.getContactos()) {
            DtoUsuario dto = transformarUsuarioLista(usuario);
            listaContactos.add(dto);
        }
        ret.setContactos(listaContactos);
        List<DtoUsuario> listaSoyContactoDe = new ArrayList<>();
        for (Usuario usuario : u.getSoyContactoDe()) {
            DtoUsuario dto = transformarUsuarioLista(usuario);
            listaSoyContactoDe.add(dto);
        }
        ret.setSoyContactoDe(listaSoyContactoDe);
        return ret;
    }

    private DtoUsuario transformarUsuarioLista(Usuario u) {
        DtoUsuario ret = new DtoUsuario();
        ret.setId(u.getId());
        ret.setContraseña(u.getContraseña());
        ret.setMail(u.getMail());
        ret.setNombre(u.getNombre());
        ret.setNombreUsuario(u.getNombreUsuario());
        ret.setContactos(null);
        ret.setSoyContactoDe(null);

        return ret;
    }

    public DtoEtiqueta transformarEtiqueta(Etiqueta e) {
        DtoEtiqueta ret = new DtoEtiqueta();
        ret.setId(e.getId());
        ret.setNombre(e.getNombre());
        return ret;
    }

    public DtoContexto transformarContexto(Contexto c) {
        DtoContexto ret = new DtoContexto();
        ret.setId(c.getId());
        ret.setDescripcion(c.getDescripcion());
        ret.setNombre(c.getNombre());
        return ret;
    }

    public DtoProyecto transformarProyecto(Proyecto p) {
        DtoProyecto ret = new DtoProyecto();
        ret.setId(p.getId());
        ret.setContexto(transformarContexto(p.getContexto()));
        ret.setFechaFin(p.getFechaFin());
        ret.setFechaInicio(p.getFechaInicio());
        ret.setNombre(p.getNombre());
        ret.setResponsable(transformarUsuarioLista(p.getResponsable()));
        List<DtoUsuario> listaUsuarios = new ArrayList<>();
        for (Usuario u : p.getUsuarios()) {
            DtoUsuario dto = transformarUsuarioLista(u);
            listaUsuarios.add(dto);
        }
        ret.setUsuarios(listaUsuarios);
        List<DtoTarea> listaTareas = new ArrayList<>();
        for (Tarea t : p.getTareas()) {
            DtoTarea dto = transformarTareaLista(t);
            listaTareas.add(dto);
        }
        ret.setTareas(listaTareas);
        return ret;
    }

    public DtoProyecto transformarProyectoLista(Proyecto p) {
        DtoProyecto ret = new DtoProyecto();
        ret.setId(p.getId());
        ret.setContexto(transformarContexto(p.getContexto()));
        ret.setFechaFin(p.getFechaFin());
        ret.setFechaInicio(p.getFechaInicio());
        ret.setNombre(p.getNombre());
        ret.setResponsable(transformarUsuarioLista(p.getResponsable()));
        ret.setUsuarios(null);
        ret.setTareas(null);
        return ret;
    }

    public DtoTarea transformarTarea(Tarea t) {
        DtoTarea ret = new DtoTarea();
        ret.setId(t.getId());
        ret.setCompletado(t.isCompletado());
        ret.setFechaCreacion(t.getFechaCreacion());
        ret.setFechaLimite(t.getFechaLimite());
        List<DtoTarea> listaRealizables = new ArrayList<>();
        for (Tarea r : t.getSubtareas()) {
            DtoTarea dto = transformarTareaLista(r);
            listaRealizables.add(dto);
        }
        ret.setSubtareas(listaRealizables);
        ret.setNombre(t.getNombre());
        ret.setPrioridad(t.getPrioridad());
        List<DtoEtiqueta> listaEtiquetas = new ArrayList<>();
        List<DtoUsuario> listaUsuarios = new ArrayList<>();
        for (Etiqueta e : t.getListaEtiquetas()) {
            DtoEtiqueta dto = transformarEtiqueta(e);
            listaEtiquetas.add(dto);
        }
        for (Usuario u : t.getListaResponsables()) {
            DtoUsuario dto = transformarUsuarioLista(u);
            listaUsuarios.add(dto);
        }
        ret.setListaEtiquetas(listaEtiquetas);
        ret.setListaResponsables(listaUsuarios);
        DtoProyecto p = transformarProyectoLista(t.getProyecto());
        ret.setProyecto(p);
        return ret;
    }

    public DtoTarea transformarTareaLista(Tarea t) {
        DtoTarea ret = new DtoTarea();
        ret.setId(t.getId());
        ret.setCompletado(t.isCompletado());
        ret.setFechaCreacion(t.getFechaCreacion());
        ret.setFechaLimite(t.getFechaLimite());
        ret.setSubtareas(null);
        ret.setNombre(t.getNombre());
        ret.setPrioridad(t.getPrioridad());
        DtoProyecto p = transformarProyectoLista(t.getProyecto());
        ret.setProyecto(p);
        ret.setListaEtiquetas(null);
        ret.setListaResponsables(null);
        return ret;
    }

}
