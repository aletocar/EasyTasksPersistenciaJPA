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
    public Usuario transformarUsuario(DtoUsuario u){
        Usuario ret = new Usuario();
        ret.setContraseña(u.getContraseña());
        //ret.setId(u.getId());
        ret.setMail(u.getMail());
        ret.setNombre(u.getNombre());
        ret.setNombreUsuario(u.getNombreUsuario());
        List<Usuario> listaContactos = new ArrayList<Usuario>();
        for (DtoUsuario usuario : u.getContactos()) {
            Usuario us = transformarUsuarioLista(usuario);
            listaContactos.add(us);
        }
        ret.setContactos(listaContactos);
        return ret;
    }
    
    private Usuario transformarUsuarioLista(DtoUsuario u){
        Usuario ret = new Usuario();
        ret.setContraseña(u.getContraseña());
        ret.setId(u.getId());
        ret.setMail(u.getMail());
        ret.setNombre(u.getNombre());
        ret.setNombreUsuario(u.getNombreUsuario());
        ret.setContactos(null);
        
        return ret;
    }
    
    public Actividad transformarActividad(DtoActividad a){
        Actividad ret = new Actividad();
        ret.setAutomatica(a.isAutomatica());
        ret.setId(a.getId());
        ret.setNombre(a.getNombre());
        ret.setCompletado(a.isCompletado());
        ret.setNombre(a.getNombre());
        ret.setPrioridad(a.getPrioridad());
        List<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        for(DtoEtiqueta e: a.getListaEtiquetas()){
            Etiqueta eti = transformarEtiqueta(e);
            listaEtiquetas.add(eti);
        }
        for(DtoUsuario u: a.getListaResponsables()){
            Usuario us = transformarUsuarioLista(u);
            listaUsuarios.add(us);
        }
        ret.setListaEtiquetas(listaEtiquetas);
        ret.setListaResponsables(listaUsuarios);
        return ret;
    }
    
    public Etiqueta transformarEtiqueta(DtoEtiqueta e){
        Etiqueta ret = new Etiqueta();
        ret.setId(e.getId());
        ret.setNombre(e.getNombre());
        return ret;
    }
    
    public Contexto transformarContexto(DtoContexto c){
        Contexto ret = new Contexto();
        ret.setId(c.getId());
        ret.setDescripcion(c.getDescripcion());
        ret.setNombre(c.getNombre());
        return ret;
    }
    
    public Proyecto transformarProyecto(DtoProyecto p){
        Proyecto ret = new Proyecto();
        ret.setId(p.getId());        
        ret.setContexto(transformarContexto(p.getContexto()));
        ret.setFechaFin(p.getFechaFin());
        ret.setFechaInicio(p.getFechaInicio());
        ret.setNombre(p.getNombre());
        ret.setResponsable(transformarUsuarioLista(p.getResponsable()));
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        for(DtoUsuario u: p.getUsuarios()){
            Usuario us = transformarUsuarioLista(u);
            listaUsuarios.add(us);
        }
        ret.setUsuarios(listaUsuarios);
        return ret;
    }
    
    public Realizable transformarRealizable(DtoRealizable r){
        Realizable ret = new Realizable();
        ret.setId(r.getId());
        ret.setCompletado(r.isCompletado());
        ret.setNombre(r.getNombre());
        ret.setPrioridad(r.getPrioridad());
        List<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        for(DtoEtiqueta e: r.getListaEtiquetas()){
            Etiqueta eti = transformarEtiqueta(e);
            listaEtiquetas.add(eti);
        }
        for(DtoUsuario u: r.getListaResponsables()){
            Usuario us = transformarUsuarioLista(u);
            listaUsuarios.add(us);
        }
        ret.setListaEtiquetas(listaEtiquetas);
        ret.setListaResponsables(listaUsuarios);
        return ret;
    }
    
    public Tarea transformarTarea(DtoTarea t){
        Tarea ret = new Tarea();
        ret.setId(t.getId());
        ret.setCompletado(t.isCompletado());
        ret.setFechaCreacion(t.getFechaCreacion());
        ret.setFechaLimite(t.getFechaLimite());
        List<Realizable> listaRealizables = new ArrayList<Realizable>();
        for(DtoRealizable r: t.getSubtareas()){
            Realizable real = transformarRealizable(r);
            listaRealizables.add(real);
        }
        ret.setSubtareas(listaRealizables);
        ret.setNombre(t.getNombre());
        ret.setPrioridad(t.getPrioridad());
        List<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>();
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        for(DtoEtiqueta e: t.getListaEtiquetas()){
            Etiqueta eti = transformarEtiqueta(e);
            listaEtiquetas.add(eti);
        }
        for(DtoUsuario u: t.getListaResponsables()){
            Usuario us = transformarUsuarioLista(u);
            listaUsuarios.add(us);
        }
        ret.setListaEtiquetas(listaEtiquetas);
        ret.setListaResponsables(listaUsuarios);
        return ret;
    }
}
