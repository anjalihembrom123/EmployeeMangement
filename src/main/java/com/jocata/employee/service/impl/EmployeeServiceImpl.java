package com.jocata.employee.service.impl;

import com.jocata.employee.dao.EmployeeDao;
import com.jocata.employee.entity.Employee;
import com.jocata.employee.entity.Organisation;
import com.jocata.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    @Override
    public Integer save(Employee employee) {
        Serializable id = employeeDao.save(employee);
        ;
        if (id != null) {
            return (Integer) id;
        } else {
            return null;
        }
    }

    @Override
    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public Employee getEntityById(int id) {
        Employee employee1 = employeeDao.getEntityById(Employee.class, id);
        if (employee1 != null) {
            return employee1;
        } else {
            return null;
        }
    }

    @Override
    public List<Employee> loadEntityByHql(int orgid) {
        List<Employee> employeeList = null;
        employeeList = employeeDao.loadEntityByHql("select distinct e from Employee e join e.organisation o with o.orgid ="+orgid);
        if (employeeList != null) {
            return employeeList;
        } else {
            return null;
        }
        }

    @Override
    public List<Employee> loadEntityByHql() {

        List<Employee> employeeList = employeeDao.loadEntityByHql("from Employee");
        if (employeeList != null) {
            return employeeList;
        } else {
            return null;
        }
    }
}

