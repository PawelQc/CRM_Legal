package pl.qceyco.report;

import pl.qceyco.employee.Employee;

public class EmployeeReport {

    private int valueOfRenderedServices;

    private Employee employee;
    //uzywac prymitywow



    public int getValueOfRenderedServices() {
        return valueOfRenderedServices;
    }

    public void setValueOfRenderedServices(int valueOfRenderedServices) {
        this.valueOfRenderedServices = valueOfRenderedServices;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
