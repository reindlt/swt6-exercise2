package swt6.orm.dao;

import swt6.orm.dao.interfaces.BaseDAO;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BaseDAOImpl<T> implements BaseDAO<T> {

    private final Class<T> clazz;

    public BaseDAOImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T insert(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            entity = em.merge(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return entity;
    }

    @Override
    public void delete(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            em.remove(em.contains(entity) ? entity : em.merge(entity));
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public T getById(long id) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            T result = em.find(clazz, id);
            JpaUtil.commit();
            return result;
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public List<T> getAll() {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> rootEntry = cq.from(clazz);
            CriteriaQuery<T> all = cq.select(rootEntry);
            TypedQuery<T> allQuery = em.createQuery(all);
            List<T> result = allQuery.getResultList();
            JpaUtil.commit();
            return result;
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }
}
