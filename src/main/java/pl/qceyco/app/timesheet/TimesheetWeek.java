package pl.qceyco.app.timesheet;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "timesheet_week")
public class TimesheetWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_monday")
    private LocalDate dateMonday;

    @Min(1)
    @Max(24)
    private Integer mondayMinutes;

    @Min(1)
    @Max(24)
    private Integer tuesdayMinutes;

    @Min(1)
    @Max(24)
    private Integer wednesdayMinutes;

    @Min(1)
    @Max(24)
    private Integer thursdayMinutes;

    @Min(1)
    @Max(24)
    private Integer fridayMinutes;

    @Min(1)
    @Max(24)
    private Integer saturdayMinutes;

    @Min(1)
    @Max(24)
    private Integer sundayMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateMonday() {
        return dateMonday;
    }

    public void setDateMonday(LocalDate dateMonday) {
        this.dateMonday = dateMonday;
    }

    public Integer getMondayMinutes() {
        return mondayMinutes;
    }

    public void setMondayMinutes(Integer mondayMinutes) {
        this.mondayMinutes = mondayMinutes;
    }

    public Integer getTuesdayMinutes() {
        return tuesdayMinutes;
    }

    public void setTuesdayMinutes(Integer tuesdayMinutes) {
        this.tuesdayMinutes = tuesdayMinutes;
    }

    public Integer getWednesdayMinutes() {
        return wednesdayMinutes;
    }

    public void setWednesdayMinutes(Integer wednesdayMinutes) {
        this.wednesdayMinutes = wednesdayMinutes;
    }

    public Integer getThursdayMinutes() {
        return thursdayMinutes;
    }

    public void setThursdayMinutes(Integer thursdayMinutes) {
        this.thursdayMinutes = thursdayMinutes;
    }

    public Integer getFridayMinutes() {
        return fridayMinutes;
    }

    public void setFridayMinutes(Integer fridayMinutes) {
        this.fridayMinutes = fridayMinutes;
    }

    public Integer getSaturdayMinutes() {
        return saturdayMinutes;
    }

    public void setSaturdayMinutes(Integer saturdayMinutes) {
        this.saturdayMinutes = saturdayMinutes;
    }

    public Integer getSundayMinutes() {
        return sundayMinutes;
    }

    public void setSundayMinutes(Integer sundayMinutes) {
        this.sundayMinutes = sundayMinutes;
    }

    @Override
    public String toString() {
        return "TimesheetWeek{" +
                "id=" + id +
                ", dateMonday=" + dateMonday +
                ", mondayMinutes=" + mondayMinutes +
                ", tuesdayMinutes=" + tuesdayMinutes +
                ", wednesdayMinutes=" + wednesdayMinutes +
                ", thursdayMinutes=" + thursdayMinutes +
                ", fridayMinutes=" + fridayMinutes +
                ", saturdayMinutes=" + saturdayMinutes +
                ", sundayMinutes=" + sundayMinutes +
                '}';
    }
}
