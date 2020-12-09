package com.mikayelovich.dao.impl;

import com.mikayelovich.dao.IssueDao;

import com.mikayelovich.model.IssueEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class IssueDaoImpl implements IssueDao {
    private final SessionFactory sessionFactory;

    @Override
    public IssueEntity findById(Long id) {
        return currentSession().find(IssueEntity.class, id);
    }

    public IssueDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(IssueEntity entity) {
        currentSession().persist(entity);
    }

    @Override
    public List<IssueEntity> getAll() {
        return currentSession()
                .createQuery("select i from IssueEntity as i", IssueEntity.class)
                .list();
    }

    @Override
    public void update(IssueEntity issueEntity) {
        currentSession().update(issueEntity);
    }

    @Override
    public void delete(Long id) {
        currentSession().delete(findById(id));
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
