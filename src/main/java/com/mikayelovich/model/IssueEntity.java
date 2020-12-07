package com.mikayelovich.model;

import com.mikayelovich.model.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueEntity implements Serializable,Comparable<IssueEntity> {
    private Long id;

    private String name;

    private IssueStatus status;

    private LocalDateTime createdAt;
    private Long sortPlace;

    @Override
    public int compareTo(IssueEntity other) {
        return this.sortPlace.compareTo(other.sortPlace);
    }
}
