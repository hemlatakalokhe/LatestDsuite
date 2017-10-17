package de.bonprix.vaadin.layout;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import de.bonprix.GeneralApplicationInformation;
import de.bonprix.I18N;
import de.bonprix.information.ClientProvider;
import de.bonprix.security.PrincipalProvider;
import de.bonprix.user.dto.Principal;
import de.bonprix.vaadin.fluentui.FluentUI;

/**
 * The footer is the status bar at the bottom of every bonprix vaadin
 * application. It can befilled with various vaadin widgets.
 *
 * @author cthiel
 *
 */
@SuppressWarnings("serial")
@UIScope
public class BpFooter extends CustomComponent {

	private transient PrincipalProvider principalProvider;

	private transient ClientProvider clientProvider;

	private final Label userLabel = FluentUI.label()
		.style("footerUser")
		.get();
	private String userLabelLoginExtension = "";

	private final Label buildDetails = FluentUI.label()
		.style("footerDetails")
		.get();
	private final Label environmentDetails = FluentUI.label()
		.width(100, Unit.PERCENTAGE)
		.get();
	private static final String UI_DSUITE_PROJECT_VERSION_FORMAT = "\\d{4}-\\d{2}-\\d{2}-\\d{2}";

	public BpFooter(GeneralApplicationInformation generalApplicationInformation, PrincipalProvider principalProvider,
			ClientProvider clientProvider) {
		this.principalProvider = principalProvider;
		this.clientProvider = clientProvider;

		setWidth("100%");
		setHeight("20px");
		setStyleName("footerPanel");

		refreshUserLabelValue();

		String buildDetsailsCaption = getBuildDetailsCaption(generalApplicationInformation);
		this.buildDetails.setCaption(buildDetsailsCaption);

		setCompositionRoot(FluentUI.horizontal()
			.add(this.userLabel, 1)
			.add(this.environmentDetails, 1)
			.add(FluentUI.css()
				.sizeUndefined()
				.add(this.buildDetails)
				.get(), 1, Alignment.MIDDLE_RIGHT)
			.style("footerPanel")
			.height("20px")
			.width("100%")
			.get());
	}

	private String getBuildDetailsCaption(GeneralApplicationInformation generalApplicationInformation) {
		/**
		 * in case of showcase project framework and dsuite project versions are
		 * the same - show timestamp instead of the dsuite project version; / in
		 * case of ui project dsuite project version is the same as jenkinsbuild
		 * version
		 **/
		return generalApplicationInformation.getJenkinsBuildVersion()
			.matches(BpFooter.UI_DSUITE_PROJECT_VERSION_FORMAT)
					? String.format("Version: %s (%s)", generalApplicationInformation.getJenkinsBuildVersion(),
									generalApplicationInformation.getFrameworkVersion())
					: String.format("Version: %s %s", generalApplicationInformation.getFrameworkVersion(),
									generalApplicationInformation.getBuildTime());
	}

	private void refreshUserLabelValue() {
		final Principal principal = this.principalProvider.getAuthenticatedPrincipal();
		this.userLabel.setValue(I18N.get(	"LOGGED_IN_AS_WITH_CLIENT", principal.getFullname(), principal.getName(),
											this.clientProvider.getClient(principal.getClientId())
												.getName())
				+ this.userLabelLoginExtension);
	}

	public void setUserLabelLoginExtension(final String loginExtension) {
		this.userLabelLoginExtension = loginExtension;
		refreshUserLabelValue();
	}

	public void setBuildDetailsText(final String text) {
		this.buildDetails.setValue(text);
	}
}
