package com.mikayelovich.model;

import com.mikayelovich.util.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class IssueEntity implements Serializable, Comparable<IssueEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.NEW;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private Long sortPlace;

    @Override
    public int compareTo(IssueEntity other) {
        return this.sortPlace.compareTo(other.sortPlace);
    }
}
