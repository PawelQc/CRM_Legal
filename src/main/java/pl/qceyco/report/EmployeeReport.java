package pl.qceyco.report;

import lombok.Data;
import pl.qceyco.employee.Employee;

import java.time.LocalDate;

@Data
public class EmployeeReport {

    private Employee reportedEmployee;
    private int valueOfRenderedServices;
    private int amountOfBillableHours;
    private int amountOfNonBillableHours;
    private int workTimeUtilizationLevel;
    private boolean isMonthlyTargetAchieved;
    private int bonusAmount;
    private LocalDate selectedMonday;

}
