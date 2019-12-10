package pl.qceyco.report;

import lombok.Data;
import pl.qceyco.project.Project;

@Data
public class ProjectReport {

    private Project project;
    private int amountOfHours;
    private int potentialValueOfRenderedServices;
    private boolean isProjectProfitable;

}


