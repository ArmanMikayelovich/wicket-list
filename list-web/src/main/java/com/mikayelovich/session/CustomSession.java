package com.mikayelovich.session;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.util.enums.SortActionType;
import lombok.Getter;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CustomSession extends WebSession {

    private static final Comparator<IssueDto> comparator = Comparator.comparing(IssueDto::getSortPlace);

    @Getter
    private final Set<IssueDto> issues = new TreeSet<>(comparator);

    public CustomSession(Request request) {
        super(request);
    }

    public List<IssueDto> getAll() {
        ArrayList<IssueDto> list = new ArrayList<>(issues);
        list.sort(comparator);
        return list;
    }

    public void refreshIssues(List<IssueDto> issueDtoList) {
        issues.clear();
        issues.addAll(issueDtoList);
    }

    public void addIssue(IssueDto dto) {
        Long newSortPlace = issues.stream().map(IssueDto::getSortPlace).max(Long::compare).orElse(0L) + 1L;
        dto.setSortPlace(newSortPlace);
        dto.setCreatedAt(LocalDateTime.now());
        issues.add(dto);
    }

    public void delete(IssueDto dto) {
        dto.setDeleted(true);
    }



    public void changeSortPlace(IssueDto dto, SortActionType sortActionType) {
        Long initialSortPlace = dto.getSortPlace();

        Predicate<IssueDto> actionType = sortActionType == SortActionType.UP ?
                relatedIssue -> relatedIssue.getSortPlace() < initialSortPlace
                : relatedIssue -> relatedIssue.getSortPlace() > initialSortPlace;

        Consumer<IssueDto> reverseSortPlace = relatedIssue -> {
            long temporalPlace = relatedIssue.getSortPlace();
            relatedIssue.setSortPlace(initialSortPlace);
            dto.setSortPlace(temporalPlace);
        };

        Stream<IssueDto> issueDtoStream = issues.stream().filter(actionType);

        if (sortActionType == SortActionType.DOWN) {
            issueDtoStream.findFirst().ifPresent(reverseSortPlace);
        } else {
            issueDtoStream.max(IssueDto::compareTo).ifPresent(reverseSortPlace);
        }
    }

}
