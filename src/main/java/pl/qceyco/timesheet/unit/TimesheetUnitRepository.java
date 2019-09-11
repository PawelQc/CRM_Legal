package pl.qceyco.timesheet.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetUnitRepository extends JpaRepository<TimesheetUnit, Long> {

    TimesheetUnit findFirstById(Long id);

    List<TimesheetUnit> findAllByProjectId(Long projectId);

    List<TimesheetUnit> findAllByEmployeeId(Long employeeId);

    List<TimesheetUnit> findAllByProjectIdOrderByEmployeeId(Long projectId);

    TimesheetUnit findFirstByEmployeeIdOrderByIdDesc(Long employeeId);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.workWeek w where t.project.id = ?1 and t.employee.id = ?2 order by w.dateMonday desc")
    List<TimesheetUnit> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.workWeek w where t.employee.id = ?1 and w.dateMonday between ?2 and ?3")
    List<TimesheetUnit> findAllByEmployeeIdInSearchPeriod(Long employeeId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.workWeek w where t.project.client.id = ?1 and w.dateMonday between ?2 and ?3 order by w.dateMonday")
    List<TimesheetUnit> findAllByClientInSearchPeriod(Long employeeId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.workWeek w where t.project.id = ?1 and w.dateMonday between ?2 and ?3")
    List<TimesheetUnit> findAllByProjectInSearchPeriod(Long projectId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.workWeek w where t.employee.id = ?1 and t.project.id = ?2 and w.dateMonday between ?3 and ?4")
    TimesheetUnit findFirstByEmployeeIdAndProjectIdInSearchPeriod(Long employeeId, Long projectId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.workWeek w where w.dateMonday between ?1 and ?2 order by t.employee.id")
    List<TimesheetUnit> findAllInSearchPeriod(LocalDate start, LocalDate end);

}

