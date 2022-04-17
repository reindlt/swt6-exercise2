package swt6.statistic;

import swt6.orm.dao.*;
import swt6.orm.dao.interfaces.*;
import swt6.orm.domain.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    private static final int BUGGY_RELEASES = 5;

    private final IssueDAO issueDAO = new IssueDAOImpl();
    private final UserStoryDAO userStoryDAO = new UserStoryDAOImpl();
    private final SprintDAO sprintDAO = new SprintDAOImpl();
    private final FeatureDAO featureDAO = new FeatureDAOImpl();
    private final LogbookEntryDAO logbookEntryDAO = new LogbookEntryDAOImpl();

    public void printLeast5BuggyReleases() {
        var issues = issueDAO.getAll();
        Map<String, Integer> releaseWithBugs = new HashMap<>();

        for (var issue : issues) {
            String releaseVersion = issue.getReleaseVersion();
            if (releaseWithBugs.containsKey(releaseVersion)) {
                releaseWithBugs.computeIfPresent(releaseVersion, (key, value) -> value + 1);
            } else {
                releaseWithBugs.put(releaseVersion, 1);
            }
        }

        var list = new ArrayList<>(releaseWithBugs.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue());

        System.out.println(BUGGY_RELEASES + " least buggy releases are:");
        for (var i = 0; i < list.size() && i < BUGGY_RELEASES; i++) {
            System.out.println("--> " + list.get(i).getKey() + "(" + list.get(i).getValue() + ")");
        }
    }

    public void printEstimationPointRatio() {
        var sprints = sprintDAO.getAll();

        long numOfSprints = 0;
        long sumStoryPoints = 0;
        long sumPoints = 0;
        double average = 0;

        for (var sprint : sprints) {
            numOfSprints++;

            var userStories = userStoryDAO.getForSprint(sprint);

            for (var userStory : userStories) {
                sumStoryPoints += userStory.getStoryPoints();

                for (var task : userStory.getTasks()) {
                    if ((task instanceof Issue && ((Issue) task).getFixedDate() != null)
                            || (task instanceof Feature && ((Feature) task).getStatus().equals(Status.done))) {
                        sumPoints += task.getPoints();
                    }
                }
            }

            average += sumPoints / (double)sumStoryPoints;
            sumPoints = 0;
            sumStoryPoints = 0;
        }

        System.out.println("Estimation point ratio is: " + average / numOfSprints);
    }

    public void printFeatureists() {
        HashSet<Employee> featureists = new HashSet<>();
        var features = featureDAO.getWithStatus(Status.done);

        for (var feature : features) {
            LogbookEntry logbookEntry = null;
            try {
                logbookEntry = logbookEntryDAO.getLatestForTask(feature);
                if (logbookEntry.getEndTime().toLocalDate().isBefore(feature.getDueDate())) {
                    featureists.add(logbookEntry.getEmployee());
                }
            } catch (NoResultException e) {
            }
        }

        if (featureists.size() > 0) {
            System.out.println("Featureists are:");
            for (var featureist : featureists) {
                System.out.println(featureist);
            }
        } else {
            System.out.println("There are no featureists");
        }

    }

    public void printFeatureStateForCurrentSprint() {
        var activeSprints = sprintDAO.getActiveSprints();

        for (var sprint : activeSprints) {
            var features = featureDAO.getForSprint(sprint);
            int numOfFeatures = features.size();

            if (numOfFeatures == 0) {
                System.out.println("No features in " + sprint);
                return;
            }

            int numOfDoneFeatures = 0;
            int numOfInProgressFeatures = 0;

            for (var feature : features) {
                if (feature.getStatus().equals(Status.done)) {
                    numOfDoneFeatures++;
                } else if (feature.getStatus().equals(Status.in_progress)) {
                    numOfInProgressFeatures++;
                }
            }
            System.out.println("Feature state for " + sprint + ":");
            System.out.println("Done: " + numOfDoneFeatures / (double) numOfFeatures);
            System.out.println("In progress: " + numOfInProgressFeatures / (double) numOfFeatures);
        }
    }
}
