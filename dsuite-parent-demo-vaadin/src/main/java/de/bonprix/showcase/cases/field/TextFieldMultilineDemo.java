package de.bonprix.showcase.cases.field;

import java.util.ArrayList;

import org.vaadin.addons.textfieldmultiline.TextFieldMultiline;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;

/**
 * 
 * @author ppries
 *
 */

@SuppressWarnings("serial")
@SpringViewComponent
public class TextFieldMultilineDemo extends ShowcaseWrapper {

	public TextFieldMultilineDemo() {
		super("STRUCTURE_HIERARCHY", "TEXTFIELDMULTILINEDEMO");
	}

	@Override
	protected Component createLayout() {

		final TextFieldMultiline multiLine = new TextFieldMultiline();
		multiLine.setInputPrompt("Enter ...");
		final Label label = new Label();
		label.setContentMode(ContentMode.HTML);

		multiLine.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				label.setValue("You entered:<br/>" + String.join("<br/>", new ArrayList<>(multiLine.getValue())));
			}
		});

		return FluentUI	.panel()
						.sizeFull()
						.content(FluentUI	.horizontal()
											.spacing()
											.margin()
											.sizeFull()
											.add(multiLine)
											.add(label)
											.get())
						.get();
	}
}
