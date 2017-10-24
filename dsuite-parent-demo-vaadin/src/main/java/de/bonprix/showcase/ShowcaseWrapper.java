package de.bonprix.showcase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bonprix.I18N;
import de.bonprix.vaadin.fluentui.FluentUI;

public abstract class ShowcaseWrapper extends CustomComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowcaseWrapper.class);

    private static final long serialVersionUID = -3423155483250229975L;

    private final String parentTitle;
    private final String title;
    private final String description;

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public ShowcaseWrapper(final String parentKey, final String captionKey) {
        this.parentTitle = I18N.get(parentKey);
        this.title = I18N.get(captionKey + "_TITLE");
        this.description = I18N.get(captionKey + "_DESCRIPTION");

        final Button buttons = new Button(I18N.get("SHOW_SOURCECODE"));
        buttons.setDescription(I18N.get("SHOW_SOURCECODE_TOOLTIP"));
        buttons.addClickListener(event -> showSourceCode());

        final Component layout = createLayout();

        final Panel panel = new Panel();
        panel.setSizeFull();
        panel.setContent(layout);

        final Label captionLabel = new Label(getTitle());
        captionLabel.setStyleName("showcase-caption");

        final Label descriptionLabel = new Label(this.description, ContentMode.HTML);
        descriptionLabel.setStyleName("showcase-description");

        final VerticalLayout mainLayout = FluentUI.vertical()
            .spacing()
            .add(captionLabel)
            .add(descriptionLabel)
            .add(buttons)
            .add(panel)
            .get();
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(panel, 1);

        setCompositionRoot(mainLayout);
    }

    protected abstract Component createLayout();

    public void showSourceCode() {
        String code = null;

        try {
            // Find URL of selected Class (.java)
            final URL url = this.getClass()
                .getResource(getClass().getSimpleName() + ".java");
            // read out complete java file
            final byte[] encoded = java.nio.file.Files.readAllBytes(Paths.get(new File(url.getFile()).toString()));
            code = new String(encoded, Charset.defaultCharset());
        }
        catch (final IOException e) {
            ShowcaseWrapper.LOGGER.error("Could not read code for showcase wrapper " + getClass(), e);
            code = "code is not readable :(";
        }

        final Window codeWindow = new Window("Code",
                new Label(String.format("<html><body><pre class=\"prettyprint\">%s</pre></body></html>", code), ContentMode.HTML));
        codeWindow.setWidth("80%");
        codeWindow.setHeight("80%");
        codeWindow.center();
        UI.getCurrent()
            .addWindow(codeWindow);

        JavaScript
            .eval("if (document.getElementById('scriptPrettify')) { prettyPrint(); } else { var s = document.createElement('script'); s.setAttribute('type', 'text/javascript'); s.setAttribute('id', 'scriptPrettify'); s.setAttribute('src', 'https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js'); document.head.appendChild(s); }");
    }

    public String getParentTitle() {
        return this.parentTitle;
    }

    public String getTitle() {
        return this.title;
    }

}
