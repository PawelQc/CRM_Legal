package pl.qceyco.app.project;

import org.hibernate.validator.constraints.NotEmpty;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.employee.Employee;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @Size(min = 4, max = 20)
    private String signature;

    @Column(length = 50)
    @Size(min = 5, max = 50)
    private String name;

    @Column(length = 200)
    @Size(min = 5, max = 200)
    private String description;

    @ManyToOne
    @NotNull
    private Client client;

    @Column(name = "cap_on_remuneration")
    @Min(0)
    @Max(100000000)
    @NotNull
    private Integer capOnRemuneration;

    @Column(name = "is_billable")
    @NotNull
    private Boolean isBillable;

    @ManyToMany
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @NotEmpty
    private List<Employee> projectTeam = new ArrayList<>();

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

    public Integer getCapOnRemuneration() {
        return capOnRemuneration;
    }

    public void setCapOnRemuneration(Integer capOnRemuneration) {
        this.capOnRemuneration = capOnRemuneration;
    }

    public Boolean getBillable() {
        return isBillable;
    }

    public void setBillable(Boolean billable) {
        isBillable = billable;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", signature='" + signature + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", client=" + client +
                ", capOnRemuneration=" + capOnRemuneration +
                ", isBillable=" + isBillable +
                '}';
    }
}