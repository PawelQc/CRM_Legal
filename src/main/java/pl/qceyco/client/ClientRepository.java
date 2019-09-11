package pl.qceyco.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findFirstById(Long clientId);

    Client findFirstByAdditionalInfo_Id(Long infoId);
}
