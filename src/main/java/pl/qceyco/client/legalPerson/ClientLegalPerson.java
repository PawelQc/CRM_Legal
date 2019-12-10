package pl.qceyco.client.legalPerson;

import lombok.Data;
import pl.qceyco.client.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients_legal_person")
@Data
public class ClientLegalPerson extends Client {

    @Column(name = "company_name", length = 100)
    @Size(min = 2, max = 100)
    private String companyName;

    @Transient
    private String nameDisplay;

    public String getNameDisplay() {
        return companyName;
    }

}