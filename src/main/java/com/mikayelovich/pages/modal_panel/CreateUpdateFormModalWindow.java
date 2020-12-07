package com.mikayelovich.pages.modal_panel;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.util.enums.IssueStatus;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class CreateUpdateFormModalWindow extends ModalWindow {

    public CreateUpdateFormModalWindow(String id, IssueDto issueDto) {
        super(id);
        WebMarkupContainer container = new WebMarkupContainer("cont");
        Form<IssueDto> form = new Form<>("form",new CompoundPropertyModel<>(issueDto));
        container.add(form);

        form.add(new TextField<String>("name"));
        form.add(new TextField<IssueStatus>("status"));
        form.add(new AjaxSubmitLink("submit") {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                System.out.println(form.getModelObject());
                super.onSubmit(target);
            }
        });
    }
}
