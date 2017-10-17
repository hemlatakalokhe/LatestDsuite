package de.bonprix.vaadin.shortcut;

import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import com.vaadin.ui.Component;

import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.provider.UiNavigationProvider;

@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ShortcutInfoDialogImpl extends AbstractBaseDialog implements ShortcutInfoDialog {

	private static final long serialVersionUID = 9039627470310821013L;

	@Autowired
	private UiNavigationProvider uiNavigationProvider;

	@Resource
	private ApplicationContext applicationContext;

	public ShortcutInfoDialogImpl() {
		super(new DialogConfigurationBuilder().withHeadline("SHORTCUT_DIALOG")
			.withSubline("SHORTCUT_DIALOG_DETAILS")
			.withButton(DialogButton.CLOSE)
			.withCloseOnButton(DialogButton.CLOSE)
			.build());
	}

	@Override
	protected Component layout() {

		BeanItemGrid<Shortcut> grid = new BeanItemGrid<>(Shortcut.class);
		ShortcutHandler shortcutsHandler = (ShortcutHandler) this.applicationContext.getBean(ShortcutHandler.class);

		grid.addAllBeans(shortcutsHandler.getShortcuts()
			.stream()
			.filter(shortcut -> shortcut.getViewName()
				.equals(this.uiNavigationProvider.getCurrentView()))
			.collect(Collectors.toList()));
		grid.removeColumn("action");

		return FluentUI.vertical()
			.margin()
			.add(grid)
			.get();
	}

}
