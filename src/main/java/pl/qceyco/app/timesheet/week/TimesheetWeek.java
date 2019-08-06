package pl.qceyco.app.timesheet.week;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "timesheet_week")
public class TimesheetWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_monday")
    private LocalDate dateMonday;

    @Column(name = "monday_hours")
    @Min(1)
    @Max(24)
    private Integer mondayHours;

    @Column(name = "tuesday_hours")
    @Min(1)
    @Max(24)
    private Integer tuesdayHours;

    @Column(name = "wednesday_hours")
    @Min(1)
    @Max(24)
    private Integer wednesdayHours;

    @Column(name = "thursday_hours")
    @Min(1)
    @Max(24)
    private Integer thursdayHours;

    @Column(name = "friday_hours")
    @Min(1)
    @Max(24)
    private Integer fridayHours;

    @Column(name = "saturday_hours")
    @Min(1)
    @Max(24)
    private Integer saturdayHours;

    @Column(name = "sunday_hours")
    @Min(1)
    @Max(24)
    private Integer sundayHours;

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

    public Integer getMondayHours() {
        return mondayHours;
    }

    public void setMondayHours(Integer mondayHours) {
        this.mondayHours = mondayHours;
    }

    public Integer getTuesdayHours() {
        return tuesdayHours;
    }

    public void setTuesdayHours(Integer tuesdayHours) {
        this.tuesdayHours = tuesdayHours;
    }

    public Integer getWednesdayHours() {
        return wednesdayHours;
    }

    public void setWednesdayHours(Integer wednesdayHours) {
        this.wednesdayHours = wednesdayHours;
    }

    public Integer getThursdayHours() {
        return thursdayHours;
    }

    public void setThursdayHours(Integer thursdayHours) {
        this.thursdayHours = thursdayHours;
    }

    public Integer getFridayHours() {
        return fridayHours;
    }

    public void setFridayHours(Integer fridayHours) {
        this.fridayHours = fridayHours;
    }

    public Integer getSaturdayHours() {
        return saturdayHours;
    }

    public void setSaturdayHours(Integer saturdayHours) {
        this.saturdayHours = saturdayHours;
    }

    public Integer getSundayHours() {
        return sundayHours;
    }

    public void setSundayHours(Integer sundayHours) {
        this.sundayHours = sundayHours;
    }

    @Override
    public String toString() {
        return "TimesheetWeek{" +
                "id=" + id +
                ", dateMonday=" + dateMonday +
                ", mondayHours=" + mondayHours +
                ", tuesdayHours=" + tuesdayHours +
                ", wednesdayHours=" + wednesdayHours +
                ", thursdayHours=" + thursdayHours +
                ", fridayHours=" + fridayHours +
                ", saturdayHours=" + saturdayHours +
                ", sundayHours=" + sundayHours +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimesheetWeek that = (TimesheetWeek) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
