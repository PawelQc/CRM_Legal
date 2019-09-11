package pl.qceyco.timesheet.workWeek.commentary;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "commentary")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMondayCommentary() {
        return mondayCommentary;
    }

    public void setMondayCommentary(String mondayCommentary) {
        this.mondayCommentary = mondayCommentary;
    }

    public String getTuesdayCommentary() {
        return tuesdayCommentary;
    }

    public void setTuesdayCommentary(String tuesdayCommentary) {
        this.tuesdayCommentary = tuesdayCommentary;
    }

    public String getWednesdayCommentary() {
        return wednesdayCommentary;
    }

    public void setWednesdayCommentary(String wednesdayCommentary) {
        this.wednesdayCommentary = wednesdayCommentary;
    }

    public String getThursdayCommentary() {
        return thursdayCommentary;
    }

    public void setThursdayCommentary(String thursdayCommentary) {
        this.thursdayCommentary = thursdayCommentary;
    }

    public String getFridayCommentary() {
        return fridayCommentary;
    }

    public void setFridayCommentary(String fridayCommentary) {
        this.fridayCommentary = fridayCommentary;
    }

    public String getSaturdayCommentary() {
        return saturdayCommentary;
    }

    public void setSaturdayCommentary(String saturdayCommentary) {
        this.saturdayCommentary = saturdayCommentary;
    }

    public String getSundayCommentary() {
        return sundayCommentary;
    }

    public void setSundayCommentary(String sundayCommentary) {
        this.sundayCommentary = sundayCommentary;
    }

    @Override
    public String toString() {
        return "Commentary{" +
                "id=" + id +
                ", mondayCommentary='" + mondayCommentary + '\'' +
                ", tuesdayCommentary='" + tuesdayCommentary + '\'' +
                ", wednesdayCommentary='" + wednesdayCommentary + '\'' +
                ", thursdayCommentary='" + thursdayCommentary + '\'' +
                ", fridayCommentary='" + fridayCommentary + '\'' +
                ", saturdayCommentary='" + saturdayCommentary + '\'' +
                ", sundayCommentary='" + sundayCommentary + '\'' +
                '}';
    }
}
