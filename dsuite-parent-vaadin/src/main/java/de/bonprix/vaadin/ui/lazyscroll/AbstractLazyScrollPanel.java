package de.bonprix.vaadin.ui.lazyscroll;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.theme.DSuiteTheme;
import de.bonprix.vaadin.ui.lazyscroll.LazyObject.ScrollDimension;

@SuppressWarnings("serial")
public abstract class AbstractLazyScrollPanel extends ScrollablePanel implements LazyScrollPanel {

	public static final int LAYOUT_MARGIN = 0;
	public static final int SCROLL_BAR_SIZE = 20;
	public static final int CAPTION_HEIGHT = 16;
	private ScrollDimension dimension;
	private int totalPosition = 0;
	private List<LazyObject> lazyList = new ArrayList<>();
	private AbstractOrderedLayout layout;

	private Button nextButton = null;
	private Button prevButton = null;
	private LazyObject currentObj;

	public AbstractLazyScrollPanel(ScrollDimension dimension) {
		this.dimension = dimension;

		if (dimension.equals(ScrollDimension.Horizontal)) {
			this.layout = new HorizontalLayout();
		} else {
			this.layout = new VerticalLayout();
		}
		this.layout.setStyleName("pageScroller");

		this.addScrollListener(new ScrollListener() {
			@Override
			public void onScroll(ScrollEvent event) {
				if (AbstractLazyScrollPanel.this.lazyList != null) {
					for (LazyObject lazy : AbstractLazyScrollPanel.this.lazyList) {
						if (AbstractLazyScrollPanel.this.dimension.equals(ScrollDimension.Horizontal)) {
							lazy.loadImages(event.getLeft());
						} else {
							lazy.loadImages(event.getTop());
						}
					}
				}
				AbstractLazyScrollPanel.this.layout.setImmediate(true);
			}
		});
		setContent(this.layout);
	}

	public AbstractOrderedLayout getLayout() {
		return this.layout;
	}

	@Override
	public Button getNextNavigator() {
		if (this.nextButton == null) {
			this.nextButton = new Button(FontBonprix.NEXT);
			this.nextButton.setStyleName(DSuiteTheme.BUTTON_ICON_ONLY);
			this.nextButton.setClickShortcut(KeyCode.ARROW_RIGHT);
			this.nextButton.addClickListener(clickEvent -> selectNext());

		}

		return this.nextButton;
	}

	@Override
	public LazyObject getNext() {
		int currentPosition = -1;
		if (this.currentObj != null)
			currentPosition = this.lazyList.indexOf(this.currentObj);
		if (this.lazyList != null && this.lazyList.size() > currentPosition
				&& currentPosition < (this.lazyList.size() - 1)) {
			return this.lazyList.get(currentPosition + 1);
		} else {
			return null;
		}
	}

	@Override
	public void selectNext() {
		if (getNext() != null) {
			LazyObject next = getNext();
			selectLazyObject(next);
			scrollToObject(next);
		}
	}

	@Override
	public Button getPreviousNavigator() {
		if (this.prevButton == null) {
			this.prevButton = new Button(FontBonprix.PREVIOUS);
			this.prevButton.setStyleName(DSuiteTheme.BUTTON_ICON_ONLY);
			this.prevButton.setClickShortcut(KeyCode.ARROW_LEFT);
			this.prevButton.addClickListener(event -> selectPrevious());
		}
		return this.prevButton;
	}

	@Override
	public LazyObject getPrevious() {
		int currentPosition = -1;
		if (this.currentObj != null)
			currentPosition = this.lazyList.indexOf(this.currentObj);
		if (this.lazyList != null && this.lazyList.size() > currentPosition && currentPosition > 0) {
			return this.lazyList.get(currentPosition - 1);
		} else {
			return null;
		}
	}

	@Override
	public void selectPrevious() {
		if (getPrevious() != null) {
			LazyObject prev = getPrevious();
			selectLazyObject(prev);
			scrollToObject(prev);
		}
	}

	@Override
	public void updateNavigatorImages() {
		if (this.prevButton != null) {
			LazyObject prev = getPrevious();
			if (prev != null) {
				this.prevButton.setEnabled(true);
				this.prevButton.setIcon(FontBonprix.PREVIOUS);
			} else {
				this.prevButton.setEnabled(false);
			}
			this.prevButton.setImmediate(true);
		}

		if (this.nextButton != null) {
			LazyObject next = getNext();
			if (next != null) {
				this.nextButton.setEnabled(true);
				this.nextButton.setIcon(FontBonprix.NEXT);
			} else {
				this.nextButton.setEnabled(false);
			}
			this.nextButton.setImmediate(true);
		}

	}

	@Override
	public void selectLazyObject(LazyObject obj) {
		if (this.currentObj != null)
			this.currentObj.highlightSelected(false);
		if (obj != null) {
			this.currentObj = obj;
			this.currentObj.highlightSelected(true);

			updateNavigatorImages();
		}
		AbstractLazyScrollPanel.this.fireEvent(new LazyObjectSelectedEvent(this.layout, obj));
	}

	@Override
	public void resetScrollPanel() {
		this.lazyList.clear();
		this.totalPosition = 0;
		this.currentObj = null;
		this.layout.removeAllComponents();
	}

	@Override
	public void addLazyObject(LazyObject obj) {
		if (this.dimension.equals(ScrollDimension.Horizontal)) {
			this.totalPosition += getContentWidth(false) + AbstractLazyScrollPanel.LAYOUT_MARGIN;
		} else {
			this.totalPosition += getContentHeight(false) + AbstractLazyScrollPanel.LAYOUT_MARGIN;
		}
		obj.setPosition(this.totalPosition);
		obj.loadImages(0);
		this.lazyList.add(obj);
		this.layout.addComponent(obj.getBaseLayout());
	}

	@Override
	public ScrollDimension getDimension() {
		return this.dimension;
	}

	@Override
	public void addLazyObjectSelectedListener(LazyObjectSelectedListener listener) {
		addListener(LazyObjectSelectedEvent.class, listener,
					LazyObjectSelectedListener.LAZY_SCROLL_PANEL_OBJECT_SELECTED_METHOD);
	}

	protected LazyObject getObject(final int index) {
		return this.lazyList.get((int) index);
	}
}
