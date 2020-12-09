package com.mikayelovich.service;

import com.mikayelovich.model.IssueDto;


import java.util.List;

public interface IssueService {
    void save(IssueDto dto);

    List<IssueDto> getAll();

    void update(IssueDto issueEntity);

    void syncWithDatabase(List<IssueDto> dtoList);

    void delete(Long id);


}
