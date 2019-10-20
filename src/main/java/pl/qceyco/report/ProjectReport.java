package pl.qceyco.report;

import pl.qceyco.project.Project;

public class ProjectReport {

    private Project project;
    private int amountOfHours;
    private int potentialValueOfRenderedServices;
    private boolean isProjectProfitable;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getAmountOfHours() {
        return amountOfHours;
    }

    public void setAmountOfHours(int amountOfHours) {
        this.amountOfHours = amountOfHours;
    }

    public int getPotentialValueOfRenderedServices() {
        return potentialValueOfRenderedServices;
    }

    public void setPotentialValueOfRenderedServices(int potentialValueOfRenderedServices) {
        this.potentialValueOfRenderedServices = potentialValueOfRenderedServices;
    }

    public boolean getIsProjectProfitable() {
        return isProjectProfitable;
    }

    public void setProjectProfitable(boolean projectProfitable) {
        isProjectProfitable = projectProfitable;
    }
}


