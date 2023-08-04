package com.jocata.employee.service.impl;

import com.jocata.employee.dao.EmployeeDao;
import com.jocata.employee.entity.Employee;
import com.jocata.employee.entity.Organisation;
import com.jocata.employee.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {
    @Autowired
    EmployeeDao employeeDao;
    @Override
    public Organisation getEntityById(int id) {
        Organisation organisation = employeeDao.getEntityById(Organisation.class,id);
        if(organisation != null){
            System.out.println(organisation);
           // System.out.println(organisation.getEmployee().size());
            return organisation;
        }
        else {
            return null;
        }
    }
    public List<Organisation> getOrg() {
        List <Organisation> organisation = employeeDao.loadEntityByHql("from Organisation");
        if(organisation != null){

            System.out.println("organisation: ");
            System.out.println(organisation.size());
            return organisation;
        }
        else {
            return null;
        }
    }
}
