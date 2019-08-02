package pl.qceyco.app.legalcase;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class LegalCaseRepositoryImpl implements LegalCaseRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LegalCase> findAllCasesWithProjectTeamMembers() {
        Query query = entityManager.createQuery("SELECT l FROM LegalCase l");
        List<LegalCase> cases = query.getResultList();
        for (LegalCase l : cases) {
            Hibernate.initialize(l.getProjectTeam());
        }
        return cases;
    }

    @Override
    public LegalCase findCaseWithProjectTeamMembers(Long caseId) {
        LegalCase legalCase = entityManager.find(LegalCase.class, caseId);
        Hibernate.initialize(legalCase.getProjectTeam());
        return legalCase;
    }

    @Override
    public List<LegalCase> findAllCasesWithProjectTeamMembersAndTimesheets() {
        Query query = entityManager.createQuery("SELECT l FROM LegalCase l");
        List<LegalCase> cases = query.getResultList();
        for (LegalCase l : cases) {
            Hibernate.initialize(l.getProjectTeam());
            Hibernate.initialize(l.getTimesheets());
        }
        return cases;
    }

    @Override
    public LegalCase findCaseWithProjectTeamMembersAndTimesheets(Long caseId) {
        LegalCase legalCase = entityManager.find(LegalCase.class, caseId);
        Hibernate.initialize(legalCase.getProjectTeam());
        Hibernate.initialize(legalCase.getTimesheets());
        return legalCase;
    }
}
