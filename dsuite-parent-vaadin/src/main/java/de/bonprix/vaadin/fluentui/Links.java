package de.bonprix.vaadin.fluentui;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

import de.bonprix.I18N;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class Links<CONFIG extends Links<?>> extends Components<Link, CONFIG> {

	protected Links(final Link component) {
		super(component);
	}

	public enum Target {
		NEW_TAB("_blank"), SELF("_self");

		private final String targetName;

		private Target(String targetName) {
			this.targetName = targetName;
		}

		public String getTargetName() {
			return this.targetName;
		}
	}

	@SuppressWarnings("unchecked")
	public CONFIG captionKey(String captionKey, Object... objects) {
		get().setCaption(I18N.get(captionKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG target(Target target) {
		get().setTargetName(target.getTargetName());
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG url(String url) {
		get().setResource(new ExternalResource(url));
		return (CONFIG) this;
	}

}
