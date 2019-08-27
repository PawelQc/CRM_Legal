package pl.qceyco.app.timesheet.workWeek.commentary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    Commentary findFirstById(Long id);

}
