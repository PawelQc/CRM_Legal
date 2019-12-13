package pl.qceyco.homepage;

import lombok.Builder;
import lombok.Data;
import pl.qceyco.project.Project;
import pl.qceyco.timesheet.unit.TimesheetUnit;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserHomeParams {
    private List<Project> projectsOfUser;
    private TimesheetUnit recentTimesheet;
    private LocalDate previousMonthFirstMonday;
    private int amountOfNonBillableHours;
    private int amountOfBillableHours;
    private int workTimeUtilizationLevel;
    private int valueOfRenderedServices;
    private boolean monthlyTargetAchieved;
    private int bonusAmount;
}
