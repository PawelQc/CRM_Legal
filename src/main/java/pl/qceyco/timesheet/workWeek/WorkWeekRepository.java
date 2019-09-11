package pl.qceyco.timesheet.workWeek;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkWeekRepository extends JpaRepository<WorkWeek, Long> {

    WorkWeek findFirstById(Long id);
}
