package pl.qceyco.client.additionalInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalInfoClientRepository extends JpaRepository<AdditionalInfoClient, Long> {

    AdditionalInfoClient findFirstById(Long id);

}
