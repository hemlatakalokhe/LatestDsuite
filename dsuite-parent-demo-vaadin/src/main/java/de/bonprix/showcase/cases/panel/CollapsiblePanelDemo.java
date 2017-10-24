/**
 *
 */
package de.bonprix.showcase.cases.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addons.collapsiblepanel.CollapsiblePanel;
import org.vaadin.addons.collapsiblepanel.CollapsiblePanel.CollapseEvent;
import org.vaadin.addons.collapsiblepanel.CollapsiblePanel.CollapseExpandListener;
import org.vaadin.addons.collapsiblepanel.CollapsiblePanel.ExpandEvent;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringViewComponent
public class CollapsiblePanelDemo extends ShowcaseWrapper {

    private static final long serialVersionUID = 1L;

    @Autowired
    private UiNotificationProvider notificationProvider;

    public CollapsiblePanelDemo() {
        super("PANEL", "COLLAPSIBLEPANELDEMO");
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    protected com.vaadin.ui.Component createLayout() {

        final VerticalLayout layout = FluentUI.vertical()
            .margin()
            .spacing()
            .add(new Label("Lorem ipsum dolor sit amet, consetetur sadipscing elitr."))
            .get();

        // create the content itself
        final Panel panel = FluentUI.panel()
            .sizeUndefined()
            .captionKey("PANEL")
            .style(DSuiteTheme.PANEL_INVERTED)
            .content(layout)
            .get();

        // create the extension that wraps around the panel and makes it
        // collapsible
        final CollapsiblePanel cp = new CollapsiblePanel(panel);

        cp.addCollapseExpandListener(new CollapseExpandListener() {
            @Override
            public void expand(final ExpandEvent event) {
                CollapsiblePanelDemo.this.notificationProvider.showInfoNotification("Component expanded");
            }

            @Override
            public void collapse(final CollapseEvent event) {
                CollapsiblePanelDemo.this.notificationProvider.showInfoNotification("Component collapsed");
            }
        });

        cp.setCollapsed(true);

        return FluentUI.vertical()
            .margin()
            .add(panel)
            .get();
    }
}
