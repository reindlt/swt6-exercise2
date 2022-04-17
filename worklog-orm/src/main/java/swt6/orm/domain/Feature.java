package swt6.orm.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Feature extends Task implements Serializable {
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Feature() {
    }

    public Feature(String title, String description, int points, LocalDate dueDate, Status status) {
        super(title, description, points);
        this.dueDate = dueDate;
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Feature: " + this.getTitle() + "(" + this.getPoints() + ")";
    }

}
