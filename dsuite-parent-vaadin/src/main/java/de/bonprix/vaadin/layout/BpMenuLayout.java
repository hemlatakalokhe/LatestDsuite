package de.bonprix.vaadin.layout;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.vaadin.fluentui.FluentUI;

/**
 * The bonprix "main layout" with a menu on top and a footer at the bottom. The
 * content inside is scrollable.
 */
@UIScope
public class BpMenuLayout extends VerticalLayout implements BpApplicationLayout {
	private static final long serialVersionUID = -1785464973401661421L;

	@Autowired
	private BpMenu bpMenu;

	private CssLayout content;

	@Autowired
	private BpFooter bpFooter;

	@Autowired
	private BpScreenshot bpScreenshot;

	@Override
	public Layout getMainContent() {
		return this.content;
	}

	@PostConstruct
	public void init() {
		this.content = FluentUI.css()
			.primaryStyle("valo-content")
			.style("v-scrollable")
			.sizeFull()
			.width(100, Unit.PERCENTAGE)
			.get();

		addComponents(this.bpMenu, this.content, this.bpFooter, this.bpScreenshot);
		// makes the content maximal sizefull
		setExpandRatio(this.content, 1);

		setSpacing(false);
		setSizeFull();

		// Screenshot function with ShortKey Ctrl+Shift+S
		addShortcutListener(new ShortcutListener("Screenshot (Ctrl+Shift+S)", KeyCode.S,
				new int[] { ModifierKey.SHIFT, ModifierKey.CTRL }) {

			private static final long serialVersionUID = -2610912325735312032L;

			@Override
			public void handleAction(Object sender, Object target) {
				BpMenuLayout.this.bpScreenshot.takeScreenshot();

			}
		});

	}

	@Override
	public void refreshMenu(String viewName) {
		this.bpMenu.refreshMenu(viewName);
	}

}
