package swt6.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.base.DataSourceDBUnitTest;
import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.UserStory;

public class SprintDAOTests extends DataSourceDBUnitTest {

    private final SprintDAO sprintDAO = new SprintDAOImpl();
    private final UserStoryDAO userStoryDAO = new UserStoryDAOImpl();

    @Test
    public void testAddUserStories() {
        var sprint = sprintDAO.getById(1);
        var userStory = new UserStory("Titel", "Beschreibung", 10);
        sprint = sprintDAO.addUserStories(sprint, userStory);
        Assert.assertTrue(sprint.getUserStories().contains(userStory));
    }

    @Test
    public void testRemoveUserStory() {
        var sprint = sprintDAO.getById(1);
        var userStory = userStoryDAO.getById(1);
        sprint = sprintDAO.removeUserStory(sprint, userStory);
        Assert.assertFalse(sprint.getUserStories().contains(userStory));
    }

    @Test
    public void testGetActiveSprints() {
        var activeSprints = sprintDAO.getActiveSprints();
        Assert.assertEquals(1, activeSprints.size());
    }
}
