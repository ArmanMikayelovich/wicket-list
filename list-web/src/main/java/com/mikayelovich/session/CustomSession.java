package com.mikayelovich.session;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.util.enums.SortActionType;
import lombok.Getter;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import java.util.*;

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

    public void addIssue(IssueDto dto) {
        Long newSortPlace = issues.stream().map(IssueDto::getSortPlace).max(Long::compare).orElse(0L) + 1L;
        dto.setSortPlace(newSortPlace);
        issues.add(dto);
    }

    public void delete(IssueDto dto) {
        dto.setDeleted(true);
    }

    public void changeSortPlace(IssueDto dto, SortActionType type) {
        Long initialSortPlace = dto.getSortPlace();
        int sortDirectionNumber = type == SortActionType.UP ? -1 : 1;
        long relatedIssueSortPlace = dto.getSortPlace() + sortDirectionNumber;
        IssueDto relatedIssueDto = null;

        for (IssueDto wantedIssue : issues) {
            if (wantedIssue.getSortPlace() != null
                    && wantedIssue.getSortPlace().equals(relatedIssueSortPlace)) {
                relatedIssueDto = wantedIssue;
                break;
            }
        }
        if (relatedIssueDto == null) {
            return;
        }

        relatedIssueDto.setSortPlace(initialSortPlace);
        dto.setSortPlace(relatedIssueSortPlace);
    }


}
