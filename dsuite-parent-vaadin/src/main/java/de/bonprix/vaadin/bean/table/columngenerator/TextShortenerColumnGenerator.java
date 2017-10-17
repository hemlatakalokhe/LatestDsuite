package de.bonprix.vaadin.bean.table.columngenerator;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

/**
 * Generates a label field which only shows the defined number of characters of the data to display. If the content
 * string is longer than the specified length, the string will be shortened and only be visible in the description when
 * hovering the text with the mouse.
 * 
 * @author cthiel
 * 
 */
public class TextShortenerColumnGenerator implements ColumnGenerator {
	private static final long serialVersionUID = -859195405263799283L;

	private final int numChars;

	public TextShortenerColumnGenerator(int numChars) {
		super();
		this.numChars = numChars;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		Property<String> p = source.getContainerDataSource().getContainerProperty(itemId, columnId);
		
		String content = p.getValue() == null ? "" : p.getValue();

		if (content.length() > numChars) {
			String contentDisplay = content.substring(0, numChars) + "...";
			Label l = new Label(contentDisplay);
			l.setDescription(content);
			return l;
		} else {
			return content;
		}
	}
}
