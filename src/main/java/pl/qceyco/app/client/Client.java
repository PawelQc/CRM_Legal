package pl.qceyco.app.client;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.pl.NIP;
import pl.qceyco.app.client.legalPersonality.LegalPersonality;
import pl.qceyco.app.employee.Employee;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "legal_personality_id")
    @NotNull
    private LegalPersonality legalPersonality;

    @ManyToOne
    @JoinColumn(name = "assigned_employee_id")
    @NotNull
    private Employee assignedEmployee;

    @Column(name = "first_name", length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;

    @Column(name = "company_name", length = 100)
    @Size(min = 2, max = 100)
    @NotBlank
    private String companyName;

    @Column(name = "tax_id_no", unique = true)
    @NIP
    @NotBlank
    private String nip;

    @Column(length = 150)
    @Size(min = 5, max = 150)
    @NotBlank
    private String address;

    @Column(length = 100, unique = true)
    @Size(min = 3, max = 100)
    @Email
    @NotBlank
    private String email;

    @Column(name = "phone_number", length = 15)
    @Size(min = 5, max = 15)
    @NotBlank
    private String phoneNumber;

    @Column(name = "bank_account", length = 26)
    @Size(min = 26, max = 26)
    @NotBlank
    private String bankAccount;

    @Column(name = "hourly_rate_charge_PLN")
    @Min(1)
    @Max(100000)
    @NotNull
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