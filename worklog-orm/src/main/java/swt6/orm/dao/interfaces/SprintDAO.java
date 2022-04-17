package swt6.orm.dao.interfaces;

import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;

import java.util.List;

public interface SprintDAO extends BaseDAO<Sprint> {
    Sprint addUserStories(Sprint sprint, UserStory... userStories);

    Sprint removeUserStory(Sprint sprint, UserStory userStory);

    List<Sprint> getActiveSprints();
}
