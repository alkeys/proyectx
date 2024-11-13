package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


import java.util.List;


public abstract class AbstractDataPersistence<T>{

    public abstract EntityManager getEntityManager();

    private final Class<T> TipoDato;

    public AbstractDataPersistence(Class<T> TipoDato){
        this.TipoDato = TipoDato;
    }

    public void create(final T entity) throws  IllegalStateException, IllegalArgumentException {
        EntityManager em = null;

        if(entity==null){
            throw new IllegalArgumentException("Parametro no valido: entity is null");
        }
        try {
            em = getEntityManager();
            if(em == null){
                throw new IllegalStateException("Error al acceder al repositorio");
            }
            em.persist(entity);

        }catch (Exception ex){
            throw new  IllegalStateException("Error al acceder al repositorio",ex);
        }

    }


    public T findById(final Object id) throws IllegalArgumentException, IllegalStateException{
        EntityManager em = null;

        if(id==null){
            throw new IllegalArgumentException("Parametro no valido: ID");
        }
        try {
            em = getEntityManager();
            if(em == null){
                throw new IllegalStateException("Error al acceder al repositorio");
            }

        }catch (Exception ex){
            throw new  IllegalStateException("Error al acceder al repositorio",ex);
        }
        return em.find(TipoDato, id);
    }

    public void delete(final T entity) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = null;

        if (entity == null) {
            throw new IllegalArgumentException("Parametro no valido: entity is null");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }

            // Asegúrate de que la entidad esté gestionada antes de eliminarla
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);

        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public T update(final T entity) throws IllegalArgumentException, IllegalStateException{
        EntityManager em = null;

        if(entity==null){
            throw new IllegalArgumentException("Parametro no valido: entity is null");
        }
        try {
            em = getEntityManager();
            if(em == null){
                throw new IllegalStateException("Error al acceder al repositorio");
            }
            return em.merge(entity);

        }catch (Exception ex){
            throw new  IllegalStateException("Error al acceder al repositorio",ex);
        }
    }

    public List<T> findRange(int first, int pageSize) throws IllegalArgumentException, IllegalStateException{
        if(first < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Parametros no validos");
        }
        EntityManager em = null;
        try{
            em = getEntityManager();
            if (em == null){
                throw new IllegalStateException("Error al acceder al repositorio");
            }
        }catch(Exception ex){
            throw new IllegalStateException("Error al acceder al repositorio");
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(TipoDato);
        Root<T> raiz = cq.from(TipoDato);
        cq.select(raiz);
        TypedQuery q = em.createQuery(cq);

        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }


    public int count() throws IllegalStateException{
        EntityManager em = null;
        try{
            em = getEntityManager();
            if (em == null){
                throw new IllegalStateException("Error al acceder al repositorio");
            }
        }catch(Exception ex){
            throw new IllegalStateException("Error al acceder al repositorio",ex);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> raiz = cq.from(TipoDato);
        cq.select(cb.count(raiz));
        TypedQuery q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public String imprimirCarnet(){ return "FM22010";}
}


