package pl.qceyco.app.client.legalPerson;

import org.hibernate.validator.constraints.NotBlank;
import pl.qceyco.app.client.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients_legal_person")
public class ClientLegalPerson extends Client {

    @Column(name = "company_name", length = 100)
    @Size(min = 2, max = 100)
    @NotBlank
    private String companyName;

    @Transient
    private String nameDisplay;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNameDisplay() {
        return companyName;
    }

    @Override
    public String toString() {
        return "Legal person: " + companyName;
    }
}