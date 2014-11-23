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
        /*try{
         EntityManagerFactory eFactory = Persistence.createEntityManagerFactory("EasyTasksPersistenciaJPAPU");
         em = eFactory.createEntityManager();
         }catch(NullPointerException n){
         System.out.println("Excepcion nula al crear la Persistencia");
         }catch(Exception e){
         System.out.println("Excepcion no controlada al crear la persistencia");
         }*/
    }

    // <editor-fold defaultstate="collapsed" desc=" Usuario ">
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarUsuario(Usuario u) throws EntityExistsException {
        try {
            em.persist(u);
        } catch (PersistenceException e) {
            throw new EntityExistsException();
        } catch (Exception e) {
            throw new EntityExistsException();
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

    private void borrarTokens(Usuario u) {
        try {
            List<Token> tokens = em.createNamedQuery("buscarTokensDeUsuario", Token.class).setParameter("idUsuario", u).getResultList();
            for (int i = tokens.size() - 1; i >= 0; i--) {
                borrarToken(tokens.get(i));
            }
        } catch (Exception e) {
            throw new EntityNotFoundException("Error Inesperado! Contacte a un administrador del sistema");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void borrarUsuario(Usuario u) throws EntityNotFoundException {
        try {
            if (u.getId() != null) {
                u = em.merge(u);
                borrarTokens(u);
                em.remove(u);
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {//Catcheo si se rompe la base de datos. o errores mas especificos
            throw e;
        } catch (Exception p) {
            System.out.println(p.getMessage());
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Usuario buscarUsuario(Long id) {
        try {
            return em.find(Usuario.class, id);
        } catch (EntityNotFoundException e) {
            //TODO: Mejorar esto
            return null;

        }
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
            throw new EntityExistsException("Ya existe un proyecto " + p.getNombre() + " para este usuario");
        } catch (Exception e) {
            throw new EntityExistsException("Error Inesperado");
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
    public void borrarProyecto(Proyecto p) {
        try {
            if (p.getId() != null) {
                p = em.merge(p);
                em.remove(p);
            } else {
                throw new EntityNotFoundException("No existe el proyecto a borrar");
            }
        } catch (EntityNotFoundException e) {//Catcheo si se rompe la base de datos. o errores mas especificos
            throw e;
        } catch (Exception ee) {
            throw new EJBException("Error Inesperado");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Proyecto buscarProyecto(Long id) {
        try {
            return em.find(Proyecto.class, id);
        } catch (Exception e) {
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
            throw new EJBException("No se encontró el proyecto indicado");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Usuario> buscarUsuariosDeProyecto(String nombre, Usuario responsable) throws EJBException {
        try {
            return em.createNamedQuery("buscarUsuariosDeProyecto", Usuario.class).setParameter("nombreP", nombre).setParameter("responsable", responsable).getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro el proyecto indicado");
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Tarea ">
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarTarea(Tarea t) {
        try {
            for (Etiqueta etiqueta : t.getListaEtiquetas()) {
                etiqueta = buscarEtiqueta(etiqueta.getNombre());
                if(etiqueta==null){
                    em.persist(etiqueta);
                }
            }
            em.persist(t);
        } catch (PersistenceException e) {
            throw new EntityExistsException("Ya existe una tarea " + t.getNombre() + " para este proyecto");
        } catch (Exception e) {
            throw new EntityExistsException("Error Inesperado");
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
        } catch (EntityNotFoundException ex) {//Catcheo si se rompe la base de datos. o errores mas especificos
            throw ex;
        } catch (Exception ex) {
            throw new EJBException("Error Inesperado");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Tarea buscarTarea(Long id) {
        try {
            return em.find(Tarea.class, id);
        } catch (Exception e) {
            //TODO: Mejorar esto
            return null;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Tarea> buscarTareasDeProyecto(Proyecto p) throws EJBException {
        try {
            return em.createNamedQuery("buscarTareasDeProyecto", Tarea.class).setParameter("proyecto", p)
                    .getResultList();
        } catch (NoResultException n) {
            throw new EJBException("No se encontro el proyecto indicado");
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Contexto ">
    /*@Override
     public void agregarContexto(Contexto c) {
     try {
     em.persist(c);
     } catch (PersistenceException e) {
     throw new EntityExistsException("Ya existe un contexto " + c.getNombre()+" para este usuario");
     } catch (Exception e) {
     throw new EntityExistsException("Error Inesperado");
     }
     }

     @Override
     public void modificarContexto(Contexto c) {
     try {
     if (c.getId() != null) {
     em.merge(c);
     } else {
     em.persist(c);
     }
     } catch (Exception e) {

     }
     }

     @Override
     public void borrarContexto(Contexto c) {
     try {
     if (c.getId() != null) {
     em.remove(c);
     } else {
     //TODO: tirar el mensaje de que no existe en la db
     }
     } catch (Exception e) {
     System.out.println("No se pudo eliminar el contexto");
     //TODO: Mejorar esto
     }
     }

     @Override
     public Contexto
     buscarContexto(Long id) {
     try {
     return em.find(Contexto.class, id);
     } catch (Exception e) {
     //TODO: Mejorar esto
     return null;

     }
     }*/
    @Override
    public Contexto buscarContexto(String nombre) {
        try {
            return (Contexto) em.createNamedQuery("buscarContexto").setParameter("nombreC", nombre).getSingleResult();
        } catch (NoResultException e) {
            return null;
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
