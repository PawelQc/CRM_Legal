package pl.qceyco.app.legalcase;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.timesheet.TimesheetWeek;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cases")
public class LegalCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    @Size(min = 4, max = 10)
    @NotBlank
    private String signature;

    @Column(length = 50)
    @Size(min = 5, max = 50)
    @NotBlank
    private String name;

    @Column(length = 200)
    @Size(min = 5, max = 200)
    @NotBlank
    private String description;

    @ManyToOne
    @NotNull
    private Client client;

    @ManyToMany
    @JoinTable(name = "case_employee",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @NotEmpty
    private List<Employee> projectTeam = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "case_timesheet",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "timesheet_id"))
    private List<TimesheetWeek> timesheets = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employee> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(List<Employee> projectTeam) {
        this.projectTeam = projectTeam;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<TimesheetWeek> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<TimesheetWeek> timesheets) {
        this.timesheets = timesheets;
    }

    @Override
    public String toString() {
        return "LegalCase{" +
                "id=" + id +
                ", signature='" + signature + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", client=" + client +
                ", projectTeam=" + projectTeam +
                ", timesheets=" + timesheets +
                '}';
    }
}