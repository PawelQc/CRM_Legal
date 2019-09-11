package pl.qceyco.project;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Project> findAllWithProjectTeamMembers() {
        Query query = entityManager.createQuery("SELECT p FROM Project p");
        List<Project> projects = query.getResultList();
        for (Project p : projects) {
            Hibernate.initialize(p.getProjectTeam());
        }
        return projects;
    }

    @Override
    public Project findFirstByIdWithProjectTeamMembers(Long projectId) {
        Project project = entityManager.find(Project.class, projectId);
        Hibernate.initialize(project.getProjectTeam());
        return project;
    }


}
