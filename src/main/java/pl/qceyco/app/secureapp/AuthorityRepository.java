package pl.qceyco.app.secureapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findFirstById(Integer authorityId);
}
