package com.tablabs.hrms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String award;

//    private Double rating;


    private String employeeId;

    @Column(name = "isEmployeeOfTheMonth")
    private boolean isEmployeeOfTheMonth = false;

    private String onWhichDate;


}
