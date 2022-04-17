package swt6.client;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import swt6.orm.dao.*;
import swt6.orm.dao.interfaces.*;
import swt6.orm.domain.*;
import swt6.statistic.Statistics;
import swt6.util.JpaUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleClient {

    private static final SprintDAO sprintDAO = new SprintDAOImpl();
    private static final BacklogDAO backlogDAO = new BacklogDAOImpl();
    private static final UserStoryDAO userStoryDAO = new UserStoryDAOImpl();
    private static final IssueDAO issueDAO = new IssueDAOImpl();
    private static final FeatureDAO featureDAO = new FeatureDAOImpl();

    private static final Statistics statistics = new Statistics();

    private static void setUpInitialData() {
        var em = JpaUtil.getTransactedEntityManager();

        Session session = em.unwrap(Session.class);

        session.doWork(conn -> {
            IDatabaseConnection connection = null;
            try {
                connection = new DatabaseConnection(conn);
                ClassLoader cl = ConsoleClient.class.getClassLoader();
                IDataSet dataSet = new FlatXmlDataSetBuilder().build(ConsoleClient.class
                        .getClassLoader()
                        .getResourceAsStream("dbunit/dataSet.xml"));

                DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
            } catch (DatabaseUnitException e) {
                e.printStackTrace();
                throw new RuntimeException("error initializing test data");
            }
        });
    }

    public static void main(String[] args) {
        setUpInitialData();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "Type following commands:\n" +
                "  - s (to insert sprint)\n" +
                "  - b (to insert backlog)\n" +
                "  - u (to insert userstory\n" +
                "  - i (to insert issue)\n" +
                "  - f (to insert feature)\n" +
                "  - stats1 (to print 5 least buggy results)\n" +
                "  - stats2 (to print estimation point ratio)\n" +
                "  - stats3 (to print featureists)\n" +
                "  - stats4 (to print feature state for current sprint)\n" +
                "  - q (to leave session)";

        System.out.println(availCmds);
        String userCmd = ConsoleUtil.promptFor(in, "");

        try {

            while (!userCmd.equals("q")) {

                switch (userCmd) {
                    case "s":
                        Sprint sprint = ConsoleUtil.readSprintFromCommandLine();
                        sprintDAO.insert(sprint);
                        System.out.println("Inserted:\n" + sprint);
                        break;
                    case "b":
                        Backlog backlog = ConsoleUtil.readBacklogFromCommandLine();
                        backlogDAO.insert(backlog);
                        System.out.println("Inserted:\n" + backlog);
                        break;
                    case "u":
                        UserStory userStory = ConsoleUtil.readUserStoryFromCommandLine();
                        userStoryDAO.insert(userStory);
                        System.out.println("Inserted:\n" + userStory);
                        break;
                    case "i":
                        Issue issue = ConsoleUtil.readIssueFromCommandLine();
                        issueDAO.insert(issue);
                        System.out.println("Inserted:\n" + issue);
                        break;
                    case "f":
                        Feature feature = ConsoleUtil.readFeatureFromCommandLine();
                        featureDAO.insert(feature);
                        System.out.println("Inserted:\n" + feature);
                        break;
                    case "stats1":
                        statistics.printLeast5BuggyReleases();
                        break;
                    case "stats2":
                        statistics.printEstimationPointRatio();
                        break;
                    case "stats3":
                        statistics.printFeatureists();
                        break;
                    case "stats4":
                        statistics.printFeatureStateForCurrentSprint();
                        break;
                    default:
                        System.out.println("ERROR: invalid command");
                        break;
                }
                System.out.println();
                System.out.println(availCmds);
                userCmd = ConsoleUtil.promptFor(in, "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
