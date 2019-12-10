package pl.qceyco.timesheet.workWeek;

import lombok.Data;
import pl.qceyco.timesheet.workWeek.commentary.Commentary;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "work_week")
@Data
public class WorkWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_monday")
    private LocalDate dateMonday;

    @Column(name = "monday_hours")
    @Min(0)
    @Max(24)
    private int mondayHours;

    @Column(name = "tuesday_hours")
    @Min(0)
    @Max(24)
    private int tuesdayHours;

    @Column(name = "wednesday_hours")
    @Min(0)
    @Max(24)
    private int wednesdayHours;

    @Column(name = "thursday_hours")
    @Min(0)
    @Max(24)
    private int thursdayHours;

    @Column(name = "friday_hours")
    @Min(0)
    @Max(24)
    private int fridayHours;

    @Column(name = "saturday_hours")
    @Min(0)
    @Max(24)
    private int saturdayHours;

    @Column(name = "sunday_hours")
    @Min(0)
    @Max(24)
    private int sundayHours;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commentary_id")
    private Commentary commentary;

}
