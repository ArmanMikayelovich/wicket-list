package com.mikayelovich.util;

import com.mikayelovich.util.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Filter implements Serializable {
@Getter
    public static final List<String> selectOptions = Arrays.asList("All", "New", "In review", "Closed");


    private String name;
    private String status;
    private LocalDate date;


}
