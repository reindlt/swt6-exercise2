package swt6.orm.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.orm.dao.interfaces.FeatureDAO;
import swt6.orm.dao.interfaces.IssueDAO;
import swt6.orm.dao.interfaces.LogbookEntryDAO;
import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.*;
import swt6.statistic.Statistics;
import swt6.util.JpaUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StatisticsTests {

    Statistics statistics = new Statistics();
    private final IssueDAO issueDao = new IssueDAOImpl();
    private final UserStoryDAO userStoryDAO = new UserStoryDAOImpl();
    private final LogbookEntryDAO logbookEntryDAO = new LogbookEntryDAOImpl();
    private final FeatureDAO featureDAO = new FeatureDAOImpl();

    @Before
    public void init() {
        JpaUtil.getTransactedTestEntityManager();
    }

    @After
    public void cleanup() {
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    public void least5BuggyReleasesTest() {
        var issue = new Issue();
        issue.setTitle("Issue");
        issue.setReleaseVersion("Release1");
        issueDao.insert(issue);

        var issue1 = new Issue();
        issue1.setTitle("Issue1");
        issue1.setReleaseVersion("Release1");
        issueDao.insert(issue1);

        var issue2 = new Issue();
        issue2.setTitle("Issue2");
        issue2.setReleaseVersion("Release2");
        issueDao.insert(issue2);

        statistics.printLeast5BuggyReleases();
    }

    @Test
    public void estimationPointRatioTest() {
        var sprint = new Sprint();

        var userStory = new UserStory();
        userStory.setTitle("Story");
        userStory.setStoryPoints(10);
        userStory.setSprint(sprint);

        var issue = new Issue();
        issue.setTitle("Issue");
        issue.setReleaseVersion("Release1");
        issue.setPoints(10);

        var issue1 = new Issue();
        issue1.setTitle("Issue1");
        issue1.setReleaseVersion("Release1");
        issue1.setPoints(5);
        issue1.setFixedDate(LocalDate.now());

        userStory.addTask(issue);
        userStory.addTask(issue1);

        userStoryDAO.insert(userStory);

        statistics.printEstimationPointRatio();
    }

    @Test
    public void featureistsTest() {
        var feature = new Feature();
        feature.setTitle("Feature");
        feature.setDueDate(LocalDate.of(2022, 04, 17));
        feature.setStatus(Status.done);

        var employee = new Employee();
        employee.setFirstName("Max");
        employee.setLastName("Mustermann");

        var logbookEntry = new LogbookEntry();
        logbookEntry.setEmployee(employee);
        logbookEntry.setTask(feature);
        logbookEntry.setStartTime(LocalDateTime.of(2022,04,16,14, 00));
        logbookEntry.setEndTime(LocalDateTime.of(2022,04,16,15, 00));

        logbookEntryDAO.insert(logbookEntry);

        statistics.printFeatureists();
    }

    @Test
    public void featureStatsForCurrentSprintTest() {
        var sprint = new Sprint();
        sprint.setStartDate(LocalDate.of(2022, 04, 15));
        sprint.setEndDate(LocalDate.of(2022, 04, 20));

        var userStory = new UserStory();
        userStory.setTitle("Story");
        userStory.setStoryPoints(10);
        userStory.setSprint(sprint);

        var feature = new Feature();
        feature.setTitle("Feature");
        feature.setDueDate(LocalDate.of(2022, 04, 17));
        feature.setStatus(Status.done);
        feature.setUserStory(userStory);

        var feature1 = new Feature();
        feature1.setTitle("Feature1");
        feature1.setDueDate(LocalDate.of(2022, 04, 18));
        feature1.setStatus(Status.in_progress);
        feature1.setUserStory(userStory);

        userStoryDAO.insert(userStory);
        featureDAO.insert(feature);
        featureDAO.insert(feature1);

        statistics.printFeatureStateForCurrentSprint();
    }
}
