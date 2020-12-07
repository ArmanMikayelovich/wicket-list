package com.mikayelovich.pages;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.pages.list_panel.ListPanel;
import com.mikayelovich.pages.modal_panel.CreateUpdateFormModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);
        add(new CreateUpdateFormModalWindow("window", new IssueDto()));
        add(new ListPanel("issueList").setOutputMarkupId(true));
    }
}
