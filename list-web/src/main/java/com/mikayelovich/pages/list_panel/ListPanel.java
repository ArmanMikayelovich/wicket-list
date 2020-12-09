package com.mikayelovich.pages.list_panel;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.pages.modal_panel.CreateUpdateFormModalWindow;
import com.mikayelovich.session.CustomSession;
import com.mikayelovich.util.enums.SortActionType;
import lombok.Getter;
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

    @Getter
    private final WebMarkupContainer container;
    private CreateUpdateFormModalWindow window;

    public ListPanel(String id) {
        super(id);
        this.setOutputMarkupId(true);
        window = new CreateUpdateFormModalWindow("window", this, new IssueDto());
        window.setOutputMarkupId(true);
        container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        add(container);
        add(window);
        CustomSession customSession = (CustomSession) Session.get();
        ListView<IssueDto> listView = getIssueDtoListView(container, customSession);
        container.add(listView);

    }

    public ListView<IssueDto> getIssueDtoListView(WebMarkupContainer container, CustomSession customSession) {
        ListView<IssueDto> issueDtoListView = new ListView<IssueDto>("list", customSession.getAll()) {

            @Override
            protected void populateItem(ListItem listItem) {
                IssueDto issue = (IssueDto) listItem.getModelObject();

                if (issue.isDeleted()) {
                    listItem.setVisible(false);
                }

                listItem.add(addUpDownModifyDeleteActions(listItem.getModel(), container));

                listItem.add(new Label("issueName", new PropertyModel<>(issue, "name")));
                listItem.add(new Label("issueStatus", new PropertyModel<>(issue, "status")));
                listItem.add(new Label("issueCreatedAt", new PropertyModel<>(issue, "createdAt")));
            }
        };
        issueDtoListView.setOutputMarkupId(true);
        return issueDtoListView;
    }

    private Component[] addUpDownModifyDeleteActions(IModel<IssueDto> model, WebMarkupContainer container) {

        Image upIcon = new Image("upIcon", "up.png");
        Image downIcon = new Image("downIcon", "down.png");
        Image deleteIcon = new Image("deleteIcon", "delete.png");
        Image modifyIcon = new Image("modifyIcon", "update.png");

        AjaxLink<IssueDto> upAjaxLink = new AjaxLink<IssueDto>("upAjaxLink", model) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                IssueDto issueDto = model.getObject();
                ((CustomSession) getSession()).changeSortPlace(issueDto, SortActionType.UP);

                container.addOrReplace(getIssueDtoListView(container, (CustomSession) getSession()));
                ajaxRequestTarget.add(container);

            }
        };
        upAjaxLink.add(upIcon);

        AjaxLink<IssueDto> downAjaxLink = new AjaxLink<IssueDto>("downAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                IssueDto issueDto = model.getObject();
                ((CustomSession) getSession()).changeSortPlace(issueDto, SortActionType.DOWN);
                container.addOrReplace(getIssueDtoListView(container, (CustomSession) getSession()));
                ajaxRequestTarget.add(container);
            }
        };
        downAjaxLink.add(downIcon);

        AjaxLink<IssueDto> deleteAjaxLink = new AjaxLink<IssueDto>("deleteAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                ((CustomSession) getSession()).delete(model.getObject());
                container.addOrReplace(getIssueDtoListView(container, (CustomSession) getSession()));
                ajaxRequestTarget.add(container);
            }
        };
        deleteAjaxLink.add(deleteIcon);

        AjaxLink<IssueDto> modifyAjaxLink = new AjaxLink<IssueDto>("modifyAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                IssueDto issueDto = model.getObject();
                window = new CreateUpdateFormModalWindow("window", ListPanel.this, issueDto);
                ListPanel.this.addOrReplace(window);
                window.show(ajaxRequestTarget);
            }
        };
        modifyAjaxLink.add(modifyIcon);

        return new Component[]{upAjaxLink, downAjaxLink, deleteAjaxLink, modifyAjaxLink};
    }


}
