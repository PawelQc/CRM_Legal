package pl.qceyco.app.client.additionalInfo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.pl.NIP;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "additional_info_clients")
public class AdditionalInfoClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public void setHourlyRateIsCharged(Integer hourlyRateIsCharged) {
        this.hourlyRateIsCharged = hourlyRateIsCharged;
    }

    @Override
    public String toString() {
        return "AdditionalInfoClient{" +
                "id=" + id +
                ", nip='" + nip + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", hourlyRateIsCharged=" + hourlyRateIsCharged +
                '}';
    }
}