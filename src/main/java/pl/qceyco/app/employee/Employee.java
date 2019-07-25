package pl.qceyco.app.employee;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String emailLogin;

    private Integer hourlyRateReceivingSalary;

    private Integer hourlyRateChargingClients;

    private Integer targetBudget;

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
