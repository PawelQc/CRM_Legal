package pl.qceyco.app.timesheet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetWeekRepository extends JpaRepository<TimesheetWeek, Long> {

    TimesheetWeek findFirstById(Long id);
}
