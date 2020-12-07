package com.mikayelovich.pages.list_panel;

import com.mikayelovich.model.IssueEntity;
import com.mikayelovich.service.IssueService;
import com.mikayelovich.service.impl.IssueServiceDummyDaoImpl;
import com.mikayelovich.session.CustomSession;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class ListPanel extends Panel {
    private final IssueService issueService = new IssueServiceDummyDaoImpl();

    public ListPanel(String id) {
        super(id);
        add(new AjaxLink<Void>("create") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                //TODO create new Issue
            }
        });
        CustomSession customSession = (CustomSession) Session.get();
        add(new ListView<IssueEntity>("list", issueService.getAll()) {

            @Override
            protected void populateItem(ListItem listItem) {
                IssueEntity issue = (IssueEntity) listItem.getModelObject();

                customSession.addIssueToSet(issue);

                listItem.add(addUpDownModifyDeleteActions(listItem.getModel()));

                listItem.add(new Label("issueName", new PropertyModel<>(issue, "name")));
                listItem.add(new Label("issueStatus", new PropertyModel<>(issue, "status")));
                listItem.add(new Label("issueCreatedAt", new PropertyModel<>(issue, "createdAt")));
            }
        });

        add(new AjaxLink<Void>("saveChangesLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                //TODO save all changes
            }
        });
        add(new AjaxLink<Void>("cancelChanges") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                //TODO cancel all changes
            }
        });
    }

    private Component[] addUpDownModifyDeleteActions(IModel<IssueEntity> model) {
        Image upIcon = new Image("upIcon", "up.png");
        Image downIcon = new Image("downIcon", "down.png");
        Image deleteIcon = new Image("deleteIcon", "delete.png");
        Image modifyIcon = new Image("modifyIcon", "update.png");

        AjaxLink<IssueEntity> upAjaxLink = new AjaxLink<IssueEntity>("upAjaxLink", model) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                //TODO up item
            }
        };
        upAjaxLink.add(upIcon);

        AjaxLink<IssueEntity> downAjaxLink = new AjaxLink<IssueEntity>("downAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                //TODO down item
            }
        };
        downAjaxLink.add(downIcon);

        AjaxLink<IssueEntity> deleteAjaxLink = new AjaxLink<IssueEntity>("deleteAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                //TODO remove item
            }
        };
        deleteAjaxLink.add(deleteIcon);

        AjaxLink<IssueEntity> modifyAjaxLink = new AjaxLink<IssueEntity>("modifyAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                //TODO modify item
            }
        };
        modifyAjaxLink.add(modifyIcon);

        return new Component[]{upAjaxLink, downAjaxLink, deleteAjaxLink, modifyAjaxLink};
    }


}
