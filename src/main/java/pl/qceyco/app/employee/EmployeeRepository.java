package pl.qceyco.app.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findFirstById(Long EmployeeId);


}
