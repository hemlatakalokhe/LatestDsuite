package de.bonprix.modules.theme.parts.textfield;

import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringView;
import de.bonprix.VaadinUI;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = TextFieldViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class TextFieldViewImpl extends AbstractMvpView<TextFieldPresenter> implements TextFieldView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "TEXT";

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI.vertical()
			.margin()
			.add(FluentUI.label()
				.value("TextField")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.textField()
					.captionKey("NORMAL")
					.inputPromptKey("TYPE_HERE")
					.get())
				.add(FluentUI.textField()
					.captionKey("ERROR")
					.inputPromptKey("SOMETHING_IS_WRONG")
					.componentError(new UserError("Fix it, now!"))
					.get())
				.add(FluentUI.textField()
					.captionKey("READ_ONLY")
					.value("Some value")
					.readOnly()
					.get())
				.add(FluentUI.textField()
					.captionKey("ONLY_UPPERCASE")
					.upperCase()
					.value("uppercase")
					.get())
				.add(FluentUI.passwordField()
					.captionKey("PASSWORDFIELD")
					.inputPromptKey("SECRET_WORDS")
					.get())
				.get())
			.add(FluentUI.label()
				.value("TextArea")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.horizontal()
				.spacing()
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.textArea()
					.captionKey("NORMAL")
					.inputPromptKey("WRITE_YOUR_COMMENT")
					.get())
				.add(FluentUI.textArea()
					.captionKey("ERROR")
					.inputPromptKey("SOMETHING_IS_WRONG")
					.componentError(new UserError("Fix it, now!"))
					.get())
				.add(FluentUI.textArea()
					.captionKey("READ_ONLY")
					.value("Field value, spanning multiple lines of text")
					.readOnly()
					.get())
				.add(FluentUI.richTextArea()
					.captionKey("RICHTEXT")
					.value("<b>Some</b> <i>rich</i> content")
					.readOnly()
					.get())
				.get())
			.get());
	}

}
