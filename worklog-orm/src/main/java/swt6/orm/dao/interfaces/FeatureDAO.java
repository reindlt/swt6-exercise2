package swt6.orm.dao.interfaces;

import swt6.orm.domain.Feature;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Status;

import java.util.List;

public interface FeatureDAO extends BaseDAO<Feature> {
    List<Feature> getWithStatus(Status status);
    List<Feature> getForSprint(Sprint sprint);
}
