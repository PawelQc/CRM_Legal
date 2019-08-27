package pl.qceyco.app.timesheet.unit;

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

    @Query("SELECT t FROM TimesheetUnit t JOIN t.timesheetWeek w where t.project.id = ?1 and t.employee.id = ?2 order by w.dateMonday desc")
    List<TimesheetUnit> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.timesheetWeek w where t.employee.id = ?1 and w.dateMonday between ?2 and ?3")
    List<TimesheetUnit> findAllByEmployeeIdIn4Weeks(Long employeeId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.timesheetWeek w where t.project.client.id = ?1 and w.dateMonday between ?2 and ?3 order by w.dateMonday")
    List<TimesheetUnit> findAllByClientIn4Weeks(Long employeeId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.timesheetWeek w where t.project.id = ?1 and w.dateMonday between ?2 and ?3")
    List<TimesheetUnit> findAllByProjectIn4Weeks(Long projectId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.timesheetWeek w where t.employee.id = ?1 and t.project.id = ?2 and w.dateMonday between ?3 and ?4")
    TimesheetUnit findFirstByEmployeeIdAndProjectIdForSpecificWeek(Long employeeId, Long projectId, LocalDate start, LocalDate end);

    @Query("SELECT t FROM TimesheetUnit t JOIN t.timesheetWeek w where w.dateMonday between ?1 and ?2 order by t.employee.id")
    List<TimesheetUnit> findAllInRecentWeek(LocalDate start, LocalDate end);

}

