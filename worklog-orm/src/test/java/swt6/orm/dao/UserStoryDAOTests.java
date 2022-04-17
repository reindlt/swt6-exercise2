package swt6.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.base.DataSourceDBUnitTest;
import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.dao.interfaces.TaskDAO;
import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.Feature;
import swt6.orm.domain.Status;

import java.time.LocalDate;

public class UserStoryDAOTests extends DataSourceDBUnitTest {

    private final UserStoryDAO userStoryDAO = new UserStoryDAOImpl();
    private final TaskDAO taskDAO = new TaskDAOImpl();
    private final SprintDAO sprintDAO = new SprintDAOImpl();

    @Test
    public void testAddTasks() {
        var userStory = userStoryDAO.getById(1);
        var task = new Feature("Titel", "Beschreibung", 10, LocalDate.now(), Status.done);
        userStory = userStoryDAO.addTasks(userStory, task);
        Assert.assertTrue(userStory.getTasks().contains(task));
    }

    @Test
    public void testRemoveTask() {
        var userStory = userStoryDAO.getById(1);
        var task = taskDAO.getById(1);
        userStory = userStoryDAO.removeTask(userStory, task);
        Assert.assertFalse(userStory.getTasks().contains(userStory));
    }

    @Test
    public void testGetForSprint() {
        var sprint = sprintDAO.getById(1);
        var userStories = userStoryDAO.getForSprint(sprint);
        Assert.assertEquals(1, userStories.size());
        Assert.assertEquals("Beschreibung 1", userStories.get(0).getDescription());
    }
}
