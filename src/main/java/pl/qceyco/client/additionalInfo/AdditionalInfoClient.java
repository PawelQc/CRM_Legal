package pl.qceyco.client.additionalInfo;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.pl.NIP;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "additional_info_clients")
@Data
public class AdditionalInfoClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_id_no", unique = true)
    @NIP
    private String nip;

    @Column(length = 150)
    @Size(min = 5, max = 150)
    private String address;

    @Column(length = 100, unique = true)
    @Size(min = 3, max = 100)
    @Email
    private String email;

    @Column(name = "phone_number", length = 15)
    @Size(min = 5, max = 15)
    private String phoneNumber;

    @Column(name = "bank_account", length = 26)
//    @BankAccount  todo pojawia się błąd przy zapisie/zmianie
    private String bankAccount;

    @Column(name = "hourly_rate_charge_PLN")
    @Min(1)
    @Max(100000)
    @NotNull
    private Integer hourlyRateIsCharged;

}