package pl.qceyco.app.employee.additinalInfo;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "additional_info_employees")
public class AdditionalInfoEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Min(0)
    @Max(100)
    @NotNull
    private Integer bonus;

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

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "AdditionalInfoEmployee{" +
                "id=" + id +
                ", hourlyRateReceivingSalary=" + hourlyRateReceivingSalary +
                ", hourlyRateChargingClients=" + hourlyRateChargingClients +
                ", targetBudget=" + targetBudget +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
