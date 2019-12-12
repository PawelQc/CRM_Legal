package pl.qceyco.project;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import pl.qceyco.client.Client;
import pl.qceyco.employee.Employee;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
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
    private int capOnRemuneration;

    @Column(name = "is_billable")
    @NotNull
    private Boolean isBillable;         //todo problem przy zmianie nazyw pola/typu

    @ManyToMany
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @NotEmpty
    private List<Employee> projectTeam = new ArrayList<>();

    public Boolean isBillable() {           //todo do usuniecia
        return isBillable;
    }
}