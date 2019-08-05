package pl.qceyco.app.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    Project findFirstById(Long legalCaseId);

    Project findFirstByClientId(Long clientId);

    @Query("SELECT p FROM Project p JOIN p.projectTeam t where t.id = ?1")
    List<Project> findAllByEmployeeId(Long employeeId);


}
