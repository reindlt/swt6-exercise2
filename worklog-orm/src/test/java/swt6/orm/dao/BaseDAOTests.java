package swt6.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import swt6.orm.dao.base.DataSourceDBUnitTest;
import swt6.orm.dao.interfaces.BaseDAO;
import swt6.orm.domain.Sprint;

import java.time.LocalDate;

public class BaseDAOTests extends DataSourceDBUnitTest {

    private final BaseDAO<Sprint> baseDAO = new BaseDAOImpl<>(Sprint.class);

    @Test
    public void testGetById() {
        var sprint = baseDAO.getById(1);
        Assert.assertEquals(Long.valueOf(1), sprint.getProject().getId());
        Assert.assertEquals(LocalDate.of(2022, 01, 01), sprint.getStartDate());
        Assert.assertEquals(LocalDate.of(2022, 01, 10), sprint.getEndDate());
    }

    @Test
    public void testGetAll() {
        var numOfSprints = baseDAO.getAll().size();
        Assert.assertEquals(6, numOfSprints);
    }

    @Test
    public void testInsert() {
        int sizeBeforeInsert = baseDAO.getAll().size();
        baseDAO.insert(new Sprint(LocalDate.now(), LocalDate.now()));
        int sizeAfterInsert = baseDAO.getAll().size();
        Assert.assertEquals(sizeBeforeInsert + 1, sizeAfterInsert);
    }

    public void testUpdate() {
        var sprint = baseDAO.getById(1);
        var date = LocalDate.now();
        sprint.setStartDate(date);
        baseDAO.update(sprint);
        Assert.assertEquals(date, baseDAO.getById(1).getStartDate());
    }

    @Test
    public void testDeleteEntity() {
        int sizeBeforeInsert = baseDAO.getAll().size();
        var sprint = new Sprint(LocalDate.now(), LocalDate.now());
        sprint = baseDAO.insert(sprint);
        int sizeAfterInsert = baseDAO.getAll().size();
        baseDAO.delete(sprint);
        int sizeAfterDelete = baseDAO.getAll().size();
        sprint = baseDAO.getById(sprint.getId());
        Assert.assertNull(sprint);
        Assert.assertEquals(sizeBeforeInsert + 1, sizeAfterInsert);
        Assert.assertEquals(sizeBeforeInsert, sizeAfterDelete);
    }

}
