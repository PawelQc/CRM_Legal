package pl.qceyco.employee.additinalInfo;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "additional_info_employees")
@Data
public class AdditionalInfoEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hourly_rate_salary_PLN")
    @Min(1)
    @Max(100000)
    @NotNull
    private int hourlyRateReceivingSalary;

    @Column(name = "hourly_rate_client_charge_PLN")
    @Min(1)
    @Max(100000)
    @NotNull
    private int hourlyRateChargingClients;

    @Column(name = "target_budget_monthly")
    @Min(1)
    @Max(1000000)
    @NotNull
    private int targetBudget;

    @Min(0)
    @Max(100)
    @NotNull
    private int bonus;

    @Column(name = "phone_number", length = 15)
    @Size(min = 5, max = 15)
    private String phoneNumber;

}
