package pl.qceyco.app.client.legalPerson;

import org.hibernate.validator.constraints.NotBlank;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.additionalInfo.AdditionalInfoClient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients_legal_person")
public class ClientLegalPerson extends Client {

    @Column(name = "company_name", length = 100)
    @Size(min = 2, max = 100)
    @NotBlank
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        AdditionalInfoClient additionalInfoClientTest = getAdditionalInfo();
        AdditionalInfoClient infoToShow = new AdditionalInfoClient();
        if (additionalInfoClientTest != null) {
            infoToShow = additionalInfoClientTest;
        }
        return "ClientLegalPerson{" +
                "companyName='" + companyName + '\'' +
                " additionalInfo: " + infoToShow.toString() +
                '}';
    }
}