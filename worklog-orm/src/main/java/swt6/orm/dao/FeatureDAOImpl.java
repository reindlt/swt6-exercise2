package swt6.orm.dao;

import swt6.orm.dao.interfaces.FeatureDAO;
import swt6.orm.domain.Feature;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Status;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class FeatureDAOImpl extends BaseDAOImpl<Feature> implements FeatureDAO {

    public FeatureDAOImpl() {
        super(Feature.class);
    }

    @Override
    public List<Feature> getWithStatus(Status status) {
        List<Feature> results;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(
                    "select f " +
                            "from Feature f " +
                            "where f.status = :status",
                    Feature.class
            ).setParameter("status", status).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }

    @Override
    public List<Feature> getForSprint(Sprint sprint) {
        List<Feature> results;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(
                    "select f " +
                            "from Feature f " +
                            "where f.userStory.sprint.id = :sprintId",
                    Feature.class
            ).setParameter("sprintId", sprint.getId()).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }
}
