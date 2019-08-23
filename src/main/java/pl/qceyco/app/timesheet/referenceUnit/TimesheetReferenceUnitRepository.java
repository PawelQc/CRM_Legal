package pl.qceyco.app.timesheet.referenceUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetReferenceUnitRepository extends JpaRepository<TimesheetReferenceUnit, Long> {

    TimesheetReferenceUnit findFirstById(Long id);

    List<TimesheetReferenceUnit> findAllByProjectId(Long projectId);

    List<TimesheetReferenceUnit> findAllByEmployeeId(Long employeeId);

    List<TimesheetReferenceUnit> findAllByProjectIdOrderByEmployeeId(Long projectId);

    TimesheetReferenceUnit findFirstByEmployeeIdOrderByIdDesc(Long employeeId);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.project.id = ?1 and t.employee.id = ?2 order by w.dateMonday desc")
    List<TimesheetReferenceUnit> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.employee.id = ?1 and w.dateMonday between ?2 and ?3")
    List<TimesheetReferenceUnit> findAllByEmployeeIdIn4Weeks(Long employeeId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.project.client.id = ?1 and w.dateMonday between ?2 and ?3 order by w.dateMonday")
    List<TimesheetReferenceUnit> findAllByClientIn4Weeks(Long employeeId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.project.id = ?1 and w.dateMonday between ?2 and ?3")
    List<TimesheetReferenceUnit> findAllByProjectIn4Weeks(Long projectId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.employee.id = ?1 and t.project.id = ?2 and w.dateMonday between ?3 and ?4")
    TimesheetReferenceUnit findFirstByEmployeeIdAndProjectIdForSpecificWeek(Long employeeId, Long projectId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where w.dateMonday between ?1 and ?2 order by t.employee.id")
    List<TimesheetReferenceUnit> findAllInRecentWeek(LocalDate start, LocalDate end);

}

