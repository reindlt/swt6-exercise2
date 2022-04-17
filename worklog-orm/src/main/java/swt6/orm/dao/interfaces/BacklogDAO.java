package swt6.orm.dao.interfaces;

import swt6.orm.domain.Backlog;
import swt6.orm.domain.UserStory;

public interface BacklogDAO extends BaseDAO<Backlog> {
    Backlog addUserStories(Backlog backlog, UserStory... userStories);

    Backlog removeUserStory(Backlog backlog, UserStory userStory);
}
