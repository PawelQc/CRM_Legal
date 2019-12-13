package pl.qceyco.timesheet.unit;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.qceyco.employee.Employee;
import pl.qceyco.project.Project;
import pl.qceyco.timesheet.workWeek.WorkWeek;

import javax.persistence.*;

@Entity
@Table(name = "timesheet_unit")
@Data
@Builder
@NoArgsConstructor
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
