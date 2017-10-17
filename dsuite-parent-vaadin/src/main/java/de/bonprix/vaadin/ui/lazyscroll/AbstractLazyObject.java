package de.bonprix.vaadin.ui.lazyscroll;

import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.CssLayout;

public abstract class AbstractLazyObject implements LazyObject {

	private static final double PERC_OUTSIDE_VISIVLE_AREA = 0.1;
	private long id;
	private boolean isLoaded = false;
	private CssLayout layout;
	private int position;
	private LazyScrollPanel scrollPanel;

	@SuppressWarnings("serial")
	public AbstractLazyObject(String caption, long id, AbstractLazyScrollPanel scrollPanel) {
		this.id = id;
		this.scrollPanel = scrollPanel;

		this.layout = new CssLayout();
		this.layout.setStyleName("lazyObject empty " + scrollPanel	.getDimension()
																	.name()
																	.toLowerCase());

		if (caption != null)
			this.layout.setCaption(caption);

		this.layout.setHeight(scrollPanel.getContentHeight(caption != null), Unit.PIXELS);
		this.layout.setWidth(scrollPanel.getContentWidth(caption != null), Unit.PIXELS);

		this.layout.addLayoutClickListener(event -> AbstractLazyObject.this.scrollPanel.selectLazyObject(AbstractLazyObject.this));
	}

	@Override
	public long getIdentifier() {
		return this.id;
	}

	@Override
	public boolean isLoaded() {
		return this.isLoaded;
	}

	@Override
	public void setLoaded(boolean value) {
		this.isLoaded = value;
		getBaseLayout().setImmediate(true);
		if (value)
			getBaseLayout().removeStyleName("empty");
	}

	@Override
	public CssLayout getBaseLayout() {
		return this.layout;
	}

	@Override
	public int getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public void highlightSelected(boolean active) {
		if (active) {
			this.layout.addStyleName("active");
		} else {
			this.layout.removeStyleName("active");
		}
	}

	@Override
	public void loadImages(int currentPosition) {
		if (!isLoaded()) {
			int visibleSize = this.scrollPanel	.getDimension()
												.equals(ScrollDimension.Horizontal)
														? Page	.getCurrent()
																.getBrowserWindowWidth()
														: Page	.getCurrent()
																.getBrowserWindowHeight();
			if ((currentPosition - ((int) visibleSize * AbstractLazyObject.PERC_OUTSIDE_VISIVLE_AREA)) <= getPosition()
					&& getPosition() <= currentPosition + visibleSize
							+ ((int) visibleSize * AbstractLazyObject.PERC_OUTSIDE_VISIVLE_AREA)) {
				generateContent();
				setLoaded(true);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LazyObject) {
			return ((LazyObject) obj).getIdentifier() == getIdentifier();
		} else {
			return super.equals(obj);
		}
	}

	@Override
	public int hashCode() {
		return String	.valueOf(getIdentifier())
						.hashCode();
	}
}
