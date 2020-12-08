package com.mikayelovich.pages.list_panel;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.model.IssueEntity;
import com.mikayelovich.service.IssueService;
import com.mikayelovich.service.impl.IssueServiceDummyDaoImpl;
import com.mikayelovich.session.CustomSession;
import com.mikayelovich.util.enums.SortActionType;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class ListPanel extends Panel {


    public ListPanel(String id) {
        super(id);
        WebMarkupContainer container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        add(container);
        container.add(new AjaxLink<Void>("create") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                //TODO create new Issue
            }
        });
        CustomSession customSession = (CustomSession) Session.get();
        ListView<IssueDto> listView = getIssueDtoListView(container, customSession);
        container.add(listView);

        container.add(new AjaxLink<Void>("saveChangesLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                //TODO save all changes
            }
        });
        container.add(new AjaxLink<Void>("cancelChanges") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                //TODO cancel all changes
            }
        });
    }

    private ListView<IssueDto> getIssueDtoListView(WebMarkupContainer container, CustomSession customSession) {
        return new ListView<IssueDto>("list", customSession.getAll()) {

            @Override
            protected void populateItem(ListItem listItem) {
                IssueDto issue = (IssueDto) listItem.getModelObject();

                listItem.add(addUpDownModifyDeleteActions(listItem.getModel(), container));

                listItem.add(new Label("issueName", new PropertyModel<>(issue, "name")));
                listItem.add(new Label("issueStatus", new PropertyModel<>(issue, "status")));
                listItem.add(new Label("issueCreatedAt", new PropertyModel<>(issue, "createdAt")));
            }
        };
    }

    private Component[] addUpDownModifyDeleteActions(IModel<IssueDto> model,WebMarkupContainer container) {
        Image upIcon = new Image("upIcon", "up.png");
        Image downIcon = new Image("downIcon", "down.png");
        Image deleteIcon = new Image("deleteIcon", "delete.png");
        Image modifyIcon = new Image("modifyIcon", "update.png");

        AjaxLink<IssueDto> upAjaxLink = new AjaxLink<IssueDto>("upAjaxLink", model) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                IssueDto issueDto = model.getObject();
                ((CustomSession) getSession()).changeSortPlace(issueDto, SortActionType.UP);
                ((CustomSession) getSession()).getAll();
                container.addOrReplace(getIssueDtoListView(container,(CustomSession) getSession()));
                ajaxRequestTarget.add(container);

            }
        };
        upAjaxLink.add(upIcon);

        AjaxLink<IssueDto> downAjaxLink = new AjaxLink<IssueDto>("downAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                IssueDto issueDto = model.getObject();
                ((CustomSession)getSession()).changeSortPlace(issueDto, SortActionType.DOWN);
                ((CustomSession) getSession()).getAll();
                container.addOrReplace(getIssueDtoListView(container,(CustomSession) getSession()));
                ajaxRequestTarget.add(container);
            }
        };
        downAjaxLink.add(downIcon);

        AjaxLink<IssueDto> deleteAjaxLink = new AjaxLink<IssueDto>("deleteAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                //TODO DELETE
                ((CustomSession) getSession()).getAll();
                container.addOrReplace(getIssueDtoListView(container,(CustomSession) getSession()));
                ajaxRequestTarget.add(container);
            }
        };
        deleteAjaxLink.add(deleteIcon);

        AjaxLink<IssueDto> modifyAjaxLink = new AjaxLink<IssueDto>("modifyAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                //TODO modify item
                ((CustomSession) getSession()).getAll();
                container.addOrReplace(getIssueDtoListView(container,(CustomSession) getSession()));
                ajaxRequestTarget.add(container);
            }
        };
        modifyAjaxLink.add(modifyIcon);

        return new Component[]{upAjaxLink, downAjaxLink, deleteAjaxLink, modifyAjaxLink};
    }


}
