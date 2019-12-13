package pl.qceyco.client.legalPerson;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.qceyco.NameDisplayInterface;
import pl.qceyco.client.Client;
import pl.qceyco.client.additionalInfo.AdditionalInfoClient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients_legal_person")
@Data
@NoArgsConstructor
public class ClientLegalPerson extends Client implements NameDisplayInterface {

    @Column(name = "company_name", length = 100)
    @Size(min = 2, max = 100)
    private String companyName;

    @Transient
    private String nameDisplay;

    @Builder
    public ClientLegalPerson(Long id, String companyName, AdditionalInfoClient additionalInfo) {
        super(id, additionalInfo);
        this.companyName = companyName;
    }

    @Override
    public String getNameDisplay() {
        return companyName;
    }
}