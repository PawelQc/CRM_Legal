package pl.qceyco.timesheet.workWeek.commentary;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "commentary")
@Data
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monday_commentary", length = 650)
    @Size(max = 650)
    private String mondayCommentary;

    @Column(name = "tuesday_commentary", length = 650)
    @Size(max = 650)
    private String tuesdayCommentary;

    @Column(name = "wednesday_commentary", length = 650)
    @Size(max = 650)
    private String wednesdayCommentary;

    @Column(name = "thursday_commentary", length = 650)
    @Size(max = 650)
    private String thursdayCommentary;

    @Column(name = "friday_commentary", length = 650)
    @Size(max = 650)
    private String fridayCommentary;

    @Column(name = "saturday_commentary", length = 650)
    @Size(max = 650)
    private String saturdayCommentary;

    @Column(name = "sunday_commentary", length = 650)
    @Size(max = 650)
    private String sundayCommentary;

}
