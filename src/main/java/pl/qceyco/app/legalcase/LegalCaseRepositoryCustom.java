package pl.qceyco.app.legalcase;

import java.util.List;

public interface LegalCaseRepositoryCustom {

    List<LegalCase> findAllCasesWithProjectTeamMembers();

    LegalCase findCaseWithProjectTeamMembers(Long caseId);

    List<LegalCase> findAllCasesWithProjectTeamMembersAndTimesheets();

    LegalCase findCaseWithProjectTeamMembersAndTimesheets(Long caseId);



}
