/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Jugadores;
import model.Partidas;

/**
 *
 * @author skynet
 */
public class PartidasJpaController implements Serializable {

    public PartidasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partidas partidas) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jugadores jugador1 = partidas.getJugador1();
            if (jugador1 != null) {
                jugador1 = em.getReference(jugador1.getClass(), jugador1.getId());
                partidas.setJugador1(jugador1);
            }
            em.persist(partidas);
            if (jugador1 != null) {
                jugador1.getPartidasCollection().add(partidas);
                jugador1 = em.merge(jugador1);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partidas partidas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partidas persistentPartidas = em.find(Partidas.class, partidas.getId());
            Jugadores jugador1Old = persistentPartidas.getJugador1();
            Jugadores jugador1New = partidas.getJugador1();
            if (jugador1New != null) {
                jugador1New = em.getReference(jugador1New.getClass(), jugador1New.getId());
                partidas.setJugador1(jugador1New);
            }
            partidas = em.merge(partidas);
            if (jugador1Old != null && !jugador1Old.equals(jugador1New)) {
                jugador1Old.getPartidasCollection().remove(partidas);
                jugador1Old = em.merge(jugador1Old);
            }
            if (jugador1New != null && !jugador1New.equals(jugador1Old)) {
                jugador1New.getPartidasCollection().add(partidas);
                jugador1New = em.merge(jugador1New);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partidas.getId();
                if (findPartidas(id) == null) {
                    throw new NonexistentEntityException("The partidas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partidas partidas;
            try {
                partidas = em.getReference(Partidas.class, id);
                partidas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partidas with id " + id + " no longer exists.", enfe);
            }
            Jugadores jugador1 = partidas.getJugador1();
            if (jugador1 != null) {
                jugador1.getPartidasCollection().remove(partidas);
                jugador1 = em.merge(jugador1);
            }
            em.remove(partidas);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partidas> findPartidasEntities() {
        return findPartidasEntities(true, -1, -1);
    }

    public List<Partidas> findPartidasEntities(int maxResults, int firstResult) {
        return findPartidasEntities(false, maxResults, firstResult);
    }

    private List<Partidas> findPartidasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partidas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Partidas findPartidas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partidas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partidas> rt = cq.from(Partidas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Partidas> getPartidasTerminadas() {
        EntityManager em = getEntityManager();
        try {
            String sqlCommand = "SELECT p FROM Partidas p WHERE p.terminada = 1";
            Query q = em.createQuery(sqlCommand);
            //q.setParameter("t", 1);

            List<Partidas> l = new ArrayList<Partidas>();
            for(Object o:q.getResultList()){
                l.add((Partidas)o);
            }
            
            return (l);
        } finally {
            em.close();
        }
    }
    
}
