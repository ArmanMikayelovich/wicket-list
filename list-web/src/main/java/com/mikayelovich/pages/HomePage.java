package com.mikayelovich.pages;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.pages.list_panel.ListPanel;
import com.mikayelovich.pages.modal_panel.CreateUpdateFormModalWindow;
import com.mikayelovich.service.IssueService;
import com.mikayelovich.session.CustomSession;
import lombok.Getter;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    private IssueService issueService;



    private WebMarkupContainer container;

    @Getter
    private CreateUpdateFormModalWindow window;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        Set<IssueDto> sessionIssues = ((CustomSession) getSession()).getIssues();
        boolean isEqualsOrEmpty = sessionIssues.isEmpty()
                || !(new ArrayList<>(sessionIssues).equals(issueService.getAll()));
        if (isEqualsOrEmpty) {
            sessionIssues.addAll(issueService.getAll());
        }

        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);

        ListPanel issueList = new ListPanel("issueList");
        container.add(issueList);
        add(container);
        add(new AjaxLink<Void>("create") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                window = new CreateUpdateFormModalWindow("window", issueList, new IssueDto());
                HomePage.this.addOrReplace(window);
                window.show(target);
            }
        });

        window = new CreateUpdateFormModalWindow("window", issueList, new IssueDto());
        add(window);


        add(new AjaxLink<Void>("saveChangesLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                List<IssueDto> allIssues = ((CustomSession) getSession()).getAll();
                issueService.syncWithDatabase(allIssues);
                target.appendJavaScript("alert('All changes saved on DB.');");
            }
        });
        add(new AjaxLink<Void>("cancelChanges") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                List<IssueDto> issuesFromDB = issueService.getAll();
                ((CustomSession) getSession()).refreshIssues(issuesFromDB);
                issueList.getContainer().addOrReplace(issueList.getIssueDtoListView(
                        issueList.getContainer(), (CustomSession) getSession()));
                target.add(issueList.getContainer());
            }
        });
    }
}
