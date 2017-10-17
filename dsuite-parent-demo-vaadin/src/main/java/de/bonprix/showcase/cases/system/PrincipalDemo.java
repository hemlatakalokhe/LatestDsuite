package de.bonprix.showcase.cases.system;

import javax.annotation.Resource;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.I18N;
import de.bonprix.RequestId;
import de.bonprix.base.demo.dto.SecurityInfo;
import de.bonprix.base.demo.service.SecurityDemoService;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;

@SuppressWarnings("serial")
@SpringViewComponent
public class PrincipalDemo extends ShowcaseWrapper {

	@Resource
	private SecurityDemoService securityDemoService;

	public PrincipalDemo() {
		super("SYSTEM", "PRINCIPALDEMO");
	}

	@Override
	protected Component createLayout() {
		final Label localAuthPrincipalLabel = new Label();
		final Label localRootPrincipalLabel = new Label();
		final Label localRequestIdLabel = new Label();
		final Label remoteAuthPrincipalLabel = new Label();
		final Label remoteRootPrincipalLabel = new Label();
		final Label remoteRequestIdLabel = new Label();

		localAuthPrincipalLabel.setCaption(I18N.get("LOCAL_AUTHENTICATED_PRINCIPAL"));
		localRootPrincipalLabel.setCaption(I18N.get("LOCAL_ROOT_PRINCIPAL"));
		localRequestIdLabel.setCaption(I18N.get("LOCAL_REQUEST_ID"));
		remoteAuthPrincipalLabel.setCaption(I18N.get("REMOTE_AUTHENTICATED_PRINCIPAL"));
		remoteRootPrincipalLabel.setCaption(I18N.get("REMOTE_ROOT_PRINCIPAL"));
		remoteRequestIdLabel.setCaption(I18N.get("REMOTE_REQUEST_ID"));

		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		final Button button = FluentUI	.button()
										.captionKey("GET_PRINCIPALS")
										.onClick(event -> {
											final SecurityInfo securityInfo = this.securityDemoService.get();

											localAuthPrincipalLabel.setValue(PrincipalSecurityContext	.getAuthenticatedPrincipal()
																										.getName());
											localRootPrincipalLabel.setValue(PrincipalSecurityContext	.getRootPrincipal()
																										.getName());
											localRequestIdLabel.setValue(RequestId.getRequestId());
											remoteAuthPrincipalLabel.setValue(securityInfo	.getAuthenticatedPrincipal()
																							.getName());
											remoteRootPrincipalLabel.setValue(securityInfo	.getRootPrincipal()
																							.getName());
											remoteRequestIdLabel.setValue(securityInfo.getRequestUuid());

										})
										.get();

		layout.addComponent(button);
		layout.addComponent(localAuthPrincipalLabel);
		layout.addComponent(localRootPrincipalLabel);
		layout.addComponent(localRequestIdLabel);
		layout.addComponent(remoteAuthPrincipalLabel);
		layout.addComponent(remoteRootPrincipalLabel);
		layout.addComponent(remoteRequestIdLabel);
		layout.setWidth(600, Unit.PIXELS);

		return layout;
	}
}
