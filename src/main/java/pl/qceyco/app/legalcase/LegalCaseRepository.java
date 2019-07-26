package pl.qceyco.app.legalcase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalCaseRepository extends JpaRepository<LegalCase, Long>, LegalCaseRepositoryCustom {

    LegalCase findFirstLegalCaseById(Long LegalCaseId);

}
