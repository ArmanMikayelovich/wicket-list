package com.mikayelovich.session;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.model.IssueEntity;
import com.mikayelovich.service.IssueService;
import com.mikayelovich.service.impl.IssueServiceDummyDaoImpl;
import com.mikayelovich.util.enums.SortActionType;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import java.util.*;

public class CustomSession extends WebSession implements IssueService {

    private static final IssueService issueService = new IssueServiceDummyDaoImpl();

    Comparator<IssueDto> comparator = Comparator.comparing(IssueDto::getSortPlace);


    private final Set<IssueDto> issues = new TreeSet<>(comparator);

    public CustomSession(Request request) {
        super(request);
        issues.addAll(issueService.getAll());
    }


    @Override
    public void save(IssueDto dto) {
        issues.add(dto);
    }

    @Override
    public List<IssueDto> getAll() {
        ArrayList<IssueDto> list = new ArrayList<>(issues);
        Collections.sort(list);
        return list;
    }

    @Override
    public void update(IssueEntity issueEntity) {
        //TODO
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
