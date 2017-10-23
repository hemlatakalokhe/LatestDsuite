package de.bonprix.showcase.cases.layout;

import java.util.Date;

import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import de.bonprix.common.dummydata.StringGenerator;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;

@SuppressWarnings("serial")
@SpringViewComponent
public class FormLayoutDemo extends ShowcaseWrapper {

    public static final String STYLE_LIGHT = "light";
    public static final String STYLE_COLORED = "colored";

    public FormLayoutDemo() {
        super("GRID_TABLE_TREE", "BEANITEMTABLEDEMO");
    }

    @Override
    protected Component createLayout() {

        final Label title = new Label("Forms");
        title.addStyleName("h1");

        final FormLayout form = new FormLayout();
        form.setMargin(false);
        form.setWidth("800px");
        form.addStyleName(FormLayoutDemo.STYLE_LIGHT);

        Label section = new Label("Personal Info");
        section.addStyleName("h2");
        section.addStyleName(FormLayoutDemo.STYLE_COLORED);
        form.addComponent(section);
        final StringGenerator sg = new StringGenerator();

        final TextField name = new TextField("Name");
        name.setValue(sg.nextString(true) + " " + sg.nextString(true));
        name.setWidth("50%");
        form.addComponent(name);

        final DateField birthday = new DateField("Birthday");
        birthday.setValue(new Date());
        form.addComponent(birthday);

        final TextField username = new TextField("Username");
        username.setValue(sg.nextString(false) + sg.nextString(false));
        username.setRequired(true);
        form.addComponent(username);

        final OptionGroup sex = new OptionGroup("Sex");
        sex.addItem("Female");
        sex.addItem("Male");
        sex.select("Male");
        sex.addStyleName("horizontal");
        form.addComponent(sex);

        section = new Label("Contact Info");
        section.addStyleName("h3");
        section.addStyleName(FormLayoutDemo.STYLE_COLORED);
        form.addComponent(section);

        final TextField email = new TextField("Email");
        email.setValue(sg.nextString(false) + "@" + sg.nextString(false) + ".com");
        email.setWidth("50%");
        email.setRequired(true);
        form.addComponent(email);

        final TextField location = new TextField("Location");
        location.setValue(sg.nextString(true) + ", " + sg.nextString(true));
        location.setWidth("50%");
        location.setComponentError(new UserError("This address doesn't exist"));
        form.addComponent(location);

        final TextField phone = new TextField("Phone");
        phone.setWidth("50%");
        form.addComponent(phone);

        final HorizontalLayout wrap = new HorizontalLayout();
        wrap.setSpacing(true);
        wrap.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        wrap.setCaption("Newsletter");
        final CheckBox newsletter = new CheckBox("Subscribe to newsletter", true);
        wrap.addComponent(newsletter);

        final ComboBox period = new ComboBox();
        period.setTextInputAllowed(false);
        period.addItem("Daily");
        period.addItem("Weekly");
        period.addItem("Monthly");
        period.setNullSelectionAllowed(false);
        period.select("Weekly");
        period.addStyleName("small");
        period.setWidth("10em");
        wrap.addComponent(period);
        form.addComponent(wrap);

        section = new Label("Additional Info");
        section.addStyleName("h4");
        section.addStyleName(FormLayoutDemo.STYLE_COLORED);
        form.addComponent(section);

        final TextField website = new TextField("Website");
        website.setInputPrompt("http://");
        website.setWidth("100%");
        form.addComponent(website);

        final TextArea shortbio = new TextArea("Short Bio");
        shortbio.setValue("Quis aute iure reprehenderit in voluptate velit esse. Cras mattis iudicium purus sit amet fermentum.");
        shortbio.setWidth("100%");
        shortbio.setRows(2);
        form.addComponent(shortbio);

        final RichTextArea bio = new RichTextArea("Bio");
        bio.setWidth("100%");
        bio.setValue("<div><p><span>Integer legentibus erat a ante historiarum dapibus.</span> <span>Vivamus sagittis lacus vel augue laoreet rutrum faucibus.</span> <span>A communi observantia non est recedendum.</span> <span>Morbi fringilla convallis sapien, id pulvinar odio volutpat.</span> <span>Ab illo tempore, ab est sed immemorabili.</span> <span>Quam temere in vitiis, legem sancimus haerentia.</span></p><p><span>Morbi odio eros, volutpat ut pharetra vitae, lobortis sed nibh.</span> <span>Quam diu etiam furor iste tuus nos eludet?</span> <span>Cum sociis natoque penatibus et magnis dis parturient.</span> <span>Quam diu etiam furor iste tuus nos eludet?</span> <span>Tityre, tu patulae recubans sub tegmine fagi  dolor.</span></p><p><span>Curabitur blandit tempus ardua ridiculus sed magna.</span> <span>Phasellus laoreet lorem vel dolor tempus vehicula.</span> <span>Etiam habebis sem dicantur magna mollis euismod.</span> <span>Hi omnes lingua, institutis, legibus inter se differunt.</span></p></div>");
        form.addComponent(bio);

        form.setReadOnly(true);
        bio.setReadOnly(true);

        final Button edit = new Button("Edit", (ClickListener) event -> {
            final boolean readOnly = form.isReadOnly();
            form.removeStyleName(FormLayoutDemo.STYLE_LIGHT);
            event.getButton()
                .removeStyleName("primary");
            if (readOnly) {
                bio.setReadOnly(false);
                form.setReadOnly(false);
                event.getButton()
                    .setCaption("Save");
            }
            else {
                bio.setReadOnly(true);
                form.setReadOnly(true);
                event.getButton()
                    .setCaption("Edit");
            }
        });

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, true, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        form.addComponent(footer);
        footer.addComponent(edit);

        final Label lastModified = new Label("Last modified by you a minute ago");
        lastModified.addStyleName(FormLayoutDemo.STYLE_LIGHT);
        footer.addComponent(lastModified);

        return FluentUI.vertical()
            .add(title, form)
            .spacing()
            .margin()
            .get();
    }

}
