package pl.qceyco.app.secureApp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findFirstById(Integer authorityId);
}
