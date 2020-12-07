package com.mikayelovich.service.impl;

import com.mikayelovich.model.IssueEntity;
import com.mikayelovich.service.IssueService;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mikayelovich.model.enums.IssueStatus.*;

public class IssueServiceDummyDaoImpl implements IssueService, Serializable {
    private final Map<Long, IssueEntity> dummyIssues = new HashMap<>();
    private final AtomicLong atomicLong = new AtomicLong(13L);

    public IssueServiceDummyDaoImpl() {
        dummyIssues.put(1L, new IssueEntity(1L, "first new", NEW, LocalDateTime.now(), 1L));
        dummyIssues.put(2L, new IssueEntity(2L, "second new", NEW, LocalDateTime.now(), 2L));
        dummyIssues.put(3L, new IssueEntity(3L, "third new", NEW, LocalDateTime.now(), 3L));
        dummyIssues.put(4L, new IssueEntity(4L, "forth new", NEW, LocalDateTime.now(), 4L));
        dummyIssues.put(5L, new IssueEntity(5L, "first in review", IN_REVIEW, LocalDateTime.now(), 5L));
        dummyIssues.put(6L, new IssueEntity(6L, "second in review", IN_REVIEW, LocalDateTime.now(), 6L));
        dummyIssues.put(7L, new IssueEntity(7L, "third in review", IN_REVIEW, LocalDateTime.now(), 7L));
        dummyIssues.put(8L, new IssueEntity(8L, "fourth in review", IN_REVIEW, LocalDateTime.now(), 8L));
        dummyIssues.put(9L, new IssueEntity(9L, "first closed", CLOSED, LocalDateTime.now(), 9L));
        dummyIssues.put(10L, new IssueEntity(10L, "second closed", CLOSED, LocalDateTime.now(), 10L));
        dummyIssues.put(11L, new IssueEntity(11L, "third closed", CLOSED, LocalDateTime.now(), 11L));
        dummyIssues.put(12L, new IssueEntity(12L, "fourth closed", CLOSED, LocalDateTime.now(), 12L));
    }

    @Override
    public void save(IssueEntity entity) {
        dummyIssues.put(atomicLong.getAndIncrement(), entity);
    }

    @Override
    public List<IssueEntity> getAll() {
        return new ArrayList<>(dummyIssues.values());
    }

    @Override
    public void update(IssueEntity issueEntity) {
        IssueEntity entity = dummyIssues.get(issueEntity.getId());
        if (entity == null) {
            throw new RuntimeException("Issue " + issueEntity.getId() + " not found");
        }
        dummyIssues.put(issueEntity.getId(), issueEntity);
    }
}
