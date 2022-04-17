package swt6.orm.dao;

import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class UserStoryDAOImpl extends BaseDAOImpl<UserStory> implements UserStoryDAO {

    public UserStoryDAOImpl() {
        super(UserStory.class);
    }

    @Override
    public UserStory addTasks(UserStory story, Task... tasks) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            story = em.merge(story);
            for (Task task : tasks) {
                story.addTask(task);
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return story;
    }

    @Override
    public UserStory removeTask(UserStory story, Task task) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            story = em.merge(story);
            story.getTasks().remove(task);
            task.setUserStory(null);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return story;
    }

    @Override
    public List<UserStory> getForSprint(Sprint sprint) {
        List<UserStory> results;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(
                    "select us " +
                            "from UserStory us " +
                            "where us.sprint = :sprint",
                    UserStory.class
            ).setParameter("sprint", sprint).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }
}
