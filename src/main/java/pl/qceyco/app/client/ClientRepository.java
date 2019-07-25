package pl.qceyco.app.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findFirstClientById(Long ClientId);

}
