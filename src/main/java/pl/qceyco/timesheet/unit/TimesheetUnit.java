package pl.qceyco.timesheet.unit;

import pl.qceyco.employee.Employee;
import pl.qceyco.project.Project;
import pl.qceyco.timesheet.workWeek.WorkWeek;

import javax.persistence.*;

@Entity
@Table(name = "timesheet_unit")
public class TimesheetUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "timesheet_week_id")
    private WorkWeek workWeek;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkWeek getWorkWeek() {
        return workWeek;
    }

    public void setWorkWeek(WorkWeek workWeek) {
        this.workWeek = workWeek;
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
                ", timesheetWeek=" + workWeek +
                ", employee=" + employee +
                ", project=" + project +
                '}';
    }

    public Integer countWeekHours() {
        return workWeek.getMondayHours() +
                workWeek.getTuesdayHours() +
                workWeek.getWednesdayHours() +
                workWeek.getThursdayHours() +
                workWeek.getFridayHours() +
                workWeek.getSaturdayHours() +
                workWeek.getSundayHours();
    }

}
