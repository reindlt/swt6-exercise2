package swt6.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.base.DataSourceDBUnitTest;
import swt6.orm.dao.interfaces.FeatureDAO;
import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.domain.Status;

public class FeatureDAOTests extends DataSourceDBUnitTest {
    private final FeatureDAO featureDAO = new FeatureDAOImpl();
    private final SprintDAO sprintDAO = new SprintDAOImpl();

    @Test
    public void testGetWithStatus() {
        var features = featureDAO.getWithStatus(Status.done);
        Assert.assertEquals(5, features.size());
    }

    @Test
    public void testGetForSprint() {
        var sprint = sprintDAO.getById(6);
        var features = featureDAO.getForSprint(sprint);
        Assert.assertEquals(4, features.size());
    }
}
