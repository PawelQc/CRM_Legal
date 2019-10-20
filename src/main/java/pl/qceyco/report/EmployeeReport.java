package pl.qceyco.report;

import pl.qceyco.employee.Employee;

import java.time.LocalDate;

public class EmployeeReport {

    private Employee reportedEmployee;
    private int valueOfRenderedServices;
    private int amountOfBillableHours;
    private int amountOfNonBillableHours;
    private int workTimeUtilizationLevel;
    private boolean isMonthlyTargetAchieved;
    private int bonusAmount;
    private LocalDate selectedMonday;

    public Employee getReportedEmployee() {
        return reportedEmployee;
    }

    public void setReportedEmployee(Employee reportedEmployee) {
        this.reportedEmployee = reportedEmployee;
    }

    public int getValueOfRenderedServices() {
        return valueOfRenderedServices;
    }

    public void setValueOfRenderedServices(int valueOfRenderedServices) {
        this.valueOfRenderedServices = valueOfRenderedServices;
    }

    public int getAmountOfBillableHours() {
        return amountOfBillableHours;
    }

    public void setAmountOfBillableHours(int amountOfBillableHours) {
        this.amountOfBillableHours = amountOfBillableHours;
    }

    public int getAmountOfNonBillableHours() {
        return amountOfNonBillableHours;
    }

    public void setAmountOfNonBillableHours(int amountOfNonBillableHours) {
        this.amountOfNonBillableHours = amountOfNonBillableHours;
    }

    public int getWorkTimeUtilizationLevel() {
        return workTimeUtilizationLevel;
    }

    public void setWorkTimeUtilizationLevel(int workTimeUtilizationLevel) {
        this.workTimeUtilizationLevel = workTimeUtilizationLevel;
    }

    public boolean getIsMonthlyTargetAchieved() {
        return isMonthlyTargetAchieved;
    }

    public void setMonthlyTargetAchieved(boolean monthlyTargetAchieved) {
        isMonthlyTargetAchieved = monthlyTargetAchieved;
    }

    public int getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(int bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public LocalDate getSelectedMonday() {
        return selectedMonday;
    }

    public void setSelectedMonday(LocalDate selectedMonday) {
        this.selectedMonday = selectedMonday;
    }


}
