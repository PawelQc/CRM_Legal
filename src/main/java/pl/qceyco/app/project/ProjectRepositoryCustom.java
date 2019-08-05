package pl.qceyco.app.project;

import java.util.List;

public interface ProjectRepositoryCustom {

    List<Project> findAllWithProjectTeamMembers();

    Project findFirstByIdWithProjectTeamMembers(Long caseId);

    List<Project> findAllWithProjectTeamMembersAndTimesheets();

    Project findFirstByIdWithProjectTeamMembersAndTimesheets(Long caseId);

}
