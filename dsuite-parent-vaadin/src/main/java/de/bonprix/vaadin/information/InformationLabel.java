package de.bonprix.vaadin.information;

import com.vaadin.server.FontIcon;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.I18N;
import de.bonprix.vaadin.FontBonprix;

/**
 * Creates a simple label with an icon and a text beneath. THis label should be
 * used to display additional informations, warnings or errors within the page
 * (not ad temporary popup or dialog window). Most times it will be a
 * description of the process inside a dialog window or warning messages if the
 * action the user wants to execute could cause some problems.<br/>
 * <br/>
 * The label can have three types which differ in the colors and the displayed
 * icon:<br/>
 * <ul>
 * <li><b>INFO</b>: simple useful additional informations to the current process
 * or screen</li>
 * <li><b>WARNING</b>: e.g. information that something in the process COULD
 * cause problems (if the user does not know what he is doing)</li>
 * <li><b>ERROR</b>: Errors in user interaction (wrong input, illegal actions or
 * other problems)</li>
 * <ul>
 * <br/>
 * The component has a default width of 100%.
 *
 * @author cthiel
 *
 */
@SuppressWarnings("serial")
public class InformationLabel extends VerticalLayout {

	public static enum Type {
		INFO(FontBonprix.INFO, "information-label-info"), WARNING(FontBonprix.ERROR,
				"information-label-warning"), ERROR(FontBonprix.CANCELLED, "information-label-error");

		private final FontIcon icon;
		private final String classname; // css stylename

		private Type(final FontIcon icon, final String classname) {
			this.icon = icon;
			this.classname = classname;
		}

		public FontIcon getIcon() {
			return this.icon;
		}

		public String getClassname() {
			return this.classname;
		}
	}

	private final GridLayout mainLayout = new GridLayout(2, 1);

	private final Label icon;
	private final Label messageLabel;

	public InformationLabel() {
		this("");
	}

	public InformationLabel(final String messageKey, Object... objects) {
		this(Type.INFO, messageKey, objects);
	}

	public InformationLabel(final Type type, final String messageKey, Object... objects) {
		this.messageLabel = new Label();

		this.icon = new Label("", ContentMode.HTML);
		this.icon.setHeight(40, Unit.PIXELS);
		this.icon.setWidth(45, Unit.PIXELS);

		this.mainLayout.addComponent(this.icon, 0, 0);
		this.mainLayout.addComponent(this.messageLabel, 1, 0);
		this.mainLayout.setColumnExpandRatio(1, 1);
		this.mainLayout.setSizeFull();

		this.mainLayout.setComponentAlignment(this.icon, Alignment.MIDDLE_CENTER);
		this.mainLayout.setComponentAlignment(this.messageLabel, Alignment.MIDDLE_CENTER);

		setWidth(100, Unit.PERCENTAGE);

		addComponent(this.mainLayout);

		setMessageKey(messageKey, objects);
		setType(type);
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 */
	public void setMessageKey(final String messageKey, Object... objects) {
		this.messageLabel.setValue(I18N.get(messageKey, objects));
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 */
	public void setType(final Type type) {
		this.icon.setValue(type	.getIcon()
								.getHtml());
		setStyleName(type.getClassname());
	}

	/**
	 * sets the contentmode of the label for example html (default is plain)
	 * 
	 * @param contentMode
	 */
	public void setContentMode(ContentMode contentMode) {
		this.messageLabel.setContentMode(contentMode);
	}

}
