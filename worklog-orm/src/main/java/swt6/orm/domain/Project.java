package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee manager;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Employee> members = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Backlog backlog;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Sprint> sprints = new HashSet<>();

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Set<Employee> getMembers() {
        return members;
    }

    public void setMembers(Set<Employee> members) {
        this.members = members;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }

    public void addMember(Employee empl) {
        if (empl == null) {
            throw new IllegalArgumentException("NULL employee");
        }
        empl.getProjects().add(this);
        this.members.add(empl);
    }

    public void removeMember(Employee empl) {
        if (empl == null) {
            throw new IllegalArgumentException("NULL employee");
        }
        empl.getProjects().remove(this);
        this.members.remove(empl);
    }

    public void addSprint(Sprint sprint) {
        if (sprint == null) {
            throw new IllegalArgumentException("NULL sprint");
        }
        if (sprint.getProject() != null) {
            sprint.getProject().getSprints().remove(sprint);
        }
        this.sprints.add(sprint);
        sprint.setProject(this);
    }

    public void removeSprint(Sprint sprint) {
        this.sprints.remove(sprint);
        sprint.setProject(null);
    }

    public String toString() {
        return name;
    }
}
