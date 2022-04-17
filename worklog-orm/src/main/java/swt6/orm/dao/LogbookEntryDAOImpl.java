package swt6.orm.dao;

import swt6.orm.dao.interfaces.LogbookEntryDAO;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Task;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class LogbookEntryDAOImpl extends BaseDAOImpl<LogbookEntry> implements LogbookEntryDAO {

    public LogbookEntryDAOImpl() {
        super(LogbookEntry.class);
    }

    @Override
    public LogbookEntry getLatestForTask(Task task) {
        LogbookEntry result;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            result = em.createQuery(
                    "select lbe " +
                            "from LogbookEntry lbe " +
                            "where lbe.task = :task " +
                            "and lbe.endTime = (select max(lbe.endTime) from LogbookEntry lbe where lbe.task = :task)",
                            LogbookEntry.class
            ).setParameter("task", task).getSingleResult();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return result;
    }
}
