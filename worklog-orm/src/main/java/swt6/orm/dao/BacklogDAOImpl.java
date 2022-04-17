package swt6.orm.dao;

import swt6.orm.dao.interfaces.BacklogDAO;
import swt6.orm.domain.Backlog;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class BacklogDAOImpl extends BaseDAOImpl<Backlog> implements BacklogDAO {

    public BacklogDAOImpl() {
        super(Backlog.class);
    }

    @Override
    public Backlog addUserStories(Backlog backlog, UserStory... userStories) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            backlog = em.merge(backlog);
            for (UserStory userStory : userStories) {
                backlog.addUserStory(userStory);
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return backlog;
    }

    public Backlog removeUserStory(Backlog backlog, UserStory userStory) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            backlog = em.merge(backlog);
            backlog.getUserStories().remove(userStory);
            userStory.setBacklog(null);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return backlog;
    }
}
