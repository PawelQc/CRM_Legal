package pl.qceyco.client.naturalPerson;

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
@Table(name = "clients_natural_person")
@Data
@NoArgsConstructor
public class ClientNaturalPerson extends Client implements NameDisplayInterface {

    @Column(name = "first_name", length = 50)
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 2, max = 50)
    private String lastName;

    @Transient
    private String nameDisplay;

    @Builder
    public ClientNaturalPerson(Long id, AdditionalInfoClient additionalInfo, String firstName, String lastName) {
        super(id, additionalInfo);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getNameDisplay() {
        return firstName + " " + lastName;
    }

}