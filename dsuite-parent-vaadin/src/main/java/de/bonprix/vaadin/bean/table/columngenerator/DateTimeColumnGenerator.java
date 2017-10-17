package de.bonprix.vaadin.bean.table.columngenerator;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
/**
 * Implemenation of a {@link ColumnGenerator} for generating a date time. 
 */
public class DateTimeColumnGenerator implements ColumnGenerator {
	private static final long serialVersionUID = 1L;

	public enum Format {
		DATE, DATETIME;
	}

	private final Format format;

	public DateTimeColumnGenerator() {
		this(Format.DATETIME);
	}

	public DateTimeColumnGenerator(final Format format) {
		super();
		this.format = format;
	}

	@Override
	public Object generateCell(final Table source, final Object itemId, final Object columnId) {
		@SuppressWarnings("unchecked")
		final Property<TemporalAccessor> property = source.getContainerDataSource()
			.getContainerProperty(itemId, columnId);

		if (property.getValue() == null) {
			return null;
		}

		if (this.format == Format.DATE) {
			return DateTimeFormatter.ISO_LOCAL_DATE.format(property.getValue());
		} else {
			return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(property.getValue());
		}

	}
}
