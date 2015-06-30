/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Movimientos;

/**
 *
 * @author skynet
 */
public class MovimientosJpaController implements Serializable {

    public MovimientosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movimientos movimientos) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(movimientos);
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

    public void edit(Movimientos movimientos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            movimientos = em.merge(movimientos);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimientos.getId();
                if (findMovimientos(id) == null) {
                    throw new NonexistentEntityException("The movimientos with id " + id + " no longer exists.");
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
            Movimientos movimientos;
            try {
                movimientos = em.getReference(Movimientos.class, id);
                movimientos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimientos with id " + id + " no longer exists.", enfe);
            }
            em.remove(movimientos);
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

    public List<Movimientos> findMovimientosEntities() {
        return findMovimientosEntities(true, -1, -1);
    }

    public List<Movimientos> findMovimientosEntities(int maxResults, int firstResult) {
        return findMovimientosEntities(false, maxResults, firstResult);
    }

    private List<Movimientos> findMovimientosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movimientos.class));
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

    public Movimientos findMovimientos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimientos.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movimientos> rt = cq.from(Movimientos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Movimientos getMovimientosLast() {
        EntityManager em = getEntityManager();
        try {
            String sqlCommand = "SELECT m FROM Movimientos m WHERE m.movimientosPK.partida = :partida";
            Query q = em.createQuery(sqlCommand);
            q.setParameter("partida",2);
            q.setMaxResults(1);

            Object o = q.getSingleResult();
            
            
            
            return ((Movimientos) q.getSingleResult());
        } finally {
            em.close();
        }
    }
    
     public Boolean crear_movimiento(int user, int partida, int columna) {

        EntityManager em = getEntityManager();
        try {
            String sqlCommand = "INSERT INTO Movimientos VALUES(" + user + "," + partida + ", 0, " + columna + ");";
            Query q = em.createNativeQuery(sqlCommand);

            return true;
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
    }
    
}
