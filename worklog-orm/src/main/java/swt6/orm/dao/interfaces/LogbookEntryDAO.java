package swt6.orm.dao.interfaces;

import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Task;

public interface LogbookEntryDAO extends BaseDAO<LogbookEntry> {
    LogbookEntry getLatestForTask(Task task);
}
