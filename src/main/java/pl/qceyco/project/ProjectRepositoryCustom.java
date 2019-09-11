package pl.qceyco.project;

import java.util.List;

public interface ProjectRepositoryCustom {

    List<Project> findAllWithProjectTeamMembers();

    Project findFirstByIdWithProjectTeamMembers(Long caseId);

}
