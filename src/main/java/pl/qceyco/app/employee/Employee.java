package pl.qceyco.app.employee;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployee;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Column(name = "email_login", length = 100, unique = true)
    @Size(min = 3, max = 100)
    @Email
    @NotBlank
    private String emailLogin;

    @Column(name = "password", length = 100)
    @Size(min = 8)
    //TODO zrób własny walidator - bo pokazuje max dlugosc = max liczba
    @NotBlank
    private String password;

    @NotNull
    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToOne
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfoEmployee additionalInfo;

    /*@ManyToMany
    @JoinTable(name = "employee_timesheet",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "timesheet_id"))
    private List<TimesheetWeek> timesheets = new ArrayList<>();*/

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

   /* public List<TimesheetWeek> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<TimesheetWeek> timesheets) {
        this.timesheets = timesheets;
    }*/

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
//                ", timesheets=" + timesheets +
                '}';
    }
}
