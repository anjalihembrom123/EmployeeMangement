package com.jocata.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employeelist")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empid")
    private int empId;
    @Column(name = "empname", unique = true)
    @NotBlank
    private String empName;
    @Column(name = "salary")
    private Float salary;

    @JoinColumn(name = "orgid")
    @ManyToOne
    @JsonIgnore
   // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Organisation organisation;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
