package de.bonprix.showcase.cases.system;

import javax.annotation.Resource;

import com.vaadin.ui.Component;

import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNavigationProvider;

@SuppressWarnings("serial")
@SpringViewComponent
public class NavigatorDemo extends ShowcaseWrapper {

    @Resource
    private UiNavigationProvider uiNavigationProvider;

    public NavigatorDemo() {
        super("SYSTEM", "NAVIGATORDEMO");
    }

    @Override
    protected Component createLayout() {

        return FluentUI.horizontal()
            .margin()
            .add(FluentUI.button()
                .captionKey("NAVIGATE_TO_THEMEVIEW")
                .onClick(event -> this.uiNavigationProvider.navigateTo(ThemeViewImpl.VIEW_NAME))
                .get())
            .get();
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
