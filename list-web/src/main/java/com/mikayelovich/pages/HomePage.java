package com.mikayelovich.pages;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.pages.list_panel.ListPanel;
import com.mikayelovich.pages.modal_panel.CreateUpdateFormModalWindow;
import com.mikayelovich.service.IssueService;
import com.mikayelovich.session.CustomSession;
import lombok.Getter;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Set;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    private IssueService issueService;

    @Getter
    private final CreateUpdateFormModalWindow window;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        Set<IssueDto> sessionIssues = ((CustomSession) getSession()).getIssues();
        if (sessionIssues.isEmpty()) {
            sessionIssues.addAll(issueService.getAll());
        }

        window = new CreateUpdateFormModalWindow("window",new IssueDto());
        add(window);


        add(new AjaxLink<Void>("create") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                window.show(target);
            }
        });
        ListPanel issueList = new ListPanel("issueList");
        issueList.setOutputMarkupId(true);
        add(issueList);

        window.setWindowClosedCallback((ModalWindow.WindowClosedCallback) target -> {
            issueList.getContainer().addOrReplace(issueList.getIssueDtoListView(
                    issueList.getContainer(), (CustomSession) getSession()));
            target.add(issueList.getContainer());
        });
    }
}
