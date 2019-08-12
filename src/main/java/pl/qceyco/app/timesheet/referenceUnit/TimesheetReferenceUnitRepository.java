package pl.qceyco.app.timesheet.referenceUnit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimesheetReferenceUnitRepository extends JpaRepository<TimesheetReferenceUnit, Long> {

    TimesheetReferenceUnit findFirstById(Long id);

    List<TimesheetReferenceUnit> findAllByEmployeeIdOrderByProjectId(Long employeeId);

    List<TimesheetReferenceUnit> findAllByEmployeeId(Long employeeId);

    List<TimesheetReferenceUnit> findAllByProjectIdOrderByEmployeeId(Long projectId);

    List<TimesheetReferenceUnit> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId);


}
