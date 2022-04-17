package swt6.orm.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Issue extends Task implements Serializable {
    private String releaseVersion;
    private LocalDate fixedDate;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    public Issue() {
    }

    public Issue(String title, String description, int points, String releaseVersion, LocalDate fixedDate, Severity severity) {
        super(title, description, points);
        this.releaseVersion = releaseVersion;
        this.fixedDate = fixedDate;
        this.severity = severity;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public LocalDate getFixedDate() {
        return fixedDate;
    }

    public void setFixedDate(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Issue: " + this.getTitle() + "(" + this.getPoints() + ")";
    }
}
