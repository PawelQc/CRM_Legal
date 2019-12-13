package pl.qceyco.report;

import lombok.Builder;
import lombok.Data;
import pl.qceyco.project.Project;

@Data
@Builder
public class ProjectReport {

    private Project project;
    private int amountOfHours;
    private int potentialValueOfRenderedServices;
    private boolean projectIsProfitable;

}


