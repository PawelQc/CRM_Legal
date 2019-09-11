package pl.qceyco.client.naturalPerson;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientNaturalPersonRepository extends JpaRepository<ClientNaturalPerson, Long> {

    ClientNaturalPerson findFirstById(Long clientId);
}
