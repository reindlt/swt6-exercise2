package swt6.client;

import swt6.orm.domain.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsoleUtil {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    static Sprint readSprintFromCommandLine() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        LocalDate startDate = LocalDate.parse(promptFor(in, "start date (dd.mm.yyyy)"), formatter);
        LocalDate endDate = LocalDate.parse(promptFor(in, "end date (dd.mm.yyyy)"), formatter);
        Sprint sprint = new Sprint(startDate, endDate);
        return sprint;
    }

    static Backlog readBacklogFromCommandLine() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String vision = promptFor(in, "vision");
        String description = promptFor(in, "description");
        Backlog backlog = new Backlog(vision, description);
        return backlog;
    }

    static UserStory readUserStoryFromCommandLine() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String title = promptFor(in, "title");
        String description = promptFor(in, "description");
        int storyPoints = Integer.parseInt(promptFor(in, "story points"));
        UserStory userStory = new UserStory(title, description, storyPoints);
        return userStory;
    }

    static Issue readIssueFromCommandLine() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String title = promptFor(in, "title");
        String description = promptFor(in, "description");
        int points = Integer.parseInt(promptFor(in, "points"));
        String releaseVersion = promptFor(in, "release version");
        LocalDate fixedDate = LocalDate.parse(promptFor(in, "fixed date (dd.mm.yyyy)"), formatter);
        Severity severity = Severity.valueOf(promptFor(in, "severity (low, medium, high)"));
        Issue issue = new Issue(title, description, points, releaseVersion, fixedDate, severity);
        return issue;
    }

    static Feature readFeatureFromCommandLine() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String title = promptFor(in, "title");
        String description = promptFor(in, "description");
        int points = Integer.parseInt(promptFor(in, "points"));
        LocalDate dueDate = LocalDate.parse(promptFor(in, "due date (dd.mm.yyyy)"), formatter);
        Status status = Status.valueOf(promptFor(in, "status (open, in_progress, done"));
        Feature feature = new Feature(title, description, points, dueDate, status);
        return feature;
    }
}
