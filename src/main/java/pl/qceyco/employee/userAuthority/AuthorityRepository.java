package pl.qceyco.employee.userAuthority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    @Query(value = "SELECT * from authority where name = ?1 limit 1", nativeQuery = true)
    Authority findFirstByName(String authorityName);

}
