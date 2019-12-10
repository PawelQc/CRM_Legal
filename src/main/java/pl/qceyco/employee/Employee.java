package pl.qceyco.employee;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import pl.qceyco.NameDisplayInterface;
import pl.qceyco.employee.additinalInfo.AdditionalInfoEmployee;
import pl.qceyco.employee.validation.Password;
import pl.qceyco.employee.userAuthority.Authority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
@Data
public class Employee implements NameDisplayInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50)
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 2, max = 50)
    private String lastName;

    @Transient
    private String nameDisplay;

    @Column(name = "email_login", length = 100, unique = true, nullable = false)
    @Size(min = 3, max = 100)
    @Email
    private String emailLogin;

    @Column(name = "password", length = 60)
    @Password()
    private String password;

    @NotNull
    @Column(name = "is_admin")
    private Boolean isAdmin;        //todo przy zmianie nazwy pola/zmianie na prymityw wywala się skypt sql

    @OneToOne
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfoEmployee additionalInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_authority",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<Authority> authorities = new HashSet<>();

    //todo bez tej metody się wywala program
    public Boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String getNameDisplay() {
        return firstName + " " + lastName;
    }

}
