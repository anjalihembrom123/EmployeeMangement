package com.jocata.employee.service;

import com.jocata.employee.entity.Employee;
import com.jocata.employee.entity.Organisation;

import java.io.Serializable;
import java.util.List;

public interface EmployeeService {
    public Integer save(Employee employee);
    public  void update(Employee employee);
    public Employee getEntityById(int id);

    public List <Employee> loadEntityByHql(int orgid);
    public List <Employee> loadEntityByHql();
}

