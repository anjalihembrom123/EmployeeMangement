package com.jocata.employee.dao;

import com.jocata.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Employee,Integer> {
}
