package pl.qceyco.client.naturalPerson;

import lombok.Data;
import pl.qceyco.NameDisplayInterface;
import pl.qceyco.client.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients_natural_person")
@Data
public class ClientNaturalPerson extends Client implements NameDisplayInterface {

    @Column(name = "first_name", length = 50)
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 2, max = 50)
    private String lastName;

    @Transient
    private String nameDisplay;

    @Override
    public String getNameDisplay() {
        return firstName + " " + lastName;
    }

}