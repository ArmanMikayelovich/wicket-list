package com.mikayelovich.service;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.model.IssueEntity;

import java.util.List;

public interface IssueService {
    void save(IssueDto dto);

    List<IssueDto> getAll();

    void update(IssueEntity issueEntity);


}
