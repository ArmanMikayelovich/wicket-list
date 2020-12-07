package com.mikayelovich.service;

import com.mikayelovich.model.IssueEntity;

import java.util.List;

public interface IssueService {
    void save(IssueEntity entity);

    List<IssueEntity> getAll();

    void update(IssueEntity issueEntity);


}
