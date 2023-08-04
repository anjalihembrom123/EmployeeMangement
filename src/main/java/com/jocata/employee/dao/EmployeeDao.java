package com.jocata.employee.dao;

import java.io.Serializable;
import java.util.List;

public interface EmployeeDao {
    public <T> Serializable save(T entity);
    public <T> void update(T entity);
    public <T> T getEntityById(Class<T> T, int id);
    public <T> List<T> loadEntityByHql(String hql);
}
