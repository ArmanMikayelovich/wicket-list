package com.mikayelovich.util.enums;

import lombok.Getter;

public enum IssueStatus {
    NEW("name"), IN_REVIEW("In review"), CLOSED("Closed");
    @Getter
    final String name;

    IssueStatus(String name) {
        this.name = name;
    }
}
