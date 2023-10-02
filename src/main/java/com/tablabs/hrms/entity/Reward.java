package com.tablabs.hrms.entity;

import javax.persistence.*;

@Entity
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String award;

    private String rating;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "employeeId")
//    private Employees employees;

    private String employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Reward() {
    }

    public Reward(Long id, String award, String rating, String employeeId) {
        this.id = id;
        this.award = award;
        this.rating = rating;
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", award='" + award + '\'' +
                ", rating='" + rating + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
