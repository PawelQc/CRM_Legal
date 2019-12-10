package pl.qceyco.report;

import lombok.Data;
import pl.qceyco.client.Client;
import pl.qceyco.project.Project;
import pl.qceyco.timesheet.unit.TimesheetUnit;

import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceReport {

    private Client client;
    private List<Project> projectsOfClient;
    private List<TimesheetUnit> timesheets;
    private LocalDate selectedMonday;
    private int amountOfHours;

}

