package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class LogbookEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String activity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Task task;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, LocalDateTime start, LocalDateTime end) {
        this.activity = activity;
        this.startTime = start;
        this.endTime = end;
    }

	public void attachEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("NULL employee");
        }
        if (this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }
        if (employee != null) {
            employee.getLogbookEntries().add(this);
        }
        this.employee = employee;
    }
  
    public void detachEmployee() {
        if (this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }

        this.employee = null;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime start) {
        this.startTime = start;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime end) {
        this.endTime = end;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return activity + ": "
                + startTime.format(formatter) + " - "
                + endTime.format(formatter);
    }
}
