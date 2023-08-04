package com.jocata.employee.dao.impl;

import com.jocata.employee.dao.EmployeeDao;
import com.jocata.employee.utils.HibernateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired
    HibernateUtils hibernateUtils;
    @Override
    public <T> Serializable save(T entity) {
        return hibernateUtils.save(entity);
    }

    @Override
    public <T> void update(T entity) {
        hibernateUtils.update(entity);
    }

    @Override
    public <T> T getEntityById(Class<T> T, int id) {
        return hibernateUtils.getEntityById(T,id);
    }

    @Override
    public <T> List<T> loadEntityByHql(String hql) {
        return hibernateUtils.loadEntityByHql(hql);
    }
}
