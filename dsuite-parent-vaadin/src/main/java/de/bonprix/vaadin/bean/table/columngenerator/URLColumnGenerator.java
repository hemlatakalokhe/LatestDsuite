package de.bonprix.vaadin.bean.table.columngenerator;

import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import de.bonprix.vaadin.FontBonprix;

/**
 * The {@link URLColumnGenerator} displays the given property as URL to an external source and also shortens the visible URL text to the given
 * <code>urlLength</code> (or the default value <code>DEFAULT_URL_LENGTH</code>.
 * 
 * @author cthiel
 * 
 */
public class URLColumnGenerator implements ColumnGenerator {

    private static final long serialVersionUID = 1397492728838127133L;

    public static final int DEFAULT_URL_LENGTH = 40;

    private final int urlLength;

    /**
     * Creates an url column generator with the default url length.
     */
    public URLColumnGenerator() {
        this(URLColumnGenerator.DEFAULT_URL_LENGTH);
    }

    /**
     * Creates an url column generator with the given url length.
     * 
     * @param urlLength
     */
    public URLColumnGenerator(final int urlLength) {
        super();
        this.urlLength = urlLength;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object generateCell(final Table source, final Object itemId, final Object columnId) {
        final Property<String> p = source.getContainerDataSource()
            .getContainerProperty(itemId, columnId);

        if (p.getValue() == null) {
            return "";
        }

        final String url = p.getValue();
        String urlText = p.getValue();

        if (urlText.length() > this.urlLength) {
            urlText = urlText.substring(0, this.urlLength) + "...";
        }

        final Link link = new Link(urlText, new ExternalResource(url));
        link.setIcon(FontBonprix.LINK);
        link.setTargetName("newTab");

        return link;
    }
}
