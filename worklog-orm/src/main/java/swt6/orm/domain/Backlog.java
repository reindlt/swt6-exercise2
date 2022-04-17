package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "backlogSequence", initialValue = 1000, allocationSize = 100)
public class Backlog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "backlogSequence")
    private Long id;
    private String vision;
    private String description;

    @OneToMany(mappedBy = "backlog", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<UserStory> userStories = new HashSet<>();

    public Backlog() {
    }

    public Backlog(String vision, String description) {
        this.vision = vision;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public void addUserStory(UserStory userStory) {
        if (userStory == null) {
            throw new IllegalArgumentException("NULL story");
        }
        if (userStory.getBacklog() != null) {
            userStory.getBacklog().getUserStories().remove(userStory);
        }
        this.userStories.add(userStory);
        userStory.setBacklog(this);
    }

    public void removeUserStory(UserStory userStory) {
        userStory.setBacklog(null);
        this.userStories.remove(userStory);
    }

    @Override
    public String toString() {
        return "Backlog: " + description;
    }
}
