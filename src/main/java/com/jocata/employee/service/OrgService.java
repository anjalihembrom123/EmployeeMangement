package com.jocata.employee.service;

import com.jocata.employee.entity.Employee;
import com.jocata.employee.entity.Organisation;

import java.util.List;

public interface OrgService {
    public Organisation getEntityById (int id);
    public List<Organisation> getOrg();
}
