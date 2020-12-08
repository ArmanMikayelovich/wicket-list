package com.mikayelovich.pages.modal_panel;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.session.CustomSession;
import com.mikayelovich.util.enums.IssueStatus;
import lombok.Setter;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateUpdateFormModalWindow extends ModalWindow {
    private static final List<String> selectOptions = Arrays.stream(IssueStatus.values())
            .map(IssueStatus::toString).collect(Collectors.toList());
    @Setter
    private IssueDto issueDto;

    public CreateUpdateFormModalWindow(String id) {
        super(id);
        WebMarkupContainer container = new WebMarkupContainer("content");
        container.setOutputMarkupId(true);
        this.setContent(container);
        Form<IssueDto> form = new Form<>("form", new CompoundPropertyModel<>(issueDto));
        container.add(form);

        TextField<String> nameField = new TextField<>("name");
        nameField.add(new AttributeModifier("defaultValue", issueDto.getName()));
        form.add(nameField);
        form.add(new DropDownChoice<>("status", Arrays.asList(IssueStatus.values())));
        form.add(new AjaxSubmitLink("submit") {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                ((CustomSession)getSession()).addIssue(form.getModelObject());
                CreateUpdateFormModalWindow.this.close(target);
            }
        });
        form.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                System.out.println("Canceled");
            }
        });
    }
}
