package de.bonprix.vaadin.ui.lazyscroll;

import de.bonprix.vaadin.ui.lazyscroll.LazyObject.ScrollDimension;

@SuppressWarnings("serial")
public class LazyVerticalScrollPanel extends AbstractLazyScrollPanel {

	private int objectHeight, componentHeight, componentWidth;

	public LazyVerticalScrollPanel(int componentHeight, int objectHeight, int componentWidth) {
		super(ScrollDimension.Vertical);

		this.objectHeight = objectHeight;
		this.componentHeight = componentHeight;
		this.componentWidth = componentWidth;

		setWidth(componentWidth, Unit.PIXELS);
		setHeight(componentHeight, Unit.PIXELS);

	}

	@Override
	public Integer getContentHeight(boolean caption) {
		return this.objectHeight - (caption ? CAPTION_HEIGHT : 0);
	}

	@Override
	public Integer getContentWidth(boolean caption) {
		return this.componentWidth - SCROLL_BAR_SIZE - LAYOUT_MARGIN;
	}

	@Override
	public void scrollToObject(LazyObject object) {
		setScrollTop(object.getPosition() - this.componentHeight / 2 - this.objectHeight);
	}

}
