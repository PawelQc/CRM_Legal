package pl.qceyco.app.timesheet.referenceUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimesheetReferenceUnitRepository extends JpaRepository<TimesheetReferenceUnit, Long> {

    TimesheetReferenceUnit findFirstById(Long id);

    List<TimesheetReferenceUnit> findAllByEmployeeIdOrderByProjectId(Long employeeId);

    List<TimesheetReferenceUnit> findAllByEmployeeId(Long employeeId);

    List<TimesheetReferenceUnit> findAllByProjectIdOrderByEmployeeId(Long projectId);

    @Query("SELECT t FROM TimesheetReferenceUnit t JOIN t.timesheetWeek w where t.project.id = ?1 and t.employee.id = ?2 order by w.dateMonday desc")
    List<TimesheetReferenceUnit> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId);


}
