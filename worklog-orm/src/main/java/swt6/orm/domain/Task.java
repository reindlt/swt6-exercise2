package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "taskSequence", initialValue = 1000, allocationSize = 100)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskSequence")
    private Long id;
    private String title;
    private String description;
    private int points;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private UserStory userStory;

    public Task() {
    }

    public Task(String title, String description, int points) {
        this.title = title;
        this.description = description;
        this.points = points;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public void addLogBookEntry(LogbookEntry logbookEntry) {
        if (logbookEntry == null) {
            throw new IllegalArgumentException("NULL logBookEntry");
        }
        if (logbookEntry.getTask() != null) {
            logbookEntry.getTask().getLogbookEntries().remove(logbookEntry);
        }
        this.logbookEntries.add(logbookEntry);
        logbookEntry.setTask(this);
    }

    public void removeLogbookEntry(LogbookEntry logBookEntry) {
        if (logBookEntry == null) {
            throw new IllegalArgumentException("NULL logBookEntry");
        }
        logBookEntry.setTask(null);
        this.logbookEntries.remove(logBookEntry);
    }

    public void attachUserStory(UserStory userStory) {
        if (userStory == null) {
            throw new IllegalArgumentException("NULL userStory");
        }
        userStory.addTask(this);
    }

    public void detachUserStory() {
        if (this.userStory != null) {
            this.userStory.getTasks().remove(this);
            this.setUserStory(null);
        }
    }
}
