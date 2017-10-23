package de.bonprix.showcase.cases.panel;

import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;

@SuppressWarnings("serial")
@SpringViewComponent
public class ScrollablePanelDemo extends ShowcaseWrapper {

    private Label eventLabel;

    public ScrollablePanelDemo() {
        super("PANEL", "SCROLLABLEPANELDEMO");
    }

    @Override
    protected com.vaadin.ui.Component createLayout() {
        final ScrollablePanel scrollPanel = new ScrollablePanel();
        this.eventLabel = new Label("Dummy Text");
        scrollPanel.addScrollListener(event -> ScrollablePanelDemo.this.eventLabel
            .setValue("event: " + (event.getScrollData() != null ? event.getScrollData() : " its empty")));
        scrollPanel.setWidth("50%");
        scrollPanel.setHeight("400px");
        scrollPanel.setStyleName("scroll-box");

        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        for (int x = 0; x < 100; x++) {
            final CssLayout box = new CssLayout();
            box.setCaption("box: " + x);
            box.setWidth("100%");
            box.setHeight("75px");
            box.setStyleName("simple-box");
            layout.addComponent(box);
        }
        scrollPanel.setContent(layout);
        scrollPanel.setScrollTop(500);

        return FluentUI.vertical()
            .margin()
            .spacing()
            .add(this.eventLabel)
            .add(scrollPanel)
            .get();

    }
}
