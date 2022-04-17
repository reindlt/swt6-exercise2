package swt6.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory emFactory;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

    private static synchronized EntityManagerFactory getEntityManagerFactory(boolean isTestInstance) {
        if (emFactory == null)
            emFactory = isTestInstance ?
                    Persistence.createEntityManagerFactory("WorklogTestPU") :
                    Persistence.createEntityManagerFactory("WorklogPU");
        return emFactory;
    }

    private static synchronized EntityManager getEntityManager() {
        if (emThread.get() == null)
            emThread.set(getEntityManagerFactory(false).createEntityManager());

        return emThread.get();
    }

    private static synchronized EntityManager getEntityManager(boolean isTestInstance) {
        if (emThread.get() == null)
            emThread.set(getEntityManagerFactory(isTestInstance).createEntityManager());

        return emThread.get();
    }

    private static synchronized void closeEntityManager() {
        if (emThread.get() != null) {
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        EntityManager em = getEntityManager(false);
        EntityTransaction tx = em.getTransaction();
        if (!tx.isActive()) tx.begin();
        return em;
    }

    public static synchronized EntityManager getTransactedTestEntityManager() {
        EntityManager em = getEntityManager(true);
        EntityTransaction tx = em.getTransaction();
        if (!tx.isActive()) tx.begin();
        return em;
    }

    public static synchronized void commit() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if (tx.isActive()) tx.commit();
        closeEntityManager();
    }

    public static synchronized void rollback() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if (tx.isActive()) tx.rollback();
        closeEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }
}
