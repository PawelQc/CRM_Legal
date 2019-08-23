package pl.qceyco.app.client.legalPerson;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLegalPersonRepository extends JpaRepository<ClientLegalPerson, Long> {

    ClientLegalPerson findFirstById(Long clientId);
}
