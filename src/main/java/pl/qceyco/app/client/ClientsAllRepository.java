package pl.qceyco.app.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsAllRepository extends JpaRepository<Client, Long> {

    Client findFirstById(Long clientId);
}
