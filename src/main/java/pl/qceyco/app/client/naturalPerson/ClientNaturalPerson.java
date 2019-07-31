package pl.qceyco.app.client.naturalPerson;

import org.hibernate.validator.constraints.NotBlank;
import pl.qceyco.app.client.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients_natural_person")
public class ClientNaturalPerson extends Client {

    @Column(name = "first_name", length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "ClientNaturalPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}