/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytasks.persistencia.persistencia;

import com.easytasks.persistencia.entidades.*;
import java.security.DigestException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

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
        } catch (Exception e) {
            throw new EntityNotFoundException();//TODO: Revisar
        }
    }

    private void borrarTokens(Usuario u) {
        try {
            List<Token> tokens = em.createNamedQuery("buscarTokensDeUsuario", Token.class).setParameter("idUsuario", u).getResultList();
            for (int i = tokens.size() - 1; i >= 0; i--) {
                borrarToken(tokens.get(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            em.persist(p);
        } catch (Exception e) {
            System.out.println("Excepcion al agregar proyecto");
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
        } catch (Exception e) {

        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void borrarProyecto(Proyecto p) {
        try {
            if (p.getId() != null) {
                p = em.merge(p);
                //borrarTokens(u);
                em.remove(p);
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {//Catcheo si se rompe la base de datos. o errores mas especificos
            throw e;
        } catch (Exception ee) {
            System.out.println(ee.getMessage());
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
    public Proyecto buscarProyecto(String nombre) throws EJBException{
        try {
            return (Proyecto) em.createNamedQuery("buscarProyecto").setParameter("nombreP", nombre).getSingleResult();
        } catch (NoResultException e) {
            throw new EJBException();
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Tarea ">
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void agregarTarea(Tarea t) {
        try {
            em.persist(t);
        } catch (Exception e) {
            System.out.println("Excepcion al agregar la tarea");
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
        } catch (Exception e) {

        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void borrarTarea(Tarea t) {
        try {
            if (t.getId() != null) {
                em.remove(t);
            } else {
                //TODO: tirar el mensaje de que no existe en la db
            }
        } catch (Exception e) {
            System.out.println("No se pudo eliminar la tarea");
            //TODO: Mejorar esto
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

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Contexto ">
    @Override
    public void agregarContexto(Contexto c) {
        try {
            em.persist(c);
        } catch (Exception e) {
            System.out.println("Excepcion al agregar el contexto");
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
    public Contexto buscarContexto(Long id) {
        try {
            return em.find(Contexto.class, id);
        } catch (Exception e) {
            //TODO: Mejorar esto
            return null;

        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Etiqueta ">
    @Override
    public void agregarEtiqueta(Etiqueta e) {
        try {
            em.persist(e);
        } catch (Exception ex) {
            System.out.println("Excepcion al agregar la etiqueta");
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
        } catch (Exception ex) {

        }
    }

    @Override
    public void borrarEtiqueta(Etiqueta e) {
        try {
            if (e.getId() != null) {
                em.remove(e);
            } else {
                //TODO: tirar el mensaje de que no existe en la db
            }
        } catch (Exception ex) {
            System.out.println("No se pudo eliminar el etiqueta");
            //TODO: Mejorar esto
        }
    }

    @Override
    public Etiqueta buscarEtiqueta(Long id) {
        try {
            return em.find(Etiqueta.class, id);
        } catch (Exception ex) {
            //TODO: Mejorar esto
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
