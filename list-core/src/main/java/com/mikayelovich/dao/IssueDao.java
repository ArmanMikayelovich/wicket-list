package com.mikayelovich.dao;


import com.mikayelovich.model.IssueEntity;

import java.util.List;

public interface IssueDao {
    IssueEntity findById(Long id);

    void save(IssueEntity entity);

    List<IssueEntity> getAll();

    void update(IssueEntity issueEntity);

    void delete(Long issueId);

}
