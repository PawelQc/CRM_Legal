package pl.qceyco.app.client;

import pl.qceyco.app.client.legalPersonality.LegalPersonality;
import pl.qceyco.app.employee.Employee;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private LegalPersonality legalPersonality;

    @ManyToOne
    private Employee assignedEmployee;

    private String firstName;

    private String lastName;

    private String companyName;

    private String nip;

    private String address;

    private String email;

    private String phoneNumber;

    private String bankAccount;

    private Integer hourlyRateIsCharged;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LegalPersonality getLegalPersonality() {
        return legalPersonality;
    }

    public void setLegalPersonality(LegalPersonality legalPersonality) {
        this.legalPersonality = legalPersonality;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Integer getHourlyRateIsCharged() {
        return hourlyRateIsCharged;
    }

    public void setHourlyRateIsCharged(Integer hourlyRate) {
        this.hourlyRateIsCharged = hourlyRate;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", legalPersonality=" + legalPersonality +
                ", assignedEmployee=" + assignedEmployee +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", nip='" + nip + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", hourlyRate=" + hourlyRateIsCharged +
                '}';
    }
}