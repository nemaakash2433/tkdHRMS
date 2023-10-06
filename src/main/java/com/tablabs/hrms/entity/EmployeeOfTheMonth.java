//package com.tablabs.hrms.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "employeeOfTheMonth")
//public class EmployeeOfTheMonth {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "isEmployeeOfTheMonth")
//    private boolean isEmployeeOfTheMonth=false;
//
//    private Date onWhichDate;
//
//    private String employeeId;
////    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
////    @JoinColumn(name = "employeeId",referencedColumnName = "id")
////    private Employees employees;
//}
