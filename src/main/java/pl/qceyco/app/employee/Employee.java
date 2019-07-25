package pl.qceyco.app.employee;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Column(name = "hourly_rate_salary_PLN")
    @Min(1)
    @Max(100000)
    @NotNull
    private Integer hourlyRateReceivingSalary;

    @Column(name = "hourly_rate_client_charge_PLN")
    @Min(1)
    @Max(100000)
    @NotNull
    private Integer hourlyRateChargingClients;

    @Column(name = "target_budget_monthly")
    @Min(1)
    @Max(1000000)
    @NotNull
    private Integer targetBudget;

    @Column(name = "phone_number", length = 15)
    @Size(min = 5, max = 15)
    @NotBlank
    private String phoneNumber;


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

    public Integer getHourlyRateReceivingSalary() {
        return hourlyRateReceivingSalary;
    }

    public void setHourlyRateReceivingSalary(Integer hourlyRateReceivingSalary) {
        this.hourlyRateReceivingSalary = hourlyRateReceivingSalary;
    }

    public Integer getHourlyRateChargingClients() {
        return hourlyRateChargingClients;
    }

    public void setHourlyRateChargingClients(Integer hourlyRateChargingClients) {
        this.hourlyRateChargingClients = hourlyRateChargingClients;
    }

    public Integer getTargetBudget() {
        return targetBudget;
    }

    public void setTargetBudget(Integer targetBudget) {
        this.targetBudget = targetBudget;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailLogin='" + emailLogin + '\'' +
                ", hourlyRateReceivingSalary=" + hourlyRateReceivingSalary +
                ", hourlyRateChargingClients=" + hourlyRateChargingClients +
                ", targetBudget=" + targetBudget +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
