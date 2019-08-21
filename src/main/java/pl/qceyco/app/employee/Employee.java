package pl.qceyco.app.employee;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployee;
import pl.qceyco.app.employee.validators.Password;
import pl.qceyco.app.secureapp.Authority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;

    @Transient
    private String nameDisplay;

    @Column(name = "email_login", length = 100, unique = true, nullable = false)
    @Size(min = 3, max = 100)
    @Email
    @NotBlank
    private String emailLogin;

    @Column(name = "password", length = 60)
    @Password(min = 8, max = 60)
    @NotBlank
    private String password;

    @NotNull
    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToOne
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfoEmployee additionalInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_authority",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public AdditionalInfoEmployee getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfoEmployee additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailLogin='" + emailLogin + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", additionalInfo=" + additionalInfo +
                ", authorities=" + authorities +
                '}';
    }
}
