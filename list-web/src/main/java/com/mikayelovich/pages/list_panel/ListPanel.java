package com.mikayelovich.pages.list_panel;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.pages.modal_panel.CreateUpdateFormModalWindow;
import com.mikayelovich.session.CustomSession;
import com.mikayelovich.util.Filter;
import com.mikayelovich.util.enums.SortActionType;
import lombok.Getter;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Arrays;
import java.util.List;

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


        container.add(getFilter());

    }

    private Form<Filter> getFilter() {

        Model<Filter> filterModel = new Model<>(new Filter());
        Form<Filter> filterForm = new Form<>("filterForm", filterModel);
        TextField<String> name = new TextField<>("name",new PropertyModel<>(filterModel,"name"));
        Component date = new LocalDateTextField("date","yyyy-MM-dd");

        DropDownChoice statusChoise = new DropDownChoice("status", Filter.selectOptions);
        filterForm.add(name);
        filterForm.add(date);
        filterForm.add(statusChoise);
        filterForm.add(new AjaxButton("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                System.out.println();
            }
        });
        filterForm.setOutputMarkupId(true);
        return filterForm;
    }

    public ListView<IssueDto> getIssueDtoListView(WebMarkupContainer container, CustomSession customSession) {
        ListView<IssueDto> issueDtoListView = new ListView<IssueDto>("list", customSession.getAll()) {

            @Override
            protected void populateItem(ListItem listItem) {
                IssueDto issue = (IssueDto) listItem.getModelObject();

                if (issue.isDeleted()) {
                    listItem.setVisible(false);
                }

                listItem.add(addUpDownModifyDeleteActions(listItem.getModel()));

                listItem.add(new Label("issueName", new PropertyModel<>(issue, "name")));
                listItem.add(new Label("issueStatus", new PropertyModel<>(issue, "status")));
                listItem.add(new Label("issueCreatedAt", new PropertyModel<>(issue, "createdAt")));
            }
        };
        issueDtoListView.setOutputMarkupId(true);
        return issueDtoListView;
    }

    private Component[] addUpDownModifyDeleteActions(IModel<IssueDto> model) {

        Image upIcon = new Image("upIcon", "up.png");
        Image downIcon = new Image("downIcon", "down.png");
        Image deleteIcon = new Image("deleteIcon", "delete.png");
        Image modifyIcon = new Image("modifyIcon", "update.png");

        AjaxLink<IssueDto> upAjaxLink = new AjaxLink<IssueDto>("upAjaxLink", model) {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                IssueDto issueDto = model.getObject();
                ((CustomSession) getSession()).changeSortPlace(issueDto, SortActionType.UP);

                updateListContentForUser(ajaxRequestTarget);

            }


        };
        upAjaxLink.add(upIcon);

        AjaxLink<IssueDto> downAjaxLink = new AjaxLink<IssueDto>("downAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                IssueDto issueDto = model.getObject();
                ((CustomSession) getSession()).changeSortPlace(issueDto, SortActionType.DOWN);
                updateListContentForUser(ajaxRequestTarget);
            }
        };
        downAjaxLink.add(downIcon);

        AjaxLink<IssueDto> deleteAjaxLink = new AjaxLink<IssueDto>("deleteAjaxLink", model) {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                ((CustomSession) getSession()).delete(model.getObject());
                updateListContentForUser(ajaxRequestTarget);
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

    private void updateListContentForUser(AjaxRequestTarget ajaxRequestTarget) {
        container.addOrReplace(getIssueDtoListView(container, (CustomSession) getSession()));
        ajaxRequestTarget.add(container);
    }

}
