package com.jocata.employee.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "organisation")
public class Organisation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "orgid")
    private int orgid;
    @Column(name = "orgname")
    private String orgName;
    @OneToMany(mappedBy = "organisation",fetch = FetchType.EAGER)
    private List<Employee> employee;

    @Override
    public String toString() {
        return "Organisation{" +
                "orgId=" + orgid +
                ", orgName='" + orgName + '\'' +
                ", employee=" + employee +
                '}';
    }


    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public int getOrgId() {
        return orgid;
    }

    public void setOrgId(int orgId) {
        this.orgid = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}


