package com.mikayelovich.session;

import com.mikayelovich.model.IssueEntity;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import java.util.*;

public class CustomSession extends WebSession {

    private final Set<IssueEntity> issues = new TreeSet<>();

    public void addIssueToSet(IssueEntity entity) {
        issues.add(entity);
    }


    public CustomSession(Request request) {
        super(request);


    }

}
