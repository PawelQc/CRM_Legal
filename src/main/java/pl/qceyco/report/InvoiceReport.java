package pl.qceyco.report;

import pl.qceyco.client.Client;
import pl.qceyco.project.Project;
import pl.qceyco.timesheet.unit.TimesheetUnit;

import java.time.LocalDate;
import java.util.List;

public class InvoiceReport {

    private Client client;
    private List<Project> projectsOfClient;
    private List<TimesheetUnit> timesheets;
    private LocalDate selectedMonday;
    private int amountOfHours;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Project> getProjectsOfClient() {
        return projectsOfClient;
    }

    public void setProjectsOfClient(List<Project> projectsOfClient) {
        this.projectsOfClient = projectsOfClient;
    }

    public List<TimesheetUnit> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<TimesheetUnit> timesheets) {
        this.timesheets = timesheets;
    }

    public LocalDate getSelectedMonday() {
        return selectedMonday;
    }

    public void setSelectedMonday(LocalDate selectedMonday) {
        this.selectedMonday = selectedMonday;
    }

    public int getAmountOfHours() {
        return amountOfHours;
    }

    public void setAmountOfHours(int amountOfHours) {
        this.amountOfHours = amountOfHours;
    }
}

