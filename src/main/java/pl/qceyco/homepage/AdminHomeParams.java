package pl.qceyco.homepage;

import lombok.Builder;
import lombok.Data;
import pl.qceyco.timesheet.unit.TimesheetUnit;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class AdminHomeParams {
    private LocalDate thisMonthFirstMonday;
    private Map<String, Integer> projectsAndHours;
    private Map<String, Integer> employeesAndUtilisation;
    private LocalDate previousMonday;
    private List<TimesheetUnit> timesheets;
}

