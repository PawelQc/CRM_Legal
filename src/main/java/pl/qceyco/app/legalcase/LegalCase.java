package pl.qceyco.app.legalcase;

import pl.qceyco.app.client.Client;
import pl.qceyco.app.employee.Employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "legal_case")
public class LegalCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String signature;

    private String name;

    private String description;

    @ManyToMany
    private List<Employee> projectTeam = new ArrayList<>();

    @ManyToOne
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employee> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(List<Employee> projectTeam) {
        this.projectTeam = projectTeam;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "LegalCase{" +
                "id=" + id +
                ", signature='" + signature + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectTeam=" + projectTeam +
                ", client=" + client +
                '}';
    }
}