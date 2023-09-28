
package com.tablabs.hrms.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "in_or_out")
    private String inOrOut;

//    @Column(name = "location_id")
//    private Long locationId;

    @Column(name = "event_Date")
    private LocalDate date;

    @Column(name = "event_Time")
    private LocalTime time;
    
    @Column(name="marked_as")
    private String markedAs="pending";

}
