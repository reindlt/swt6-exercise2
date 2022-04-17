package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "userStorySequence", initialValue = 1000, allocationSize = 100)
public class UserStory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userStorySequence")
    private Long id;
    private String title;
    private String description;
    private int storyPoints;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Sprint sprint;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Backlog backlog;

    @OneToMany(mappedBy = "userStory", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Task> tasks = new HashSet<>();

    public UserStory() {
    }

    public UserStory(String title, String description, int storyPoints) {
        this.title = title;
        this.description = description;
        this.storyPoints = storyPoints;
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

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("NULL task");
        }
        if (task.getUserStory() != null) {
            task.getUserStory().getTasks().remove(task);
        }
        this.tasks.add(task);
        task.setUserStory(this);
    }

    public void removeTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("NULL task");
        }
        task.setUserStory(null);
        this.getTasks().remove(task);
    }

    @Override
    public String toString() {
        return "Story: " + title + "(" + storyPoints + ")";
    }
}
