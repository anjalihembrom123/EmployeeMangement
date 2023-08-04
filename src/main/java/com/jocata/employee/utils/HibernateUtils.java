package com.jocata.employee.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class HibernateUtils {
    @Autowired
    SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    private void closeSession() {
        sessionFactory.close();
    }

    public <T> Serializable save(T entity) {
        Session currentSession = getSession();
        Serializable id = null;
        try {
            currentSession.beginTransaction();
            id = (Serializable) currentSession.save(entity);
            currentSession.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }


    public <T> void update(T entity) {
        Session currentSession = getSession();
        try {
            currentSession.beginTransaction();
            currentSession.update(entity);
            currentSession.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public <T> T getEntityById(Class<T> T, int id) {
        T entity = null;
        Session currentSession = getSession();
        try {
            currentSession.beginTransaction();
            entity = currentSession.get(T, id);
            currentSession.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entity;

}

    public <T> List<T> loadEntityByHql(String hql) {
        List<T> entityList = null;
        Session currentSesson = getSession();
        try {
            currentSesson.beginTransaction();
            Query records = currentSesson.createQuery(hql);
            entityList = records.list();
            currentSesson.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entityList;
    }
}
