/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Partidas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Jugadores;

/**
 *
 * @author skynet
 */
public class JugadoresJpaController implements Serializable {

    public JugadoresJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugadores jugadores) throws RollbackFailureException, Exception {
        if (jugadores.getPartidasCollection() == null) {
            jugadores.setPartidasCollection(new ArrayList<Partidas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Partidas> attachedPartidasCollection = new ArrayList<Partidas>();
            for (Partidas partidasCollectionPartidasToAttach : jugadores.getPartidasCollection()) {
                partidasCollectionPartidasToAttach = em.getReference(partidasCollectionPartidasToAttach.getClass(), partidasCollectionPartidasToAttach.getId());
                attachedPartidasCollection.add(partidasCollectionPartidasToAttach);
            }
            jugadores.setPartidasCollection(attachedPartidasCollection);
            em.persist(jugadores);
            for (Partidas partidasCollectionPartidas : jugadores.getPartidasCollection()) {
                Jugadores oldJugador1OfPartidasCollectionPartidas = partidasCollectionPartidas.getJugador1();
                partidasCollectionPartidas.setJugador1(jugadores);
                partidasCollectionPartidas = em.merge(partidasCollectionPartidas);
                if (oldJugador1OfPartidasCollectionPartidas != null) {
                    oldJugador1OfPartidasCollectionPartidas.getPartidasCollection().remove(partidasCollectionPartidas);
                    oldJugador1OfPartidasCollectionPartidas = em.merge(oldJugador1OfPartidasCollectionPartidas);
                }
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

    public void edit(Jugadores jugadores) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jugadores persistentJugadores = em.find(Jugadores.class, jugadores.getId());
            Collection<Partidas> partidasCollectionOld = persistentJugadores.getPartidasCollection();
            Collection<Partidas> partidasCollectionNew = jugadores.getPartidasCollection();
            Collection<Partidas> attachedPartidasCollectionNew = new ArrayList<Partidas>();
            for (Partidas partidasCollectionNewPartidasToAttach : partidasCollectionNew) {
                partidasCollectionNewPartidasToAttach = em.getReference(partidasCollectionNewPartidasToAttach.getClass(), partidasCollectionNewPartidasToAttach.getId());
                attachedPartidasCollectionNew.add(partidasCollectionNewPartidasToAttach);
            }
            partidasCollectionNew = attachedPartidasCollectionNew;
            jugadores.setPartidasCollection(partidasCollectionNew);
            jugadores = em.merge(jugadores);
            for (Partidas partidasCollectionOldPartidas : partidasCollectionOld) {
                if (!partidasCollectionNew.contains(partidasCollectionOldPartidas)) {
                    partidasCollectionOldPartidas.setJugador1(null);
                    partidasCollectionOldPartidas = em.merge(partidasCollectionOldPartidas);
                }
            }
            for (Partidas partidasCollectionNewPartidas : partidasCollectionNew) {
                if (!partidasCollectionOld.contains(partidasCollectionNewPartidas)) {
                    Jugadores oldJugador1OfPartidasCollectionNewPartidas = partidasCollectionNewPartidas.getJugador1();
                    partidasCollectionNewPartidas.setJugador1(jugadores);
                    partidasCollectionNewPartidas = em.merge(partidasCollectionNewPartidas);
                    if (oldJugador1OfPartidasCollectionNewPartidas != null && !oldJugador1OfPartidasCollectionNewPartidas.equals(jugadores)) {
                        oldJugador1OfPartidasCollectionNewPartidas.getPartidasCollection().remove(partidasCollectionNewPartidas);
                        oldJugador1OfPartidasCollectionNewPartidas = em.merge(oldJugador1OfPartidasCollectionNewPartidas);
                    }
                }
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
                Integer id = jugadores.getId();
                if (findJugadores(id) == null) {
                    throw new NonexistentEntityException("The jugadores with id " + id + " no longer exists.");
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
            Jugadores jugadores;
            try {
                jugadores = em.getReference(Jugadores.class, id);
                jugadores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugadores with id " + id + " no longer exists.", enfe);
            }
            Collection<Partidas> partidasCollection = jugadores.getPartidasCollection();
            for (Partidas partidasCollectionPartidas : partidasCollection) {
                partidasCollectionPartidas.setJugador1(null);
                partidasCollectionPartidas = em.merge(partidasCollectionPartidas);
            }
            em.remove(jugadores);
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

    public List<Jugadores> findJugadoresEntities() {
        return findJugadoresEntities(true, -1, -1);
    }

    public List<Jugadores> findJugadoresEntities(int maxResults, int firstResult) {
        return findJugadoresEntities(false, maxResults, firstResult);
    }

    private List<Jugadores> findJugadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugadores.class));
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

    public Jugadores findJugadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugadores> rt = cq.from(Jugadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Boolean check_password(String username, String password){
       
        EntityManager em = getEntityManager();
        try {
           String sqlCommand = "SELECT check_password('"+username+"','"+password+"');" ;
           Query q = em.createNativeQuery(sqlCommand);
           
           
           return ((Boolean)q.getSingleResult());
        } finally {
            em.close();
        }
    }
    
   
    
    
    public int getJugadorID (String nombre) {
        EntityManager em = getEntityManager();
        try {
            String sqlCommand = "SELECT j.id FROM Jugadores j WHERE j.nombre = :nombre";
            Query q = em.createQuery(sqlCommand);
            q.setParameter("nombre", nombre);
            q.setMaxResults(1);
            
            return ((int) q.getSingleResult());
        } catch(Exception e){
            return -1;
        } finally {
            em.close();
        }
    }
    
    public Boolean existeJugador (String nombre) {
        EntityManager em = getEntityManager();
        try {
            String sqlCommand = "SELECT id FROM Jugadores WHERE nombre = '"+nombre+"';";
            Query q = em.createNativeQuery(sqlCommand);
            
            List result = q.getResultList();
            
            if(result.isEmpty()){
                return true;
            } else{
                return false;
            }
        }catch(Exception e){
                return false;
        } finally {
            em.close();
        }
    }
}
