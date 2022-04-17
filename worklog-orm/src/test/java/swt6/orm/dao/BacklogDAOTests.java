package swt6.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.base.DataSourceDBUnitTest;
import swt6.orm.dao.interfaces.BacklogDAO;
import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.UserStory;

public class BacklogDAOTests extends DataSourceDBUnitTest {

    private final BacklogDAO backlogDAO = new BacklogDAOImpl();
    private final UserStoryDAO userStoryDAO = new UserStoryDAOImpl();

    @Test
    public void testAddUserStory() {
        var backlog = backlogDAO.getById(1);
        var userStory = new UserStory("Titel", "Beschreibung", 10);
        backlog = backlogDAO.addUserStories(backlog, userStory);
        Assert.assertTrue(backlog.getUserStories().contains(userStory));
    }

    @Test
    public void testRemoveUserStory() {
        var backlog = backlogDAO.getById(1);
        var userStory = userStoryDAO.getById(1);
        backlog = backlogDAO.removeUserStory(backlog, userStory);
        Assert.assertFalse(backlog.getUserStories().contains(userStory));
    }
}
