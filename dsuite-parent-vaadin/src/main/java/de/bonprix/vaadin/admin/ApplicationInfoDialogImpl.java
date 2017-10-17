/**
 *
 */
package de.bonprix.vaadin.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.ApplicationInformation;
import de.bonprix.I18N;
import de.bonprix.service.appinfo.ApplicationInfoDto;
import de.bonprix.service.appinfo.ApplicationInfoService;
import de.bonprix.vaadin.admin.VaadinApplicationInformation.SessionInformation;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.information.InformationLabel;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * Generel dialog to display all information provided by
 * {@link ApplicationInformation} beans.
 *
 * @author cthiel
 * @date 15.11.2016
 *
 */
@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ApplicationInfoDialogImpl extends AbstractBaseDialog implements ApplicationInfoDialog {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInfoDialogImpl.class);

	@Resource
	private List<ApplicationInformation> informationProviders;

	@Resource
	private ApplicationContext applicationContext;

	@Resource
	private VaadinApplicationInformation vaadinAppInfo;

	public ApplicationInfoDialogImpl() {
		super(new DialogConfigurationBuilder().withHeadline("APPLICATION_INFO_DIALOG")
			.withSubline("APPLICATION_INFO_DIALOG_DETAILS")
			.withButton(DialogButton.CLOSE)
			.withCloseOnButton(DialogButton.CLOSE)
			.withHeight(750)
			.withWidth(1200)
			.build());
	}

	@Override
	protected Component layout() {
		final ComponentContainer tabSheet = new TabSheet();

		tabSheet.setSizeFull();
		tabSheet.addComponent(createVaadinLayout());

		for (final Map.Entry<String, ApplicationInfoService> entry : this.applicationContext
			.getBeansOfType(ApplicationInfoService.class)
			.entrySet()) {
			final Component c = createRemoteLayout(entry.getValue());
			c.setCaption(entry.getKey());
			tabSheet.addComponent(c);
		}

		return FluentUI.vertical()
			.margin()
			.add(tabSheet)
			.sizeFull()
			.get();
	}

	private Component createVaadinLayout() {
		final VerticalLayout detailsLayout = new VerticalLayout();
		detailsLayout.setSpacing(true);

		for (final ApplicationInformation appInfo : this.informationProviders) {
			final FormLayout formLayout = FluentUI.form()
				.margin()
				.style(DSuiteTheme.FORMLAYOUT_LIGHT, DSuiteTheme.FORMLAYOUT_NO_PADDING_LAST_ROW)
				.get();

			for (final String key : appInfo.getKeys()) {
				formLayout.addComponent(createLabel(key, appInfo.getValue(key), appInfo.translateKeys()));
			}

			detailsLayout.addComponent(FluentUI.panel()
				.content(FluentUI.vertical()
					.margin()
					.add(formLayout)
					.get())
				.captionKey(appInfo.getI18NCaptionKey())
				.get());
		}

		final BeanItemGrid<SessionInformation> sessionGrid = new BeanItemGrid<>(SessionInformation.class);
		sessionGrid.setSizeFull();
		sessionGrid.addNestedContainerProperty("principal.id");
		sessionGrid.addNestedContainerProperty("principal.name");
		sessionGrid.setColumns(new Object[] { "principal.id", "principal.name", "lastAction" });
		sessionGrid.addAllBeans(this.vaadinAppInfo.getAllSessionInformations());
		sessionGrid.setCaptionKey("ACTIVE_UI_SESSIONS");

		final ScrollablePanel scrollPanel = new ScrollablePanel(detailsLayout);
		scrollPanel.setSizeFull();

		return FluentUI.vertical()
			.add(FluentUI.horizontal()
				.spacing()
				.sizeFull()
				.add(scrollPanel)
				.add(sessionGrid)
				.get(), Alignment.TOP_CENTER)
			.margin()
			.spacing()
			.sizeFull()
			.captionKey("VAADIN_INFORMATION")
			.get();
	}

	private Component createRemoteLayout(final ApplicationInfoService infoService) {
		final VerticalLayout detailsLayout = new VerticalLayout();

		try {
			final List<ApplicationInfoDto> infos = infoService.get();

			final MultivaluedMap<String, ApplicationInfoDto> infoMap = new MultivaluedHashMap<>();
			for (final ApplicationInfoDto info : infos) {
				infoMap.add(info.getGroup(), info);
			}

			for (final String group : infoMap.keySet()) {
				final FormLayout formLayout = new FormLayout();
				formLayout.setMargin(true);

				for (final ApplicationInfoDto dto : infoMap.get(group)) {
					formLayout.addComponent(createLabel(dto.getKey(), dto.getValue(), dto.translateKey()));
				}
				final Panel panel = FluentUI.panel()
					.content(formLayout)
					.captionKey(group)
					.get();
				detailsLayout.addComponent(panel);
			}

			final ScrollablePanel scrollPanel = new ScrollablePanel(detailsLayout);
			scrollPanel.setSizeFull();

			return FluentUI.horizontal()
				.sizeFull()
				.margin()
				.add(scrollPanel)
				.get();
		} catch (final NotFoundException e) {
			ApplicationInfoDialogImpl.LOGGER.error(e.getLocalizedMessage(), e);
			return FluentUI.horizontal()
				.sizeFull()
				.margin()
				.add(new InformationLabel("NO_APPLICATION_INFORMATION_AVAILABLE", InformationLabel.Type.INFO))
				.get();
		} catch (final Exception e) {
			ApplicationInfoDialogImpl.LOGGER.error(e.getLocalizedMessage(), e);
			return FluentUI.horizontal()
				.sizeFull()
				.margin()
				.add(new InformationLabel("COULD_NOT_ACCESS_APPLICATION_INFORMATION", InformationLabel.Type.WARNING))
				.get();
		}
	}

	private Label createLabel(final String key, final Object value, final boolean translateKeys) {
		final Label l = new Label();
		l.setCaption(translateKeys ? I18N.get(key) : key);
		l.setValue(value == null ? "--" : value.toString());

		return l;
	}

}
