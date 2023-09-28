
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

    @Column(name = "check_in")
    private LocalTime checkIn=LocalTime.now();
    
    @Column(name = "check_Out")
    private LocalTime checkOut=LocalTime.now();

    @Column(name = "event_Date")
    private LocalDate date=LocalDate.now();

    @Column(name = "event_Time")
    private LocalTime time=LocalTime.now();
    
    @Column(name="marked_as")
    private String markedAs="pending";

}
