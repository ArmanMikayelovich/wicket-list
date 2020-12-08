package com.mikayelovich.model;

import com.mikayelovich.util.enums.IssueStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class IssueDto implements Serializable,Comparable<IssueDto> {
    private Long id;

    private String name;

    private IssueStatus status;

    private LocalDateTime createdAt;

    private Long sortPlace;

    private boolean isDeleted;

    @Override
    public int compareTo(IssueDto other) {
        return this.sortPlace.compareTo(other.sortPlace);
    }
}
