package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "sprintSequence", initialValue = 1000, allocationSize = 100)
public class Sprint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprintSequence")
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Project project;

    @OneToMany(mappedBy = "sprint", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<UserStory> userStories = new HashSet<>();

    public Sprint() {
    }

    public Sprint(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public void addUserStory(UserStory story) {
        if (story == null) {
            throw new IllegalArgumentException("NULL story");
        }
        if (story.getSprint() != null) {
            story.getSprint().getUserStories().remove(story);
        }
        this.userStories.add(story);
        story.setSprint(this);
    }

    public void removeUserStory(UserStory story) {
        if (this.userStories.contains(story)) {
            this.userStories.remove(story);
            story.setSprint(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprint sprint = (Sprint) o;
        return this.id == sprint.id;
    }

    @Override
    public String toString() {
        return "Sprint " + id + " (" + startDate + " - " + endDate + ")";
    }
}
