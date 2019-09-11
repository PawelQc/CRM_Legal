package pl.qceyco.employee.additinalInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalInfoEmployeeRepository extends JpaRepository<AdditionalInfoEmployee, Long> {

    AdditionalInfoEmployee findFirstById(Long id);

}
