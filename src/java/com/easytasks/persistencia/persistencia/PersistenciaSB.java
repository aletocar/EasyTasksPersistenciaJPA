/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytasks.persistencia.persistencia;

import com.easytasks.persistencia.entidades.*;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author alumnoFI
 */
@Stateless
public class PersistenciaSB implements PersistenciaSBLocal {

    @PersistenceContext(unitName = "EasyTasksPersistenciaJPAPU")
    private EntityManager em;

    public PersistenciaSB() {

    }

    // <editor-fold defaultstate="collapsed" desc=" Usuario ">
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarUsuario(Usuario u) throws EntityExistsException {
        try {
            em.persist(u);
        } catch (PersistenceException e) {
            throw new EntityExistsException("Ya existe el usuario", e);
        } catch (Exception e) {
            throw new EntityExistsException("Ocurrió un problema inesperado al agregar un usuario. Por favor intente nuevamente", e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void modificarUsuario(Usuario u) {
        try {
            if (u.getId() != null) {
                em.merge(u);
            } else {
                em.persist(u);
            }
        } catch (PersistenceException e) {
            throw new EntityNotFoundException("No se encontró el usuario a modificar");
        } catch (Exception e) {
            throw new EntityNotFoundException("Error Inesperado");
        }
    }

    private void borrarTokens(Usuario u) throws EntityNotFoundException {
        List<Token> tokens = em.createNamedQuery("buscarTokensDeUsuario", Token.class).setParameter("idUsuario", u).getResultList();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            borrarToken(tokens.get(i));
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void borrarUsuario(Usuario u) throws EntityNotFoundException {
        if (u.getId() != null) {
            u = em.merge(u);
            borrarTokens(u);
            em.remove(u);
        } else {
            throw new EntityNotFoundException("No se encontró el usuario a borrar");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Usuario buscarUsuario(Long id) throws EntityNotFoundException {
        return em.find(Usuario.class, id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Usuario buscarUsuario(String username) throws EJBException {
        try {
            return (Usuario) em.createNamedQuery("buscarUsuario").setParameter("nombreU", username).getSingleResult();
        } catch (NoResultException e) {
            throw new EJBException("No se encontró el usuario " + username, e);
        }
    }

    @Override
    public List<Usuario> buscarContactos(Usuario u) {
        try {
            return em.createNamedQuery("buscarContactos", Usuario.class).setParameter("usuario", u).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Proyecto ">
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarProyecto(Proyecto p) {
        try {
            if (p.getContexto().getId() == null) {
                em.persist(p.getContexto());
            }
            em.persist(p);
        } catch (PersistenceException e) {
            throw new EntityExistsException("Ya existe un proyecto " + p.getNombre() + " para este usuario", e);
        } catch (Exception e) {
            throw new EntityExistsException("Error Inesperado", e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void modificarProyecto(Proyecto p) {
        try {
            if (p.getId() != null) {
                em.merge(p);
            } else {
                em.persist(p);
            }
        } catch (PersistenceException e) {
            throw new EntityNotFoundException("No se encontró el proyecto a modificar");
        } catch (Exception e) {
            throw new EntityNotFoundException("Error Inesperado");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void borrarProyecto(Proyecto p) throws EJBException{
        try {
            if (p.getId() != null) {
                p = em.merge(p);
                em.remove(p);
            } else {
                throw new EntityNotFoundException("No existe el proyecto a borrar");
            }
        } catch (EntityNotFoundException e) {
            throw new EJBException("Error Inesperado", e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Proyecto buscarProyecto(Long id) {
        try {
            return em.find(Proyecto.class, id);
        } catch (PersistenceException e) {
            //TODO: Mejorar esto
            return null;

        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Proyecto buscarProyecto(String nombre, Usuario responsable) throws EJBException {
        try {
            return (Proyecto) em.createNamedQuery("buscarProyecto").setParameter("nombreP", nombre).setParameter("responsable", responsable).getSingleResult();
        } catch (NoResultException e) {
            throw new EJBException("No se encontró el proyecto indicado", e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Usuario> buscarUsuariosDeProyecto(String nombre, Usuario responsable) throws EJBException {
        try {
            return em.createNamedQuery("buscarUsuariosDeProyecto", Usuario.class).setParameter("nombreP", nombre).setParameter("responsable", responsable).getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro el proyecto indicado", n);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Tarea ">
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarTarea(Tarea t) {
        try {
            for (Etiqueta etiqueta : t.getListaEtiquetas()) {
                Etiqueta etiqueta2 = buscarEtiqueta(etiqueta.getNombre());
                if (etiqueta2 == null) {
                    em.persist(etiqueta);
                } else {
                    etiqueta.setId(etiqueta2.getId());
                }
            }
            em.persist(t);
        } catch (PersistenceException e) {
            throw new EntityExistsException("Ya existe una tarea " + t.getNombre() + " para este proyecto", e);
        } catch (Exception e) {
            throw new EntityExistsException("Error Inesperado al agregar la tarea", e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void modificarTarea(Tarea t) {
        try {
            if (t.getId() != null) {
                em.merge(t);
            } else {
                em.persist(t);
            }
        } catch (PersistenceException e) {
            throw new EntityNotFoundException("No se encontró la tarea a modificar");
        } catch (Exception e) {
            throw new EntityNotFoundException("Error Inesperado");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void borrarTarea(Tarea t) {
        try {
            if (t.getId() != null) {
                t = em.merge(t);
                em.remove(t);
            } else {
                throw new EntityNotFoundException("No existe la tarea a borrar");
            }
        } catch (EntityNotFoundException ex) {
            throw new EJBException(ex.getMessage(), ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Tarea buscarTarea(Long id) {
            return em.find(Tarea.class, id);//Si no encuentra nada devuelve null
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Tarea buscarTarea(String nombre, Proyecto proyecto) throws EJBException {
        try {
            return (Tarea) em.createNamedQuery("buscarTarea").setParameter("nombreT", nombre).setParameter("proyecto", proyecto).getSingleResult();
        } catch (NoResultException e) {
            throw new EJBException("No se encontró la tarea indicada", e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Tarea> buscarTareasDeProyecto(Proyecto p) throws EJBException {
        try {
            return em.createNamedQuery("buscarTareasDeProyecto", Tarea.class).setParameter("proyecto", p)
                    .getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro el proyecto indicado", n);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Tarea> buscarSubtareasDeTarea(String nombre, Proyecto p) throws EJBException {
        try {
            return em.createNamedQuery("buscarSubTareasDeTarea", Tarea.class).setParameter("proyecto", p).setParameter("nombreT", nombre).getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro la tarea indicada", n);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Usuario> buscarResponsablesDeTarea(String nombre, Proyecto p) throws EJBException {
        try {
            return em.createNamedQuery("buscarResponsablesDeTarea", Usuario.class).setParameter("proyecto", p).setParameter("nombreT", nombre)
                    .getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro la tarea indicada", n);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Tarea> buscarTareasCompletadasPorUsuario(Usuario u) throws EJBException {
        try {
            return em.createNamedQuery("buscarTareasRealizadasDeUsuario", Tarea.class).setParameter("usuario", u)
                    .getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro ninguna tarea indicada", n);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Tarea> buscarTareasCompletadasResponsable(Usuario u) throws EJBException {
        try {
            return em.createNamedQuery("buscarTareasRealizadasResponsable", Tarea.class).setParameter("usuario", u)
                    .getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro ninguna tarea indicada",n);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Contexto ">
    @Override
    public Contexto buscarContexto(String nombre) {
        try {
            return (Contexto) em.createNamedQuery("buscarContexto").setParameter("nombreC", nombre).getSingleResult();
        } catch (NoResultException e) {
            return null; //Para estas consultas, no me interesa guardar la excepcion
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Etiqueta ">
    @Override
    public void agregarEtiqueta(Etiqueta e) {
        try {
            em.persist(e);
        } catch (PersistenceException ee) {
            throw new EntityExistsException("Ya existe una etiqueta " + e.getNombre());
        } catch (Exception ee) {
            throw new EntityExistsException("Error Inesperado");
        }
    }

    @Override
    public void modificarEtiqueta(Etiqueta e) {
        try {
            if (e.getId() != null) {
                em.merge(e);
            } else {
                em.persist(e);
            }
        } catch (PersistenceException ex) {
            throw new EntityNotFoundException("No se encontró la etiqueta a modificar");
        } catch (Exception ex) {
            throw new EntityNotFoundException("Error Inesperado");
        }
    }

    @Override
    public void borrarEtiqueta(Etiqueta e) {
        try {
            if (e.getId() != null) {
                e = em.merge(e);
                em.remove(e);
            } else {
                throw new EntityNotFoundException("No existe la etiqueta a borrar");
            }
        } catch (EntityNotFoundException ex) {//Catcheo si se rompe la base de datos. o errores mas especificos
            throw ex;
        } catch (Exception ex) {
            throw new EJBException("Error Inesperado");
        }
    }

    @Override
    public Etiqueta
            buscarEtiqueta(Long id) {
        try {
            return em.find(Etiqueta.class, id);
        } catch (Exception ex) {
            //TODO: Mejorar esto
            return null;

        }
    }

    @Override
    public Etiqueta buscarEtiqueta(String nombre) {
        try {
            return (Etiqueta) em.createNamedQuery("buscarEtiqueta").setParameter("nombreE", nombre).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Token ">
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarToken(Token t) {
        try {
            em.persist(t);
        } catch (PersistenceException p) {
            throw new EntityExistsException();
        } catch (Exception e) {
            throw new EntityExistsException();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void borrarToken(Token t) throws EntityNotFoundException {
        try {
            if (t.getId() != null) {
                em.remove(t);
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {//Catcheo si se rompe la base de datos. o errores mas especificos
            throw e;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Token buscarToken(String t) throws EJBException {
        try {
            return (Token) em.createNamedQuery("buscarToken").setParameter("token", t).getSingleResult();
        } catch (NoResultException e) {
            throw new EJBException("No se encontró el token " + t, e);
        }

    }

    //</editor-fold>
}
