
package com.tablabs.hrms.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "attendance")
public class Attendance{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "in_time")
    private Date inTime;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    private String date;

    @Column(name = "out_time")
    private Date outTime;

    @Column(name="marked_as")
    private String markedAs="pending";


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getMarkedAs() {
        return markedAs;
    }

    public void setMarkedAs(String markedAs) {
        this.markedAs = markedAs;
    }


    public Attendance(Long id, String employeeId, Long cardId, Date inTime, String location, String date,
                      Date outTime, String markedAs) {
        super();
        this.id = id;
        this.employeeId = employeeId;
        this.cardId = cardId;
        this.inTime = inTime;
        this.location = location;
        this.date = date;
        this.outTime = outTime;
        this.markedAs = markedAs;
    }

    public Attendance() {
        super();
        // TODO Auto-generated constructor stub
    }

}
