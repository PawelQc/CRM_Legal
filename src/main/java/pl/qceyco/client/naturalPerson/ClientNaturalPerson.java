package pl.qceyco.client.naturalPerson;

import pl.qceyco.client.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients_natural_person")
public class ClientNaturalPerson extends Client {

    @Column(name = "first_name", length = 50)
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 2, max = 50)
    private String lastName;

    @Transient
    private String nameDisplay;

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

    public String getNameDisplay() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Natural person: " + firstName + " " + lastName;
    }
}