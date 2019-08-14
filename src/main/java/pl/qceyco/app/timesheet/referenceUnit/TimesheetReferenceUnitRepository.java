package pl.qceyco.app.timesheet.referenceUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.qceyco.app.timesheet.week.TimesheetWeek;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetReferenceUnitRepository extends JpaRepository<TimesheetReferenceUnit, Long> {

    TimesheetReferenceUnit findFirstById(Long id);

    List<TimesheetReferenceUnit> findAllByEmployeeIdOrderByProjectId(Long employeeId);

    List<TimesheetReferenceUnit> findAllByEmployeeId(Long employeeId);

    List<TimesheetReferenceUnit> findAllByProjectIdOrderByEmployeeId(Long projectId);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.project.id = ?1 and t.employee.id = ?2 order by w.dateMonday desc")
    List<TimesheetReferenceUnit> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.employee.id = ?1 and w.dateMonday between ?2 and ?3")
    List<TimesheetReferenceUnit> findAllByEmployeeIdIn4Weeks(Long employeeId, LocalDate start, LocalDate end);


}
