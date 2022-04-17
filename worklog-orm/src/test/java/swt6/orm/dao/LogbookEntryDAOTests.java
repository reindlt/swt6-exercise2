package swt6.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.base.DataSourceDBUnitTest;
import swt6.orm.dao.interfaces.LogbookEntryDAO;
import swt6.orm.dao.interfaces.TaskDAO;

public class LogbookEntryDAOTests extends DataSourceDBUnitTest {
    private final LogbookEntryDAO logbookEntryDAO = new LogbookEntryDAOImpl();
    private final TaskDAO taskDAO = new TaskDAOImpl();

    @Test
    public void testGetLatestForTask() {
        var task = taskDAO.getById(1);
        var logbookEntry = logbookEntryDAO.getLatestForTask(task);
        Assert.assertEquals("Aktivität 1", logbookEntry.getActivity());
    }
}
