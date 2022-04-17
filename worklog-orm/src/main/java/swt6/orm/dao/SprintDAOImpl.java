package swt6.orm.dao;

import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class SprintDAOImpl extends BaseDAOImpl<Sprint> implements SprintDAO {

    public SprintDAOImpl() {
        super(Sprint.class);
    }

    @Override
    public Sprint addUserStories(Sprint sprint, UserStory... userStories) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            sprint = em.merge(sprint);
            for (UserStory story : userStories) {
                sprint.addUserStory(story);
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return sprint;
    }

    @Override
    public Sprint removeUserStory(Sprint sprint, UserStory userStory) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            sprint = em.merge(sprint);
            sprint.getUserStories().remove(userStory);
            userStory.setSprint(null);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return sprint;
    }

    @Override
    public List<Sprint> getActiveSprints() {
        List<Sprint> results;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(
                    "select s " +
                            "from Sprint s " +
                            "where startDate < current_date " +
                            "and endDate > current_date",
                    Sprint.class
            ).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }

}
