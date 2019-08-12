package pl.qceyco.app.timesheet.referenceUnit;

import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.timesheet.week.TimesheetWeek;

import javax.persistence.*;

@Entity
@Table(name = "timesheet_reference_unit")
public class TimesheetReferenceUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "timesheet_week_id")
    private TimesheetWeek timesheetWeek;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    //todo dodać możliwość dodawania komentarza do ts?

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimesheetWeek getTimesheetWeek() {
        return timesheetWeek;
    }

    public void setTimesheetWeek(TimesheetWeek timesheetWeek) {
        this.timesheetWeek = timesheetWeek;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "TimesheetReferenceUnit{" +
                "id=" + id +
                ", timesheetWeek=" + timesheetWeek +
                ", employee=" + employee +
                ", project=" + project +
                '}';
    }
}
