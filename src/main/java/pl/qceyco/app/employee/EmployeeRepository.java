package pl.qceyco.app.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findFirstById(Long employeeId);

    Employee findFirstByEmailLogin(String email);

    Employee findFirstByAdditionalInfo_Id(Long infoId);

    @Query("SELECT e FROM Employee e where e.emailLogin <> ?1")
    List<Employee> findAllExceptForCurrentEmployee(String email);

}
