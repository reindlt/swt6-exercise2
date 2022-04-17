package swt6.orm.dao;

import swt6.orm.dao.interfaces.TaskDAO;
import swt6.orm.domain.Task;

public class TaskDAOImpl extends BaseDAOImpl<Task> implements TaskDAO {

    public TaskDAOImpl() {
        super(Task.class);
    }
}
