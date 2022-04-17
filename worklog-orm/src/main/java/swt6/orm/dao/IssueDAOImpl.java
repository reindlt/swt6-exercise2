package swt6.orm.dao;

import swt6.orm.dao.interfaces.IssueDAO;
import swt6.orm.domain.Issue;

public class IssueDAOImpl extends BaseDAOImpl<Issue> implements IssueDAO {
    public IssueDAOImpl() {
        super(Issue.class);
    }
}
